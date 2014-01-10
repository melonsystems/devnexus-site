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
package com.devnexus.ting.core.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.devnexus.ting.common.CalendarUtils;
import com.devnexus.ting.common.SystemInformationUtils;
import com.devnexus.ting.core.dao.ApplicationCacheDao;
import com.devnexus.ting.core.dao.CfpSubmissionDao;
import com.devnexus.ting.core.dao.EvaluationDao;
import com.devnexus.ting.core.dao.EventDao;
import com.devnexus.ting.core.dao.OrganizerDao;
import com.devnexus.ting.core.dao.PresentationDao;
import com.devnexus.ting.core.dao.PresentationTagDao;
import com.devnexus.ting.core.dao.RoomDao;
import com.devnexus.ting.core.dao.ScheduleItemDao;
import com.devnexus.ting.core.dao.SpeakerDao;
import com.devnexus.ting.core.dao.TrackDao;
import com.devnexus.ting.core.model.ApplicationCache;
import com.devnexus.ting.core.model.CfpSubmission;
import com.devnexus.ting.core.model.Evaluation;
import com.devnexus.ting.core.model.Event;
import com.devnexus.ting.core.model.FileData;
import com.devnexus.ting.core.model.Organizer;
import com.devnexus.ting.core.model.Presentation;
import com.devnexus.ting.core.model.PresentationTag;
import com.devnexus.ting.core.model.Room;
import com.devnexus.ting.core.model.ScheduleItem;
import com.devnexus.ting.core.model.ScheduleItemList;
import com.devnexus.ting.core.model.ScheduleItemType;
import com.devnexus.ting.core.model.Speaker;
import com.devnexus.ting.core.model.Track;
import com.devnexus.ting.core.service.BusinessService;

