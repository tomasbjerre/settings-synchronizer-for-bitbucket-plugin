package se.bjurr.ssfb.service;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static se.bjurr.ssfb.service.SettingsService.STORAGE_KEY;
import static se.bjurr.ssfb.settings.SsfbRepoSettings.ssfbRepoSettingsBuilder;
import static se.bjurr.ssfb.settings.SsfbSettings.ssfbSettingsBuilder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.google.common.base.Optional;
import com.google.gson.Gson;

import se.bjurr.ssfb.settings.SsfbRepoSettings;
import se.bjurr.ssfb.settings.SsfbSettings;

public class SettingsServiceTest {

 @Mock
 private PluginSettings pluginSettings;
 @Mock
 private PluginSettingsFactory pluginSettingsFactory;
 private final String projectKey = "projectKey";
 @Mock
 private RepositoryService repositoryService;
 private final String repoSlug = "repoSlug";
 @Captor
 private ArgumentCaptor<String> ssfbSettingsStringCaptor;
 private SettingsService sut;

 @Before
 public void before() {
  initMocks(this);
  when(this.pluginSettingsFactory.createGlobalSettings())//
    .thenReturn(this.pluginSettings);
  TransactionTemplate transactionTemplate = new TransactionTemplate() {
   @Override
   public <T> T execute(TransactionCallback<T> action) {
    return action.doInTransaction();
   }
  };
  this.sut = new SettingsService(this.pluginSettingsFactory, transactionTemplate, this.repositoryService);
 }

 @Test
 public void testThatEmptySettingsIsUsedFirstTime() throws Exception {
  when(this.pluginSettings.get(STORAGE_KEY))//
    .thenReturn(null);

  Optional<SsfbRepoSettings> actual = this.sut.getSsfbSettings(this.projectKey, this.repoSlug);
  assertThat(actual.isPresent())//
    .isFalse();
 }

 @Test
 public void testThatNotAllSettingsHasToBeSupplied() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .setHookConfigurationKeysToSync(null)//
    .build();

  this.sut.setSsfbSettings(this.projectKey, this.repoSlug, expectedSsfbRepoSettings);

  verify(this.pluginSettings)//
    .put(eq(STORAGE_KEY), this.ssfbSettingsStringCaptor.capture());
  SsfbSettings ssfbSettings = new Gson().fromJson(this.ssfbSettingsStringCaptor.getValue(), SsfbSettings.class);
  assertThat(ssfbSettings.findRepoSettings("projectKey", "repoSlug").get())//
    .isEqualTo(expectedSsfbRepoSettings);
 }

 @Test
 public void testThatSettingsCanBePartialyStored() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .build();

  this.sut.setSsfbSettings(this.projectKey, this.repoSlug, expectedSsfbRepoSettings);

  verify(this.pluginSettings)//
    .put(eq(STORAGE_KEY), this.ssfbSettingsStringCaptor.capture());
  SsfbSettings ssfbSettings = new Gson().fromJson(this.ssfbSettingsStringCaptor.getValue(), SsfbSettings.class);
  assertThat(ssfbSettings.findRepoSettings("projectKey", "repoSlug").get())//
    .isEqualTo(expectedSsfbRepoSettings);
 }

 @Test
 public void testThatSettingsCanBeRead() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .setHookConfigurationKeysToSync(newArrayList("hookkey1", ",hookkey2"))//
    .build();
  SsfbSettings ssfbSettings = ssfbSettingsBuilder()//
    .setRepoSettings("projectKey", "repoSlug", expectedSsfbRepoSettings)//
    .build();
  String settingsJson = new Gson().toJson(ssfbSettings);
  when(this.pluginSettings.get(STORAGE_KEY))//
    .thenReturn(settingsJson);

  SsfbRepoSettings actual = this.sut.getSsfbSettings(this.projectKey, this.repoSlug).orNull();

  assertThat(actual)//
    .isEqualTo(expectedSsfbRepoSettings);
 }

 @Test
 public void testThatSettingsCanBeStored() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .setHookConfigurationKeysToSync(newArrayList("hookkey1", ",hookkey2"))//
    .build();

  this.sut.setSsfbSettings(this.projectKey, this.repoSlug, expectedSsfbRepoSettings);

  verify(this.pluginSettings)//
    .put(eq(STORAGE_KEY), this.ssfbSettingsStringCaptor.capture());
  SsfbSettings ssfbSettings = new Gson().fromJson(this.ssfbSettingsStringCaptor.getValue(), SsfbSettings.class);
  assertThat(ssfbSettings.findRepoSettings("projectKey", "repoSlug").get())//
    .isEqualTo(expectedSsfbRepoSettings);
 }

}
