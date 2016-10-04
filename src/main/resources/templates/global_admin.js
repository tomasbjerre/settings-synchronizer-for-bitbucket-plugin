define('plugin/ssfb/globaladmin', [
 'jquery',
 'aui',
 'plugin/ssfb/common'
], function($, AJS, common) {

 $(document).ready(function() {
  var globalRepoAdminUrl = AJS.contextPath() + "/rest/ssfb/1.0/globaladmin"; 
  $.getJSON(globalRepoAdminUrl, function(data) {
   common.setupRepoSettingsForm(data);
  });

  $("#repoadmin").submit(function(e) {
   e.preventDefault();
   var repoAdminUrl = AJS.contextPath() + "/rest/ssfb/1.0/projects/" + projectKey + "/repos/" + repoSlug + "/repoadmin"; 
   common.postForm(repoAdminUrl, '#repoadmin');
  });

  $(".sync-now").click(function(e) {
   e.preventDefault();
   common.syncNow('', '');
  });

  $("#globalrepoadmin").submit(function(e) {
   e.preventDefault();
   var repoAdminUrl = AJS.contextPath() + "/rest/ssfb/1.0/repoadmin"; 
   common.postForm(repoAdminUrl, '#globalrepoadmin');
  });
 });
});

AJS.$(document).ready(function() {
 require('plugin/ssfb/globaladmin');
});
