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
  <li data-type="0" id="button1" class="active "><a href="javascript:void(0)"><spring:message code="rendezvous.list.all"/></a></li>
  <li data-type="1" id="button2" class=""><a href="javascript:void(0)"><spring:message code="rendezvous.list.mine"/></a></li>
  <li data-type="2" id="button3" class=""><a href="javascript:void(0)"><spring:message code="rendezvous.list.rsvpd"/></a></li>
  <li data-type="3" id="button4" class=""><a href="javascript:void(0)"><spring:message code="rendezvous.list.non-rsvpd"/></a></li>
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


<%-- 
<script>
function rsvp(){
	$('#mainContainer').find('.cardContainer').not('.rsvp').hide();
	$('#mainContainer').find('.cardContainer.rsvp').show();
	document.getElementById("button1").className = "";
	document.getElementById("button2").className = "";
	document.getElementById("button3").className = "active";
	document.getElementById("button4").className = "";
};

function non_rsvp(){
	$('#mainContainer').find('.cardContainer').not('.rsvp').show();
	$('#mainContainer').find('.cardContainer.rsvp').hide();
	$('#mainContainer').find('.cardContainer.inappropriate').hide();
	document.getElementById("button1").className = "";
	document.getElementById("button2").className = "";
	document.getElementById("button3").className = "";
	document.getElementById("button4").className = "active";
};

function mine(){
	$('#mainContainer').find('.cardContainer').not('.mine').hide();
	$('#mainContainer').find('.cardContainer.mine').show();
	document.getElementById("button1").className = "";
	document.getElementById("button2").className = "active";
	document.getElementById("button3").className = "";
	document.getElementById("button4").className = "";
};

function all(){
	$('#mainContainer').find('.cardContainer').show();
	document.getElementById("button1").className = "active";
	document.getElementById("button2").className = "";
	document.getElementById("button3").className = "";
	document.getElementById("button4").className = "";
};

</script>
<script>
	$(function(){
		$.get('ajax/rendezvous/list.do',function(data){
			$('#rendezvousesDiv').html(data);
		});
		$.get('ajax/category/getSubCategories.do',function(data){
			$('#categoryDiv').treeview({
				data:data,
				showTags:true,
				multiSelect: true,
			});
			$('#categoryDiv').treeview('collapseAll', { silent: true });
			$('#categoryDiv').on('nodeSelected', function(event,node){
				$('.cardContainer').show();
				var nodes = $('#categoryDiv').treeview('getSelected',null);
				var nodeCategories = [];
				$.each(nodes,function(node){
					nodeCategories.push(''+$(this).attr('categoryId'));
				});
				if(nodes.length !== 0){
					$('.cardContainer').filter(function(i){
						var rendezvousCategories = $(this).attr("data-categories").split(",");
						var count = $.grep(rendezvousCategories,function(a){
							return $.inArray(a,nodeCategories) !== -1;
						}).length;
						return count === 0;
					}).hide(); 
				}
			});
			$('#categoryDiv').on('nodeUnselected', function(event,node){
				$('.cardContainer').show();
				var nodes = $('#categoryDiv').treeview('getSelected',null);
				var nodeCategories = [];
				$.each(nodes,function(node){
					nodeCategories.push(''+$(this).attr('categoryId'));
				});
				if(nodes.length !== 0){
					$('.cardContainer').filter(function(i){
						var rendezvousCategories = $(this).attr("data-categories").split(",");
						var count = $.grep(rendezvousCategories,function(a){
							return $.inArray(a,nodeCategories) !== -1;
						}).length;
						return count === 0;
					}).hide(); 
				}
			});
		});
		
		$('#showCategoriesPanel').click(function(e){
			$(this).children('button').each(function(){
				$(this).toggle();
			});
		});
		
		function filterByCategories(nodes){
			
		};
	});
</script>
--%>