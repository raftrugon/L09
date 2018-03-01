<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>	
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
		
<div class="userCard" style="overflow:hidden;padding:0">
  <img src="images/kS1.png" style="width:100%;margin-top:-25px;">
  <button class="cardUserButton" onclick="location.href = 'user-display.do?userId=${user.id}'" style="margin-top:-25px"><jstl:out value="${user.name} ${user.surnames}"/></button>
  <p><strong><spring:message code="user.address" /></strong></p>
  <p><jstl:if test="${user.address eq null}">-</jstl:if><jstl:out value="${user.address}"/></p>
  <p><strong><spring:message code="user.phoneNumber" /></strong></p>
  <p><jstl:if test="${user.phoneNumber eq null}">-</jstl:if><jstl:out value="${user.phoneNumber}"/></p>
  <p><strong><spring:message code="user.email" /></strong></p>
  <p><jstl:out value="${user.email}"/></p>
  <p><strong><spring:message code="user.birthDate" /></strong></p>
  <p><fmt:formatDate value="${user.birthDate}" type="date" dateStyle="long"/></p>
</div>