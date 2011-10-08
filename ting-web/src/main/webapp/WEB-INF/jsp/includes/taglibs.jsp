<%@page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" deferredSyntaxAllowedAsLiteral="true"%>

<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://code.google.com/p/jmesa" prefix="jmesa" %>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page"    prefix="page"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>

<c:set var="ctx" value="${pageContext['request'].contextPath}"/>
<head>
    <c:choose>
        <c:when test="${currentSitePreference.mobile}">

            <meta name="decorator" content="mobile"/>
        </c:when>
        <c:otherwise>
            <meta name="decorator" content="default"/>
        </c:otherwise>
    </c:choose>
</head>