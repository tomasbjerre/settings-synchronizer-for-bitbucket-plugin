package se.bjurr.ssfb.admin.dto;

import se.bjurr.ssfb.settings.SsfbRepoSettings;

public class SsfbRepoSettingsDTOTransfomer {
 private SsfbRepoSettingsDTOTransfomer() {
 }

 public static SsfbRepoSettingsDTO fromSsfbRepoSettings(SsfbRepoSettings from) {
  SsfbRepoSettingsDTO transformed = new SsfbRepoSettingsDTO();
  transformed.setBranchingModel(from.getBranchingModel());
  transformed.setBranchPermissions(from.getBranchPermissions());
  transformed.setFromProject(from.getFromProject());
  transformed.setFromRepo(from.getFromRepo());
  transformed.setPullRequestSettings(from.getPullRequestSettings());
  transformed.setRepositoryDetails(from.getRepositoryDetails());
  transformed.setRepositoryHooks(from.getRepositoryHooks());
  transformed.setRepositoryPermissions(from.getRepositoryPermissions());
  return transformed;
 }

}
