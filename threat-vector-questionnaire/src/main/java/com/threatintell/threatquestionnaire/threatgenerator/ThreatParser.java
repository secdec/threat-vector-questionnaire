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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.threatintell.threatquestionnaire.model.Threat;

public class ThreatParser {
	
	private static String filename = "/threat_library.tsv";
	
	private Map<Long, Threat> threats;
    private Map<Threat, List<Long>> threatToCaweMapping;
    private Map<Long, List<Threat>> caweToThreatMapping;
    
    public Map<Long, Threat> getThreats(){ return threats; }
    
    public Map<Threat, List<Long>> getThreatToCaweMapping(){ return threatToCaweMapping; }
    public Map<Long, List<Threat>> getCaweToThreatMapping(){ return caweToThreatMapping; }
	    
	public void parse() {
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";

        threats = new HashMap<>();
        threatToCaweMapping = new HashMap<>();
        caweToThreatMapping = new HashMap<>();
        
        try {

            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filename)));
            String headers = br.readLine();
            while ((line = br.readLine()) != null) {

                String[] threatString = line.split(cvsSplitBy);
                Threat threat = new Threat();
                threat.setThreatId(Long.parseLong(threatString[Threat.ID_COL]));
                threat.setName(threatString[Threat.NAME_COL]);
                threat.setDescription(threatString[Threat.DESCRIPTION_COL]);
                
                if(threatString.length > 4) {
                	for(String strideType: threatString[Threat.STRIDE_COL].split(",")) {
                		threat.addStrideType(strideType.trim());
                	}
                }
                
                threats.put(threat.getThreatId(), threat);
                
                threatToCaweMapping.put(threat, new ArrayList<>());
                for(String caweIdString: threatString[Threat.CAWE_COL].split(",")) {
                	Long caweId = Long.parseLong(caweIdString.trim());
                	threatToCaweMapping.get(threat).add(caweId);
                	
                	if(!caweToThreatMapping.containsKey(caweId)) {
                		caweToThreatMapping.put(caweId, new ArrayList<>());
                	}
                	
                	caweToThreatMapping.get(caweId).add(threat);
                }
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public static void main(String[] args) {
		
		ThreatParser parser = new ThreatParser();
		parser.parse();
		
		for(Threat threat: parser.getThreats().values()) {
			System.out.println(threat);	
		}
	}
}
