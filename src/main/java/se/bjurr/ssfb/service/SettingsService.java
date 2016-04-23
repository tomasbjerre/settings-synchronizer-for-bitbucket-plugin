package se.bjurr.ssfb.service;

import static se.bjurr.ssfb.settings.SsfbSettings.ssfbSettingsBuilder;
import se.bjurr.ssfb.settings.SsfbRepoSettings;
import se.bjurr.ssfb.settings.SsfbSettings;

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
  return transactionTemplate.execute(new TransactionCallback<SsfbSettings>() {
   @Override
   public SsfbSettings doInTransaction() {
    return doGetSsfbSettings();
   }
  });
 }

 private SsfbSettings doGetSsfbSettings() {
  Object storedSettings = pluginSettings.get(STORAGE_KEY);
  if (storedSettings == null) {
   return ssfbSettingsBuilder().build();
  }
  return gson.fromJson(storedSettings.toString(), SsfbSettings.class);
 }

 public void setSsfbSettings(SsfbSettings ssfbSettings) {
  transactionTemplate.execute(new TransactionCallback<Void>() {
   @Override
   public Void doInTransaction() {
    doSetSsfbSettings(ssfbSettings);
    return null;
   }
  });
 }

 private void doSetSsfbSettings(SsfbSettings ssfbSettings) {
  String data = gson.toJson(ssfbSettings);
  pluginSettings.put(STORAGE_KEY, data);
 }

 public void setSsfbSettings(String projectKey, String repoSlug, SsfbRepoSettings ssfbRepoSettings) {
  SsfbSettings ssfbSettings = getSsfbSettings();
  ssfbSettings.setRepoSettings(projectKey, repoSlug, ssfbRepoSettings);
  setSsfbSettings(ssfbSettings);
 }

 public Optional<SsfbRepoSettings> getSsfbSettings(String projectKey, String repoSlug) {
  return getSsfbSettings()//
    .findRepoSettings(projectKey, repoSlug);
 }

}
