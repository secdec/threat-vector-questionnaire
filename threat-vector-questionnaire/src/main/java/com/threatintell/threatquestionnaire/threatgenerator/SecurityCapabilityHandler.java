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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.threatintell.threatquestionnaire.model.SecurityCapability;

public class SecurityCapabilityHandler extends DefaultHandler {
	
    private SecurityCapability securityCapability;  
    private Map<Integer, SecurityCapability> securityCapabilities = new HashMap<>();
    private Map<Integer, List<Long>> securityCapabilityToCaweMapping = new HashMap<>();
    
    public Map<Integer, SecurityCapability> getSecurityCapabilities(){ return securityCapabilities; }
    
    public Map<Integer, List<Long>> getSecurityCapabilityToCaweMapping(){
    	return securityCapabilityToCaweMapping;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	
    	String tag = qName.toLowerCase();
    	
    	switch(tag) {
    	case "category":
    		securityCapability = new SecurityCapability();
    		securityCapability.setId(Integer.parseInt(attributes.getValue("ID")));
    		securityCapability.setName(attributes.getValue("Name"));
    		break;
    	case "relationships":
    		securityCapabilityToCaweMapping.put(securityCapability.getId(), new ArrayList<>());
    		break;
    	case "has_member":
    		securityCapabilityToCaweMapping.get(securityCapability.getId()).add(Long.parseLong(attributes.getValue("CWE_ID")));
    		break;
    	}
    }

    @Override
	public void endElement(String namespaceURI,String localName,String qName){
    	String tag = qName.toLowerCase();
    	
    	switch(tag) {
    	case "category":
    		securityCapabilities.put(securityCapability.getId(), securityCapability);
    		break;
    	}
	}
}