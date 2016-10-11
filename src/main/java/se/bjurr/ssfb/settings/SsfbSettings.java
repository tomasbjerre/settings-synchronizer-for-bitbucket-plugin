package se.bjurr.ssfb.settings;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newTreeMap;

import java.util.Map;

import com.google.common.base.Optional;

public class SsfbSettings {

 public static class Builder {

  private Map<String, SsfbRepoSettings> repoSettings = newTreeMap();

  private Builder() {
  }

  public SsfbSettings build() {
   return new SsfbSettings(this);
  }

  public Builder setRepoSettings(Map<String, SsfbRepoSettings> repoSettings) {
   this.repoSettings = repoSettings;
   return this;
  }

  public Builder setRepoSettings(String projectKey, String repoSlug, SsfbRepoSettings expectedSsfbRepoSettings) {
   String key = repoSettingsKey(projectKey, repoSlug);
   this.repoSettings.put(key, expectedSsfbRepoSettings);
   return this;
  }
 }

 public static Builder ssfbSettingsBuilder() {
  return new Builder();
 }

 public static Builder ssfbSettingsBuilder(SsfbSettings from) {
  return new Builder()//
    .setRepoSettings(from.getRepoSettings());
 }

 private static String repoSettingsKey(String projectKey, String repoSlug) {
  return projectKey + "-" + repoSlug;
 }

 private final Map<String, SsfbRepoSettings> repoSettings;

 public SsfbSettings(Builder builder) {
  this.repoSettings = checkNotNull(builder.repoSettings, "repoSettings");
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
  SsfbSettings other = (SsfbSettings) obj;
  if (this.repoSettings == null) {
   if (other.repoSettings != null) {
    return false;
   }
  } else if (!this.repoSettings.equals(other.repoSettings)) {
   return false;
  }
  return true;
 }

 public Optional<SsfbRepoSettings> findRepoSettings(String projectKey, String repoSlug) {
  return fromNullable(this.repoSettings.get(repoSettingsKey(projectKey, repoSlug)));
 }

 @Override
 public int hashCode() {
  final int prime = 31;
  int result = 1;
  result = prime * result + ((this.repoSettings == null) ? 0 : this.repoSettings.hashCode());
  return result;
 }

 public void setRepoSettings(String projectKey, String repoSlug, SsfbRepoSettings ssfbRepoSettings) {
  this.repoSettings.put(repoSettingsKey(projectKey, repoSlug), ssfbRepoSettings);
 }

 @Override
 public String toString() {
  return "SsfbSettings [repoSettings=" + this.repoSettings + "]";
 }

 private Map<String, SsfbRepoSettings> getRepoSettings() {
  return this.repoSettings;
 }

}
