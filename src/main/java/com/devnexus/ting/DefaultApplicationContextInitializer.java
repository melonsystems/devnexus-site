/*
 * Copyright 2002-2015 the original author or authors.
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
package com.devnexus.ting;

import java.io.File;
import java.io.IOException;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;

import com.devnexus.ting.common.Apphome;
import com.devnexus.ting.common.SpringContextMode;
import com.devnexus.ting.common.SpringProfile;
import com.devnexus.ting.common.SystemInformationUtils;

/**
 *
 * @author Gunnar Hillert
 *
 */
public class DefaultApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApplicationContextInitializer.class);
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {


		final CloudEnvironment env = new CloudEnvironment();
		if (env.getInstanceInfo() != null) {
			LOGGER.info("cloud API: " + env.getCloudApiUri());
			applicationContext.getEnvironment().setActiveProfiles("cloud", SpringContextMode.DemoContextConfiguration.getCode());
		}
		else {

			final Apphome apphome = SystemInformationUtils.retrieveBasicSystemInformation();
			SpringContextMode springContextMode;

			if (SystemInformationUtils.existsConfigFile(apphome.getAppHomePath())) {
				springContextMode = SpringContextMode.ProductionContextConfiguration;
			} else {
				springContextMode = SpringContextMode.DemoContextConfiguration;
			}

			applicationContext.getEnvironment().setActiveProfiles(springContextMode.getCode());
		}

		final ConfigurableEnvironment environment = applicationContext.getEnvironment();

		if (environment.acceptsProfiles(SpringProfile.STANDALONE)) {
			final String tingHome = environment.getProperty(Apphome.APP_HOME_DIRECTORY);
			final PropertySource<?> propertySource;
			final YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();

			final String productionPropertySourceLocation = "file:" + tingHome + File.separator + SystemInformationUtils.TING_CONFIG_FILENAME;
			try {
				propertySource = yamlPropertySourceLoader.load("devnexus-standalone", new DefaultResourceLoader().getResource(productionPropertySourceLocation), null);
			} catch (IOException e) {
				throw new IllegalStateException("Unable to get ResourcePropertySource '" + productionPropertySourceLocation + "'", e);
			}
			environment.getPropertySources().addFirst(propertySource);
			LOGGER.info("Properties for standalone mode loaded [" + productionPropertySourceLocation + "].");
		}
		else {
			LOGGER.info("Using Properties for demo mode.");
		}

		final boolean mailEnabled      = environment.getProperty("mail.enabled",      Boolean.class, Boolean.FALSE);
		final boolean twitterEnabled   = environment.getProperty("twitter.enabled",   Boolean.class, Boolean.FALSE);
		final boolean websocketEnabled = environment.getProperty("websocket.enabled", Boolean.class, Boolean.FALSE);

		if (mailEnabled) {
			applicationContext.getEnvironment().addActiveProfile(SpringProfile.MAIL_ENABLED);
		}
		if (twitterEnabled) {
			applicationContext.getEnvironment().addActiveProfile(SpringProfile.TWITTER_ENABLED);
		}
		if (websocketEnabled) {
			applicationContext.getEnvironment().addActiveProfile(SpringProfile.WEBSOCKET_ENABLED);
		}

	}

}