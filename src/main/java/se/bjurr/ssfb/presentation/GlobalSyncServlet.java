package se.bjurr.ssfb.presentation;

import static com.google.common.base.Strings.isNullOrEmpty;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.ok;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import se.bjurr.ssfb.admin.dto.SsfbSyncDTO;
import se.bjurr.ssfb.service.SyncService;

@Path("/globalsync")
public class GlobalSyncServlet {
 private final SyncService syncService;

 public GlobalSyncServlet(SyncService syncService) {
  this.syncService = syncService;
 }

 @POST
 @Consumes(APPLICATION_JSON)
 @Produces(TEXT_PLAIN)
 public Response post(final SsfbSyncDTO ssfbSyncDTO) throws Exception {
  if (isNullOrEmpty(ssfbSyncDTO.getRepoSlug())) {
   syncService.performSyncOnAllRepos();
  } else {
   syncService.performSync(ssfbSyncDTO.getProjectKey(), ssfbSyncDTO.getRepoSlug());
  }
  return ok() //
    .build();
 }

}