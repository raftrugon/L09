<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>

<form:form id="categoryForm" modelAttribute="category">

<!-- Shared Variables -->
		<jstl:set var="model" value="category" scope="request"/>	
		
		<!-- Hidden Attributes -->
		<lib:input name="id,version,zervices" type="hidden" />
			
		<!-- Attributes -->
		
		<lib:input name="name" type="text" />
		<lib:input name="description" type="text" />
<div class="form-group">
	<input id="saveCategoryButton" type="button" class="btn btn-block btn-success" value="<spring:message code='category.save'/> ">
</div>
</form:form>
