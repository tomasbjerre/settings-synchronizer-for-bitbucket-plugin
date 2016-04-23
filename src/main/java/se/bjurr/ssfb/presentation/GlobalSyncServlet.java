package se.bjurr.ssfb.presentation;

import static com.google.common.base.Strings.isNullOrEmpty;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import se.bjurr.ssfb.admin.dto.SsfbSyncDTO;
import se.bjurr.ssfb.service.SyncService;

import com.atlassian.sal.api.user.UserManager;

@Path("/globalsync")
public class GlobalSyncServlet {
 private final SyncService syncService;
 private final UserManager userManager;

 public GlobalSyncServlet(SyncService syncService, UserManager userManager) {
  this.syncService = syncService;
  this.userManager = userManager;
 }

 @POST
 @Consumes(APPLICATION_JSON)
 @Produces(TEXT_PLAIN)
 public Response post(//
   final SsfbSyncDTO ssfbSyncDTO) throws Exception {
  if (!userManager.isAdmin(userManager.getRemoteUserKey())) {
   return status(UNAUTHORIZED)//
     .build();
  }

  if (isNullOrEmpty(ssfbSyncDTO.getRepoSlug())) {
   syncService.performSyncOnAllRepos();
  } else {
   syncService.performSync(ssfbSyncDTO.getProjectKey(), ssfbSyncDTO.getRepoSlug());
  }
  return ok() //
    .build();
 }

}