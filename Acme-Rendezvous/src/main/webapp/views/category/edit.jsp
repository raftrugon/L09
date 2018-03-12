<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>

<script>
	$(function(){
		$.get('ajax/category/getSubCategories.do',function(data){
			$('#parentDiv').treeview({
				data:data,
				showTags:true,
				onNodeSelected: function(event,node){
					$('#category').val($(node).attr("categoryId"));
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

<form:form id="categoryForm" modelAttribute="category">

<!-- Shared Variables -->
		<jstl:set var="model" value="category" scope="request"/>	
		
		<!-- Hidden Attributes -->
		<lib:input name="id,version,zervices" type="hidden" />
			
		<!-- Attributes -->
		
		<lib:input name="name" type="text" />
		<lib:input name="description" type="text" />
		
		
		<div id="parentDiv" class="form-group">
		</div>
<div class="btn-group btn-group-justified">
		<div class="btn-group">
			<input class="btn btn-success" id="saveCategoryButton" name="save" value="<spring:message code="category.save"/>" />
		</div>
		<jstl:if test="${category.id != 0 && empty category.zervices}">
			<div class="btn-group">
		   		<input class="btn btn-danger deleteCategoryButton" id="${category.id}" name="delete" value="<spring:message code="category.delete" />" />
			</div>
	  	</jstl:if>
	</div>
</form:form>


<script>
$(function(){
	$('#saveCategoryButton').click(function(e){
		e.preventDefault();
		$.post( "admin/ajax/category/save.do",{category: $('#categoryForm').serialize()}, function( data ) {
			if(data==1) {
				notify('success','<spring:message code="category.edit.success"/>');
				$('#categoryEditModal').modal('hide');
				loadCategories();
			}
			else{
				notify('danger','<spring:message code="category.edit.error"/>');
				$('#categoryEditModal').modal('hide');
			}
		});
	});
});
</script>

<script>
$(function(){
	$('.deleteCategoryButton').click(function(e){
		e.preventDefault();
		$.post( "admin/ajax/category/delete.do",{categoryId: $(this).attr('id')}, function( data ) {
			if(data==1) {
				notify('success','<spring:message code="category.delete.success"/>');
				$('#categoryEditModal').modal('hide');
				loadCategories();
			}
			else{
				notify('danger','<spring:message code="category.delete.error"/>');
				$('#categoryEditModal').modal('hide');
			}
		});
	});
});
</script>