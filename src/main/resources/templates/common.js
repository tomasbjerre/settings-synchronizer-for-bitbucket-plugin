define('plugin/ssfb/common', [
 'jquery',
], function($) {

 function postForm(url, formId) {
  var data = $(formId).serializeArray().reduce(function(obj, v) {
   if (v.value === 'on') {
    obj[v.name] = true;
   } else {
    obj[v.name] = v.value;
   }
   return obj;
  }, {});
  var jsonString = JSON.stringify(data);
  $.ajax({
   url: url,
   type: "POST",
   contentType: "application/json; charset=utf-8",
   dataType: "json",
   data: jsonString
  });
 }

 function populateProjects(data, selected) {
  $('#fromProject').empty();
  for (var i = 0; i < data.values.length; i++) {
   var projectKey = data.values[i].key;
   if (projectKey === selected) {
    $('#fromProject').append('<option value="' + projectKey + '" selected>' + projectKey + '</option>');
   } else {
    $('#fromProject').append('<option value="' + projectKey + '">' + projectKey + '</option>');
   }
  }
 }

 function getProjects(whenDone) {
  var projectsUrl = AJS.contextPath() + "/rest/api/1.0/projects?limit=999999"; 
  $.getJSON(projectsUrl, function(data) {
   whenDone(data);
  });
 }

 function populateRepos(data, selected) {
  $('#fromRepo').empty();
  for (var i = 0; i < data.values.length; i++) {
   var repoSlug = data.values[i].slug;
   if (repoSlug === selected) {
    $('#fromRepo').append('<option value="' + repoSlug + '" selected>' + repoSlug + '</option>');
   } else {
    $('#fromRepo').append('<option value="' + repoSlug + '">' + repoSlug + '</option>');
   }
  }
 }

 function getRepos(projectKey, whenDone) {
  var reposUrl = AJS.contextPath() + "/rest/api/1.0/projects/" + projectKey + "/repos?limit=999999"; 
  $.getJSON(reposUrl, function(data) {
   whenDone(data);
  });
 }

 function setupRepoSettingsForm(repoSettings) {
  getProjects(function(data) {
   populateProjects(data, repoSettings.fromProject);
   getRepos($('#fromProject').val(), function(data) {
    populateRepos(data, repoSettings.fromRepo);
   });
  });

  $('#fromProject').change(function() {
   getRepos($('#fromProject').val(), function(data) {
    populateRepos(data, repoSettings.fromProject);
   });
  });

  $('#repositoryDetails').attr('checked', repoSettings.repositoryDetails);
  $('#repositoryHooks').attr('checked', repoSettings.repositoryHooks);
  $('#repositoryPermissions').attr('checked', repoSettings.repositoryPermissions);
  $('#branchPermissions').attr('checked', repoSettings.branchPermissions);
  $('#pullRequestSettings').attr('checked', repoSettings.pullRequestSettings);
  $('#branchingModel').attr('checked', repoSettings.branchingModel);
 }

 function syncNow(projectKey, repoSlug) {
  var syncUrl = AJS.contextPath() + "/rest/ssfb/1.0/globalsync"; 
  var data = {
   projectKey: projectKey,
   repoSlug: repoSlug
  };
  var jsonString = JSON.stringify(data);
  $.ajax({
   url: syncUrl,
   type: "POST",
   contentType: "application/json; charset=utf-8",
   dataType: "json",
   data: jsonString
  });
 }

 function forAllRepos(func) {
  getProjects(function(projectData) {
   for (var i = 0; i < projectData.values.length; i++) {
    var projectKey = projectData.values[i].key;
    getRepos(projectKey, function(repoData) {
     for (var i = 0; i < repoData.values.length; i++) {
      func(repoData.values[i]);
     }
    });
   }
  });
 }

 $(document)
  .ajaxStart(function() {
   $('.ssfb button').attr('aria-disabled', 'true');
  })
  .ajaxStop(function() {
   $('.ssfb button').attr('aria-disabled', 'false');
  });

 return {
  postForm: postForm,
  syncNow: syncNow,
  getRepos: getRepos,
  getProjects: getProjects,
  forAllRepos: forAllRepos,
  setupRepoSettingsForm: setupRepoSettingsForm
 }
});
