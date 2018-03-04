<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>

<form:form id="rsvpForm" modelAttribute="rsvp">
<lib:input type="hidden" name="id,version,user,rendezvous,questionsAndAnswers"/>
<jstl:forEach items="${rsvp.questionsAndAnswers}" var="entity">
	<div class="form-group">
		<div class="well well-sm" style="margin-bottom:5px;font-weight:bold"><jstl:out value="${entity.key}"/></div>
		<input type="text" class="form-control answerInput" value="${entity.value}" />
	</div>
</jstl:forEach>
<div class="form-group">
	<input id="saveButtonrsvp" type="button" class="btn btn-block btn-success" value="<spring:message code='rendezvous.rsvp'/> ">
</div>
</form:form>
<script>
	$('#saveButtonrsvp').click(function(e){
		e.preventDefault();
		var rsvp = {};
		var questionsAndAnswers = {};
		$('#rsvpForm').find('input[type=text],input[type=hidden]').not('.answerInput').not('[name=questionsAndAnswers]').each(function(){
			rsvp[$(this).attr('name')] = $(this).val();
		});
		$('#rsvpForm').find('.answerInput').each(function(){
			if($(this).val().trim() !== ""){
				questionsAndAnswers[$(this).prev().html()] = $(this).val().trim();
			}
		});
		rsvp['questionsAndAnswers'] = questionsAndAnswers;
		$.ajax({
			type:'post',
			url:"user/ajax/rsvp/save.do",
			data: rsvp,
			processData: 'false',
			contentType: 'application/x-www-form-urlencoded',
			dataType: 'json',
			success: function(data){
				$('#qaModal').modal('hide');
				if(data==1){
					notify('success','<spring:message code="rsvp.save.success" />');
					reloadChips();
				}else{
					notify('danger','<spring:message code="rsvp.save.error" />');
				}
				reloadButtons();
			}
		});
	});
</script>