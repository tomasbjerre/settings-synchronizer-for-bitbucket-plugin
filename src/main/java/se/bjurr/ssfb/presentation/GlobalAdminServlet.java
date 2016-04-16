package se.bjurr.ssfb.presentation;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.ok;
import static se.bjurr.ssfb.settings.SsfbGlobalSyncSettingsDTOTransformer.fromSettings;
import static se.bjurr.ssfb.settings.SsfbSettings.ssfbSettingsBuilder;

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

@Path("/globaladmin")
public class GlobalAdminServlet {
 private final SettingsService settingsService;
 private final ScheduleService scheduleService;

 public GlobalAdminServlet(ScheduleService scheduleService, SettingsService settingsService) {
  this.settingsService = settingsService;
  this.scheduleService = scheduleService;
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
 public Response post(final SsfbGlobalSyncSettingsDTO ssfbRepoSettingsDTO) throws Exception {
  SsfbSettings settings = settingsService.getSsfbSettings();
  SsfbSettings updatedSettings = ssfbSettingsBuilder(settings)//
    .setStartTime(ssfbRepoSettingsDTO.getStartTime())//
    .setSyncEvery(SyncEvery.valueOf(ssfbRepoSettingsDTO.getSyncEvery()))//
    .build();

  settingsService.setSsfbSettings(updatedSettings);
  scheduleService.setup(updatedSettings.getStartTime(), updatedSettings.getSyncEvery());
  return ok() //
    .build();
 }
}