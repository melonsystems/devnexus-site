/*
 * Copyright 2002-2013 the original author or authors.
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
package com.devnexus.ting.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Gunnar Hillert
 */
@XmlRootElement(name="cfp-submissions")
@XmlAccessorType(XmlAccessType.FIELD)
public class CfpSubmissionList implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer numberOfCfps;
	private Integer numberOfAcceptedCfps = 0;
	private Integer numberOfRejectedCfps = 0;
	private Integer numberOfPendingCfps = 0;

	@XmlElement(name="cfp-submission")
	private List<CfpSubmission> cfpSubmissions;

	/**
	 * @param cfpSubmissions
	 */
	public CfpSubmissionList(List<CfpSubmission> cfpSubmissions) {
		super();
		this.cfpSubmissions = cfpSubmissions;
		this.numberOfCfps = cfpSubmissions.size();

		for (CfpSubmission cfpSubmission : cfpSubmissions) {
			if (CfpSubmissionStatusType.ACCEPTED.equals(cfpSubmission.getStatus())) {
				numberOfAcceptedCfps++;
			}
			else if (CfpSubmissionStatusType.REJECTED.equals(cfpSubmission.getStatus())) {
				numberOfRejectedCfps++;
			}
			else if (CfpSubmissionStatusType.PENDING.equals(cfpSubmission.getStatus()) || cfpSubmission.getStatus() == null) {
				numberOfPendingCfps++;
			}
			else {
				throw new IllegalStateException("Unsupported CfpSubmissionStatusType " + cfpSubmission.getStatus());
			}
		}

	}

	public CfpSubmissionList() {
		super();
	}

	public List<CfpSubmission> getCfpSubmissions() {
		return cfpSubmissions;
	}

	public void setCfpSubmissions(List<CfpSubmission> cfpSubmissions) {
		this.cfpSubmissions = cfpSubmissions;
	}

	public Integer getNumberOfCfps() {
		return numberOfCfps;
	}

	public Integer getNumberOfAcceptedCfps() {
		return numberOfAcceptedCfps;
	}

	public Integer getNumberOfRejectedCfps() {
		return numberOfRejectedCfps;
	}

	public Integer getNumberOfPendingCfps() {
		return numberOfPendingCfps;
	}

}
