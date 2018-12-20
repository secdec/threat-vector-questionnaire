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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;


@Entity
@NamedEntityGraph(
		name = "SecurityCapability.withAllChildren",
		attributeNodes = {
				@NamedAttributeNode(value="cawes", subgraph="cawes")
		},
		subgraphs= {
				@NamedSubgraph(name="cawes", attributeNodes= {
						@NamedAttributeNode("capecs"), 
						@NamedAttributeNode("threats")
						})
		})
public class SecurityCapability implements Comparable<SecurityCapability>{
	
	@Id @Column private Integer id;
	@Column private String name;

	@OneToMany(cascade= {CascadeType.MERGE}) private Set<CAWE> cawes = new HashSet<>();

	/**Creates a Security Capability object*/
	public SecurityCapability() {}
	
	/**Sets the name of the Security Capability object*/
	public void setName(String name) { this.name = name; }
	
	/**Sets the id of Security Capability object*/
	public void setId(Integer id) { this.id = id; }
	
	private boolean active = false;
		
	public void addCawe(CAWE cawe) { cawes.add(cawe); }
	
	/**@return The name of the Security Capability*/
	public String getName() { return name; }
	
	/**@return The id of the Security Capability*/
	public Integer getId() { return id; }
	
	/**@return The related cawe ids of the Security Capability object**/
	public Set<CAWE> getCawes() { return cawes; }
	
	public boolean isActive() {
		return this.active;
	}
	
	public void disable() {
		this.active = false;
	}
	
	public void enable() {
		this.active = true;
	}
	
	@Override 
	public String toString(){
		StringBuffer secCapString = new StringBuffer(); 
		secCapString.append("Name: " + getName() + "\n");
		secCapString.append("ID: " + getId() + "\n");
		secCapString.append("Related CAWEs:" + "\n");
		for(CAWE cawe:getCawes()) {
			secCapString.append(cawe.getName() + ": " + cawe.getId() + "\n");
		}
		
		return secCapString.toString();
	}

	@Override
	public int compareTo(SecurityCapability capability) {
		return this.getName().compareTo(capability.getName());
	}
}