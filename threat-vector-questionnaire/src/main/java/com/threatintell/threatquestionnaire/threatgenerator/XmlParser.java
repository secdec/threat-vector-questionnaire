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
package com.threatintell.threatquestionnaire.threatgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

import com.threatintell.threatquestionnaire.model.CAPEC;
import com.threatintell.threatquestionnaire.model.CAWE;
import com.threatintell.threatquestionnaire.model.SecurityCapability;
import com.threatintell.threatquestionnaire.model.Threat;

public class XmlParser {
	
	private Map<Long, Threat> threats;
	private Map<Long, CAWE> cawes; 
	private Map<Integer, CAPEC> capecs; 
	private Map<Integer, SecurityCapability> securityCapabilities;

	private Map<Integer, List<Long>> securityCapabilityToCaweMapping;
	private Map<Long, List<Integer>> caweToCapecMapping;
	private Map<Long, List<Threat>> caweToThreatMapping;
	
	/**xmlParser Constructor*/
	public XmlParser() {}
	
	public static void main(String[] args) {
		XmlParser parser = new XmlParser();
		try {
			parser.parse();

			for(CAWE cawe: parser.getCawes()){
				if(cawe.getId() == 114) {
					System.out.println(cawe);
				}
			}
			
			//for(SecurityCapability capability: parser.getSecurityCapabilities()) {
			//	System.out.println(capability);
			//}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
	}
	
	/**Parses all the XML library files and generates 3 lists that contain all threat,CAWE, and CAPEC objects*/
	public void parse() throws ParserConfigurationException, SAXException {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		
		CAWEHandler caweHandler = new CAWEHandler();
		CAPECHandler capecHandler = new CAPECHandler();
		SecurityCapabilityHandler securtyCapabilityHandler = new SecurityCapabilityHandler();
		
		ThreatParser threatParser = new ThreatParser();
		
		try {
			InputStream caweFile = this.getClass().getResourceAsStream("/1008.xml");
			InputStream capecFile = this.getClass().getResourceAsStream("/CAPEC_Library.xml");
			InputStream securityCapabilityFile = this.getClass().getResourceAsStream("/1008.xml");
			
			saxParser.parse(caweFile,caweHandler);
			saxParser.parse(capecFile,capecHandler);
			saxParser.parse(securityCapabilityFile,securtyCapabilityHandler);
			
			threatParser.parse();
			
		}catch(SAXException | IOException e){
			System.err.println("XML Parsing Error");
			e.printStackTrace();
		}
		
		cawes = caweHandler.getCAWEs();
		caweToCapecMapping = caweHandler.getCaweToCapecMapping();
		
		capecs = capecHandler.getCAPECs();
		
		securityCapabilities = securtyCapabilityHandler.getSecurityCapabilities();
		securityCapabilityToCaweMapping = securtyCapabilityHandler.getSecurityCapabilityToCaweMapping();
		
		threats = threatParser.getThreats();
		caweToThreatMapping = threatParser.getCaweToThreatMapping();
		
		this.buildRelationships();
        
	}
	
	public void buildRelationships() {
		
		for(Map.Entry<Integer, List<Long>> entry: securityCapabilityToCaweMapping.entrySet()) {
			Integer securityCapabilityId = entry.getKey();
			List<Long> caweIds = entry.getValue();
			if(securityCapabilities.containsKey(securityCapabilityId)) {
				SecurityCapability securityCapability = securityCapabilities.get(securityCapabilityId);
				for(Long caweId: caweIds) {
					
					if(cawes.containsKey(caweId)) {
						CAWE cawe = cawes.get(caweId);
						securityCapability.addCawe(cawe);
						
//						if(caweToThreatMapping.containsKey(caweId)) {
//							for(Threat threat: caweToThreatMapping.get(caweId)) {
//								if(threats.containsKey(threat.getThreatId())) {
//									cawe.addThreat(threats.get(threat.getThreatId()));
//								}
//							}
//						}
						
						if(caweToCapecMapping.containsKey(caweId)) {
							List<Integer> capecIds = caweToCapecMapping.get(caweId);
							for(Integer capecId: capecIds) {
								if(capecs.containsKey(capecId)) {
									CAPEC capec = capecs.get(capecId);
									cawe.addCapec(capec);
								}
							}
						}
					}
				}
			}
		}
	}
		
	/**@return List of threats found in the XML library*/
	public List<Threat> getThreats(){ return new ArrayList<>(threats.values()); }
	        
	/**@return List of CAWEs found in the XML library*/
	public List<CAWE> getCawes(){ return new ArrayList<>(cawes.values()); } 
	
	/**@return Map of CAWEs found in the XML library*/
	public Map<Long, CAWE> getCaweMap(){ return cawes; } 
	  
	/**@return List of CAPECs found in the XML library */
	public List<CAPEC> getCapecs(){ return new ArrayList<>(capecs.values()); }
	  
	/**@return List of CAPECs found in the XML library */
	public List<SecurityCapability> getSecurityCapabilities(){ return new ArrayList<>(securityCapabilities.values()); }
	
	public Map<Long, List<Threat>> getCaweToThreatMapping(){
		return this.caweToThreatMapping;
	}

}