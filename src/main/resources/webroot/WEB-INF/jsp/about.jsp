<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>

<head>
	<title>${contextEvent.title} | About</title>
	<style type="text/css">

	.tech-logos {
		text-align: center;
	}
	.tech-logos img {
	background-color: #ffffff;
	margin: 5px 20px;
	border-radius: 8px;
	-webkit-border-radius: 8px;
	-moz-border-radius: 8px;
	-o-border-radius: 8px;

	border-color: rgba(123,173,52,0.3);;
	border-style: solid;
	border-width: 2px;

	transition: all 0.2s;
	-webkit-transition: all 0.2s;
	-moz-transition: all 0.2s;
	-o-transition: all 0.2s;
	-ms-transition: all 0.2s;
}

.tech-logos img:HOVER, .tech-logos img:FOCUS {
	border-color: rgba(123,173,52,1);

	transition: all 0.2s;
	-webkit-transition: all 0.2s;
	-moz-transition: all 0.2s;
	-o-transition: all 0.2s;
	-ms-transition: all 0.2s;
}

	</style>
</head>

<section class="container-fluid conference-information" >
	<h1 class="featured-header">We are Open Source</h1>
	<div class="container">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">

				<div class="row">
					<div class="col-xs-4"><a href="http://opensource.org/" target="_blank"><img src="${ctx}/assets/img/tech-logos/open-source-110.png" alt="Open Source Initiative" title="Open Source Initiative"></a></div>
					<div class="col-xs-8">The DevNexus application is <strong>100% open source</strong> (OSS). The source code is licensed under the liberal <a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">Apache License v2.0</a>.</div>
				</div>
				<div class="row">
					<div class="col-xs-4"><a href="https://github.com/devnexus/devnexus-site" target="_blank"><img src="${ctx}/assets/img/tech-logos/github-110.png" alt="GitHub" title="GitHub"></a></div>
					<div class="col-xs-8">The application is hosted on GitHub at: <a href="https://github.com/devnexus/devnexus-site" target="_blank">https://github.com/devnexus/devnexus-site</a>.</div>
				</div>

				<h2 class="text-center">Are you running in the Cloud?</h2>
					<p>
						DevNexus runs in the cloud allowing us to focus on what is important: planning and executing an awesome conference.
						As PaaS platform, we are using:
					</p>
					<p class="text-center">
						<a href="http://run.pivotal.io/" target="_blank"><img alt="Pivotal Web Services" title="Pivotal Web Services" src="${ctx}/assets/img/pws-logo.png"></a>
					</p>
					<p>
						We are <strong>super-grateful</strong> for the support provided by <a href="http://pivotal.io/" target="_blank">Pivotal</a>. PWS uses <a href="https://www.cloudfoundry.org/" target="_blank">Cloud Foundry</a>
						and gives us among others the following benefits:
					</p>
					<ul style="list-style: disc;">
						<li><a href="http://martinfowler.com/bliki/BlueGreenDeployment.html" target="_blank">Blue/Green deployments</a></li>
						<li>Fault tolerance</li>
						<li>Ability to scale up instances with the push of a button</li>
					</ul>
				<h2 class="text-center">Show me your Stack</h2>
				<div class="row">
					<div class="col-xs-12">
							<p>
								Some of the technologies we use are:
							</p>

						<div class="row tech-logos">
							<div class="col-md-12">
								<a href="http://www.eclipse.org/jetty/"                 target="_blank"><img src="${ctx}/assets/img/tech-logos/jetty-110.png"              alt="Jetty"              title="Jetty"></a>
								<a href="http://www.postgresql.org/"                    target="_blank"><img src="${ctx}/assets/img/tech-logos/postgres-110.png"           alt="PostgreSQL"         title="PostgreSQL"></a>
								<a href="http://hibernate.org/"                         target="_blank"><img src="${ctx}/assets/img/tech-logos/hibernate-110.png"          alt="Hibernate"          title="Hibernate"></a>
								<a href="http://redis.io/"                              target="_blank"><img src="${ctx}/assets/img/tech-logos/redis-110.png"              alt="Redis"              title="Redis"></a>
								<a href="http://projects.spring.io/spring-framework/"   target="_blank"><img src="${ctx}/assets/img/tech-logos/spring-framework-110.png"   alt="Spring Framework"   title="Spring Framework"></a>
								<a href="http://projects.spring.io/spring-boot/"        target="_blank"><img src="${ctx}/assets/img/tech-logos/spring-boot-110.png"        alt="Spring Boot"        title="Spring Boot"></a>
								<a href="http://projects.spring.io/spring-security/"    target="_blank"><img src="${ctx}/assets/img/tech-logos/spring-security-110.png"    alt="Spring Security"    title="Spring Security"></a>
								<a href="http://projects.spring.io/spring-data/"        target="_blank"><img src="${ctx}/assets/img/tech-logos/spring-data-110.png"        alt="Spring Data"        title="Spring Data"></a>
								<a href="http://projects.spring.io/spring-social/"      target="_blank"><img src="${ctx}/assets/img/tech-logos/spring-social-110.png"      alt="Spring Social"      title="Spring Social"></a>
								<a href="http://projects.spring.io/spring-integration/" target="_blank"><img src="${ctx}/assets/img/tech-logos/spring-integration-110.png" alt="Spring Integration" title="Spring Spring Integration"></a>
							</div>
						</div>
					</div>
				</div>

				<h2 class="text-center">We are giving back to the OSS Community</h2>

				<div class="row">
					<div class="col-xs-2"><a href="http://junit.org/junit-lambda.html" target="_blank"><img style="margin-top: 40px;" src="${ctx}/assets/img/tech-logos/junit-lambda-110.png" alt="Junit Lambda" title="Junit Lambda"></a></div>
					<div class="col-xs-10"><p>The <a href="http://www.ajug.org/"
					target="_blank">Atlanta Java Users Group</a> and DevNexus
					have been one of the earliest major <a href="http://junit.org/junit-lambda-contributors.html" target="_blank">
					<strong>Crowdfunding Campaign Contributors</strong></a>  for the <strong>JUnit Lambda</strong> project.
					<a href="http://junit.org/" target="_blank">JUnit</a> is one of the globe's most heavily used testing libraries.
					We want to support the efforts of creating the next major version
					of JUnit, enabling it to support Java 8 features (including Lambdas) among other features.</p></div>
				</div>
			</div>
		</div>
	</div>
</section>
