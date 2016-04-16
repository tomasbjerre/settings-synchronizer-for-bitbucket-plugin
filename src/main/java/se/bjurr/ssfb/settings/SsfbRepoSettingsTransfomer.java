package se.bjurr.ssfb.settings;

import static se.bjurr.ssfb.settings.SsfbRepoSettings.ssfbRepoSettingsBuilder;
import se.bjurr.ssfb.admin.dto.SsfbRepoSettingsDTO;

public class SsfbRepoSettingsTransfomer {
 private SsfbRepoSettingsTransfomer() {
 }

 public static SsfbRepoSettings fromSsfbRepoSettingsDTO(SsfbRepoSettingsDTO from) {
  return ssfbRepoSettingsBuilder()//
    .setBranchingModel(from.getBranchingModel())//
    .setBranchPermissions(from.getBranchPermissions())//
    .setFromProject(from.getFromProject())//
    .setFromRepo(from.getFromRepo())//
    .setPullRequestSettings(from.getPullRequestSettings())//
    .setRepositoryDetails(from.getRepositoryDetails())//
    .setRepositoryHooks(from.getRepositoryHooks())//
    .setRepositoryPermissions(from.getRepositoryPermissions())//
    .build();
 }
}
