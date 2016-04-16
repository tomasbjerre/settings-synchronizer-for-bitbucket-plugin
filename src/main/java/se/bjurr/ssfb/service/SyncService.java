package se.bjurr.ssfb.service;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import se.bjurr.ssfb.settings.SsfbRepoSettings;

import com.atlassian.bitbucket.hook.repository.RepositoryHook;
import com.atlassian.bitbucket.hook.repository.RepositoryHookService;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.bitbucket.setting.Settings;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;
import com.atlassian.bitbucket.util.PageRequestImpl;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.google.common.base.Optional;

public class SyncService {

 private final RepositoryService repositoryService;
 private final RepositoryHookService repositoryHookService;
 private final SettingsService settingsService;

 public SyncService(RepositoryService repositoryService, PluginSettingsFactory pluginSettingsFactory,
   RepositoryHookService repositoryHookService, SettingsService settingsService) {
  this.repositoryService = repositoryService;
  this.repositoryHookService = repositoryHookService;
  this.settingsService = settingsService;
 }

 public void performSyncOnAllRepos() {
  Page<Repository> repositories = repositoryService.findAll(inOnePage());
  for (Repository repository : repositories.getValues()) {
   performSync(repository.getProject().getKey(), repository.getSlug());
  }
 }

 public void performSync(String projectKey, String repoSlug) {
  Optional<SsfbRepoSettings> ssfbRepoSettingsOpt = settingsService.getSsfbSettings(projectKey, repoSlug);
  if (hasNoSyncSettings(ssfbRepoSettingsOpt)) {
   return;
  }
  SsfbRepoSettings ssfbRepoSettings = ssfbRepoSettingsOpt.get();

  Repository fromRepository = repositoryService.getBySlug(ssfbRepoSettings.getFromProject(),
    ssfbRepoSettings.getFromRepo());
  Repository toRepository = repositoryService.getBySlug(projectKey, repoSlug);

  if (ssfbRepoSettings.getRepositoryHooks()) {
   syncRepositoryHooks(fromRepository, toRepository);
  }

 }

 private void syncRepositoryHooks(Repository fromRepository, Repository toRepository) {
  Map<String, RepositoryHook> fromHooks = getAllHooks(fromRepository);
  for (String fromConfigurationKey : fromHooks.keySet()) {
   RepositoryHook fromHook = fromHooks.get(fromConfigurationKey);

   if (fromHook.isEnabled()) {
    repositoryHookService.enable(toRepository, fromConfigurationKey);
   } else {
    repositoryHookService.disable(toRepository, fromConfigurationKey);
   }

   if (fromHook.isConfigured()) {
    Settings fromSettings = repositoryHookService.getSettings(fromRepository, fromConfigurationKey);
    repositoryHookService.setSettings(toRepository, fromConfigurationKey, fromSettings);
   }
  }
 }

 private boolean hasNoSyncSettings(Optional<SsfbRepoSettings> ssfbRepoSettings) {
  return !ssfbRepoSettings.isPresent();
 }

 private Map<String, RepositoryHook> getAllHooks(Repository repository) {
  Map<String, RepositoryHook> hooks = newHashMap();
  Page<RepositoryHook> repositoryHooks = repositoryHookService.findAll(repository, inOnePage());
  for (RepositoryHook repositoryHook : repositoryHooks.getValues()) {
   String configurationFormKey = repositoryHook.getDetails().getKey();
   hooks.put(configurationFormKey, repositoryHook);
  }
  return hooks;
 }

 private PageRequest inOnePage() {
  return new PageRequestImpl(0, 1000000);
 }

}
