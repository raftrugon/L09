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
	<div class=center-text>
		<form:form action="${actionUri}" modelAttribute="rendezvous">		
				
			<!-- Shared Variables -->
			<jstl:set var="model" value="rendezvous" scope="request"/>	
			
			<!-- Hidden Attributes -->
			
			<div class="questionDiv" id="questionDiv">
				<jstl:forEach items="${questions}" var="q">
					<div class="form-group">
						<input class="form-control focus" type="text" value="${q}">
					</div>
				</jstl:forEach>
				
			</div>
			<div class="form-group">
				<input type="button" class="btn btn-primary addQuestion btn-block " style="margin-bottom:5px" value='<spring:message code="rendezvous.newQuestion"/>'/>
			</div>
			<div class="form-group">
				<input type="button"  id="${rendezvous.id}" class="btn btn-success saveQuestions btn-block " style="margin-bottom:5px" value='<spring:message code="rendezvous.save"/>'/>
			</div>
		
		</form:form>		
	</div>
</security:authorize>

<script id="newQuestionRow" class="focus" type="text/plain">
	<div class="form-group">
		<input class="form-control" type="text" value="">
	</div>
</script>

<script>
$(function(){
	$('.addQuestion').click(function(e){
		e.preventDefault();
		$('.questionDiv').append($('#newQuestionRow').html());
	});
});
$(function(){
	$('.saveQuestions').click(function(e){
		e.preventDefault();
		questions = [];
		$("#questionDiv").find('input').each(function(e){
			questions.push($(this).val());
		});
		$.post( "user/ajax/rendezvous/qa/edit.do",{rendezvousId: $(this).attr('id'), questions:questions}, function( data ) {
			if(data==1) {
				notify('success','<spring:message code="questions.edit.success"/>');
				$('#qaModal').modal('hide');
			}
			else{
				notify('danger','<spring:message code="questions.edit.error"/>');
				$('#qaModal').modal('hide');
			}
		});
	});
});
</script>