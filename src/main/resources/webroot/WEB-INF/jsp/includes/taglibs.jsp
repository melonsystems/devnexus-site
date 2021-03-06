<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<c:set var="ctx" value="${pageContext['request'].contextPath}"/>
<c:set var="baseSiteUrl" value="/s"/>
<c:set var="assetsUrl" value="${ctx}/assets"/>
<c:if test="${event ne null}">
	<c:set var="siteUrl" value="${ctx}${baseSiteUrl}/${event.eventKey}"/>
</c:if>
<c:if test="${event eq null}">
	<c:set var="siteUrl" value="${ctx}${baseSiteUrl}"/>
</c:if>
