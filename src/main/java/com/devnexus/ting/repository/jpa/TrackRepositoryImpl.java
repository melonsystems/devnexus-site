/*
 * Copyright 2002-2014 the original author or authors.
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

import org.springframework.stereotype.Repository;

import com.devnexus.ting.model.Track;
import com.devnexus.ting.repository.TrackRepositoryCustom;

/**
 *
 * @author Gunnar Hillert
 *
 */
@Repository("trackDao")
public class TrackRepositoryImpl implements TrackRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Track> getTracksForEvent(Long eventId) {

		final List<Track> tracks = this.entityManager
				.createQuery("select t from Track t "
						+ "where t.event.id = :eventId "
						+ "order by t.trackOrder ASC", Track.class)
			.setParameter("eventId", eventId)
			.getResultList();

		return tracks;
	}

}
