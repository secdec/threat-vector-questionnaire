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
package com.threatintell.threatquestionnaire.questionnaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

import com.threatintell.threatquestionnaire.questionnaire.QuestionnaireResult.Answer;
import com.threatintell.threatquestionnaire.questionnaire.comparator.QuestionComparator;

class QuestionnaireParser {
	
	public static Questionnaire parse(String filename) {
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        Questionnaire questionnaire = new Questionnaire();
        
        try {

            br = new BufferedReader(new InputStreamReader(QuestionnaireParser.class.getResourceAsStream(filename)));
            String headers = br.readLine();
            while ((line = br.readLine()) != null) {

                String[] question = line.split(cvsSplitBy);
                questionnaire.addQuestion(Question.fromCSVLine(question));
                
            }
            
            Collections.sort(questionnaire.getQuestions(), new QuestionComparator());

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
        
		return questionnaire;
	}
	
	public static void main(String[] args) {
		Questionnaire questionnaire = QuestionnaireParser.parse("/questionnaire.csv");
		
		System.out.println(questionnaire);
		
		System.out.println(questionnaire.getQuestions().get(0).getCawesFromAnswer(Answer.NOT_SURE));
	}
}
