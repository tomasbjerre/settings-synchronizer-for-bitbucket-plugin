package se.bjurr.ssfb.settings;

import se.bjurr.ssfb.admin.dto.SsfbGlobalSyncSettingsDTO;

public class SsfbGlobalSyncSettingsDTOTransformer {

 public static SsfbGlobalSyncSettingsDTO fromSettings(SsfbSettings settings) {
  SsfbGlobalSyncSettingsDTO entity = new SsfbGlobalSyncSettingsDTO();
  entity.setStartTime(settings.getStartTime());
  if (settings.getSyncEvery() == null) {
   entity.setSyncEvery(SyncEvery.NEVER.name());
  } else {
   entity.setSyncEvery(settings.getSyncEvery().name());
  }
  return entity;
 }

}
