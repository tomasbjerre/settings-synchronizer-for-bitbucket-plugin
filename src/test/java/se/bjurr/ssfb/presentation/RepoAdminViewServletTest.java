package se.bjurr.ssfb.presentation;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import se.bjurr.ssfb.presentation.RepoAdminViewServlet;

import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.templaterenderer.TemplateRenderer;

public class RepoAdminViewServletTest {
 @Mock
 private TemplateRenderer renderer;
 @Mock
 private RepositoryService repositoryService;
 @Mock
 private Repository repository;
 private RepoAdminViewServlet sut;

 @Before
 public void before() {
  initMocks(this);
  sut = new RepoAdminViewServlet(renderer, repositoryService);
 }

 @Test
 public void testThatProjectAndRepoCanBeParsed() {
  when(repositoryService.getBySlug("PROJECT_1", "rep_1"))//
    .thenReturn(repository);
  sut.getRepository("http://localhost:7990/bitbucket/plugins/servlet/ssfb/repoadminview/PROJECT_1/rep_1");

  verify(repositoryService)//
    .getBySlug(eq("PROJECT_1"), eq("rep_1"));
 }

}
