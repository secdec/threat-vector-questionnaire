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
import java.util.Collections;
import java.util.List;

import com.threatintell.threatquestionnaire.questionnaire.QuestionnaireResult.Answer;

public class Question {

	private static final int ID_COL = 0;
	private static final int QUESTION_COL = 1;
	private static final int ANSWER_YES_COL = 2;
	private static final int ANSWER_NO_COL = 3;
	private static final int PREREQ_COL = 4;
	private static final int TAG_COL = 5;
	
	private Integer id;
	private String text;
	private String tags;
	private List<Long> yesCawes;
	private List<Long> noCawes;
	private List<Integer> prereqs;
	
	public static Question fromCSVLine(String[] questionLine) {
		
		Question question = new Question();
		question.setId(Integer.parseInt(questionLine[ID_COL].trim()));
		question.setText(questionLine[QUESTION_COL].trim());
		question.setYesCaweFromString(questionLine[ANSWER_YES_COL].trim());
		question.setNoCaweFromString(questionLine[ANSWER_NO_COL].trim());
		question.setPrereqFromString(questionLine[PREREQ_COL].trim());
		question.setTags(questionLine[TAG_COL].trim());
		
		return question;
	}
	
	public Integer getId() {
		return id;
	}
	
	private void setId(int id) {
		this.id = id;
		
	}
	
	public String getText() {
		return this.text;
	}
	
	private void setText(String text) {
		this.text = text;
	}
	
	public String getTags() {
		return this.tags;
	}
	
	private void setTags(String tags) {
		this.tags = tags;
	}
	
	private void setYesCaweFromString(String yesCawes) {
		
		this.yesCawes = new ArrayList<>();
		if(!yesCawes.isEmpty()) {
			String[] splitCawes = yesCawes.split(";");
			if(splitCawes.length > 0) {
				for(String caweId: splitCawes) {
					this.yesCawes.add(Long.parseLong(caweId.trim()));
				}
			}
		}
	}
	
	private void setNoCaweFromString(String noCawes) {
		
		this.noCawes = new ArrayList<>();
		if(!noCawes.isEmpty()) {
			String[] splitCawes = noCawes.split(";");
			for(String caweId: splitCawes) {
				this.noCawes.add(Long.parseLong(caweId.trim()));
			}
		}
	}
	
	private void setPrereqFromString(String prereqs) {
		this.prereqs = new ArrayList<>();
		if(!prereqs.isEmpty()) {
			String[] splitPrereqs = prereqs.split(";");
			if(splitPrereqs.length > 0) {
				for(String caweId: splitPrereqs) {
					int prereqId = Integer.parseInt(caweId.trim());
					if(prereqId > 0) {
						this.prereqs.add(prereqId);
					}
				}
			}
		}
	}


	public List<Long> getCawesFromAnswer(Answer answer){
		if(answer == Answer.YES) {
			return yesCawes;
		} else if(answer == Answer.NO) {
			return noCawes;
		} else if(answer == Answer.NOT_SURE) {
			List<Long> caweIds = new ArrayList<>();
			caweIds.addAll(yesCawes);
			caweIds.addAll(noCawes);
			return caweIds;
		} else {
			return Collections.emptyList();
		}
	}
	
	public String toString() {
		
		String question = "";
		question += (text + "\n");
		question += "Answer Yes: " + yesCawes.toString() + "\n";
		question += "Answer No: " + noCawes.toString() + "\n";
		question += "Prereqs: " + prereqs.toString() + "\n\n";
		
		return question;
	}
}
