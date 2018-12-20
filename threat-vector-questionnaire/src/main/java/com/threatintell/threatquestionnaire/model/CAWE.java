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
package com.threatintell.threatquestionnaire.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class CAWE {
	
	@Id @Column private long id;
	@Column private String name; 
	@Column(length=8192) private String description = "";
	@Column(length=8192) private String extendedDescription = "";
	@Column private String securityTactic;
	@Column private String impactType;
	@Column private Double averageCvssScore = 10.0;
	
	@ElementCollection private List<String> impacts = new ArrayList<String>();
	@ElementCollection private List<String> recommendations = new ArrayList<String>();
	@ElementCollection private List<String> scopes = new ArrayList<>();
	@ManyToMany(mappedBy="cawes") private List<SecurityCapability> securityCapabilities = new ArrayList<>(); 
	@ManyToMany(cascade=CascadeType.MERGE) private Set<Threat> threats = new HashSet<Threat>();
	@ManyToMany(cascade=CascadeType.MERGE) private Set<CAPEC> capecs = new HashSet<CAPEC>();
	
	/**Creates a CAWE object*/
	public CAWE() {
		
	}
	
	/**Sets the name of CAWE object*/
	public void setName(String name) { this.name = name; }
	
	/**Sets the id of CAWE object*/
	public void setId(Long id) { this.id = id; }
	
	/**Sets the description of CAWE object*/
	public void setDescription(String description) { this.description = description; }
	
	public Double getAverageCvss() {
		return averageCvssScore;
	}

	public void setAverageCvss(Double averageCvssScore) {
		this.averageCvssScore = averageCvssScore;
	}

	/**Sets the security tactic of CAWE object*/
	public void setSecurityTactic(String securityTactic) { this.securityTactic = securityTactic; }
	
	/**Add an impact to list of existing ones for CAWE object*/
	public void addImpact(String impact) { this.impacts.add(impact); }
	
	/**Add a recommendation to list of existing ones for CAWE object*/
	public void addRecommendation(String recommendations) { this.recommendations.add(recommendations); }
	
	/**Add a CAPEC to list of existing ones for CAWE object*/
	public void addCapec(CAPEC capec) { this.capecs.add(capec); }
	
	/**@return The name of the CAWE*/
	public String getName() { return name; }
	
	/**@return The id of the CAWE*/
	public long getId() { return id; }
	
	/**@return The description of the CAWE*/
	public String getDescription() { return description; }
	
	public String getExtendedDescription() {
		return this.extendedDescription;
	}
	
	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}
	
	public List<String> getScopes(){
		return this.scopes;
	}
	
	public void addScope(String scope) {
		getScopes().add(scope);
	}
	
	/**@return A list of impacts for the CAWE*/
	public List<String> getImpacts(){ return impacts; }
	
	/**Sets the impact type of CAWE object*/
	public void setImpactType(String threatType) { this.impactType = threatType; }
	
	/**@return The security tactic of the CAWE*/
	public String getSecurityTactic() { return securityTactic; }
	
	/**@return The threat type of the CAWE*/
	public String getThreatType() { return impactType; }

	/**@return A list of threats objects for the CAWE*/
	public Set<Threat> getThreats(){ return threats; }
	
	/**Add a threat object to list of existing ones for CAWE object*/
	public void addThreat(Threat threatObj) { this.threats.add(threatObj); }
	
	/**@return A list of recommendations for the CAWE*/
	public List<String> getRecommendations(){ return recommendations; }	
	
	public List<SecurityCapability> getSecurityCapabilities(){
		return this.securityCapabilities;
	}
	
	/**@return A list of CAPEC objects for the CAWE*/
	public Set<CAPEC> getCapecs(){ return capecs; }
	
	@Override 
	public String toString(){
		StringBuffer threatString = new StringBuffer(); 
		threatString.append("Name: " + getName() + "\n");
		threatString.append("ID: " + getId() + "\n");
		threatString.append("Description: " + getDescription() + "\n");
		threatString.append("Extended Description: " + getExtendedDescription() + "\n");
		threatString.append("Average CVSS: " + getAverageCvss() + "\n");
		threatString.append("Security Tactic: " + getSecurityTactic() + "\n");
		threatString.append("Threat Type: " + getThreatType() + "\n");
		
        threatString.append("Scopes:" + "\n");
        for(String scope: getScopes()) {
            threatString.append("\t- " + scope + "\n");
        }
        
		threatString.append("Potential Impacts:" + "\n");
        for(String impact: getImpacts()) {
            threatString.append("\t- " + impact.replaceAll("^\"|\"$", "") + "\n");
        }
        
        threatString.append("Related Threat-s:" + "\n");
        for(Threat threat: getThreats()) {
            threatString.append("\t- " + threat.getName() + "\n");
        }
        
        threatString.append("Recommendations:" + "\n");
        for(int i = 0; i < getRecommendations().size(); i++) {
            threatString.append("\t- " + getRecommendations().get(i) + "\n");
        }
        
        threatString.append("Related CAPECs:" + "\n");
        for(CAPEC capec: getCapecs()) {
            threatString.append("\t- " + capec + "\n");
        }
        
		return threatString.toString();
	}
}
