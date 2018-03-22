<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<security:authorize access="hasRole('USER')"> 
<div style="padding-right:15px;padding-left:15px;margin-bottom:10px">
<ul id="typeNav" class="nav nav-pills nav-justified" style="margin-bottom:10px">
  <li data-type="0" id="button0" class=""><a href="javascript:void(0)"><spring:message code="rendezvous.list.all"/></a></li>
  <li data-type="1" id="button1" class=""><a href="javascript:void(0)"><spring:message code="rendezvous.list.mine"/></a></li>
  <li data-type="2" id="button2" class=""><a href="javascript:void(0)"><spring:message code="rendezvous.list.rsvpd"/></a></li>
  <li data-type="3" id="button3" class=""><a href="javascript:void(0)"><spring:message code="rendezvous.list.non-rsvpd"/></a></li>
</ul>
</div>
</security:authorize>

<div id="showCategoriesPanel" class="col-xs-12" style="margin-bottom:10px">
	<button class="btn btn-info btn-block" data-toggle="collapse" data-target="#categoryDiv" ><spring:message code="rendezvous.showCategoryFilter"/>&emsp;<i class="fas fa-angle-down"></i></button>
	<button class="btn btn-info btn-block" data-toggle="collapse" data-target="#categoryDiv" style="display:none"><spring:message code="rendezvous.hideCategoryFilter"/>&emsp;<i class="fas fa-angle-up"></i></button>
</div>
<div id="categoryDiv" class="collapse col-xs-12"></div>

<div id="rendezvousesDiv" class="col-xs-12"></div>


<script defer>
	$(function(){
		$.get('ajax/rendezvous/list.do',function(data){
			$('#rendezvousesDiv').html(data);
			$('#button'+type).addClass('active');
		});
		$.get('ajax/category/getSubCategories.do',function(data){
			$('#categoryDiv').treeview({
				data:data,
				showTags:true,
				multiSelect: true,
			});
			$('#categoryDiv').treeview('collapseAll', { silent: true });
			$('#categoryDiv').on('nodeSelected', function(event,node){
				getSubList();
			});
			$('#categoryDiv').on('nodeUnselected', function(event,node){
				getSubList();
			});
		});
		$('#showCategoriesPanel').click(function(e){
			$(this).children('button').each(function(){
				$(this).toggle();
			});
		});
		$('#typeNav li a').click(function(){
			$('#typeNav li').removeClass('active');
			$(this).parent().addClass('active');
			getSubList();
		});
	});
</script>
<script defer>
	function getSubList(){
		var type = $('#typeNav li.active').attr('data-type');
		var nodes = $('#categoryDiv').treeview('getSelected',null);
		var categories = [];
		$.each(nodes,function(node){
			categories.push($(this).attr('categoryId'));
		});
		$.get('ajax/rendezvous/list.do',{type:type,categories:categories}, function(data){
			$('#rendezvousesDiv').html(data);
		});
	}
</script>