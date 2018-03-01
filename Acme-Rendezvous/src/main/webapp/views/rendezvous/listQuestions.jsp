<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>

<div class=center-text>
<display:table pagesize="5" class= "displaytag" keepStatus="true" name="questions" requestURI="${requestUri}" id="row">

	<display:column sortable="true"> 
		<jstl:out value="${row}" />
	</display:column>
	
</display:table>

<jstl:if test="${pageContext.request.userPrincipal.name eq rendezvous.user.userAccount.username}"> 
<a href="user/rendezvous/questions-edit.do?rendezvousId=${rendezvous.id}">
<spring:message code="rendezvous.edit"/></a>
</jstl:if>

</div>
