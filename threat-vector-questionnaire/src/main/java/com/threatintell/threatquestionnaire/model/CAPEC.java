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

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class CAPEC {
	
	@Id @Column	private Integer id;
	@Column private String name;
	@Column(length=1023) private String description;
	@ManyToMany(mappedBy="capecs") Set<CAWE> cawes;
	
	/**Creates a threat object*/
	public CAPEC() {}
	
	/**Sets the name of CAWE object*/
	public void setName(String name) { this.name = name; }
	
	/**Sets the id of CAWE object*/
	public void setId(Integer id) { this.id = id; }
	
	/**Sets the description of CAWE object*/
	public void setDescription(String description) { this.description = description; }
	
	/**@return Name of the CAPEC object*/
	public String getName() { return name; }
	
	/**@return ID of the CAPEC object*/
	public Integer getId() { return id; }
	
	/**@return Description of the CAPEC object*/
	public String getDescription() { return description; }
	
	public Set<CAWE> getCawes() {
		return cawes;
	}

	@Override 
	public String toString(){
		StringBuffer threatString = new StringBuffer(); 
		threatString.append("Name: " + getName() + "\n");
		threatString.append("ID: " + getId() + "\n");
		threatString.append("Description: " + getDescription() + "\n");

		return threatString.toString();
	}
}
