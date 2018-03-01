 <%--
 * login.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script type="text/javascript" src="scripts/md5.min.js"></script>

<div class="col-sm-4 col-sm-offset-4">
<form:form id="loginForm" action="j_spring_security_check" modelAttribute="credentials" >
	<div class="form-group">
	<form:label path="username">
		<spring:message code="security.username" />
	</form:label>
	<form:input id="usernameInput" path="username" class="form-control"/>	
	<form:errors class="error" path="username" />
	<br />
	</div>
	<div class="form-group">
	<form:label path="password">
		<spring:message code="security.password" />
	</form:label>
	<form:password path="password" class="form-control"/>	
	<form:errors class="error" path="password" />
	<br />
	</div>
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="security.login.failed" />
		</div>
	</jstl:if>

	
	<input type="submit" class="btn btn-success btn-group-justified" value="<spring:message code="security.login" />" />
		
	
	
</form:form>
</div>
