//
//  Threat Vector Questionnaire
//
//  Copyright (C) 2018 Applied Visions - http://securedecisions.com
//
//  Written by AITEK Security - http://aiteksecurity.com
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
$(window).load(function () {
	
	$("#importGit").hide();
	$("#uploadFile").hide();
	$("#backButtonDiv").hide();
	
	$( "#gitHover" ).hover(
			  function() {
			    $("#gitPrompt").show();
			  }, function() {
			    $("#gitPrompt").hide();
			  }
			);
	
	$( "#fileHover" ).hover(
			  function() {
			    $("#filePrompt").show();
			  }, function() {
			    $("#filePrompt").hide();
			  }
	);

	$("#importGitButton").click(function(){
		$("#projectTypeSelect").hide();
		$("#importGit").show();
		$("#toProjects").hide();
		$("#backButtonDiv").show();
	})
	
	$("#uploadFileButton").click(function(){
		$("#projectTypeSelect").hide();
		$("#uploadFile").show();
		$("#toProjects").hide();
		$("#backButtonDiv").show();
	})
	
	$(".backButton").click(function(){
		$("#importGit").hide();
		$("#uploadFile").hide();
		$("#projectTypeSelect").show();
		$("#toProjects").show();
		$("#backButtonDiv").hide();

	})
	
})