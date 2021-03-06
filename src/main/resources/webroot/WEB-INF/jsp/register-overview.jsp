<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>

<head>
    <title>${contextEvent.title} |  DevNexus 2017</title>
</head>
<body>
    <section class="container-fluid register" >


        <h1 class="featured-header">
            REGISTER
        </h1>
        <section>
            <c:if test="${not empty groupTicketGroups}">
                <h2>
                    INDIVIDUAL
                </h2>
            </c:if>
            <div class="ticket-detail">
                <header>
                    <c:if test="${empty invididualTicketGroups}">
                        <p>
                            Stay tuned!  We will have tickets available soon!
                        </p>
                    </c:if>


                </header>

                <c:if test="${ not empty invididualTicketGroups}">
                    <div class="row tickets">

                        <c:forEach items="${invididualTicketGroups}" var="ticket" varStatus="status">

                            <div class="col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <p class="pull-right">${ticket.price}</p>
                                        <h3 class="panel-title">${ticket.label}</h3>
                                    </div>
                                    <div class="panel-body">
                                        <a class="btn btn-register" href="register.html">BUY NOW</a>
                                        ${ticket.descriptionAsHtml}
                                    </div>
                                </div>
                            </div>

                        </c:forEach>


                    </div>
                </c:if>
            </div>
        </section>

        <section>
            <c:if test="${not empty groupTicketGroups}">

                <h2>
                    GROUP
                </h2>
            </c:if>
            <div class="ticket-detail">
                <header>

                    <c:if test="${empty groupTicketGroups}">
                        <p>
                            Stay tuned!  We will have group tickets available soon!
                        </p>
                    </c:if>
                </header>

                <c:if test="${ not empty groupTicketGroups}">

                    <div class="row tickets">


                        <c:forEach items="${groupTicketGroups}" var="ticket" varStatus="status">

                            <div class="col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <p class="pull-right">${ticket.price}</p>
                                        <h3 class="panel-title">${ticket.label}</h3>
                                    </div>
                                    <div class="panel-body">
                                        <a class="btn btn-register" href="register.html">BUY NOW</a>
                                        ${ticket.descriptionAsHtml}
                                    </div>
                                </div>
                            </div>

                        </c:forEach>

							<div class="col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <p class="pull-right">$695</p>
                                        <h3 class="panel-title">LATE WORKSHOP/CONFERENCE COMBO PASS (FEB 22-24)</h3>
                                    </div>
                                    <div class="panel-body">
                                        <a class="btn btn-register"
                                           href="mailto:info@ajug.org?subject=DevNexus+Workshop+Conference+Combo+Pass+(Group)">EMAIL US</a>
                                        Contact us to purchase group passes for the workshop/conference combo pass.
                                    </div>
                                </div>
                            </div>
                    </div>
                </c:if>
            </div>
        </section>
    </section>

</body>
