package se.bjurr.ssfb.settings;

import static com.google.common.base.Preconditions.checkNotNull;

public class SsfbRepoSettings {
 public static class Builder {
  private String fromProject;
  private String fromRepo;
  private boolean repositoryDetails;
  private boolean repositoryHooks;
  private boolean repositoryPermissions;
  private boolean branchPermissions;
  private boolean pullRequestSettings;
  private boolean branchingModel;

  private Builder() {
  }

  public Builder setBranchingModel(Boolean branchingModel) {
   this.branchingModel = branchingModel;
   return this;
  }

  public Builder setBranchPermissions(Boolean branchPermissions) {
   this.branchPermissions = branchPermissions;
   return this;
  }

  public Builder setFromProject(String fromProject) {
   this.fromProject = fromProject;
   return this;
  }

  public Builder setFromRepo(String fromRepo) {
   this.fromRepo = fromRepo;
   return this;
  }

  public Builder setPullRequestSettings(Boolean pullRequestSettings) {
   this.pullRequestSettings = pullRequestSettings;
   return this;
  }

  public Builder setRepositoryDetails(Boolean repositoryDetails) {
   this.repositoryDetails = repositoryDetails;
   return this;
  }

  public Builder setRepositoryHooks(Boolean repositoryHooks) {
   this.repositoryHooks = repositoryHooks;
   return this;
  }

  public Builder setRepositoryPermissions(Boolean repositoryPermissions) {
   this.repositoryPermissions = repositoryPermissions;
   return this;
  }

  public SsfbRepoSettings build() {
   return new SsfbRepoSettings(this);
  }
 }

 private final String fromProject;
 private final String fromRepo;
 private final Boolean repositoryDetails;
 private final Boolean repositoryHooks;
 private final Boolean repositoryPermissions;
 private final Boolean branchPermissions;
 private final Boolean pullRequestSettings;
 private final Boolean branchingModel;

 public SsfbRepoSettings(Builder builder) {
  fromProject = checkNotNull(builder.fromProject, "fromProject");
  fromRepo = checkNotNull(builder.fromRepo, "fromRepo");
  repositoryDetails = checkNotNull(builder.repositoryDetails, "repositoryDetails");
  repositoryHooks = checkNotNull(builder.repositoryHooks, "repositoryHooks");
  repositoryPermissions = checkNotNull(builder.repositoryPermissions, "repositoryPermissions");
  branchPermissions = checkNotNull(builder.branchPermissions, "branchPermissions");
  pullRequestSettings = checkNotNull(builder.pullRequestSettings, "pullRequestSettings");
  branchingModel = checkNotNull(builder.branchingModel, "branchingModel");
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

 public static Builder ssfbRepoSettingsBuilder() {
  return new Builder();
 }

 @Override
 public int hashCode() {
  final int prime = 31;
  int result = 1;
  result = prime * result + ((branchPermissions == null) ? 0 : branchPermissions.hashCode());
  result = prime * result + ((branchingModel == null) ? 0 : branchingModel.hashCode());
  result = prime * result + ((fromProject == null) ? 0 : fromProject.hashCode());
  result = prime * result + ((fromRepo == null) ? 0 : fromRepo.hashCode());
  result = prime * result + ((pullRequestSettings == null) ? 0 : pullRequestSettings.hashCode());
  result = prime * result + ((repositoryDetails == null) ? 0 : repositoryDetails.hashCode());
  result = prime * result + ((repositoryHooks == null) ? 0 : repositoryHooks.hashCode());
  result = prime * result + ((repositoryPermissions == null) ? 0 : repositoryPermissions.hashCode());
  return result;
 }

 @Override
 public boolean equals(Object obj) {
  if (this == obj) {
   return true;
  }
  if (obj == null) {
   return false;
  }
  if (getClass() != obj.getClass()) {
   return false;
  }
  SsfbRepoSettings other = (SsfbRepoSettings) obj;
  if (branchPermissions == null) {
   if (other.branchPermissions != null) {
    return false;
   }
  } else if (!branchPermissions.equals(other.branchPermissions)) {
   return false;
  }
  if (branchingModel == null) {
   if (other.branchingModel != null) {
    return false;
   }
  } else if (!branchingModel.equals(other.branchingModel)) {
   return false;
  }
  if (fromProject == null) {
   if (other.fromProject != null) {
    return false;
   }
  } else if (!fromProject.equals(other.fromProject)) {
   return false;
  }
  if (fromRepo == null) {
   if (other.fromRepo != null) {
    return false;
   }
  } else if (!fromRepo.equals(other.fromRepo)) {
   return false;
  }
  if (pullRequestSettings == null) {
   if (other.pullRequestSettings != null) {
    return false;
   }
  } else if (!pullRequestSettings.equals(other.pullRequestSettings)) {
   return false;
  }
  if (repositoryDetails == null) {
   if (other.repositoryDetails != null) {
    return false;
   }
  } else if (!repositoryDetails.equals(other.repositoryDetails)) {
   return false;
  }
  if (repositoryHooks == null) {
   if (other.repositoryHooks != null) {
    return false;
   }
  } else if (!repositoryHooks.equals(other.repositoryHooks)) {
   return false;
  }
  if (repositoryPermissions == null) {
   if (other.repositoryPermissions != null) {
    return false;
   }
  } else if (!repositoryPermissions.equals(other.repositoryPermissions)) {
   return false;
  }
  return true;
 }

 @Override
 public String toString() {
  return "SsfbRepoSettings [fromProject=" + fromProject + ", fromRepo=" + fromRepo + ", repositoryDetails="
    + repositoryDetails + ", repositoryHooks=" + repositoryHooks + ", repositoryPermissions=" + repositoryPermissions
    + ", branchPermissions=" + branchPermissions + ", pullRequestSettings=" + pullRequestSettings + ", branchingModel="
    + branchingModel + "]";
 }

}
