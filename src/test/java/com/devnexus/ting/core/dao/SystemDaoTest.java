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
package com.devnexus.ting.core.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

/**
 * @author Gunnar Hillert
 *
 */
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class SystemDaoTest extends BaseDaoIntegrationTest {

	@Autowired private SystemDao systemDao;

	/**
	 *
	 */
	@Test
	public void testGenerateSchema() {
		//systemDao.createDatabase(true, "org.hibernate.dialect.MySQLDialect");
		systemDao.createDatabase(true, "org.hibernate.dialect.PostgreSQLDialect");
		//systemDao.createDatabase(true, "org.hibernate.dialect.H2Dialect");
	}

}
