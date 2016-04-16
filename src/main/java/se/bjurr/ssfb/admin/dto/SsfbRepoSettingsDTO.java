package se.bjurr.ssfb.admin.dto;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(FIELD)
public class SsfbRepoSettingsDTO {
 public String fromProject;
 public String fromRepo;
 public boolean repositoryDetails;
 public boolean repositoryHooks;
 public boolean repositoryPermissions;
 public boolean branchPermissions;
 public boolean pullRequestSettings;
 public boolean branchingModel;

 public SsfbRepoSettingsDTO() {
 }

 public Boolean getBranchingModel() {
  return branchingModel;
 }

 public Boolean getBranchPermissions() {
  return branchPermissions;
 }

 public String getFromProject() {
  return fromProject;
 }

 public String getFromRepo() {
  return fromRepo;
 }

 public Boolean getPullRequestSettings() {
  return pullRequestSettings;
 }

 public Boolean getRepositoryDetails() {
  return repositoryDetails;
 }

 public Boolean getRepositoryHooks() {
  return repositoryHooks;
 }

 public Boolean getRepositoryPermissions() {
  return repositoryPermissions;
 }

 public void setBranchingModel(Boolean branchingModel) {
  this.branchingModel = branchingModel;
 }

 public void setBranchPermissions(Boolean branchPermissions) {
  this.branchPermissions = branchPermissions;
 }

 public void setFromProject(String fromProject) {
  this.fromProject = fromProject;
 }

 public void setFromRepo(String fromRepo) {
  this.fromRepo = fromRepo;
 }

 public void setPullRequestSettings(Boolean pullRequestSettings) {
  this.pullRequestSettings = pullRequestSettings;
 }

 public void setRepositoryDetails(Boolean repositoryDetails) {
  this.repositoryDetails = repositoryDetails;
 }

 public void setRepositoryHooks(Boolean repositoryHooks) {
  this.repositoryHooks = repositoryHooks;
 }

 public void setRepositoryPermissions(Boolean repositoryPermissions) {
  this.repositoryPermissions = repositoryPermissions;
 }
}
