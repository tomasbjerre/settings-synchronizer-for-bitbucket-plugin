define('plugin/ssfb/common', [
 'jquery',
 'plugin/ssfb/3rdparty'
], function($, rdparty) {

 function postForm(url, formId) {
  var data = $(formId).serializeJSON();
  $.ajax({
   url: url,
   type: "POST",
   contentType: "application/json; charset=utf-8",
   dataType: "json",
   data: data,
   success: function(data) {
    AJS.flag({
     close: 'auto',
     type: 'success',
     title: 'Saved',
     body: '<p>=)</p>'
    });
   },
   error: function(xhr, status, error) {
    AJS.flag({
     close: 'auto',
     type: 'error',
     title: 'Not saved',
     body: '<p>:(</p>'
    });
   }
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

 function populateRepos(data, repoSettings) {
  $('#fromRepo').empty();
  for (var i = 0; i < data.values.length; i++) {
   var repoSlug = data.values[i].slug;
   if (repoSettings && repoSlug === repoSettings.fromRepo) {
    $('#fromRepo').append('<option value="' + repoSlug + '" selected>' + repoSlug + '</option>');
   } else {
    $('#fromRepo').append('<option value="' + repoSlug + '">' + repoSlug + '</option>');
   }
  }

  addHookCheckboxes(repoSettings);
  $('#fromRepo').change(function() {
   addHookCheckboxes(repoSettings);
  });
 }

 function getRepos(projectKey, whenDone) {
  var reposUrl = AJS.contextPath() + "/rest/api/1.0/projects/" + projectKey + "/repos?limit=999999"; 
  $.getJSON(reposUrl, function(data) {
   whenDone(data);
  });
 }

 function setupRepoSettingsForm(repoSettings) {
  getProjects(function(data) {
   if (repoSettings) {
    populateProjects(data, repoSettings.fromProject);
   } else {
    populateProjects(data, undefined);
   }
   getRepos($('#fromProject').val(), function(data) {
    if (repoSettings) {
     populateRepos(data, repoSettings);
    } else {
     populateRepos(data, undefined);
    }
   });
  });

  $('#fromProject').change(function() {
   getRepos($('#fromProject').val(), function(data) {
    if (repoSettings) {
     populateRepos(data, repoSettings);
    } else {
     populateRepos(data, undefined);
    }
   });
  });
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

 function addHookCheckboxes(repoSettings) {
  var projectKey = $('#fromProject').val();
  var repoSlug = $('#fromRepo').val();
  var hooksUrl = AJS.contextPath() + "/rest/api/1.0/projects/" + projectKey + "/repos/" + repoSlug + "/settings/hooks"; 
  var $hooksArea = $("#hookcheckboxes");
  $hooksArea.empty();
  $.getJSON(hooksUrl, function(data) {
   for (var i = 0; i < data.values.length; i++) {
    var hook = data.values[i];
    var $hookHtml = $(".hook-checkbox-template").clone();
    $hookHtml.removeClass('hook-checkbox-template');
    $hookHtml.find('input').attr('value', hook.details.key);
    $hookHtml.find('label').html(hook.details.name);
    if (repoSettings && repoSettings.hookConfigurationKeysToSync) {
     var isChecked = $.inArray(hook.details.key, repoSettings.hookConfigurationKeysToSync) != -1;
     $hookHtml.find('input').attr('checked', isChecked);
    }
    $hooksArea.append($hookHtml);
   }
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
