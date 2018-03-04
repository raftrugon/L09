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

<jstl:if test="${categoryParents ne null }">
		<script>
	$(function(){
		var categoryParents = <jstl:out value="${categoryParents}"/> ;
		var recursiveStr = 'printSubCategories()';
		$.each(categoryParents,function(i,id){
			recursiveStr += '.then(printSubCategories('+id+'))';
		});
		eval(recursiveStr);
	});
	</script>
</jstl:if>
<jstl:if test="${categoryParents eq null }">
	<script>
	$(function(){
		printSubCategories();
	});
	</script>
</jstl:if>
<script>
	$(function(){
		$('#zervice').submit(function(){
			$('#category').val($('#categoryDiv .btn-success').val());
		});
	});
	
	function printSubCategories(id){
		var div,url;
		$('#categoryDiv .btn').removeClass('btn-success');
		if(jQuery.type(id) == 'undefined'){
			url = 'ajax/category/getSubCategories.do';
			div = $('#categoryDiv');
		}else{
			url = 'ajax/category/getSubCategories.do?categoryId='+id;
			div = $('#categoryDiv'+id);
			$('#categoryBtn'+id).addClass("btn-success");
			div.siblings('div').html('');
		}
		return $.get(url,function(data){
			var htmlString = '';
			$.each(JSON.parse(data),function(i,val){
				var arr = val.split("$$");
				htmlString += '<button id="categoryBtn'+arr[0]+'"type="button" onclick="javascript:printSubCategories('+arr[0]+')" value="'+arr[0]+'" class="btn btn-primary" style="margin:2px">'+arr[1]+'&ensp;<span class="badge">'+arr[2]+'</span></button><br><div id="categoryDiv'+arr[0]+'" style="padding-left:1em"></div>';
			});
			div.html(htmlString);
		});
	}
</script>
<div class="well col-md-6 col-md-offset-3">
	<form:form action="manager/zervice/save.do" modelAttribute="zervice">		
		
		<!-- Shared Variables -->
		<jstl:set var="model" value="zervice" scope="request"/>	
		
		<!-- Hidden Attributes -->
		<lib:input name="id,category" type="hidden" />
			
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
