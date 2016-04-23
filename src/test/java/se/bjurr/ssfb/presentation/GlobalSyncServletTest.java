package se.bjurr.ssfb.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import se.bjurr.ssfb.admin.dto.SsfbSyncDTO;
import se.bjurr.ssfb.service.SyncService;

import com.atlassian.sal.api.user.UserKey;
import com.atlassian.sal.api.user.UserManager;

public class GlobalSyncServletTest {
 @Mock
 private SyncService syncService;
 @Mock
 private UserManager userManager;
 private final UserKey userKey = new UserKey("userkey");
 @Captor
 private ArgumentCaptor<String> repoSlugCaptor;
 @Captor
 private ArgumentCaptor<String> projectKeyCaptor;
 private GlobalSyncServlet sut;

 @Before
 public void before() {
  initMocks(this);
  sut = new GlobalSyncServlet(syncService, userManager);
  when(userManager.getRemoteUserKey())//
    .thenReturn(userKey);
  when(userManager.isAdmin(userKey))//
    .thenReturn(true);
 }

 @Test
 public void testAllReposCanBeSynced() throws Exception {
  SsfbSyncDTO ssfbSyncDTO = new SsfbSyncDTO();

  sut.post(ssfbSyncDTO);

  verify(syncService)//
    .performSyncOnAllRepos();
 }

 @Test
 public void testSpecificRepoCanBeSynced() throws Exception {
  SsfbSyncDTO ssfbSyncDTO = new SsfbSyncDTO();
  ssfbSyncDTO.setProjectKey("projectKey");
  ssfbSyncDTO.setRepoSlug("repoSlug");

  sut.post(ssfbSyncDTO);

  verify(syncService)//
    .performSync(projectKeyCaptor.capture(), repoSlugCaptor.capture());
  assertThat(projectKeyCaptor.getValue())//
    .isEqualTo("projectKey");
  assertThat(repoSlugCaptor.getValue())//
    .isEqualTo("repoSlug");
 }

}
