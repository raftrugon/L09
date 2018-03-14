<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:useBean id="now" class="java.util.Date" />
<jstl:forEach items="${zervices}" var="zervice">

<jstl:set var="inappropriateStyle" value=""/>
<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 cardContainerZervice" id="zervicesContainer">
			<jstl:if test="${zervice.inappropriate eq true}">
				<jstl:set var="inappropriateStyle" value="filter: blur(5px);-webkit-filter: blur(5px);"/>
				<div class="alert alert-danger" style="position:absolute;top:40%;right:10%;left:10%;text-align:center;z-index:500;"><strong><spring:message code="zervice.inappropriate.alert"/></strong></div>
			</jstl:if>
			
	<div class="card" style="${inappropriateStyle}" style="height:375px">
		<div onclick="${rendClick}" style="height:100%;">
			<jstl:if test="${empty zervice.picture}">
				<div class="nopicContainer">
					<img src="images/nopic2.jpg" style="object-fit:cover;height:200px;width:100%" class="nopic"/>
					<div class="nopicCaption2 alert alert-warning"><spring:message code="master.page.nopic"/></div>
				</div>
			</jstl:if>
			<jstl:if test="${not empty zervice.picture}">
				<img src="${zervice.picture}" style="object-fit:cover;height:200px;width:100%">
			</jstl:if>
	        <h1>
	        	<jstl:out value="${zervice.name}"/>
	        </h1>
	        	<span class="label label-primary"><jstl:out value="${zervice.category.name}"/></span>
	        <div style="text-align:center;margin-top:5px;" class="cardDate">
				<jstl:out value="${zervice.description}"/>
	        </div>
		</div>
		<security:authorize access="hasRole('ADMIN')">
				<jstl:if test="${not zervice.inappropriate}">
					<a class="cardButton btn-danger deleteZerviceButton" id="${zervice.id}"><spring:message code="zervice.markAsInappropriate"/></a>
				</jstl:if>
		</security:authorize>
	</div>
</div>
</jstl:forEach>
