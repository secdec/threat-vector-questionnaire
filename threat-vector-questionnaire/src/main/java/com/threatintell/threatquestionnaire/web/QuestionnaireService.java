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
package com.threatintell.threatquestionnaire.web;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threatintell.threatquestionnaire.dao.repositories.QuestionnaireRepository;
import com.threatintell.threatquestionnaire.questionnaire.Questionnaire;
import com.threatintell.threatquestionnaire.questionnaire.QuestionnaireResult;

@Service
public class QuestionnaireService {

	@PersistenceContext
	EntityManager entityManager;
	
	private Questionnaire questionnaire;
	private QuestionnaireRepository questionnaireRepository;
	
	@Autowired
	public QuestionnaireService(Questionnaire questionnaire, QuestionnaireRepository questionnaireRepository) {
		this.questionnaire = questionnaire;
		this.questionnaireRepository = questionnaireRepository;
	}
	
	public Questionnaire getQuestionnaire() {
		return this.questionnaire;
	}
	
	public QuestionnaireResult save(QuestionnaireResult result) {
		return questionnaireRepository.save(result);
	}
	
	public QuestionnaireResult findOne(long resultId) {
		return questionnaireRepository.findOne(resultId);
	}
	
	public List<QuestionnaireResult> findAll(){
		return questionnaireRepository.findAll();
	}
}
