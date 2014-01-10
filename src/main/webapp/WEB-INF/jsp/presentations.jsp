<%@page import="com.devnexus.ting.core.model.PresentationType" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>

<head>
	<title><c:out value="${event.title}"/> - Presentations</title>
</head>

<div id="devnex" class="jumbotron">
    <div class="container">
        <div id="banner">
            <h1 id="gray"><c:out value="${event.title}"/></h1>

            <h1 id="white">Presentations</h1>
            <h3>Data + Integration, Java/JavaEE/Spring, HTML5 + JavaScript, Alternative Languages, Cloud, Agile + Tools, Mobile</h3>
        </div>
    </div>
</div>

<c:if test="${presentationList.presentations.isEmpty()}">
    <style>
        .jumbotron {
            margin-bottom: 0px;
        }
    </style>
    <div id="yellow" class="jumbotron" style="margin-bottom:0">
        <div class="container">
            <h1>Presentations are still coming in.</h1>

            <div class="row">
                <div class="col-md-12">

                    <p>
                        We will begin announcing topics in January; in the meanwhile, why don't you check out last year's presentations.
                    </p>
                    <c:url var="oldUrl" value="${baseSiteUrl}/devnexus2013/presentations"/>

                    <center><a href="${oldUrl}" class="btn btn-primary btn-lg">DevNexus 2013 Presentations</a></center>


                </div>

            </div>
        </div>
    </div>
</c:if>
<c:if test="${presentationList.presentations.isEmpty() eq false}">

<div class="container">
	<!-- Example row of columns -->
	<c:set var="trackName" value="nill"/>
	<c:forEach items="${presentationList.presentations}" var="presentation" varStatus="status">
		<c:if test="${presentation.room ne null}">
			<c:if test="${trackName ne presentation.room.name}">
				<c:set var="trackName" value="${presentation.room.name}"/>
				<c:if test="${!status.first}">
				    </div>
					</div>
				</c:if>
				<h1 class="${presentation.room.cssStyleName}"><strong>${presentation.room.name}</strong><br/>Workshop</h1>

				<div id="h4wrap"><h4>Presentations</h4></div>
				<div id="speakers">
					<div class="row ${presentation.room.cssStyleName} js-masonry" data-masonry-options='{"itemSelector": ".presentation" }'>
			</c:if>
			<div id="id-${presentation.id}" class="col-md-4 presentation">
				<div class="row ${presentation.room.cssStyleName}">
					<div class="col-md-5">
						<c:if test="${presentation.speaker.picture != null}">
							<img class="speaker" src="${ctx}${baseSiteUrl}/speakers/${presentation.speaker.id}.jpg"/>
						</c:if>
					</div>
					<div class="col-md-7">
						<c:choose>
							<c:when test="${not empty presentation.speaker}">
								<h4>
									<a href="${siteUrl}/speakers#${presentation.speaker.firstName}_${presentation.speaker.lastName}">
										<c:out value="${presentation.speaker.firstName}"/> <c:out
												value="${presentation.speaker.lastName}"/>
									</a>
								</h4>
							</c:when>
							<c:otherwise>
								<p class="speaker">TBD</p>
							</c:otherwise>
						</c:choose>
						<h3 class="title"><c:out value="${presentation.title}"/></h3>
						<c:out value=""/>
						<c:out value="${presentation.descriptionAsHtml}" escapeXml="false"/>
						<c:if test="${not empty presentation.skillLevel}">
							<br/>
							<strong>Skill Level: </strong><c:out value="${presentation.skillLevel.name}"/>
						</c:if>
						<c:choose>
							<c:when test="${not empty presentation.presentationLink}">
								<br/>
								<p class="download">
									<a href="${presentation.presentationLink}">Download Presentation</a> (External Link)
								</p>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${presentation.presentationFile != null}">
								<br/>
								<p class="download">
									<a href="${ctx}${baseSiteUrl}/presentations/${presentation.id}/slides">Download Presentation</a>
								</p>
							</c:when>
						</c:choose>
						<c:if test="${not empty presentation.audioLink}">
							<br/>
							<p class="download">
								<a href="${presentation.audioLink}">Audio Recording</a> (MP3)
							</p>
						</c:if>
					</div>
				</div>
			</div>
		</c:if>
        <c:if test="${presentation.room eq null}">
            <c:if test="${trackName ne 'Sessions'}">
                <c:set var="trackName" value="${'Sessions'}"/>
                <c:if test="${!status.first}">
                    </div>
                    </div>
                </c:if>
                <h1 class="track-1"><strong>Sessions</strong><br/></h1>

                <div id="h4wrap"><h4>Presentations</h4></div>
                <div id="speakers">
                <div class="row track-1 js-masonry" data-masonry-options='{"itemSelector": ".presentation" }'>
            </c:if>
            <div id="id-${presentation.id}" class="col-md-4 presentation">
                <div class="row track-1">
                    <div class="col-md-5  ">
                        <c:if test="${presentation.speaker.picture != null}">
                            <img class="speaker" src="${ctx}${baseSiteUrl}/speakers/${presentation.speaker.id}.jpg"/>
                        </c:if>
                    </div>
                    <div class="col-md-7">
                        <c:choose>
                            <c:when test="${not empty presentation.speaker}">
                                <h4>
                                    <a href="${siteUrl}/speakers#${presentation.speaker.firstName}_${presentation.speaker.lastName}">
                                        <c:out value="${presentation.speaker.firstName}"/> <c:out
                                            value="${presentation.speaker.lastName}"/>
                                    </a>
                                </h4>
                            </c:when>
                            <c:otherwise>
                                <p class="speaker">TBD</p>
                            </c:otherwise>
                        </c:choose>
                        <h3 class="title"><c:out value="${presentation.title}"/></h3>
                        <c:out value=""/>
                        <c:out value="${presentation.descriptionAsHtml}" escapeXml="false"/>
                        <c:if test="${not empty presentation.skillLevel}">
                            <br/>
                            <strong>Skill Level: </strong><c:out value="${presentation.skillLevel.name}"/>
                        </c:if>
                        <c:choose>
                            <c:when test="${not empty presentation.presentationLink}">
                                <br/>
                                <p class="download">
                                    <a href="${presentation.presentationLink}">Download Presentation</a> (External Link)
                                </p>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${presentation.presentationFile != null}">
                                <br/>
                                <p class="download">
                                    <a href="${ctx}${baseSiteUrl}/presentations/${presentation.id}/slides">Download Presentation</a>
                                </p>
                            </c:when>
                        </c:choose>
                        <c:if test="${not empty presentation.audioLink}">
                            <br/>
                            <p class="download">
                                <a href="${presentation.audioLink}">Audio Recording</a> (MP3)
                            </p>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:if>
	</c:forEach>
</div>
</div>

</div>

<content tag='bottom'>
	<script type="text/javascript">
		$(document).ready(function() {
			var $container = $('#bio');

			$container.imagesLoaded(function () {
				$container.masonry({
						itemSelector: '.speakerContainer',
						columnWidth: '.speakerContainer',
						isAnimated: true
				});
			});

		});
	</script>
</content>
</c:if>