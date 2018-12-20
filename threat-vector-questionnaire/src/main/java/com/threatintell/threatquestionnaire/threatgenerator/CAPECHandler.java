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

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.threatintell.threatquestionnaire.model.CAPEC;

public class CAPECHandler extends DefaultHandler {

    private Map<Integer, CAPEC> capecs = new HashMap<Integer, CAPEC>();
    
    private CAPEC capec;
    private String content = "";
    
    public Map<Integer, CAPEC> getCAPECs(){ return capecs; }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	if(qName.equalsIgnoreCase("capec")) {
    		capec = new CAPEC();
    		capec.setId(Integer.parseInt(attributes.getValue("ID").trim()));
    	}
    }

    @Override
	public void endElement(String namespaceURI,String localName,String qName){
    	if(qName.equalsIgnoreCase("capec")) {
    		capecs.put(capec.getId(), capec);
    	}else if(qName.equalsIgnoreCase("capec_name")) {
    		capec.setName(content.trim());
    	}else if(qName.equalsIgnoreCase("capec_description")) {
    		capec.setDescription(content.trim());
    	}
	}
    
    @Override
    public void characters(char[] buffer, int start, int length) {
    	content = new String(buffer, start, length);
 } 
}