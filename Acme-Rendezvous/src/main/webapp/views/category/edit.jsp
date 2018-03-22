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
					$('#parent').val($(node).attr("categoryId"));
				},	
				onNodeUnselected: function(event,node){
					$('#parent').val(null);
				}
			});
			var parent = $('#parent').val();
			if(parent !== ''){
				var selectNode = jQuery.grep($('#parentDiv').treeview('getUnselected',null),function(n){
					return n.categoryId === parseInt(parent);
				});
				$('#parentDiv').treeview('selectNode',[selectNode[0],{silent:true}]);
				$('#parentDiv').treeview('revealNode',[selectNode[0],{silent:true}]);
			}
			var category = $('#id').val();
			if(category !== ''){
				var selectNode = jQuery.grep($('#parentDiv').treeview('getUnselected',null),function(n){
					return n.categoryId === parseInt(category);
				});
				$('#parentDiv').treeview('disableNode',[selectNode[0],{silent:true}]);
				$('#parentDiv').treeview('collapseNode',[selectNode[0],{silent:true}]);
			}
		});
	});
</script>

<form:form id="categoryForm" modelAttribute="category">

<!-- Shared Variables -->
		<jstl:set var="model" value="category" scope="request"/>	
		
		<!-- Hidden Attributes -->
		<lib:input name="id,parent" type="hidden" />
			
		<!-- Attributes -->
		
		<lib:input name="name" type="text" />
		<p id="nameError" class="error" style="display:none"><spring:message code="org.hibernate.validator.constraints.NotBlank.message"/></p>
		<lib:input name="description" type="text" />
		<p id="descriptionError" class="error" style="display:none"><spring:message code="org.hibernate.validator.constraints.NotBlank.message"/></p>
		
		<div class="alert alert-info"><i class="fas fa-question-circle"></i> <spring:message code="category.selectParent"/></div>
		<div id="parentDiv" class="form-group">
		</div>

		<div class="btn-group btn-group-justified">
			<div class="btn-group">
				<input type="button" class="btn btn-success" id="saveCategoryButton" name="save" value="<spring:message code="category.save"/>" />
			</div>
			<jstl:if test="${category.id != 0 && empty category.zervices && empty category.categories}">
				<div class="btn-group">
			   		<input type="button" class="btn btn-danger deleteCategoryButton" id="${category.id}" name="delete" value="<spring:message code="category.delete" />" />
				</div>
		  	</jstl:if>
		</div>
</form:form>


<script>
$(function(){
	$('#saveCategoryButton').click(function(e){
		e.preventDefault();
		$(this).prop("disabled",true);
		$('#descriptionError').hide();
		$('#nameError').hide();
		var submitting = true;
		if($('#name').val().trim() == ''){
			submitting = false;
			$('#nameError').show();
		}
		if($('#description').val().trim() == ''){
			submitting = false;
			$('#descriptionError').show();
		}
		if(submitting){
			$.post("admin/ajax/category/save.do",$('#categoryForm').serialize(), function( data ) {
					if(data==1) {
						notify('success','<spring:message code="category.edit.success"/>');
						$('#categoryEditModal').modal('hide');
						loadCategories();
					}
					else
						notify('danger','<spring:message code="category.edit.error"/>');
			});
		}else
			$('#saveCategoryButton').prop("disabled",false);
	});
});
</script>

<script>
$(function(){
	$('.deleteCategoryButton').click(function(e){
		e.preventDefault();
		$(this).attr("disabled","disabled");
		$.post( "admin/ajax/category/delete.do",{categoryId: $(this).attr('id')}, function( data ) {
			if(data==1) {
				notify('success','<spring:message code="category.delete.success"/>');
				$('#categoryEditModal').modal('hide');
				loadCategories();
			}
			else{
				notify('danger','<spring:message code="category.delete.error"/>');
			}
		});
	});
});
</script>