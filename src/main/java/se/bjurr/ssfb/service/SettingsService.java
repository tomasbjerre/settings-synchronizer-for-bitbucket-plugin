package se.bjurr.ssfb.service;

import static se.bjurr.ssfb.settings.SsfbSettings.ssfbSettingsBuilder;
import se.bjurr.ssfb.settings.SsfbRepoSettings;
import se.bjurr.ssfb.settings.SsfbSettings;
import se.bjurr.ssfb.settings.SyncEvery;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.google.common.base.Optional;
import com.google.gson.Gson;

public class SettingsService {

 public static final String STORAGE_KEY = "se.bjurr.ssfb.settings-synchronizer-for-bitbucket";
 private static Gson gson = new Gson();
 private final PluginSettings pluginSettings;
 private final TransactionTemplate transactionTemplate;

 public SettingsService(PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate) {
  this.pluginSettings = pluginSettingsFactory.createGlobalSettings();
  this.transactionTemplate = transactionTemplate;
 }

 public SsfbSettings getSsfbSettings() {
  return inSynchronizedTransaction(new TransactionCallback<SsfbSettings>() {
   @Override
   public SsfbSettings doInTransaction() {
    return doGetSsfbSettings();
   }
  });
 }

 public void setSsfbSettings(String startTime, SyncEvery syncEvery) {
  inSynchronizedTransaction(new TransactionCallback<Void>() {
   @Override
   public Void doInTransaction() {
    SsfbSettings oldSettings = doGetSsfbSettings();
    SsfbSettings newSettings = ssfbSettingsBuilder(oldSettings)//
      .setStartTime(startTime)//
      .setSyncEvery(syncEvery)//
      .build();
    doSetSsfbSettings(newSettings);
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

 public Optional<SsfbRepoSettings> getSsfbSettings(String projectKey, String repoSlug) {
  return inSynchronizedTransaction(new TransactionCallback<Optional<SsfbRepoSettings>>() {
   @Override
   public Optional<SsfbRepoSettings> doInTransaction() {
    return doGetSsfbSettings()//
      .findRepoSettings(projectKey, repoSlug);
   }
  });
 }

 private synchronized <T> T inSynchronizedTransaction(TransactionCallback<T> transactionCallback) {
  return transactionTemplate.execute(transactionCallback);
 }

 private SsfbSettings doGetSsfbSettings() {
  Object storedSettings = pluginSettings.get(STORAGE_KEY);
  if (storedSettings == null) {
   return ssfbSettingsBuilder().build();
  }
  return gson.fromJson(storedSettings.toString(), SsfbSettings.class);
 }

 private void doSetSsfbSettings(SsfbSettings ssfbSettings) {
  String data = gson.toJson(ssfbSettings);
  pluginSettings.put(STORAGE_KEY, data);
 }
}
