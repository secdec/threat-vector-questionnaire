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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.threatintell.threatquestionnaire.model.CAWE;

public class CAWEHandler extends DefaultHandler {

    private CAWE cawe;
    private String content = "";
    private Map<Long, List<Integer>> caweToCapecMapping = new HashMap<>();
    
    private Map<Long, CAWE> cawes = new HashMap<>();
    
    public Map<Long, CAWE> getCAWEs(){ return cawes; }
    public Map<Long, List<Integer>> getCaweToCapecMapping(){
    	return caweToCapecMapping;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	
    	String tag = qName.toLowerCase();
    	
    	switch(tag) {
    	case "weaknesses":
    		break;
    	case "weakness":
    		cawe = new CAWE();
    		cawe.setId(Long.parseLong(attributes.getValue("ID").trim()));
    		cawe.setName(attributes.getValue("Name").trim());
    		break;
    	case "description":
    		content = "";
    		break;
    	case "extended_description":
    		content = "";
    		break;
    	case "scope":
    		content = "";
    		break;
    	case "impact":
    		content = "";
    		break;
    	case "related_attack_patterns":
    		caweToCapecMapping.put(cawe.getId(), new ArrayList<>());
    		break;
    	case "related_attack_pattern":
    		caweToCapecMapping.get(cawe.getId()).add(Integer.parseInt(attributes.getValue("CAPEC_ID")));
    		break;
    	default:
    		break;
    	}
    }

    @Override
	public void endElement(String namespaceURI, String localName, String qName){
    	String tag = qName.toLowerCase();
    	
    	switch(tag) {
    	case "weaknesses":
    		break; 
    	case "weakness":
    		cawes.put(cawe.getId(), cawe);
    		cawe = null;
    		break;
    	case "description":
    		if(cawe != null && cawe.getDescription().isEmpty()) {
    			cawe.setDescription(content);
    		}
    		break;
    	case "extended_description":
    		cawe.setExtendedDescription(content);
    		break;
    	case "scope":
    		cawe.addScope(content.trim());
    		break;
    	case "impact":
    		cawe.addImpact(content.trim());
    		break;
    	default:
    		break;	
    	}
    }
    
    @Override
    public void characters(char[] buffer, int start, int length) {
    	String line = new String(buffer, start, length);
    	Pattern xhtmlRegex = Pattern.compile("(<\\/?xhtml:[A-Za-z\\s=\"]*>)");
    	Matcher matcher = xhtmlRegex.matcher(line);
    	if(matcher.find()) {
        	line = line.replaceAll("xhtml:", "");
    	}
    	
    	content += line;
    }
}