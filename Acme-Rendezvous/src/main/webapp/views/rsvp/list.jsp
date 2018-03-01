<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>

<div class=center-text>
<display:table pagesize="5" class= "displaytag" keepStatus="true" name="rsvps" requestURI="${requestUri}" id="row">
	<!-- Shared Variables -->
	<jstl:set var="model" value="rsvp" scope="request"/>
	<!-- Attributes -->  	
	<spring:message code="user.name" var="NameHeader" />
	<display:column title="${NameHeader}" sortable="true"> 
		<a href="user-display.do?userId=<jstl:out value="${row.user.id}"/>"><jstl:out value="${row.user.name}"/></a>
	</display:column>
	
	<spring:message code="user.surnames" var="SurnamesHeader" />
	<display:column title="${SurnamesHeader}" sortable="true"> 
		<jstl:out value="${row.user.surnames}" />
	</display:column>
	
	<spring:message code="user.address" var="AddressHeader" />
	<display:column title="${AddressHeader}" sortable="true"> 
		<jstl:out value="${row.user.address}" />
	</display:column>
	
	<spring:message code="user.phoneNumber" var="PhoneNumberHeader" />
	<display:column title="${PhoneNumberHeader}" sortable="true"> 
		<jstl:out value="${row.user.phoneNumber}" />
	</display:column>
	
	<spring:message code="user.email" var="EmailHeader" />
	<display:column title="${EmailHeader}" sortable="true"> 
		<jstl:out value="${row.user.email}" />
	</display:column>
	
	<spring:message code="user.birthDate" var="BirthDateHeader" />
	<display:column title="${BirthDateHeader}" sortable="true" format="{0,date,dd/MM/yy HH:mm}"> 
		<jstl:out value="${row.user.birthDate}" />
	</display:column>
	
	<lib:column name="questionsAndAnswers"/>


</display:table>
	</div>
