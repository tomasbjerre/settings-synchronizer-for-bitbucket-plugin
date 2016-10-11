package se.bjurr.ssfb.admin.dto;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(FIELD)
public class SsfbSyncDTO {
 public String projectKey;
 public String repoSlug;

 public SsfbSyncDTO() {
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
  SsfbSyncDTO other = (SsfbSyncDTO) obj;
  if (this.projectKey == null) {
   if (other.projectKey != null) {
    return false;
   }
  } else if (!this.projectKey.equals(other.projectKey)) {
   return false;
  }
  if (this.repoSlug == null) {
   if (other.repoSlug != null) {
    return false;
   }
  } else if (!this.repoSlug.equals(other.repoSlug)) {
   return false;
  }
  return true;
 }

 public String getProjectKey() {
  return this.projectKey;
 }

 public String getRepoSlug() {
  return this.repoSlug;
 }

 @Override
 public int hashCode() {
  final int prime = 31;
  int result = 1;
  result = prime * result + ((this.projectKey == null) ? 0 : this.projectKey.hashCode());
  result = prime * result + ((this.repoSlug == null) ? 0 : this.repoSlug.hashCode());
  return result;
 }

 public void setProjectKey(String projectKey) {
  this.projectKey = projectKey;
 }

 public void setRepoSlug(String repoSlug) {
  this.repoSlug = repoSlug;
 }

 @Override
 public String toString() {
  return "SsfbSyncDTO [projectKey=" + this.projectKey + ", repoSlug=" + this.repoSlug + "]";
 }
}
