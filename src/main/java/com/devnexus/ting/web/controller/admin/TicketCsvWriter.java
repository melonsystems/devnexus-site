/*
 * Copyright 2015 the original author or authors.
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
package com.devnexus.ting.web.controller.admin;

import static org.supercsv.prefs.CsvPreference.STANDARD_PREFERENCE;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.AbstractCsvWriter;
import org.supercsv.io.ICsvBeanWriter;

import com.devnexus.ting.model.TicketOrderDetail;

/**
 * This class is responsible for formatting TicketOrderDetail instances as csv
 * rows.
 *
 * @author Summers Pittman
 */
class TicketCsvWriter extends AbstractCsvWriter implements ICsvBeanWriter {

	private final DateFormat dateFormat;

	/**
	 *
	 * Creates the ticket writer using a date format.
	 *
	 * @param writer
	 *            the writer
	 * @param dateFormat
	 *            The formate of the created date field.
	 */
	public TicketCsvWriter(PrintWriter writer, DateFormat dateFormat) {
		super(writer, STANDARD_PREFERENCE);
		this.dateFormat = dateFormat;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final Object source, final String... nameMapping) throws IOException {

		TicketOrderDetail detail = (TicketOrderDetail) source;
		// update the current row/line numbers
		super.incrementRowAndLineNo();

		// write the list
		super.writeRow(detail.getFirstName(), detail.getLastName(), detail.getEmailAddress(), detail.getCity(),
				detail.getState(), detail.getCountry(), detail.getJobTitle(), detail.getCompany(),
				detail.gettShirtSize(), detail.getVegetarian(), detail.getSponsorMayContact(),
				dateFormat.format(detail.getCreatedDate()), detail.getLabel(),
				detail.getRegistration().getPaymentState().name(), detail.getRegistration().getContactEmailAddress(), detail.getRegistration().getContactName(),detail.getRegistration().getContactPhoneNumber());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final Object source, final String[] nameMapping, final CellProcessor[] processors)
			throws IOException {
		TicketOrderDetail detail = (TicketOrderDetail) source;
		// update the current row/line numbers
		super.incrementRowAndLineNo();

		// write the list
		super.writeRow(detail.getFirstName(), detail.getLastName(), detail.getEmailAddress(), detail.getCity(),
				detail.getState(), detail.getCountry(), detail.getJobTitle(), detail.getCompany(),
				detail.gettShirtSize(), detail.getVegetarian(), detail.getSponsorMayContact(),
				dateFormat.format(detail.getCreatedDate()), detail.getLabel(),
				detail.getRegistration().getPaymentState().name());

	}

}
