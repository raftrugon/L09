<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>


	<jstl:if test="${rsvpd eq true}">
	<div id="newCommentDiv">
	<form:form modelAttribute="newComment">		
	<jstl:set var="model" value="comment" scope="request"/>
	<lib:input type="hidden" name="id,version,creationMoment,replies,replyingTo,user,rendezvous,inappropriate"/>
	<lib:input type="textarea" name="text" rows="4"/>
	<lib:input type="url" name="picture" addon="<i class='fas fa-paperclip'></i> <spring:message code='comment.picture.addon'/>" placeholder="http://www.url.com"/>
	<div class="btn-group btn-group-justified">
		<div class="btn-group">
			<input class="btn btn-success" type="button" id="saveButtonComment" onClick="javascript:saveCommentButton(this)" name="save" value="<spring:message code="comment.save"/>" />
		</div>
	</div>
	</form:form>
	</div>
	</jstl:if>
	<jstl:if test="${empty comments}">
		<div class="alert alert-info" style="text-align:center;font-weight:bold;margin-top:10px"><spring:message code="comment.none"/></div>
	</jstl:if>
	<jstl:forEach items="${comments}" var="comment">
	<jstl:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 9) + 1 %></jstl:set>
	<div class="media panel panel-default">
		<div class="panel-heading"> 
			<div class="media-left">
			  <img src="images/kC${rand}.png" class="media-object" style="width:45px">
			</div>
			<div class="media-body">
				<h4 class="media-heading">${comment.user.name} ${comment.user.surnames}<small><i>
				<fmt:formatDate type = "both" dateStyle = "long" timeStyle = "long" value = "${comment.creationMoment}" /></i>
				<jstl:if test="${comment.inappropriate eq false}">
					<security:authorize access="hasRole('ADMIN')">
						<a href="#" style="color:red;padding:1em;" id="${comment.id}" class="deleteCommentLink">
							<i class="fas fa-times"></i> 
							<spring:message code="rendezvous.adminDelete"/>
						</a>
					</security:authorize>
				</jstl:if>
				</small></h4>
				<jstl:if test="${not comment.inappropriate}">
					<p>${comment.text}</p>
				</jstl:if>
				<jstl:if test="${comment.inappropriate}">
					<div class="alert alert-danger" style="margin-bottom: 0;text-align: center;"><i class="fas fa-ban"></i>&emsp;<spring:message code="comment.inappropriate"/></div>
				</jstl:if>
			</div>
			<div class="media-right" style="text-align:right">
				<img src="${comment.picture}" style="max-height:150px">
			</div>
		</div> 
		<jstl:if test="${not empty comment.replies}">
			<div class="panel commentpanel" style="text-align:center" data-toggle="collapse" data-parent="#accordion" href="#collapse${comment.id}">
				<span style="white-space:nowrap;"><spring:message code="rendezvous.viewReplies"/> <i class="fas fa-chevron-down"></i></span>
				<span style="white-space:nowrap;display:none"><spring:message code="rendezvous.hideReplies"/> <i class="fas fa-chevron-up"></i></span>
			</div>
			<div id="collapse${comment.id}" class="panel-collapse collapse">
				<jstl:forEach items="${comment.replies}" var="reply">
					<jstl:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 9) + 1 %></jstl:set>
					<div class="media" style="padding-left:45px;margin-top:5px;">
						<div class="media-left">
							<img src="images/kC${rand}.png" class="media-object" style="width:45px">
						</div>
						<div class="media-body">
							<h4 class="media-heading">${reply.user.name} ${reply.user.surnames}<small><i>
							<fmt:formatDate type = "both" dateStyle = "long" timeStyle = "long" value = "${reply.creationMoment}" /></i></small></h4>
							<jstl:if test="${not reply.inappropriate}">
								<p>${reply.text}</p>
							</jstl:if>
							<jstl:if test="${reply.inappropriate}">
								<div class="alert alert-danger" style="margin-bottom: 10px;text-align: center;"><i class="fas fa-ban"></i>&emsp;<spring:message code="comment.inappropriate"/></div>
							</jstl:if>
						</div>
						<div class="media-right">
							<img src="${reply.picture}" style="max-height:150px;margin-right:15px;margin-bottom:10px;">
						</div>
					</div>	
				</jstl:forEach>
			</div>
		</jstl:if>
		<jstl:if test="${not comment.inappropriate}">
			<div class="panel-footer">
				<jstl:if test="${rsvpd eq true}">
					<input id="${comment.id}" type="button" class="btn btn-block btn-success newReplyBtn" value="<spring:message code='rendezvous.comment.reply'/>" />
				</jstl:if>
			</div>
		</jstl:if>
	</div>
	</jstl:forEach>
	
	<script>
	$(function(){
		$('.newReplyBtn').click(function(e){
			e.preventDefault();
			$.get("user/comment/replyComment.do?commentId="+$(this).attr('id'), function(data){
				$('#modalBody').html(data);
			});
			$('#modalTitle').html('<spring:message code="comment.reply.header"/>');
			$('#qaModal').modal('show');
		});	
		$('.deleteCommentLink').click(function(e){
			e.preventDefault();
			$.post( "admin/ajax/comment/delete.do",{commentId: $(this).attr('id') }, function( data ) {
				if(data==1){
					notify('success','<spring:message code="rendezvous.comment.delete.success"/>');
					reloadComments();
				}
				else notify('danger','<spring:message code="rendezvous.comment.delete.error"/>');
				});
		});
	});
	</script>