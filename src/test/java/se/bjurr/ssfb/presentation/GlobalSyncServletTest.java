package se.bjurr.ssfb.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import se.bjurr.ssfb.admin.dto.SsfbSyncDTO;
import se.bjurr.ssfb.presentation.GlobalSyncServlet;
import se.bjurr.ssfb.service.SyncService;

public class GlobalSyncServletTest {
 @Mock
 private SyncService syncService;
 @Captor
 private ArgumentCaptor<String> repoSlugCaptor;
 @Captor
 private ArgumentCaptor<String> projectKeyCaptor;
 private GlobalSyncServlet sut;

 @Before
 public void before() {
  initMocks(this);
  sut = new GlobalSyncServlet(syncService);
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
