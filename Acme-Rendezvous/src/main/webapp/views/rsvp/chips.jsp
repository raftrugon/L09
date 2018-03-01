<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<jstl:forEach items="${rsvps}" var="r">
		<div id="chip${r.user.userAccount.username}" class="chip">
		<img src="images/kC1.png" width="96" height="96">
		<a href="user-display.do?userId=${r.user.id}"><small><jstl:out value="${r.user.name} ${r.user.surnames}"/></small></a> 
		<button class="btn btn-info chipQA" id="${r.id}"><small>Q&#38;A</small></button>
		</div>
	</jstl:forEach>
	
	<script>
	$('.chipQA').click(function(e){
		e.preventDefault();
		showQAModal($(this).attr('id'));
	});	
	</script>