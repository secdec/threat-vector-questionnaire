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
package com.threatintell.threatquestionnaire.dao;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.threatintell.threatquestionnaire.dao.repositories.SecurityCapabilityRepository;
import com.threatintell.threatquestionnaire.dao.repositories.ThreatRepository;
import com.threatintell.threatquestionnaire.model.SecurityCapability;
import com.threatintell.threatquestionnaire.threatgenerator.XmlParser;


@Component("databaseUtil")
@ConditionalOnProperty(name="spring.jpa.hibernate.ddl-auto", havingValue="create")
public class DatabaseUtil{

	private final Logger logger = LogManager.getLogger(DatabaseUtil.class);
	private SecurityCapabilityRepository securityCapabilityRepository;
	
	@Autowired
	public DatabaseUtil(
			ThreatRepository threatRepository, 
			SecurityCapabilityRepository securityCapabilityRepository) {
		this.securityCapabilityRepository = securityCapabilityRepository;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initializeDatabase() {
		
		logger.info("Initializing Database");
		
		XmlParser xmlParser = new XmlParser();
		try {
			xmlParser.parse();
			
			for(SecurityCapability securityCapability: xmlParser.getSecurityCapabilities()) {
				securityCapabilityRepository.save(securityCapability);
			}
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		
		logger.info("Completed Database Initialization");
		
	}
}
