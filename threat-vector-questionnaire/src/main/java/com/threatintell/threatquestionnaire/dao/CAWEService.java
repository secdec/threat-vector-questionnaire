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
package com.threatintell.threatquestionnaire.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.threatintell.threatquestionnaire.dao.repositories.CaweRepository;
import com.threatintell.threatquestionnaire.model.CAWE;


@Repository("caweService")
@Transactional
public class CAWEService{

	@PersistenceContext
	EntityManager entityManager;
	
	CaweRepository caweRepository;
	
	@Autowired
	public CAWEService(CaweRepository caweRepository) {
		this.caweRepository = caweRepository;
	}
	
	public CAWE getProxy(long caweId) {
		return entityManager.getReference(CAWE.class, caweId);
	}

	public void createThreatCaweConnection(Long threatId, Long caweId) {
		Query query = entityManager.createNativeQuery("INSERT INTO cawe_threats (cawes_id, threats_id) VALUES (:caweId, :threatId)");
		query.setParameter("caweId", caweId);
		query.setParameter("threatId", threatId);
		query.executeUpdate();
	}
	
	public CAWE findById(Long id) {
		return caweRepository.findById(id);
	}
}
