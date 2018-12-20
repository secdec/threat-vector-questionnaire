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

public enum Severity {
	LOW,
	MEDIUM,
	HIGH,
	CRITICAL;
	
	public static Severity fromString(String name) {
		switch(name.toLowerCase()) {
		case "low":
			return LOW;
		case "medium":
			return MEDIUM;
		case "high":
			return HIGH;
		default:
			return CRITICAL;
		}
	}
	
	public static Severity fromDouble(Double score) {
	
		if(score >= 0 && score <= 2) {
			return LOW;
		} else if(score > 2 && score <= 5) {
			return MEDIUM;
		} else if(score > 5 && score <= 8) {
			return HIGH;
		} else {
			return CRITICAL;
		}
		
	}
}
