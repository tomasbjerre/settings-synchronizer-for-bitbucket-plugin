package se.bjurr.ssfb.admin.dto;

import se.bjurr.ssfb.settings.SsfbRepoSettings;

public class SsfbRepoSettingsDTOTransfomer {
 public static SsfbRepoSettingsDTO fromSsfbRepoSettings(SsfbRepoSettings from) {
  SsfbRepoSettingsDTO transformed = new SsfbRepoSettingsDTO();
  transformed.setHookConfigurationKeysToSync(from.getHookConfigurationKeysToSync());
  transformed.setFromProject(from.getFromProject());
  transformed.setFromRepo(from.getFromRepo());
  return transformed;
 }

 private SsfbRepoSettingsDTOTransfomer() {
 }

}
