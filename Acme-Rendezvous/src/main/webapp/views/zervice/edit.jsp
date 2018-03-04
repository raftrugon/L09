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

<script>
	$(function(){
		printSubCategories(null);
	});
	
	function printSubCategories(id){
		var categories = [];
		var div = $('#categoryDiv'+id);
		$.get('ajax/category/getSubCategories?categoryId='+id,function(data){
			$.each(data,function(val){
				var arr = data.split("$$");
				div.append('<button id="'+arr[0]+'"type="button" class="btn btn-primary" style="margin:2px">'+arr[1]+'<span class="badge">'+arr[2]+'</span></button>');
			});
		});
	}
</script>
<div class="well col-md-6 col-md-offset-3">
	<form:form action="manager/zervice/save.do" modelAttribute="zervice">		
		
		<!-- Shared Variables -->
		<jstl:set var="model" value="zervice" scope="request"/>	
		
		<!-- Hidden Attributes -->
		<lib:input name="id" type="hidden" />
			
		<!-- Attributes -->
		<h1><spring:message code="master.page.zervice.create" /></h1>
		<hr>
		<lib:input name="name" type="text" />
		<lib:input name="description" type="text" />			
		<lib:input name="picture" type="text" />
		<div id="categoryDiv" class="form-group">
		
		</div>
		<hr>		
		<lib:button noDelete="true" model="zervice" id="${zervice.id}"/>
	</form:form>		
</div>
