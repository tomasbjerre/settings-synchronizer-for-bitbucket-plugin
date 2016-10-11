package se.bjurr.ssfb.settings;

import static se.bjurr.ssfb.settings.SsfbRepoSettings.ssfbRepoSettingsBuilder;

import se.bjurr.ssfb.admin.dto.SsfbRepoSettingsDTO;

public class SsfbRepoSettingsTransfomer {
 public static SsfbRepoSettings fromSsfbRepoSettingsDTO(SsfbRepoSettingsDTO from) {
  return ssfbRepoSettingsBuilder()//
    .setFromProject(from.getFromProject())//
    .setFromRepo(from.getFromRepo())//
    .setHookConfigurationKeysToSync(from.getHookConfigurationKeysToSync())//
    .build();
 }

 private SsfbRepoSettingsTransfomer() {
 }
}
