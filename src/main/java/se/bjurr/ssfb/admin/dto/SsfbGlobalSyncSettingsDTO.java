package se.bjurr.ssfb.admin.dto;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(FIELD)
public class SsfbGlobalSyncSettingsDTO {
 public String startTime;
 public String syncEvery;

 public SsfbGlobalSyncSettingsDTO() {
 }

 public void setStartTime(String startTime) {
  this.startTime = startTime;
 }

 public void setSyncEvery(String syncEvery) {
  this.syncEvery = syncEvery;
 }

 public String getStartTime() {
  return startTime;
 }

 public String getSyncEvery() {
  return syncEvery;
 }
}
