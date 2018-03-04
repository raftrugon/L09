<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>


<jstl:forEach items="${qa}" var="entity">
	<div class="well well-sm" style="margin-bottom:5px"><strong><jstl:out value="${entity.key}"/></strong></div>
	<p style="padding-left:3em"><jstl:out value="${entity.value}"/></p>
</jstl:forEach>

<jstl:if test="${pageContext.request.userPrincipal.name eq rsvp.user.userAccount.username}" >
	<jstl:forEach items="${pendingQuestions}" var="item">
		<div class="well well-sm" style="margin-bottom:5px"><strong><jstl:out value="${item}"/></strong></div>
		<form>
			<input type="hidden" name="rsvpId" value="${rsvp.id}"/>
			<input type="hidden" name="question" value="${item}"/>
			<div class="form-group input-group">
				<input type="text" class="form-control" name="answer"/>
				<div class="input-group-btn">
					<button id="answerBtn" class="sendAnswer btn btn-success">
					<i class="fab fa-telegram-plane"></i> <spring:message code='rsvp.sendAnswer'/>
					</button>
				</div>
			</div>
		</form>
	</jstl:forEach>
	
		<script>
		$(function(){
			$('.sendAnswer').click(function(e){
				e.preventDefault();
				var id = $(this).parent().parent().parent().find('input[name=rsvpId]').val();
				$.post('user/ajax/newAnswer.do',$(this).parent().parent().parent().serialize(),function(data){
					if(data==1){
						showQAModal(id);
						notify('success','<spring:message code="rsvp.sendAnswer.success" />');
					}else{ 
						notify('danger','<spring:message code="rsvp.sendAnswer.error" />');
					}
				});
			});
		});
		</script>
</jstl:if>