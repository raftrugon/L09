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

<security:authorize access="isAnonymous()">
	<div class="col-md-6 col-md-offset-3" style="padding:0;margin-bottom:-5px;">
	<ul class="nav nav-tabs nav-justified">
	  <li class="active"><a data-toggle="tab" href="#userTab">User</a></li>
	  <li><a id="managerTabLink" data-toggle="tab" href="#managerTab">Manager</a></li>
	</ul>
	</div>
	<div class="well col-md-6 col-md-offset-3">
		
		<div class="tab-content">
		<div id="userTab" class="tab-pane fade in active">
			<form:form action="register/user.do" modelAttribute="user">
	
				<!-- Shared Variables -->
				<jstl:set var="model" value="user" scope="request"/>
	
	
				<!-- Attributes -->
				<!-- ------------- ACCOUNT DATA -----------------  -->
				<h1><spring:message code="accountData" /></h1>
				<lib:input name="userAccount.username" type="text" />
	
				<div class="form-group">
					<form:label class="control-label" path="userAccount.password">
						<spring:message code="user.userAccount.password" />:
					</form:label>
					<form:input type="password" class="form-control userAccountPassword" path="userAccount.password" id="userAccountPassword"/>
					<form:errors cssClass="error" path="userAccount.password" />
				</div>
	
				<div class="form-group">
					<label class="control-label"><spring:message code="user.repeatpassword" />:</label>
					<input id="userConfirmPassword" type="password" class="form-control"/>
					<p id="nomatch" style="display:none;color:red;font-weight:bold"><spring:message code="user.repeatpassword.nomatch"/></p>
				</div>
	
				<!-- ------------- PERSONAL DATA -----------------  -->
				<h1><spring:message code="personalData" /></h1>
				<lib:input name="name" type="text" />
				<lib:input name="surnames" type="text" />
				<lib:input name="address" type="text" />
				<lib:input name="phoneNumber" type="text" />
				<lib:input name="email" type="text" />
				<lib:input name="birthDate" type="date" />
				<div class="form-group">
					<input type="checkbox" id="tyc" name="tyc" value="" required="true"/>&emsp;<a data-toggle="modal" data-target="#tycModal" style="cursor:pointer" class="form-label"><spring:message code="accept.terms"/></a>
				</div>
	
	
				<lib:button model="user" id="${id}" cancelUri="/Acme-Rendezvous" noDelete="true" />
	
			</form:form>
		</div>
		<div id="managerTab" class="tab-pane fade">
			<form:form action="register/manager.do" modelAttribute="manager">
	
				<!-- Shared Variables -->
				<jstl:set var="model" value="manager" scope="request"/>
	
	
				<!-- Attributes -->
				<!-- ------------- ACCOUNT DATA -----------------  -->
				<h1><spring:message code="accountData" /></h1>
				<lib:input name="userAccount.username" type="text" />
	
				<div class="form-group">
					<form:label class="control-label" path="userAccount.password">
						<spring:message code="user.userAccount.password" />:
					</form:label>
					<form:input type="password" class="form-control userAccountPasswordManager" path="userAccount.password" id="userAccountPassword"/>
					<form:errors cssClass="error" path="userAccount.password" />
				</div>
	
				<div class="form-group">
					<label class="control-label"><spring:message code="user.repeatpassword" />:</label>
					<input id="managerConfirmPassword" type="password" class="form-control"/>
					<p id="nomatch" style="display:none;color:red;font-weight:bold"><spring:message code="user.repeatpassword.nomatch"/></p>
				</div>
	
				<!-- ------------- PERSONAL DATA -----------------  -->
				<h1><spring:message code="personalData" /></h1>
				<lib:input name="vat" type="text" />
				<lib:input name="name" type="text" />
				<lib:input name="surnames" type="text" />
				<lib:input name="address" type="text" />
				<lib:input name="phoneNumber" type="text" />
				<lib:input name="email" type="text" />
				<div class="form-group">
					<input type="checkbox" id="tyc" name="tyc" value="" required="true"/>&emsp;<a data-toggle="modal" data-target="#tycModal" style="cursor:pointer" class="form-label"><spring:message code="accept.terms"/></a>
				</div>
	
	
				<lib:button model="manager" id="${id}" cancelUri="/Acme-Rendezvous" noDelete="true" />
	
			</form:form>
		</div>
		</div>
		
	</div>
	<script>
	$(function(){
		$('#user').submit(function(e){
			if($('.userAccountPassword').val() !== $('#userConfirmPassword').val()){
				$('#nomatchUser').show();
				return false;
			}else{
				$('#nomatchUser').hide();
				return true;
			}
		});
		$('#manager').submit(function(e){
			if($('.userAccountPasswordManager').val() !== $('#managerConfirmPassword').val()){
				$('#nomatchUser').show();
				return false;
			}else{
				$('#nomatchUser').hide();
				return true;
			}
		});
	});	
	</script>
	<jstl:if test="${mode eq 1}">
		<script>
		$(function(){
			$('#managerTabLink').click();
		});
		</script>
	</jstl:if>
</security:authorize>