/**
 *
 * @author Gunnar Hillert
 * @since 1.0
 */
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	/**
	 *   Initialize Logging.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceImpl.class);

	@Autowired private CfpSubmissionDao cfpSubmissionDao;
	@Autowired private EvaluationDao   evaluationDao;
	@Autowired private EventDao        eventDao;
	@Autowired private OrganizerDao    organizerDao;
	@Autowired private PresentationDao presentationDao;
	@Autowired private PresentationTagDao presentationTagDao;
	@Autowired private RoomDao         roomDao;
	@Autowired private ScheduleItemDao scheduleItemDao;
	@Autowired private SpeakerDao      speakerDao;
	@Autowired private TrackDao        trackDao;
	@Autowired private ApplicationCacheDao applicationCacheDao;
	@Autowired private Environment environment;

	@Autowired private MessageChannel mailChannel;

	private final TransactionTemplate transactionTemplate;

	@Autowired
	public BusinessServiceImpl(PlatformTransactionManager transactionManager) {
		super();
		Assert.notNull(transactionManager, "The 'transactionManager' argument must not be null.");
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public void deleteEvent(Event event) {
		Assert.notNull(event, "The provided event must not be null.");
		Assert.notNull(event.getId(), "Id must not be Null for event " + event);

		LOGGER.debug("Deleting Event {}", event);
		eventDao.remove(event);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public void deleteOrganizer(Organizer organizerFromDb) {

		Assert.notNull(organizerFromDb,         "The provided organizer must not be null.");
		Assert.notNull(organizerFromDb.getId(), "Id must not be Null for organizer " + organizerFromDb);

		LOGGER.debug("Deleting Organizer {}", organizerFromDb);
		organizerDao.remove(organizerFromDb);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public void deletePresentation(Presentation presentation) {

		Assert.notNull(presentation,         "The provided presentation must not be null.");
		Assert.notNull(presentation.getId(), "Id must not be Null for presentation " + presentation);

		LOGGER.debug("Deleting Presentation {}", presentation);

		presentationDao.remove(presentation);

	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public void deleteSpeaker(Speaker speaker) {

		Assert.notNull(speaker,         "The provided speaker must not be null.");
		Assert.notNull(speaker.getId(), "Id must not be Null for speaker " + speaker);

		LOGGER.debug("Deleting Speaker {}", speaker);

		speakerDao.remove(speaker);
	}

	/** {@inheritDoc} */
	@Override
	public List<Event> getAllEventsOrderedByName() {
		return eventDao.getAllEventsOrderedByName();
	}

	/** {@inheritDoc} */
	@Override
	public List<Event> getAllNonCurrentEvents() {
		return eventDao.getAllNonCurrentEvents();
	}

	/** {@inheritDoc} */
	@Override
	public List<Organizer> getAllOrganizers() {
		return organizerDao.getAllOrganizers();
	}

	/** {@inheritDoc} */
	@Override
	public List<Presentation> getAllPresentations() {
		return presentationDao.getAll();
	}

	/** {@inheritDoc} */
	@Override
	public List<Speaker> getAllSpeakersOrderedByName() {
		return speakerDao.getAllSpeakersOrderedByName();
	}

	/** {@inheritDoc} */
	@Override
	public Event getEvent(Long id) {
		return eventDao.get(id);
	}

	/** {@inheritDoc} */
	@Override
	public Event getEventByEventKey(String eventKey) {
		return eventDao.getByEventKey(eventKey);
	}

	/** {@inheritDoc} */
	@Override
	public Organizer getOrganizer(final Long organizerId) {
		return organizerDao.get(organizerId);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public Organizer getOrganizerWithPicture(Long organizerId) {
		return organizerDao.getOrganizerWithPicture(organizerId);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional(readOnly=false)
	public Presentation getPresentation(Long id) {
		return presentationDao.get(id);
	}

	/** {@inheritDoc} */
	@Override
	public List<Presentation> getPresentationsForCurrentEvent() {
        List<Presentation> list = presentationDao.getPresentationsForCurrentEvent();
        Collections.sort(list);
		return list;
	}

	/** {@inheritDoc} */
	@Override
	public List<Presentation> getPresentationsForEvent(Long eventId) {
        List<Presentation> list = presentationDao.getPresentationsForEvent(eventId);
        Collections.sort(list);
		return list;
	}

	/** {@inheritDoc} */
	@Override
	public Speaker getSpeaker(Long speakerId) {
		return speakerDao.get(speakerId);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional(readOnly=false)
	public byte[] getSpeakerImage(Long speakerId) {

		Assert.notNull(speakerId, "SpeakerId must not be null.");

		final Speaker speaker = getSpeaker(speakerId);

		final byte[] speakerPicture;

		if (speaker==null || speaker.getPicture() == null) {
			speakerPicture = SystemInformationUtils.getSpeakerImage(null);
		} else {
			speakerPicture = speaker.getPicture().getFileData();
		}

		return speakerPicture;

	}

	/** {@inheritDoc} */
	@Override
	public List<Speaker> getSpeakersForCurrentEvent() {
		return speakerDao.getSpeakersForCurrentEvent();
	}

	/** {@inheritDoc} */
	@Override
	public List<Speaker> getSpeakersForEvent(Long eventId) {
		return speakerDao.getSpeakersForEvent(eventId);
	}

	@Override
	public List<Room> getRoomsForEvent(Long eventId) {
		return roomDao.getRoomsForEvent(eventId);
	}

	@Override
	public List<Track> getTracksForEvent(Long eventId) {
		return trackDao.getTracksForEvent(eventId);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public void saveEvent(Event event) {
		eventDao.save(event);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public Organizer saveOrganizer(Organizer organizer) {
		return organizerDao.save(organizer);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public void savePresentation(Presentation presentation) {
		presentationDao.save(presentation);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public Speaker saveSpeaker(Speaker speaker) {
		return speakerDao.save(speaker);
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public ApplicationCache updateApplicationCacheManifest() {

		final List<ApplicationCache> applicationCacheList = applicationCacheDao.getAll();

		if (applicationCacheList.isEmpty()) {
			ApplicationCache applicationCache = new ApplicationCache();
			applicationCache.setUpdatedDate(new Date());
			applicationCache.setUuid(UUID.randomUUID().toString());
			ApplicationCache savedApplicationCache = applicationCacheDao.save(applicationCache);
			return savedApplicationCache;
		} else if (applicationCacheList.size() >1) {
			throw new IllegalStateException("ApplicationCacheList should only contain 1 elements but found " + applicationCacheList.size());
		} else {
			ApplicationCache applicationCache = applicationCacheList.iterator().next();
			applicationCache.setUpdatedDate(new Date());
			applicationCache.setUuid(UUID.randomUUID().toString());
			return applicationCacheDao.save(applicationCache);
		}

	}

	@Override
	@Transactional
	public ApplicationCache getApplicationCacheManifest() {
		final List<ApplicationCache> applicationCacheList = applicationCacheDao.getAll();

		if (applicationCacheList.isEmpty()) {
			ApplicationCache applicationCache = new ApplicationCache();
			applicationCache.setUpdatedDate(new Date());
			applicationCache.setUuid(UUID.randomUUID().toString());
			ApplicationCache savedApplicationCache = applicationCacheDao.save(applicationCache);
			return savedApplicationCache;
		} else if (applicationCacheList.size() >1) {
			throw new IllegalStateException("ApplicationCacheList should only contain 1 elements but found " + applicationCacheList.size());
		} else {
			return applicationCacheList.iterator().next();
		}
	}

	@Override
	@Transactional
	public FileData getPresentationFileData(Long presentationId) {

		final Presentation presentation = this.getPresentation(presentationId);

		if (presentation == null) {
			return null;
		}

		FileData fileData = presentation.getPresentationFile();
		return fileData;
	}

	@Override
	public Event getCurrentEvent() {
		environment.getActiveProfiles();
		environment.getProperty("database.jdbc.url");
		return eventDao.getCurrentEvent();
	}

	@Override
	public Room getRoom(Long id) {
		return roomDao.get(id);
	}

	@Override
	public ScheduleItemList getScheduleForEvent(Long eventId) {

		final List<ScheduleItem> scheduleItems = scheduleItemDao.getScheduleForEvent(eventId);

		final ScheduleItemList scheduleItemList = new ScheduleItemList();
		scheduleItemList.setScheduleItems(scheduleItems);

		ScheduleItem currentScheduleItem = null;

		String hourOfDay = null;

		final SortedSet<Date> days = new TreeSet<Date>();

		int numberOfSessions = 0;
		int numberOfKeynoteSessions = 0;
		int numberOfBreakoutSessions = 0;
		int numberOfUnassignedSessions = 0;

		int numberOfBreaks = 0;

		Set<Long> speakerIds = new HashSet<Long>();
		Set<Long> roomIds = new HashSet<Long>();

		for (ScheduleItem scheduleItem : scheduleItems) {

			roomIds.add(scheduleItem.getRoom().getId());

			final Date fromTime = scheduleItem.getFromTime();
			final Date dayOfConference = CalendarUtils.getCalendarWithoutTime(fromTime).getTime();
			days.add(dayOfConference);

			if (ScheduleItemType.KEYNOTE.equals(scheduleItem.getScheduleItemType())
					|| ScheduleItemType.SESSION.equals(scheduleItem.getScheduleItemType())) {

				numberOfSessions++;

				if (scheduleItem.getPresentation() != null) {
					speakerIds.add(scheduleItem.getPresentation().getSpeaker().getId());
				} else {
					numberOfUnassignedSessions++;
				}

				if (ScheduleItemType.KEYNOTE.equals(scheduleItem.getScheduleItemType())) {
					numberOfKeynoteSessions++;

				}

				if (ScheduleItemType.SESSION.equals(scheduleItem.getScheduleItemType())) {
					numberOfBreakoutSessions++;
				}

			}

			if (ScheduleItemType.BREAK.equals(scheduleItem.getScheduleItemType())) {
				numberOfBreaks++;
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(fromTime);

			String loopHour = cal.get(Calendar.HOUR_OF_DAY) + "_" + cal.get(Calendar.MINUTE);

			if (hourOfDay == null || !hourOfDay.equals(loopHour)) {
				currentScheduleItem = scheduleItem;
				hourOfDay = loopHour;
			} else {
				currentScheduleItem.setRowspan(currentScheduleItem.getRowspan() + 1);
			}

		}

		scheduleItemList.setDays(days);
		scheduleItemList.setNumberOfBreakoutSessions(numberOfBreakoutSessions);
		scheduleItemList.setNumberOfBreaks(numberOfBreaks);
		scheduleItemList.setNumberOfSessions(numberOfSessions);
		scheduleItemList.setNumberOfKeynoteSessions(numberOfKeynoteSessions);
		scheduleItemList.setNumberOfUnassignedSessions(numberOfUnassignedSessions);
		scheduleItemList.setNumberOfSpeakersAssigned(speakerIds.size());
		scheduleItemList.setNumberOfRooms(roomIds.size());
		return scheduleItemList;
	}

	@Override
	@Transactional
	public Evaluation saveEvaluation(Evaluation evaluation) {
		return evaluationDao.save(evaluation);
	}

	@Override
	public List<Evaluation> getEvaluationsForCurrentEvent() {
		return evaluationDao.getEvaluationsForCurrentEvent();
	}

	@Override
	public void removeEvaluation(Long evaluationId) {
		evaluationDao.remove(evaluationId);
	}

	@Override
	public List<CfpSubmission> getCfpSubmissions(Long eventId) {
		return cfpSubmissionDao.getCfpSubmissions(eventId);
	}

	@Override
	@Transactional
	public CfpSubmission saveCfpSubmission(CfpSubmission cfpSubmission) {
		return cfpSubmissionDao.save(cfpSubmission);
	}

	@Override
	public CfpSubmission saveAndNotifyCfpSubmission(final CfpSubmission cfpSubmission) {
		final BusinessService businessService = this;
		final CfpSubmission savedCfpSubmission = transactionTemplate.execute(new TransactionCallback<CfpSubmission>() {
			public CfpSubmission doInTransaction(TransactionStatus status) {
				return businessService.saveCfpSubmission(cfpSubmission);
			}
		});

		final String mailEnabled = environment.getProperty("mail.enabled");

		if (Boolean.valueOf(mailEnabled)) {
			mailChannel.send(MessageBuilder.withPayload(cfpSubmission).build());
		}

		return savedCfpSubmission;
	}

	@Override
	public CfpSubmission getCfpSubmission(Long cfpId) {
		return this.cfpSubmissionDao.get(cfpId);
	}

	@Override
	public Track getTrack(Long id) {
		return trackDao.get(id);
	}

	@Override
	public PresentationTag getPresentationTag(String tagName) {
		return presentationTagDao.getPresentationTag(tagName);
	}

	@Override
	public PresentationTag savePresentationTag(PresentationTag presentationTag) {
		return presentationTagDao.save(presentationTag);
	}

	@Override
	public Map<PresentationTag, Long> getTagCloud(Long eventId) {
		return presentationTagDao.getPresentationTagCountForEvent(eventId);
	}

}