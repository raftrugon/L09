<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	$(function(){
		$.get('ajax/category/getSubCategories.do?admin=true',function(data){
			$('#categoryDiv').treeview({
				data:data,
				showTags:true,
				highlightSelected:false,
				onNodeSelected: function(event,node){
					$(node).attr("categoryId");
					$.get('admin/ajax/category/edit.do',{categoryId: $(node).attr("categoryId")}, function(data){
						$('#categoryEditModal .modal-body').html(data);
						$('#categoryEditModal').modal('show');
					});
				}	
			});
		});
	});
</script>

<div class="well col-md-6 col-md-offset-3">
	<div id="categoryDiv" class="form-group">
	</div>
	
	<div id="categoryEditModal" class="modal fade categoryEditModal" role="dialog">
	  <div class="modal-dialog modal-lg" style="margin-top:10vh">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title"><spring:message code="category.edit"/></h4>
	      </div>
	      <div class="modal-body" style="overflow-y:auto">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
</div>
<script>
$(function(){
	$('.deleteCategory').click(function(e){
		e.stopPropagation();
		e.preventDefault();
		$.post( "admin/ajax/category/delete.do",{categoryId: $(this).attr('id')}, function( data ) {
			if(data==1) {
				notify('success','<spring:message code="category.edit.success"/>');
				$('#categoryEditModal').modal('hide');
			}
			else{
				notify('danger','<spring:message code="category.edit.error"/>');
				$('#categoryEditModal').modal('hide');
			}
		});
	});
});
</script>
 