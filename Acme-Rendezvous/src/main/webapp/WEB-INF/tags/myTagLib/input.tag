<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<%@ attribute name="model"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Model attribute" %>
<%@ attribute name="name"  rtexprvalue="true"  required="true" type="java.lang.String"  description="Name" %>
<%@ attribute name="type"  rtexprvalue="true"  required="true" type="java.lang.String"  description="Type" %> 
<%@ attribute name="placeholder"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Placeholder" %> 
<%@ attribute name="readonly"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Readonly" %> 
<%@ attribute name="required"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Required" %> 
<%@ attribute name="min"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Min number" %> 
<%@ attribute name="max"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Max number" %> 
<%@ attribute name="step"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Steps" %> 
<%@ attribute name="addon"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Input text addon" %> 
<%@ attribute name="rows"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Rows for textarea" %> 
<%@ attribute name="noLabel"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Disable label" %> 



<jstl:choose>
<jstl:when test="${(type eq 'text' or type eq 'password') and addon ne null}">
	<div class="form-group">
		<div class="input-group">
			<span class="input-group-addon">${addon}</span>
			<form:input type="${type}" class="form-control" placeholder="${placeholder}" path="${name}" readonly="${readonly}" required="${required}"/>
		</div>
	<form:errors cssClass="error" path="${name}" />	
	</div>
</jstl:when>
<jstl:when test="${(type eq 'text' or type eq 'password') and addon eq null }">
	<div class="form-group">
	<jstl:if test="${noLabel eq null}">
		<form:label class="control-label" path="${name}">
			<spring:message code="${model}.${name}" />:
		</form:label>
	</jstl:if>
	<form:input type="${type}" class="form-control" placeholder="${placeholder}" path="${name}" readonly="${readonly}" required="${required}"/>
	<form:errors cssClass="error" path="${name}" />	
	</div>
</jstl:when>
<jstl:when test="${type eq 'number' }">
	<div class="form-group">
	<form:label class="control-label" path="${name}">
		<spring:message code="${model}.${name}" />:
	</form:label>
	<form:input type="number" class="form-control" path="${name}" readonly="${readonly}" min="${min}" max="${max}" step="${step}"/>
	<form:errors cssClass="error" path="${name}" />	
	</div>
</jstl:when>
<jstl:when test="${type eq 'moment' }">
	<div class="form-group">
		<form:label class="control-label " path="${name}">	
			<spring:message code="${model}.${name}" />:	
		</form:label>	
		<div class='input-group date dtPicker' id='dtPicker.${name}'>
	        <form:input type='text' class="form-control" path="${name}"/>
	        <span class="input-group-addon">
	            <span class="glyphicon glyphicon-calendar"></span>
	        </span>
	    </div>
	    <form:errors cssClass="error" path="${name}" />
	</div>
	<script type="text/javascript">
	    $(document).ready(function () {
	        $('.dtPicker').datetimepicker({
	        	format: "DD/MM/YYYY HH:mm",
	        	locale: "<jstl:out value='${pageContext.request.locale.language}'/>"
	        });
	    });
</script>
</jstl:when>
<jstl:when test="${type eq 'date' }">
	<div class="form-group">
		<form:label class="control-label " path="${name}">	
			<spring:message code="${model}.${name}" />:	
		</form:label>	
		<div class='input-group date dtPicker' id='dtPicker.${name}'>
	        <form:input type='text' class="form-control" path="${name}"/>
	        <span class="input-group-addon">
	            <span class="glyphicon glyphicon-calendar"></span>
	        </span>
	    </div>
	    <form:errors cssClass="error" path="${name}" />
	</div>
	<script type="text/javascript">
	    $(document).ready(function () {
	        $('.dtPicker').datetimepicker({
	        	format: "DD/MM/YYYY",
	        	locale: "<jstl:out value='${pageContext.request.locale.language}'/>"
	        });
	    });
</script>
</jstl:when>
<jstl:when test="${type eq 'hidden'}">
	<jstl:set var="hiddenFields" value="${fn:split(name,',')}"/>
	<jstl:forEach var="i" begin="0" end="${fn:length(hiddenFields)-1}">
		<form:hidden path="${hiddenFields[i]}"/>
	</jstl:forEach>
</jstl:when>
<jstl:when test="${type eq 'checkBox' }">
	<div >
	<form:label  path="${name}">
		<spring:message code="${model}.${name}" />:
	</form:label>
	<form:checkbox path="${name}" readonly="${readonly}"/>
	<form:errors cssClass="error" path="${name}" />	
	</div>
</jstl:when>
<jstl:when test="${type eq 'textarea'}">
	<div class="form-group">
			<spring:message code='${model}.${name}' var="placeholder"/>
			<form:textarea required="${required}" readonly="${readonly}" path="${name}" style="resize:none" class="form-control" rows="${rows}" placeholder="${placeholder}" ></form:textarea>
			<form:errors cssClass="error" path="${name}" />	
	</div>
</jstl:when>
</jstl:choose>