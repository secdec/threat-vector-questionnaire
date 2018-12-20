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
package com.threatintell.threatquestionnaire.questionnaire;

import java.util.ArrayList;
import java.util.List;

import com.threatintell.threatquestionnaire.questionnaire.QuestionnaireResult.Answer;

public class Questionnaire {
	
	List<Question> questions;
	
	public Questionnaire() {
		this.questions = new ArrayList<>();
	}
	
	public static Questionnaire fromFile(String filepath) {
		return QuestionnaireParser.parse(filepath);
	}
	
	void addQuestion(Question question) {
		questions.add(question);
	}
	
	public List<Long> getCawesFromResult(QuestionnaireResult result){
		
		int numberOfQuestions = questions.size();
		List<Long> caweIds = new ArrayList<>();
		
		List<Answer> answers = result.getAnswers();
		
		for(int i = 0; i < numberOfQuestions; i++) {
			caweIds.addAll(questions.get(i).getCawesFromAnswer(answers.get(i)));
		}
		
		return caweIds;
	}
	
	public List<Answer> getDefaultResponses(){
		List<Answer> responses = new ArrayList<>(questions.size());
		for(int i = 0; i < questions.size(); i++) {
			responses.add(i, Answer.NOT_AVAILABLE);
		}
		
		return responses;
	}
	
	public List<Answer> getResponses(QuestionnaireResult result){
		return result.getAnswers();
	}
	
	public List<Question> getQuestions(){
		return this.questions;
	}
	
	public String toString() {
		
		String questions = "";
		
		for(Question question: this.questions) {
			questions += question.toString();
		}
		
		return questions;
	}
}
