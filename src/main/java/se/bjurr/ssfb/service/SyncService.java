package se.bjurr.ssfb.service;

import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;

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

import se.bjurr.ssfb.settings.SsfbRepoSettings;
import se.bjurr.ssfb.settings.SsfbSettings;

public class SyncService {

 private final RepositoryHookService repositoryHookService;
 private final RepositoryService repositoryService;
 private final SettingsService settingsService;

 public SyncService(RepositoryService repositoryService, PluginSettingsFactory pluginSettingsFactory,
   RepositoryHookService repositoryHookService, SettingsService settingsService) {
  this.repositoryService = repositoryService;
  this.repositoryHookService = repositoryHookService;
  this.settingsService = settingsService;
 }

 public void performSync(String projectKey, String repoSlug) {
  Optional<SsfbRepoSettings> ssfbRepoSettingsOpt = this.settingsService.getSsfbSettings(projectKey, repoSlug);
  if (hasNoSyncSettings(ssfbRepoSettingsOpt)) {
   return;
  }
  SsfbRepoSettings ssfbRepoSettings = ssfbRepoSettingsOpt.get();

  Repository fromRepository = this.repositoryService.getBySlug(ssfbRepoSettings.getFromProject(),
    ssfbRepoSettings.getFromRepo());
  Repository toRepository = this.repositoryService.getBySlug(projectKey, repoSlug);

  syncRepositoryHooks(ssfbRepoSettings.getHookConfigurationKeysToSync(), fromRepository, toRepository);
 }

 public void performSyncOnAllRepos() {
  SsfbSettings ssfbSettings = this.settingsService.getSsfbSettings();
  Page<Repository> repositories = this.repositoryService.findAll(inOnePage());
  for (Repository toRepository : repositories.getValues()) {
   String projectKey = toRepository.getProject().getKey();
   String repoSlug = toRepository.getSlug();
   Optional<SsfbRepoSettings> ssfbSettingsOpt = ssfbSettings.findRepoSettings(projectKey, repoSlug);
   if (hasNoSyncSettings(ssfbSettingsOpt)) {
    continue;
   }
   SsfbRepoSettings ssfbRepoSettings = ssfbSettingsOpt.get();
   Repository fromRepository = this.repositoryService.getBySlug(ssfbRepoSettings.getFromProject(),
     ssfbRepoSettings.getFromRepo());
   syncRepositoryHooks(ssfbRepoSettings.getHookConfigurationKeysToSync(), fromRepository, toRepository);
  }
 }

 private Map<String, RepositoryHook> getAllHooks(Repository repository) {
  Map<String, RepositoryHook> hooks = newHashMap();
  Page<RepositoryHook> repositoryHooks = this.repositoryHookService.findAll(repository, inOnePage());
  for (RepositoryHook repositoryHook : repositoryHooks.getValues()) {
   String configurationFormKey = repositoryHook.getDetails().getKey();
   hooks.put(configurationFormKey, repositoryHook);
  }
  return hooks;
 }

 private boolean hasNoSyncSettings(Optional<SsfbRepoSettings> ssfbRepoSettings) {
  return !ssfbRepoSettings.isPresent();
 }

 private PageRequest inOnePage() {
  return new PageRequestImpl(0, 1000000);
 }

 private void syncRepositoryHooks(List<String> hookConfigurationKeysToSync, Repository fromRepository,
   Repository toRepository) {
  Map<String, RepositoryHook> fromHooks = getAllHooks(fromRepository);
  for (String fromConfigurationKey : fromHooks.keySet()) {
   if (!hookConfigurationKeysToSync.contains(fromConfigurationKey)) {
    continue;
   }
   RepositoryHook fromHook = fromHooks.get(fromConfigurationKey);

   if (fromHook.isConfigured()) {
    Settings fromSettings = this.repositoryHookService.getSettings(fromRepository, fromConfigurationKey);
    this.repositoryHookService.setSettings(toRepository, fromConfigurationKey, fromSettings);
   }

   if (fromHook.isEnabled()) {
    this.repositoryHookService.enable(toRepository, fromConfigurationKey);
   } else {
    this.repositoryHookService.disable(toRepository, fromConfigurationKey);
   }
  }
 }

}
