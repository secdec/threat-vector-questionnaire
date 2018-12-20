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
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionnaireResult {
	
	public static enum Answer {
		NOT_AVAILABLE,
		NO,
		YES,
		NOT_SURE;
		
		public String printName() {
			
			String[] arr = this.name().toLowerCase().split("_");
		    StringBuffer sb = new StringBuffer();

		    for (int i = 0; i < arr.length; i++) {
		        sb.append(Character.toUpperCase(arr[i].charAt(0)))
		            .append(arr[i].substring(1)).append(" ");
		    }          
		    return sb.toString().trim();
		}
		
		public static Answer valueOf(int ordinal) {
			return Answer.values()[ordinal];
		}
	}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private long id;
	@Column private String name;
	@Column(length=512) private String answers;
	
	public QuestionnaireResult() {}
	
	public QuestionnaireResult(String name, List<Answer> answers) {
		this.name = name;
		saveAnswers(answers);
	}
	
	public void saveAnswers(List<Answer> answers) {
		
		StringBuffer sb = new StringBuffer();
		Iterator<Answer> iterator = answers.iterator();
		while(iterator.hasNext()){
			sb.append(iterator.next().ordinal());
			
			if(iterator.hasNext()) {
				sb.append(",");
			}
		}
		
		this.answers = sb.toString();	
	}
	
	public List<Answer> getAnswers(){
		
		List<Answer> convertedAnswers = new ArrayList<>();
		
		for(String answerOrdinal: this.answers.split(",")) {
			Answer answer = Answer.valueOf(Integer.parseInt(answerOrdinal));
			convertedAnswers.add(answer);
		}
		
		return convertedAnswers;
		
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static void main(String[] args) {
		
		QuestionnaireResult result = new QuestionnaireResult();
		List<Answer> answers = new ArrayList<>();
		answers.add(Answer.NOT_AVAILABLE);
		answers.add(Answer.YES);
		answers.add(Answer.NOT_SURE);
		answers.add(Answer.NO);
		answers.add(Answer.NOT_AVAILABLE);
		answers.add(Answer.YES);
		answers.add(Answer.NOT_SURE);
		answers.add(Answer.NO);
		answers.add(Answer.NOT_AVAILABLE);
		answers.add(Answer.YES);
		answers.add(Answer.NOT_SURE);
		answers.add(Answer.NO);
		answers.add(Answer.NOT_AVAILABLE);
		answers.add(Answer.YES);
		answers.add(Answer.NOT_SURE);
		answers.add(Answer.NO);
		answers.add(Answer.NOT_AVAILABLE);
		answers.add(Answer.YES);
		answers.add(Answer.NOT_SURE);
		answers.add(Answer.NO);

		
		result.saveAnswers(answers);
		
		System.out.println(result.getAnswers());
	}
}
