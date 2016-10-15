define('plugin/ssfb/globaladmin', [
 'jquery',
 'aui',
 'plugin/ssfb/common'
], function($, AJS, common) {

 $(document).ready(function() {
  common.setupRepoSettingsForm(undefined);

  $("#repoadmin").submit(function(e) {
   e.preventDefault();
   var repoAdminUrl = AJS.contextPath() + "/rest/ssfb/1.0/projects/" + projectKey + "/repos/" + repoSlug + "/globaladmin"; 
   common.postForm(repoAdminUrl, '#repoadmin');
  });

  $(".ssfb-sync-now").click(function(e) {
   e.preventDefault();
   common.syncNow('', '');
  });

  $(".ssfb-save").click(function(e) {
   e.preventDefault();
   var repoAdminUrl = AJS.contextPath() + "/rest/ssfb/1.0/repoadmin"; 
   common.postForm(repoAdminUrl, '#globalrepoadmin');
  });
 });
});

AJS.$(document).ready(function() {
 require('plugin/ssfb/globaladmin');
});
