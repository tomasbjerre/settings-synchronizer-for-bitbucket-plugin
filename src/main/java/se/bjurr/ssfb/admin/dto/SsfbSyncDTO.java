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

 public String getProjectKey() {
  return projectKey;
 }

 public String getRepoSlug() {
  return repoSlug;
 }

 public void setProjectKey(String projectKey) {
  this.projectKey = projectKey;
 }

 public void setRepoSlug(String repoSlug) {
  this.repoSlug = repoSlug;
 }
}
