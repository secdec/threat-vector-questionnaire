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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;


@Entity
public class Threat implements Comparable<Threat> {

	public static int ID_COL = 0;
	public static int NAME_COL = 1;
	public static int DESCRIPTION_COL = 2;
	public static int CAWE_COL = 3;
	public static int STRIDE_COL = 4;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
	@Transient private Long threatId;
	@Column private String name;
	@Column(length=1023) private String description;
	@Column(length=1023) private String recommendation;
	@Column private boolean active = true;
	
	@ElementCollection private Set<String> strideTypes = new HashSet<String>();
	@ManyToMany(mappedBy="threats", cascade=CascadeType.PERSIST) private Set<CAWE> cawes;
	
	public Threat() {
		this.cawes = new HashSet<>();
	}
	
	public Set<SecurityCapability> getSecurityCapabilities(){
		Set<SecurityCapability> capabilities = new HashSet<>();
		
		for(CAWE cawe: this.getCawes()) {
			capabilities.addAll(cawe.getSecurityCapabilities());
		}
		return capabilities;
	}
	
	public Set<CAPEC> getCapecs(){
		Set<CAPEC> capecs = new HashSet<>();
		
		for(CAWE cawe: this.getCawes()) {
			capecs.addAll(cawe.getCapecs());
		}
		return capecs;
	}
	
	public Long getId() { return id; }
	
	public void setId(Long id) { this.id = id; }
	
	public Long getThreatId() { return threatId; }
	
	public void setThreatId(Long threatId) { this.threatId = threatId; }
	
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public Severity getSeverity() {
		Double score = 0.0;
		Double caweCount = 0.0;
		
		for(CAWE cawe: getCawes()) {
			score += cawe.getAverageCvss();
			caweCount++;
		}

		return Severity.fromDouble(score/caweCount);
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void disable() {
		this.active = false;
	}
	
	public void enable() {
		this.active = true;
	}

	public Set<String> getStrideTypes(){ return strideTypes; }
	
	public void addStrideType(String stride) { this.strideTypes.add(stride); }
	
	public Set<CAWE> getCawes() {
		return cawes;
	}
	
	public void addCawe(CAWE cawe) {
		this.cawes.add(cawe);
	}
	
	@Override
	public int compareTo(Threat threat) {
		return this.getName().compareTo(threat.getName());
	}

	@Override 
	public String toString(){
		StringBuffer threatString = new StringBuffer();
		threatString.append("ID: " + getId() + "\n");
		threatString.append("Name: " + getName() + "\n");
		threatString.append("Description: " + getDescription() + "\n");
		threatString.append("Recommendation: " + getRecommendation() + "\n");
		threatString.append("Severity: " + getSeverity() + "\n");
		threatString.append("Stride Types:" + "\n");
        for(String stride: getStrideTypes()) {
            threatString.append("   " + stride.replaceAll("^\"|\"$", "") + "\n");
        }
		threatString.append("CAWE:" + "\n");
        for(CAWE cawe: getCawes()) {
            threatString.append("   " + cawe.getId() + "\n");
        }
		return threatString.toString();
	}
}