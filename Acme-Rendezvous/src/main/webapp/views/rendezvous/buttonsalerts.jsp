<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<jsp:useBean id="now" class="java.util.Date" />

	<jstl:if test="${rendezvous.organisationMoment lt now and not rendezvous.deleted}">
		<div class="alert alert-warning" style="text-align:center"><strong><spring:message code="rendezvous.past"/></strong></div>
	</jstl:if>
	<jstl:if test="${not rendezvous.finalMode and rendezvous.organisationMoment gt now and not rendezvous.deleted}">
		<div class="alert alert-warning" style="text-align:center"><strong><spring:message code="rendezvous.notFinal"/></strong></div>
	</jstl:if>
	<security:authorize access="hasRole('USER')">
		<jstl:if test="${not rsvpd and rendezvous.organisationMoment gt now and not rendezvous.deleted}">
			<input type="button" id="RSVPbtn" class="btn btn-block btn-primary" value='<spring:message code="rendezvous.rsvp" />' />
		</jstl:if>
		<jstl:if test="${rsvpd and rendezvous.organisationMoment gt now and not rendezvous.deleted}">
			<input type="button" id="cancelRSVPbtn" class="btn btn-block btn-warning" value='<spring:message code="rendezvous.cancelRSVP" />' />
		</jstl:if>		
	</security:authorize>
	<jstl:if test="${rendezvous.deleted}">
		<div class="alert alert-danger" style="text-align:center"><strong><spring:message code="rendezvous.deleted"/></strong></div>
	</jstl:if>
	
	<script>
	$('#RSVPbtn').click(function(e){
		e.preventDefault();
		var questionss = '<jstl:out value="${rendezvous.questions}"/>';
		if(questionss.length <= 2){
			$.post("user/ajax/rsvp/createWithoutQuestions.do",{rendezvousId:"<jstl:out value='${rendezvous.id}'/>"}, function(data){
				if(data==1){
					notify('success','<spring:message code="rsvp.save.success" />');
				}else{
					notify('danger','<spring:message code="rsvp.save.error" />');
				}
				reloadButtons();
				reloadChips();
		});
		}else{
		$.get("user/ajax/rsvp/create.do?rendezvousId=<jstl:out value='${rendezvous.id}'/>", function(data){
			$('#modalBody').html(data);
			$('#qaModal').modal('show');
		});
		}
	});
	$('#cancelRSVPbtn').click(function(e){
		e.preventDefault();
		$.post("user/ajax/rsvp/cancelRSVP.do",{rendezvousId:"<jstl:out value='${rendezvous.id}'/>"}, function(data){
			if(data==1){
				notify('success','<spring:message code="rsvp.cancel.success" />');
			}else{
				notify('danger','<spring:message code="rsvp.cancel.error" />');
			}
			reloadButtons();
			$('#chip${pageContext.request.userPrincipal.name}').remove();
		});
	});
	
	</script>