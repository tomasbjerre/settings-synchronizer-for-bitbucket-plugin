package se.bjurr.ssfb.settings;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class SsfbRepoSettings {
 public static class Builder {
  public List<String> hookConfigurationKeysToSync = newArrayList();
  private String fromProject;
  private String fromRepo;

  private Builder() {
  }

  public SsfbRepoSettings build() {
   return new SsfbRepoSettings(this);
  }

  public Builder setFromProject(String fromProject) {
   this.fromProject = fromProject;
   return this;
  }

  public Builder setFromRepo(String fromRepo) {
   this.fromRepo = fromRepo;
   return this;
  }

  public Builder setHookConfigurationKeysToSync(List<String> hookConfigurationKeysToSync) {
   if (hookConfigurationKeysToSync == null) {
    this.hookConfigurationKeysToSync = newArrayList();
   } else {
    this.hookConfigurationKeysToSync = hookConfigurationKeysToSync;
   }
   return this;
  }

 }

 public static Builder ssfbRepoSettingsBuilder() {
  return new Builder();
 }

 private final String fromProject;

 private final String fromRepo;

 private final List<String> hookConfigurationKeysToSync;

 public SsfbRepoSettings(Builder builder) {
  this.fromProject = checkNotNull(builder.fromProject, "fromProject");
  this.fromRepo = checkNotNull(builder.fromRepo, "fromRepo");
  this.hookConfigurationKeysToSync = checkNotNull(builder.hookConfigurationKeysToSync, "hookConfigurationKeysToSync");
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
  if (this.fromProject == null) {
   if (other.fromProject != null) {
    return false;
   }
  } else if (!this.fromProject.equals(other.fromProject)) {
   return false;
  }
  if (this.fromRepo == null) {
   if (other.fromRepo != null) {
    return false;
   }
  } else if (!this.fromRepo.equals(other.fromRepo)) {
   return false;
  }
  if (this.hookConfigurationKeysToSync == null) {
   if (other.hookConfigurationKeysToSync != null) {
    return false;
   }
  } else if (!this.hookConfigurationKeysToSync.equals(other.hookConfigurationKeysToSync)) {
   return false;
  }
  return true;
 }

 public String getFromProject() {
  return this.fromProject;
 }

 public String getFromRepo() {
  return this.fromRepo;
 }

 public List<String> getHookConfigurationKeysToSync() {
  return this.hookConfigurationKeysToSync;
 }

 @Override
 public int hashCode() {
  final int prime = 31;
  int result = 1;
  result = prime * result + ((this.fromProject == null) ? 0 : this.fromProject.hashCode());
  result = prime * result + ((this.fromRepo == null) ? 0 : this.fromRepo.hashCode());
  result = prime * result
    + ((this.hookConfigurationKeysToSync == null) ? 0 : this.hookConfigurationKeysToSync.hashCode());
  return result;
 }

 @Override
 public String toString() {
  return "SsfbRepoSettings [fromProject=" + this.fromProject + ", fromRepo=" + this.fromRepo
    + ", hookConfigurationKeysToSync=" + this.hookConfigurationKeysToSync + "]";
 }

}
