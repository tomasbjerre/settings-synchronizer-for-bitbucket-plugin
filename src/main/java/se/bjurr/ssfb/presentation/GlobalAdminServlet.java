package se.bjurr.ssfb.presentation;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static se.bjurr.ssfb.settings.SsfbGlobalSyncSettingsDTOTransformer.fromSettings;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import se.bjurr.ssfb.admin.dto.SsfbGlobalSyncSettingsDTO;
import se.bjurr.ssfb.service.ScheduleService;
import se.bjurr.ssfb.service.SettingsService;
import se.bjurr.ssfb.settings.SsfbSettings;
import se.bjurr.ssfb.settings.SyncEvery;

import com.atlassian.sal.api.user.UserManager;

@Path("/globaladmin")
public class GlobalAdminServlet {
 private final SettingsService settingsService;
 private final ScheduleService scheduleService;
 private final UserManager userManager;

 public GlobalAdminServlet(ScheduleService scheduleService, SettingsService settingsService, UserManager userManager) {
  this.settingsService = settingsService;
  this.scheduleService = scheduleService;
  this.userManager = userManager;
 }

 @GET
 @Produces(APPLICATION_JSON)
 public Response get() throws Exception {
  SsfbSettings settings = settingsService.getSsfbSettings();
  SsfbGlobalSyncSettingsDTO entity = fromSettings(settings);
  return ok(entity)//
    .build();
 }

 @POST
 @Consumes(APPLICATION_JSON)
 @Produces(TEXT_PLAIN)
 public Response post(//
   final SsfbGlobalSyncSettingsDTO ssfbRepoSettingsDTO) throws Exception {
  if (!userManager.isAdmin(userManager.getRemoteUserKey())) {
   return status(UNAUTHORIZED)//
     .build();
  }

  SyncEvery syncEvery = SyncEvery.valueOf(ssfbRepoSettingsDTO.getSyncEvery());
  settingsService.setSsfbSettings(ssfbRepoSettingsDTO.getStartTime(), syncEvery);
  scheduleService.setup(ssfbRepoSettingsDTO.getStartTime(), syncEvery);
  return ok() //
    .build();
 }
}