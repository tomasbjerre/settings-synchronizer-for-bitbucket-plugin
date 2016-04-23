define('plugin/ssfb/repoadmin', [
 'jquery',
 'aui',
 'bitbucket/util/state',
 'plugin/ssfb/common'
], function($, AJS, pageState, common) {
 var projectKey = pageState.getProject().key;
 var repoSlug = pageState.getRepository().slug;
 var repoAdminUrl = AJS.contextPath() + "/rest/ssfb/1.0/projects/" + projectKey + "/repos/" + repoSlug + "/repoadmin"; 

 $(document).ready(function() {
  $.getJSON(repoAdminUrl, function(data) {
   common.setupRepoSettingsForm(data);
  }).fail(function() {
   common.setupRepoSettingsForm(undefined);
  });

  $("#repoadmin").submit(function(e) {
   e.preventDefault();
   common.postForm(repoAdminUrl, '#repoadmin');
  });

  $(".sync-now").click(function(e) {
   common.syncNow(projectKey, repoSlug);
  });
 });
});

AJS.$(document).ready(function() {
 require('plugin/ssfb/repoadmin');
});
