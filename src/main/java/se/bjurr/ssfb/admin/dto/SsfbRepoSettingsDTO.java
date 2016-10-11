package se.bjurr.ssfb.admin.dto;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(FIELD)
public class SsfbRepoSettingsDTO {
 public String fromProject;
 public String fromRepo;
 public List<String> hookConfigurationKeysToSync;

 public SsfbRepoSettingsDTO() {
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
  SsfbRepoSettingsDTO other = (SsfbRepoSettingsDTO) obj;
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

 public void setFromProject(String fromProject) {
  this.fromProject = fromProject;
 }

 public void setFromRepo(String fromRepo) {
  this.fromRepo = fromRepo;
 }

 public void setHookConfigurationKeysToSync(List<String> hookConfigurationKeysToSync) {
  this.hookConfigurationKeysToSync = hookConfigurationKeysToSync;
 }

 @Override
 public String toString() {
  return "SsfbRepoSettingsDTO [fromProject=" + this.fromProject + ", fromRepo=" + this.fromRepo
    + ", hookConfigurationKeysToSync=" + this.hookConfigurationKeysToSync + "]";
 }
}
