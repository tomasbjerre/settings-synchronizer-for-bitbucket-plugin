package se.bjurr.ssfb.service;

import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static se.bjurr.ssfb.service.SettingsService.STORAGE_KEY;
import static se.bjurr.ssfb.settings.SsfbRepoSettings.ssfbRepoSettingsBuilder;
import static se.bjurr.ssfb.settings.SsfbSettings.ssfbSettingsBuilder;
import static se.bjurr.ssfb.settings.SyncEvery.DAILY;
import static se.bjurr.ssfb.settings.SyncEvery.HOURLY;
import static se.bjurr.ssfb.settings.SyncEvery.NEVER;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import se.bjurr.ssfb.settings.SsfbRepoSettings;
import se.bjurr.ssfb.settings.SsfbSettings;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.google.common.base.Optional;
import com.google.gson.Gson;

public class SettingsServiceTest {

 @Mock
 private PluginSettingsFactory pluginSettingsFactory;
 @Mock
 private PluginSettings pluginSettings;
 @Captor
 private ArgumentCaptor<String> ssfbSettingsStringCaptor;
 private SettingsService sut;
 private final String projectKey = "projectKey";
 private final String repoSlug = "repoSlug";

 @Before
 public void before() {
  initMocks(this);
  when(pluginSettingsFactory.createGlobalSettings())//
    .thenReturn(pluginSettings);
  TransactionTemplate transactionTemplate = new TransactionTemplate() {
   @Override
   public <T> T execute(TransactionCallback<T> action) {
    return action.doInTransaction();
   }
  };
  sut = new SettingsService(pluginSettingsFactory, transactionTemplate);
 }

 @Test
 public void testThatEmptySettingsIsUsedFirstTime() throws Exception {
  when(pluginSettings.get(STORAGE_KEY))//
    .thenReturn(null);

  Optional<SsfbRepoSettings> actual = sut.getSsfbSettings(projectKey, repoSlug);
  assertThat(actual.isPresent())//
    .isFalse();
 }

 @Test
 public void testThatSettingsCanBePartialyStored() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setBranchingModel(TRUE)//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .setRepositoryPermissions(TRUE)//
    .build();

  sut.setSsfbSettings(projectKey, repoSlug, expectedSsfbRepoSettings);

  verify(pluginSettings)//
    .put(eq(STORAGE_KEY), ssfbSettingsStringCaptor.capture());
  SsfbSettings ssfbSettings = new Gson().fromJson(ssfbSettingsStringCaptor.getValue(), SsfbSettings.class);
  assertThat(ssfbSettings.getStartTime())//
    .isEqualTo("00:00");
  assertThat(ssfbSettings.getSyncEvery())//
    .isEqualTo(NEVER);
  assertThat(ssfbSettings.findRepoSettings("projectKey", "repoSlug").get())//
    .isEqualTo(expectedSsfbRepoSettings);
 }

 @Test
 public void testThatSettingsCanBeStored() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setBranchingModel(TRUE)//
    .setBranchPermissions(TRUE)//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .setPullRequestSettings(TRUE)//
    .setRepositoryDetails(TRUE)//
    .setRepositoryHooks(TRUE)//
    .setRepositoryPermissions(TRUE)//
    .build();

  sut.setSsfbSettings(projectKey, repoSlug, expectedSsfbRepoSettings);

  verify(pluginSettings)//
    .put(eq(STORAGE_KEY), ssfbSettingsStringCaptor.capture());
  SsfbSettings ssfbSettings = new Gson().fromJson(ssfbSettingsStringCaptor.getValue(), SsfbSettings.class);
  assertThat(ssfbSettings.getStartTime())//
    .isEqualTo("00:00");
  assertThat(ssfbSettings.getSyncEvery())//
    .isEqualTo(NEVER);
  assertThat(ssfbSettings.findRepoSettings("projectKey", "repoSlug").get())//
    .isEqualTo(expectedSsfbRepoSettings);
 }

 @Test
 public void testThatSyncSettingsCanBeSaved() {
  SsfbSettings oldStoredData = ssfbSettingsBuilder()//
    .setStartTime("03:04")//
    .setSyncEvery(HOURLY)//
    .build();
  when(pluginSettings.get(STORAGE_KEY))//
    .thenReturn(new Gson().toJson(oldStoredData));

  sut.setSsfbSettings("01:01", DAILY);

  SsfbSettings expectedStoredData = ssfbSettingsBuilder()//
    .setStartTime("01:01")//
    .setSyncEvery(DAILY)//
    .build();
  verify(pluginSettings).put(eq(STORAGE_KEY), eq(new Gson().toJson(expectedStoredData)));
 }

 @Test
 public void testThatSettingsCanBeRead() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setBranchingModel(TRUE)//
    .setBranchPermissions(TRUE)//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .setPullRequestSettings(TRUE)//
    .setRepositoryDetails(TRUE)//
    .setRepositoryHooks(TRUE)//
    .setRepositoryPermissions(TRUE)//
    .build();
  SsfbSettings ssfbSettings = ssfbSettingsBuilder()//
    .setRepoSettings("projectKey", "repoSlug", expectedSsfbRepoSettings)//
    .build();
  String settingsJson = new Gson().toJson(ssfbSettings);
  when(pluginSettings.get(STORAGE_KEY))//
    .thenReturn(settingsJson);

  SsfbRepoSettings actual = sut.getSsfbSettings(projectKey, repoSlug).orNull();

  assertThat(actual)//
    .isEqualTo(expectedSsfbRepoSettings);
 }
}
