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
		$.get('ajax/category/getSubCategories.do',function(data){
			$('#categoryDiv').treeview({
				data:data,
				showTags:true,
				onNodeSelected: function(event,node){
					$('#category').val($(node).attr("categoryId"));
				},	
				onNodeUnselected: function(event,node){
					$('#category').val(null);
				}
			});
			var category = $('#category').val();
			if(typeof(category) !== 'undefined'){
				var selectNode = jQuery.grep($('#categoryDiv').treeview('getUnselected',null),function(n){
					return n.categoryId === parseInt($('#category').val());
				});
				$('#categoryDiv').treeview('selectNode',[selectNode[0],{silent:true}]);
			}
		});
	});
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
		<lib:input name="price" type="text" />			
		<lib:input name="picture" type="text" />
		<hr> 
		<div class="form-group">
		<form:errors cssClass="error" path="category" />	
		<div id="categoryDiv" class="form-group">
		</div>
		</div>
		<hr>		
		<lib:button model="zervice" id="${zervice.id}" cancelUri="zervice/list.do"/>
	</form:form>		
</div>
