/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.devnexus.ting.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.devnexus.ting.model.CfpSubmission;
import com.devnexus.ting.repository.CfpSubmissionRepositoryCustom;

/**
 *
 * @author Gunnar Hillert
 *
 */
public class CfpSubmissionRepositoryImpl implements CfpSubmissionRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CfpSubmission> getCfpSubmissions(Long eventId) {

		final List<CfpSubmission> cfpSubmissions = this.entityManager
				.createQuery("select distinct cfp from CfpSubmission cfp "
						+ "    join cfp.event e "
						+ "    join fetch cfp.cfpSubmissionSpeakers s "
						+ "where e.id = :eventId "
						+ "order by s.lastName ASC", CfpSubmission.class)
			 .setParameter("eventId", eventId)
			 .getResultList();

		return cfpSubmissions;
	}

	@Override
	public List<CfpSubmission> getCfpSubmissionsForUserAndEvent(Long userId, Long eventId) {
		final List<CfpSubmission> cfpSubmissions = this.entityManager
				.createQuery("select distinct cfp from CfpSubmission cfp "
						+ "    join cfp.event e "
						+ "    join cfp.createdByUser u "
						+ "    join fetch cfp.cfpSubmissionSpeakers s "
						+ "where e.id = :eventId "
						+ "and u.id = :userId "
						+ "order by s.lastName ASC", CfpSubmission.class)
			.setParameter("userId", userId)
			.setParameter("eventId", eventId)
			.getResultList();

		return cfpSubmissions;
	}

	@Override
	public CfpSubmission getSingleCfpSubmissionForUserAndEvent(Long cfpSubmissionId, Long userId, Long eventId) {
		final CfpSubmission cfpSubmission = this.entityManager
				.createQuery("select distinct cfp from CfpSubmission cfp "
						+ "    join cfp.event e "
						+ "    join cfp.createdByUser u "
						+ "    join fetch cfp.cfpSubmissionSpeakers s "
						+ "where e.id = :eventId "
						+ "and u.id = :userId "
						+ "and cfp.id = :cfpSubissionId "
						+ "order by s.lastName ASC", CfpSubmission.class)
			.setParameter("userId", userId)
			.setParameter("eventId", eventId)
			.setParameter("cfpSubissionId", cfpSubmissionId)
			.getSingleResult();

		return cfpSubmission;
	}
}
