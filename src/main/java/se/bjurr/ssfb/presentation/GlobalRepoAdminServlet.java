package se.bjurr.ssfb.presentation;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static se.bjurr.ssfb.settings.SsfbRepoSettingsTransfomer.fromSsfbRepoSettingsDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;
import com.atlassian.bitbucket.util.PageRequestImpl;
import com.atlassian.sal.api.user.UserManager;

import se.bjurr.ssfb.admin.dto.SsfbRepoSettingsDTO;
import se.bjurr.ssfb.service.SettingsService;
import se.bjurr.ssfb.settings.SsfbRepoSettings;

@Path("/repoadmin")
public class GlobalRepoAdminServlet {
 private final RepositoryService repositoryService;
 private final SettingsService settingsService;
 private final UserManager userManager;

 public GlobalRepoAdminServlet(SettingsService settingsService, UserManager userManager,
   RepositoryService repositoryService) {
  this.settingsService = settingsService;
  this.userManager = userManager;
  this.repositoryService = repositoryService;
 }

 @POST
 @Consumes(APPLICATION_JSON)
 @Produces(TEXT_PLAIN)
 public Response post(//
   final SsfbRepoSettingsDTO ssfbRepoSettingsDTO) throws Exception {
  if (!this.userManager.isAdmin(this.userManager.getRemoteUserKey())) {
   return status(UNAUTHORIZED)//
     .build();
  }

  SsfbRepoSettings ssfbRepoSettings = fromSsfbRepoSettingsDTO(ssfbRepoSettingsDTO);

  Page<Repository> repositories = this.repositoryService.findAll(inOnePage());
  for (Repository repository : repositories.getValues()) {
   String projectKey = repository.getProject().getKey();
   String repoSlug = repository.getSlug();
   this.settingsService.setSsfbSettings(projectKey, repoSlug, ssfbRepoSettings);
  }
  return ok() //
    .build();
 }

 private PageRequest inOnePage() {
  return new PageRequestImpl(0, 1000000);
 }
}