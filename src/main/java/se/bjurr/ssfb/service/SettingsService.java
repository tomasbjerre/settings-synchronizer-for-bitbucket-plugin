package se.bjurr.ssfb.service;

import static se.bjurr.ssfb.settings.SsfbSettings.ssfbSettingsBuilder;

import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;
import com.atlassian.bitbucket.util.PageRequestImpl;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.google.common.base.Optional;
import com.google.gson.Gson;

import se.bjurr.ssfb.settings.SsfbRepoSettings;
import se.bjurr.ssfb.settings.SsfbSettings;

public class SettingsService {

 public static final String STORAGE_KEY = "se.bjurr.ssfb.settings-synchronizer-for-bitbucket";
 private static Gson gson = new Gson();
 private final PluginSettings pluginSettings;
 private final RepositoryService repositoryService;
 private final TransactionTemplate transactionTemplate;

 public SettingsService(PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate,
   RepositoryService repositoryService) {
  this.pluginSettings = pluginSettingsFactory.createGlobalSettings();
  this.transactionTemplate = transactionTemplate;
  this.repositoryService = repositoryService;
 }

 public SsfbSettings getSsfbSettings() {
  return inSynchronizedTransaction(new TransactionCallback<SsfbSettings>() {
   @Override
   public SsfbSettings doInTransaction() {
    return doGetSsfbSettings();
   }
  });
 }

 public Optional<SsfbRepoSettings> getSsfbSettings(String projectKey, String repoSlug) {
  return inSynchronizedTransaction(new TransactionCallback<Optional<SsfbRepoSettings>>() {
   @Override
   public Optional<SsfbRepoSettings> doInTransaction() {
    return doGetSsfbSettings()//
      .findRepoSettings(projectKey, repoSlug);
   }
  });
 }

 public void setSsfbSettings(SsfbRepoSettings ssfbRepoSettings) {
  inSynchronizedTransaction(new TransactionCallback<Void>() {
   @Override
   public Void doInTransaction() {
    SsfbSettings ssfbSettings = doGetSsfbSettings();
    Page<Repository> repositories = SettingsService.this.repositoryService.findAll(inOnePage());
    for (Repository repository : repositories.getValues()) {
     String projectKey = repository.getProject().getKey();
     String repoSlug = repository.getSlug();
     ssfbSettings.setRepoSettings(projectKey, repoSlug, ssfbRepoSettings);
    }
    doSetSsfbSettings(ssfbSettings);
    return null;
   }
  });
 }

 public void setSsfbSettings(String projectKey, String repoSlug, SsfbRepoSettings ssfbRepoSettings) {
  inSynchronizedTransaction(new TransactionCallback<Void>() {
   @Override
   public Void doInTransaction() {
    SsfbSettings ssfbSettings = doGetSsfbSettings();
    ssfbSettings.setRepoSettings(projectKey, repoSlug, ssfbRepoSettings);
    doSetSsfbSettings(ssfbSettings);
    return null;
   }
  });
 }

 private SsfbSettings doGetSsfbSettings() {
  Object storedSettings = this.pluginSettings.get(STORAGE_KEY);
  if (storedSettings == null) {
   return ssfbSettingsBuilder().build();
  }
  return gson.fromJson(storedSettings.toString(), SsfbSettings.class);
 }

 private void doSetSsfbSettings(SsfbSettings ssfbSettings) {
  String data = gson.toJson(ssfbSettings);
  this.pluginSettings.put(STORAGE_KEY, data);
 }

 private PageRequest inOnePage() {
  return new PageRequestImpl(0, 1000000);
 }

 private synchronized <T> T inSynchronizedTransaction(TransactionCallback<T> transactionCallback) {
  return this.transactionTemplate.execute(transactionCallback);
 }
}
