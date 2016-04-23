# Settings Synchronizer for Bitbucket Changelog

Changelog of Settings Synchronizer for Bitbucket.

## Unreleased
### GitHub [#7](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/issues/7) Use transactionTemplate

**Using transactionTemplate #7**

 * When loading and persisting plugin settings. 

[55236900743ea29](https://github.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/commit/55236900743ea29) Tomas Bjerre *2016-04-23 07:52:59*


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


