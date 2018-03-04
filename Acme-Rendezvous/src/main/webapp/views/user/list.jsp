<%--
 * action-1.jsp
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
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<jstl:forEach items="${users}" var="user">
<jstl:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 9) + 1 %></jstl:set>
<div class="col-md-2">
	<div class="userCard" style="overflow:hidden">
		  <img src="images/kS${rand}.png" style="width:100%;margin-top:-25px;">
		  <button class="cardUserButton" onclick="location.href = 'user-display.do?userId=${user.id}'" style="margin-top:-25px;"><jstl:out value="${user.name} ${user.surnames}"/></button>
		  <p><strong><spring:message code="user.address" /></strong></p>
		  <p><jstl:if test="${user.address eq null}">-</jstl:if><jstl:out value="${user.address}"/></p>
		  <p><strong><spring:message code="user.phoneNumber" /></strong></p>
		  <p><jstl:if test="${user.phoneNumber eq null}">-</jstl:if><jstl:out value="${user.phoneNumber}"/></p>
		  <p><strong><spring:message code="user.email" /></strong></p>
		  <p><jstl:out value="${user.email}"/></p>
		  <p><strong><spring:message code="user.birthDate" /></strong></p>
		  <p><fmt:formatDate value="${user.birthDate}" type="date" dateStyle="long"/></p>
	</div>
</div>
</jstl:forEach>