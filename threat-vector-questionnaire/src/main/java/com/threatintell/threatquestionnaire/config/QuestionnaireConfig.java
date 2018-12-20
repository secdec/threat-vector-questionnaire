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
package com.threatintell.threatquestionnaire.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.threatintell.threatquestionnaire.questionnaire.Questionnaire;

@Configuration
public class QuestionnaireConfig {

	private static String filename = "/questionnaire.csv";
	
	@Bean
	Questionnaire questionnaire(){
		return Questionnaire.fromFile("/questionnaire.csv");
	}
}
