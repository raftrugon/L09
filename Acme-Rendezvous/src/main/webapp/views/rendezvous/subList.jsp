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
<jstl:forEach items="${rendezvouss}" var="rendezvous">
<jstl:set var="rendClick" value=""/>
<jstl:set var="rendStyle" value=""/>
<jstl:set var="inappropriateStyle" value=""/>
<jstl:set var="userClick" value=""/>
<jstl:set var="pastRend" value=""/>
<jstl:set var="rsvp" value=""/>
<jstl:set var="mine" value=""/>
<jstl:set var="inappropriateClass" value=""/>
<jstl:if test="${fn:contains(rsvpdRendezvouses, rendezvous)}">
	<jstl:set var="rsvp" value="rsvp"/>
</jstl:if>
<jstl:if test="${rendezvous.user.userAccount.username eq pageContext.request.userPrincipal.name }">
	<jstl:set var="mine" value="mine"/>
</jstl:if>
<jstl:if test="${rendezvous.inappropriate eq true}">
	<jstl:set var="inappropriateStyle" value="filter:blur(5px);-webkit-filter:blur(5px);"/>
	<jstl:set var="inappropriateClass" value="inappropriate"/>
</jstl:if>
<jstl:if test="${rendezvous.inappropriate ne true}">
	<jstl:set var="rendClick" value="location.href = 'rendezvous/display.do?rendezvousId=${rendezvous.id}'"/>
	<jstl:set var="rendStyle" value="cursor:pointer;"/>
	<jstl:set var="userClick" value="location.href = 'user-display.do?userId=${rendezvous.user.id}'"/>
</jstl:if>
<jstl:if test="${rendezvous.organisationMoment lt now}">
	<jstl:set var="pastRend" value="color:red;"/>
</jstl:if>
<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12 ${rsvp} ${mine} ${inappropriate} cardContainer" data-categories='<jstl:forEach items="${rendezvous.categoriesId}" var="categoryId">${categoryId},</jstl:forEach>'>
			<jstl:if test="${rendezvous.inappropriate eq true}">
				<div class="alert alert-danger" style="position:absolute;top:40%;right:10%;left:10%;text-align:center;z-index:500;"><strong><spring:message code="rendezvous.inappropriate.alert"/></strong></div>
			</jstl:if>
	<div class="card" style="${inappropriateStyle}">
		<div onclick="${rendClick}" style="height:100%;${rendStyle}">
			<jstl:if test="${empty rendezvous.picture}">
				<div class="nopicContainer">
					<img src="images/nopic.jpg" style="object-fit:cover;height:200px;width:100%" class="nopic"/>
					<div class="nopicCaption alert alert-warning"><spring:message code="master.page.nopic"/></div>
				</div>
			</jstl:if>
			<jstl:if test="${not empty rendezvous.picture}">
				<img src="${rendezvous.picture}" style="object-fit:cover;height:200px;width:100%">
			</jstl:if>
	        <h1>
	        	<jstl:out value="${rendezvous.name}"/>
	        </h1>
	        <div style="text-align:center;${pastRend}" class="cardDate">
				<fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${rendezvous.organisationMoment}"/>
				<jstl:if test="${rendezvous.organisationMoment lt now}"><br/><strong><i><spring:message code="rendezvous.list.past"/></i></strong></jstl:if>
	        </div>
		</div>
		<input class="cardButton" type="button" name="cancel"
				value="${rendezvous.user.name} ${rendezvous.user.surnames} "	
		onclick="${userClick}"/>
	</div>
</div>
</jstl:forEach>
<script>
	var type = '<jstl:out value="${type}"/>';	
</script>