package se.bjurr.ssfb.presentation;

import static java.lang.Boolean.TRUE;
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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import se.bjurr.ssfb.admin.dto.SsfbRepoSettingsDTO;
import se.bjurr.ssfb.service.SettingsService;
import se.bjurr.ssfb.settings.SsfbRepoSettings;
import se.bjurr.ssfb.settings.SsfbSettings;

public class RepoAdminServletTest {

 @Mock
 private SettingsService settingsService;
 @Captor
 private ArgumentCaptor<String> ssfbSettingsStringCaptor;
 @Captor
 private ArgumentCaptor<String> projectKeyCaptor;
 @Captor
 private ArgumentCaptor<String> repoSlugCaptor;
 @Captor
 private ArgumentCaptor<SsfbRepoSettings> ssfbRepoSettingsCaptor;
 private RepoAdminServlet sut;
 private final String projectKey = "projectKey";
 private final String repoSlug = "repoSlug";
 private HttpServletRequest request;

 @Before
 public void before() {
  initMocks(this);
  sut = new RepoAdminServlet(settingsService);
 }

 @Test
 public void testThatEmptySettingsIsUsedFirstTime() throws Exception {
  when(settingsService.getSsfbSettings())//
    .thenReturn(ssfbSettingsBuilder().build());

  Response actual = sut.get(projectKey, repoSlug);
  assertThat(actual.getStatus())//
    .isEqualTo(NOT_FOUND.getStatusCode());
 }

 @Test
 public void testThatSettingsCanBePartialyStored() throws Exception {
  SsfbRepoSettings expectedSsfbRepoSettings = ssfbRepoSettingsBuilder()//
    .setBranchingModel(TRUE)//
    .setFromProject("fromProject")//
    .setFromRepo("fromRepo")//
    .setRepositoryPermissions(TRUE)//
    .build();
  SsfbRepoSettingsDTO ssfbRepoSettingsDTO = fromSsfbRepoSettings(expectedSsfbRepoSettings);

  sut.post(projectKey, repoSlug, ssfbRepoSettingsDTO, request);

  verify(settingsService)//
    .setSsfbSettings(projectKeyCaptor.capture(), repoSlugCaptor.capture(), ssfbRepoSettingsCaptor.capture());

  assertThat(projectKeyCaptor.getValue())//
    .isEqualTo("projectKey");
  assertThat(repoSlugCaptor.getValue())//
    .isEqualTo("repoSlug");
  assertThat(ssfbRepoSettingsCaptor.getValue())//
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
  SsfbRepoSettingsDTO ssfbRepoSettingsDTO = fromSsfbRepoSettings(expectedSsfbRepoSettings);

  sut.post(projectKey, repoSlug, ssfbRepoSettingsDTO, request);

  verify(settingsService)//
    .setSsfbSettings(projectKeyCaptor.capture(), repoSlugCaptor.capture(), ssfbRepoSettingsCaptor.capture());

  assertThat(projectKeyCaptor.getValue())//
    .isEqualTo("projectKey");
  assertThat(repoSlugCaptor.getValue())//
    .isEqualTo("repoSlug");
  assertThat(ssfbRepoSettingsCaptor.getValue())//
    .isEqualTo(expectedSsfbRepoSettings);
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
  when(settingsService.getSsfbSettings())//
    .thenReturn(ssfbSettings);

  Response actual = sut.get(projectKey, repoSlug);

  assertThat(actual.getStatus())//
    .isEqualTo(OK.getStatusCode());
  SsfbRepoSettingsDTO ssfbRepoSettingsDTO = (SsfbRepoSettingsDTO) actual.getEntity();
  assertThat(fromSsfbRepoSettingsDTO(ssfbRepoSettingsDTO))//
    .isEqualTo(expectedSsfbRepoSettings);
 }
}
