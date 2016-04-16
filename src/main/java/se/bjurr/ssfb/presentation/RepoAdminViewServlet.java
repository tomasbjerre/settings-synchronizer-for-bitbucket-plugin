package se.bjurr.ssfb.presentation;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableMap.of;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.google.common.annotations.VisibleForTesting;

public class RepoAdminViewServlet extends HttpServlet {
 private static final long serialVersionUID = 4498918439097208161L;

 private final TemplateRenderer renderer;
 private final RepositoryService repositoryService;

 public RepoAdminViewServlet(TemplateRenderer renderer, RepositoryService repositoryService) {
  this.renderer = renderer;
  this.repositoryService = repositoryService;
 }

 @Override
 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  final Repository repository = getRepository(req.getPathInfo());

  resp.setContentType("text/html;charset=UTF-8");
  renderer.render( //
    "templates/repo_admin.vm", //
    of( //
      "repository", repository //
    ), //
    resp.getWriter());
 }

 @VisibleForTesting
 Repository getRepository(String pathInfo) {
  String[] components = pathInfo.split("/");
  String project = components[components.length - 2];
  String repoSlug = components[components.length - 1];
  final Repository repository = checkNotNull(repositoryService.getBySlug(project, repoSlug), //
    "Did not find " + project + " " + repoSlug);
  return repository;
 }
}