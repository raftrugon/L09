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

<security:authorize access="hasRole('USER')">
	<div class="center-text col-md-8 col-md-offset-2 well">
		<form:form action="user/rendezvous/save.do" modelAttribute="rendezvous">		

			<!-- Shared Variables -->
			<jstl:set var="model" value="rendezvous" scope="request"/>	
			
			<!-- Hidden Attributes -->
			<lib:input name="id,questions" type="hidden" />
				
			<!-- Attributes -->
			<h1><spring:message code="rendezvous.new" /></h1>
			<hr>
			<lib:input name="name" type="text" />
			<lib:input name="description" type="text" />
			<lib:input name="organisationMoment" type="moment" />		
			<lib:input name="latitude" type="text" />
			<lib:input name="longitude" type="text" />				
			<lib:input name="picture" type="text" />
			
			<!-- Select rendezvouses for link them to the new rendezvous -->	
			<div class="form-group">
			<label for="linkSelect"><strong><spring:message code="rendezvous.link"/></strong></label>
			<spring:message code="rendezvous.link.explanation"/>
			<form:select id="linkSelect" class="selectpicker form-control" multiple="true" data-live-search="true" data-selected-text-format="count > 1" path="rendezvouses">
	   			<form:options items="${rendezvouses}" itemValue="id" itemLabel="name"/>
			</form:select>
			</div>
			<div class="form-group">
			<jstl:if test="${isAdult}">
				<span class="col-md-6 " style="padding:0; margin:0;"><lib:input name="adultOnly" type="checkBox" /></span>		
			</jstl:if>
			
			<span class="col-md-6"><lib:input name="finalMode" type="checkBox" /></span>			

			</div>
			<div class="form-group">
			<label class="form-label"><strong><spring:message code="rendezvous.questions"/>:</strong></label>
			
			<div class="questionDiv" id="questionDiv">
			<jstl:if test="${rendezvous.id eq 0}">
				<div class="form-group input-group">
					<input class="form-control questionInput" type="text" value="">
					<div class="input-group-btn">
						<button class="btn btn-danger delQuestion">
							<i class="fas fa-minus-circle"></i>
						</button>
					</div>
				</div>
			</jstl:if>
			<jstl:if test="${rendezvous.id ne 0}">
				<jstl:forEach items="${rendezvous.questions}" var="question">
					<div class="form-group input-group">
					<input class="form-control questionInput" type="text" value="${question}">
					<div class="input-group-btn">
						<button class="btn btn-danger delQuestion">
							<i class="fas fa-minus-circle"></i>
						</button>
					</div>
				</div>
				</jstl:forEach>
			</jstl:if>
			</div>
			</div>
			<div class="form-group">
				<button class="btn btn-primary addQuestion btn-block " style="margin-bottom:5px"><i class="fas fa-plus-square"></i></button>
			</div>
			<hr>		
			<p><hr><spring:message code="termsTextHead" /><a data-toggle="modal" data-target="#tycModal" style="cursor:pointer" > <spring:message code="termsAndConditions" /></a> <spring:message code="termsTextTail" />.</p>
			
			<lib:button noDelete="true" model="rendezvous" id="${rendezvous.id}" cancelUri="/Acme-Rendezvous" />
	
			
			
		</form:form>		
	</div>
</security:authorize>

<script id="newQuestionRow" class="focus" type="text/plain">
	<div class="form-group input-group">
		<input class="form-control questionInput" type="text" value="">
		<div class="input-group-btn">
			<button class="btn btn-danger delQuestion">
				<i class="fas fa-minus-circle"></i>
			</button>
		</div>
	</div>


</script>

<script>
$(function(){
	$('.addQuestion').click(function(e){
		e.preventDefault();
		$('.questionDiv').append($('#newQuestionRow').html());
		addListener();
	});
	function addListener(){
		$('.delQuestion').click(function(e){
			e.preventDefault();
			$(this).parent().parent().remove();
		});
	}
	addListener();
});
</script>
<script>
$('#rendezvous').submit(function(e){
	var questions = [];
	$('#rendezvous').find('.questionInput').each(function(){
		questions.push($(this).val());
	});
	$('#questions').val(questions);
	return true;
});
</script>

