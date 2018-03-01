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
	<div class="well col-md-6 col-md-offset-3">

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
				<form:input type="password" class="form-control" path="userAccount.password" id="userAccountPassword"/>
				<form:errors cssClass="error" path="userAccount.password" />
			</div>

			<div class="form-group">
				<label class="control-label"><spring:message code="user.repeatpassword" />:</label>
				<input id="confirmPassword" type="password" class="form-control"/>
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
	<script>
	$(function(){
		$('#user').submit(function(e){
			if($('#userAccountPassword').val() !== $('#confirmPassword').val()){
				$('#nomatch').show();
				return false;
			}else{
				$('#nomatch').hide();
				return true;
			}
		});
	});
	</script>
</security:authorize>
