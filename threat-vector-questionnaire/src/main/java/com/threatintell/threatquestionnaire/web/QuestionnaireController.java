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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.threatintell.threatquestionnaire.dao.CAWEService;
import com.threatintell.threatquestionnaire.model.CAWE;
import com.threatintell.threatquestionnaire.model.Threat;
import com.threatintell.threatquestionnaire.questionnaire.Questionnaire;
import com.threatintell.threatquestionnaire.questionnaire.QuestionnaireResult;
import com.threatintell.threatquestionnaire.threatgenerator.XmlParser;
import com.threatintell.threatquestionnaire.web.forms.QuestionnaireForm;


@Controller
@RequestMapping("/")
public class QuestionnaireController {
	
	private Questionnaire questionnaire;
	private QuestionnaireService questionnaireService;
	private CAWEService caweService;
	
	@Autowired
	public QuestionnaireController(Questionnaire questionnaire, QuestionnaireService questionnaireService, CAWEService caweService) {
		this.questionnaire = questionnaire;
		this.questionnaireService = questionnaireService;
		this.caweService = caweService;
	}
	
	@RequestMapping
	public String questionnaireList(ModelMap model) {
		model.addAttribute("questionnaires", questionnaireService.findAll());
		return "questionnaireList";
	}
	
	@RequestMapping("/new")
	public String newQuestionnaire(ModelMap model) {
		model.addAttribute("questions", questionnaire.getQuestions());
		model.addAttribute("questionnaireForm", new QuestionnaireForm(questionnaire.getDefaultResponses()));
		return "questionnaireNew";
	}
	
	@RequestMapping("/{id}")
	public String questionnaire(ModelMap model, @PathVariable("id") Long resultId) {
		model.addAttribute("questions", questionnaire.getQuestions());
		
		QuestionnaireResult result = questionnaireService.findOne(resultId);
		QuestionnaireForm form = new QuestionnaireForm(questionnaire.getResponses(result));
		form.setId(result.getId());
		form.setName(result.getName());
		model.addAttribute("questionnaireForm", form);
		model.addAttribute("resultID", resultId);
		return "questionnaire";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute(name="questionnaireForm") QuestionnaireForm questionnaireForm) {
		QuestionnaireResult results = questionnaireForm.toResult();
		QuestionnaireResult savedResult = questionnaireService.save(results);
		return "redirect:/" + savedResult.getId() + "/results";
	}
	
	@RequestMapping(value="/{id}/results")
	public String getResults(ModelMap model, @PathVariable("id") Long resultId) throws ParserConfigurationException, SAXException {
		
		QuestionnaireResult result = questionnaireService.findOne(resultId);
		XmlParser xmlParser = new XmlParser();
		xmlParser.parse();
		
		Set<Threat> threats = new HashSet<Threat>();
		List<Long> caweIds = questionnaire.getCawesFromResult(result);
			
		for(Long caweId: caweIds){
			CAWE cawe = caweService.findById(caweId);
			List<Threat> threatList = xmlParser.getCaweToThreatMapping().get(caweId);
			if(threatList != null && cawe != null) {
				for(Threat threat: threatList) {
					threat.addCawe(cawe);
					threats.add(threat);
				}
			}
		}

		model.addAttribute("threats", threats);
		model.addAttribute("resultID", resultId);
		model.addAttribute("questionnaireName", result.getName());
		return "questionnaireResults";
	}
}
