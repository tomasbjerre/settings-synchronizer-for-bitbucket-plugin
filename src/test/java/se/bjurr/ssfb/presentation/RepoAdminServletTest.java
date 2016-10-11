package se.bjurr.ssfb.presentation;

import static com.google.common.collect.Lists.newArrayList;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static se.bjurr.ssfb.admin.dto.SsfbRepoSettingsDTOTransfomer.fromSsfbRepoSettings;
import static se.bjurr.ssfb.settings.SsfbRepoSettings.ssfbRepoSettingsBuilder;
import static se.bjurr.ssfb.settings.SsfbRepoSettingsTransfomer.fromSsfbRepoSettingsDTO;
import static se.bjurr.ssfb.settings.SsfbSettings.ssfbSettingsBuilder;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import com.atlassian.sal.api.user.UserKey;
import com.atlassian.sal.api.user.UserManager;

import se.bjurr.ssfb.admin.dto.SsfbRepoSettingsDTO;
import se.bjurr.ssfb.service.SettingsService;
import se.bjurr.ssfb.settings.SsfbRepoSettings;
import se.bjurr.ssfb.settings.SsfbSettings;

public class RepoAdminServletTest {

 private final String projectKey = "projectKey";
 @Captor
 private ArgumentCaptor<String> projectKeyCaptor;
 private final String repoSlug = "repoSlug";
 @Captor
 private ArgumentCaptor<String> repoSlugCaptor;
 @Mock
 private SettingsService settingsService;
 @Captor
 private ArgumentCaptor<SsfbRepoSettings> ssfbRepoSettingsCaptor;
 @Captor
 private ArgumentCaptor<String> ssfbSettingsStringCaptor;
 private RepoAdminServlet sut;
 private final UserKey userKey = new UserKey("userkey");
 @Mock
 private UserManager userManager;

 @Before
 public void before() {
  initMocks(this);
  this.sut = new RepoAdminServlet(this.settingsService, this.userManager);
  when(this.userManager.getRemoteUserKey())//
    .thenReturn(this.userKey);
  when(this.userManager.isAdmin(this.userKey))//
    .thenReturn(true);
 }

 @Test
 public void testThatEmptySettingsIsUsedFirstTime() throws Exception {
  when(this.settingsService.getSsfbSettings())//
    .thenReturn(ssfbSettingsBuilder().build());

  Response actual = this.sut.get(this.projectKey, this.repoSlug);
  assertThat(actual.getStatus())//
    .isEqualTo(NOT_FOUND.getStatusCode());
 }

 @Test
 public void testThatSettingsCanBePartialyStored() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .build();
  SsfbRepoSettingsDTO ssfbRepoSettingsDTO = fromSsfbRepoSettings(expectedSsfbRepoSettings);

  this.sut.post(this.projectKey, this.repoSlug, ssfbRepoSettingsDTO);

  verify(this.settingsService)//
    .setSsfbSettings(this.projectKeyCaptor.capture(), this.repoSlugCaptor.capture(),
      this.ssfbRepoSettingsCaptor.capture());

  assertThat(this.projectKeyCaptor.getValue())//
    .isEqualTo("projectKey");
  assertThat(this.repoSlugCaptor.getValue())//
    .isEqualTo("repoSlug");
  assertThat(this.ssfbRepoSettingsCaptor.getValue())//
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
  when(this.settingsService.getSsfbSettings())//
    .thenReturn(ssfbSettings);

  Response actual = this.sut.get(this.projectKey, this.repoSlug);

  assertThat(actual.getStatus())//
    .isEqualTo(OK.getStatusCode());
  SsfbRepoSettingsDTO ssfbRepoSettingsDTO = (SsfbRepoSettingsDTO) actual.getEntity();
  assertThat(fromSsfbRepoSettingsDTO(ssfbRepoSettingsDTO))//
    .isEqualTo(expectedSsfbRepoSettings);
 }

 @Test
 public void testThatSettingsCanBeStored() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .setHookConfigurationKeysToSync(newArrayList("hookkey1", ",hookkey2"))//
    .build();
  SsfbRepoSettingsDTO ssfbRepoSettingsDTO = fromSsfbRepoSettings(expectedSsfbRepoSettings);

  this.sut.post(this.projectKey, this.repoSlug, ssfbRepoSettingsDTO);

  verify(this.settingsService)//
    .setSsfbSettings(this.projectKeyCaptor.capture(), this.repoSlugCaptor.capture(),
      this.ssfbRepoSettingsCaptor.capture());

  assertThat(this.projectKeyCaptor.getValue())//
    .isEqualTo("projectKey");
  assertThat(this.repoSlugCaptor.getValue())//
    .isEqualTo("repoSlug");
  assertThat(this.ssfbRepoSettingsCaptor.getValue())//
    .isEqualTo(expectedSsfbRepoSettings);
 }
}
