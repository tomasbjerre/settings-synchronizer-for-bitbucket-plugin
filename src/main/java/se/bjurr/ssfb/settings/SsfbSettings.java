package se.bjurr.ssfb.settings;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newTreeMap;
import static se.bjurr.ssfb.settings.SyncEvery.NEVER;

import java.util.Map;

import com.google.common.base.Optional;

public class SsfbSettings {
 public static class Builder {

  private Map<String, SsfbRepoSettings> repoSettings = newTreeMap();
  private String startTime = "00:00";
  private SyncEvery syncEvery = NEVER;

  private Builder() {
  }

  public Builder setRepoSettings(Map<String, SsfbRepoSettings> repoSettings) {
   this.repoSettings = repoSettings;
   return this;
  }

  public Builder setStartTime(String startTime) {
   this.startTime = startTime;
   return this;
  }

  public Builder setSyncEvery(SyncEvery syncEvery) {
   this.syncEvery = syncEvery;
   return this;
  }

  public SsfbSettings build() {
   return new SsfbSettings(this);
  }

  public Builder setRepoSettings(String projectKey, String repoSlug, SsfbRepoSettings expectedSsfbRepoSettings) {
   String key = repoSettingsKey(projectKey, repoSlug);
   repoSettings.put(key, expectedSsfbRepoSettings);
   return this;
  }
 }

 private final Map<String, SsfbRepoSettings> repoSettings;
 private final String startTime;
 private final SyncEvery syncEvery;

 public SsfbSettings(Builder builder) {
  repoSettings = checkNotNull(builder.repoSettings, "repoSettings");
  startTime = checkNotNull(builder.startTime);
  syncEvery = checkNotNull(builder.syncEvery, "syncEvery");
 }

 public Optional<SsfbRepoSettings> findRepoSettings(String projectKey, String repoSlug) {
  return fromNullable(repoSettings.get(repoSettingsKey(projectKey, repoSlug)));
 }

 public void setRepoSettings(String projectKey, String repoSlug, SsfbRepoSettings ssfbRepoSettings) {
  repoSettings.put(repoSettingsKey(projectKey, repoSlug), ssfbRepoSettings);
 }

 private static String repoSettingsKey(String projectKey, String repoSlug) {
  return projectKey + "-" + repoSlug;
 }

 public static Builder ssfbSettingsBuilder() {
  return new Builder();
 }

 public static Builder ssfbSettingsBuilder(SsfbSettings from) {
  return new Builder()//
    .setRepoSettings(from.getRepoSettings())//
    .setStartTime(from.getStartTime())//
    .setSyncEvery(from.getSyncEvery());
 }

 private Map<String, SsfbRepoSettings> getRepoSettings() {
  return repoSettings;
 }

 public String getStartTime() {
  return startTime;
 }

 public SyncEvery getSyncEvery() {
  return syncEvery;
 }

}
