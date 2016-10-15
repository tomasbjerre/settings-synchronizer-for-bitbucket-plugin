# Settings Synchronizer for Bitbucket Changelog

Changelog of Settings Synchronizer for Bitbucket.

## Unreleased
### No issue

**doc**


[88121011e7dce05](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/88121011e7dce05) Tomas Bjerre *2016-10-14 18:25:23*


## 1.7
### GitHub [#10](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/issues/10) Ability to choose what hook to sync

**Adding possibility to select which hooks to sync #9 #10**

 * Doing batch save in backend #9. After issues in a 10000+ repos installation. 
 * Removing dead code. 
 * Allowing admin to pick the hooks that should be synced. 

[ab9ef6fee7c858a](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/ab9ef6fee7c858a) Tomas Bjerre *2016-10-14 18:11:01*


### GitHub [#9](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/issues/9) Tried and working in Test but not working in Production

**Adding possibility to select which hooks to sync #9 #10**

 * Doing batch save in backend #9. After issues in a 10000+ repos installation. 
 * Removing dead code. 
 * Allowing admin to pick the hooks that should be synced. 

[ab9ef6fee7c858a](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/ab9ef6fee7c858a) Tomas Bjerre *2016-10-14 18:11:01*


## 1.6
### GitHub [#9](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/issues/9) Tried and working in Test but not working in Production

**Revert "Doing batch save in backend #9"**

 * This reverts commit 486107f52975705c22f0be98c02ee66769342012. 

[ad5ec2e259e5b74](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/ad5ec2e259e5b74) Tomas Bjerre *2016-10-13 19:02:51*


## 1.5
### GitHub [#9](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/issues/9) Tried and working in Test but not working in Production

**Doing batch save in backend #9**

 * For a repo with 10000+ repos, doing it in frontend may result in CANCELLED requests. 

[486107f52975705](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/486107f52975705) Tomas Bjerre *2016-10-04 15:01:53*


### No issue

**Only formatting files in src with jsbeautifier**


[9bc354f122e3503](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/9bc354f122e3503) Tomas Bjerre *2016-05-07 06:21:36*

**doc**


[7f00708515f2e13](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/7f00708515f2e13) Tomas Bjerre *2016-04-26 04:18:07*


## 1.4
### GitHub [#7](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/issues/7) Use transactionTemplate

**Handling settings synchronized #7**


[ef056a410a25503](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/ef056a410a25503) Tomas Bjerre *2016-04-23 11:06:14*


## 1.3
### GitHub [#7](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/issues/7) Use transactionTemplate

**Using transactionTemplate #7**

 * When loading and persisting plugin settings. 

[d2b96dc51f8bdd6](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/d2b96dc51f8bdd6) Tomas Bjerre *2016-04-23 07:54:48*


### GitHub [#8](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/issues/8) Deny user if not logged in

**Only allowing admins to configure plugin #8**

 * Also making sure repo admin page does not crash on initial load. 

[6fdf2c8a979dd4f](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/6fdf2c8a979dd4f) Tomas Bjerre *2016-04-23 07:37:00*


### No issue

**Using 999999 as limit in rest calls**


[fdeee27fcf9138d](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/fdeee27fcf9138d) Tomas Bjerre *2016-04-22 18:43:23*


## 1.2
### No issue

**Syncing all repos, not only the first 25**

 * And disabling buttons while ajax requests are in progress. 

[4a4035fbfa8fe99](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/4a4035fbfa8fe99) Tomas Bjerre *2016-04-21 16:43:50*


## 1.1
### No issue

**Enabling plugin after settings saved**

 * Was causing error if plugin was enabled before settings had been applied. 
 * Also changing global repo admin js so that settings are saved with ajax. 

[a246b8c86c3aae3](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/a246b8c86c3aae3) Tomas Bjerre *2016-04-20 19:47:42*

**Doc**


[4a28d1900bf114e](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/4a28d1900bf114e) Tomas Bjerre *2016-04-19 18:44:54*


## 1.0
### No issue

**Initial commit**

 * Global and repo level config. 
 * Batch config all repos on global level. 
 * Synchronization settings for hooks. 

[35216699cf747ef](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/35216699cf747ef) Tomas Bjerre *2016-04-19 18:36:35*


