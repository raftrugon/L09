<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Modal to display the notifications from an ajax method -->
   		<div id="notificationDiv">
   			<div id="notificationAlert" class=""></div>
   		</div>
<!-- Function to receive parameters and message for modal ajax notifications -->
		<script>
   			function notify(i,msg){
   				if(!(typeof notificationTimeout ==="undefined"))clearTimeout(notificationTimeout);
   				$('#notificationAlert').html(''); 
   				$('#notificationAlert').attr('class','alert alert-'+i);
				$('#notificationAlert').prepend(msg);
				$('#notificationDiv').show('slow');
				notificationTimeout = setTimeout(function() {
			        $("#notificationDiv").hide('slow');
			        $('#notificationAlert').html('');  
			        }, 7000);
   			}
   		</script>
<jstl:if test="${message ne null}">
	<script>
	$(function(){
		notify('danger',"<spring:message code="${message}"/>");
	});
	</script>
</jstl:if>
<nav class="navbar navbar-inverse navbar-fixed-top" style="z-index:1500">
	<div class="containter-fluid">
		<div class="navbar-header">
		 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>                        
	      </button>
			<a class="navbar-brand" href="#">Acme-Rendezvous</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
		<ul class="nav navbar-nav">			
				
		<li><a href="rendezvous/list.do"><spring:message code="master.page.rendezvous.list" /></a></li>
		<li><a href="user-list.do"><spring:message code="master.page.user.list" /></a></li>
		<li><a href="announcement/list.do"><spring:message code="master.page.announcement.feed" /></a></li>
		
			<security:authorize access="hasRole('ADMIN')">
				<button onClick="javascript:window.location.href = 'admin/dashboard.do'" class="btn btn-danger navbar-btn"><spring:message code="master.page.dashboard" /></button>
			</security:authorize>
			
			<security:authorize access="hasRole('USER')">
				<button onClick="javascript:window.location.href = 'user/rendezvous/create.do'" class="btn btn-success navbar-btn"><i class="fas fa-plus-square"></i> <spring:message code="master.page.rendezvous.create" /></button>
			</security:authorize>
			
		<%-- <security:authorize access="all()"> 
		<form class="navbar-form navbar-left" action="trip/list.do">
		  <div class="input-group">
		    <input id="ajaxTripInput" type="text" class="form-control" name="keyword" placeholder='<spring:message code="trip.search"/>'>
		    <div class="input-group-btn">
		      <button class="btn btn-default" type="submit">
		       <spring:message code="trip.search"/>
		      </button>
		    </div>
		  </div>
		</form>
		</security:authorize> --%>
		
			
			</ul>	
		<ul class="nav navbar-nav navbar-right">
		
		<li class="btn-group">	
			<button onClick="javascript:window.location.href = '?language=en'" class="btn navbar-btn btn-info" >en</button>
			<button onClick="javascript:window.location.href = '?language=es'" class="btn navbar-btn btn-info" >es</button>
		</li>
		
			<security:authorize access="isAuthenticated()">
				<li><a href="#"><span class="glyphicon glyphicon-user"></span> <security:authentication property="principal.username"/></a></li>
				<li><a href="j_spring_security_logout"><span class="glyphicon glyphicon-log-out"> </span><spring:message code="master.page.logout" /> &nbsp; </a></li>
			</security:authorize>
			
			
			
			<security:authorize access="isAnonymous()">
				<li><a href="register/user.do"><span class="glyphicon glyphicon-user"></span> <spring:message code="master.page.register" /> &nbsp;</a></li>
				<li><a  href="security/login.do"><span class="glyphicon glyphicon-log-in"></span> <spring:message code="master.page.login" /> &nbsp; </a></li>
			</security:authorize>
		</ul>
		</div>
	</div>
	
	
</nav>
