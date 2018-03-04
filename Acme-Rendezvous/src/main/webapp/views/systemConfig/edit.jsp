<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>




<div class="col-md-4 col-md-offset-4">	
<form:form action="${actionUri}" modelAttribute="systemConfig">		
				
	<!-- Shared Variables -->
	<jstl:set var="model" value="systemConfig" scope="request"/>			
	
	<lib:input name="id" type="hidden" />
	<lib:input name="version" type="hidden" />	

	
	<lib:input name="bussinessName" type="text" />
	<lib:input name="banner" type="text" />
	<lib:input name="welcomeMessageEN" type="text" />
	<lib:input name="welcomeMessageES" type="text" />
	
	<div class="btn-group btn-group-justified">
		<div class="btn-group">
			<input class="btn btn-success" type="submit" id="saveButton" name="save" value="<spring:message code="systemConfig.save"/>" />
		</div>
	</div>

</form:form>
</div>

	        
