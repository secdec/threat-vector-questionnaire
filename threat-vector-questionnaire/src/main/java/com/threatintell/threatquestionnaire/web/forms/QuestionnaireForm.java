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
package com.threatintell.threatquestionnaire.web.forms;

import java.util.List;

import com.threatintell.threatquestionnaire.questionnaire.QuestionnaireResult;
import com.threatintell.threatquestionnaire.questionnaire.QuestionnaireResult.Answer;

public class QuestionnaireForm {

	long id;
	String name;
	List<Answer> answers;
	
	public QuestionnaireForm() {}
	
	public QuestionnaireForm(List<Answer> answers) {
		this.answers = answers;
	}
	
	public QuestionnaireResult toResult() {
		QuestionnaireResult result = new QuestionnaireResult(name, answers);
		
		if(id > 0) {
			result.setId(this.getId());
		}
		
		return result;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Answer> getAnswers(){
		return this.answers;
	}
	
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
}
