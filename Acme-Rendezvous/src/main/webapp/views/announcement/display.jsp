<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>


	<jstl:if test="${empty announcements}">
		<div class="alert alert-info" style="text-align:center;font-weight:bold"><spring:message code="announcement.none"/></div>
	</jstl:if>
	
<div class="timeline">

	<jstl:forEach items="${announcements}" var="announcementItem" varStatus="x">
	<jstl:choose>
	<jstl:when test="${x.count mod 2 eq 1}">
		<div class="timelinecontainer timelineleft ">
		    <div class="timelinecontent" style="padding:0;">
		    <jstl:if test="${not announcementItem.inappropriate}">
		    <div class="panel panel-default" style="padding:0;margin:0;">
		    	<div class="panel-heading">
			      <h2 style="text-align:center"><strong><jstl:out value="${announcementItem.title}"/></strong></h2>
			    </div>
				<div class="panel-body">
		     		<h4 style="margin-bottom:25px"><jstl:out value="${announcementItem.description}"/></h4>
				</div>
				<div class="panel-footer" style="padding: 5px 15px 35px 15px;">
					<div class="col-md-7" style="padding:0">
						<small class="text-primary"><h5><fmt:formatDate value="${announcementItem.creationMoment}" type="both" dateStyle="long" timeStyle="long"/></h5></small>
					</div>
					<security:authorize access="hasRole('ADMIN')">
						<div class="col-xl-5" style="padding:0">
					 		<button id="${announcementItem.id}" class="btn btn-danger deleteAnnouncementButton" >
					  			<spring:message code="rendezvous.adminDelete"/>
					 		</button>
					 	</div>
				 	</security:authorize>
 				</div>
			</div>
			</jstl:if>
			   <jstl:if test="${announcementItem.inappropriate}">
			   		<div class="alert alert-danger" style="margin-bottom: 0;text-align: center;"><i class="fas fa-ban"></i>&emsp;<spring:message code="announcement.inappropriate"/></div>
			   </jstl:if>
		    </div>
		 </div>
	</jstl:when>
	<jstl:otherwise>
		<div class="timelinecontainer timelineright ">
		    <div class="timelinecontent" style="padding:0;">
		    <jstl:if test="${not announcementItem.inappropriate}">
		    <div class="panel panel-default" style="padding:0;margin:0;">
		    	<div class="panel-heading">
			      <h2 style="text-align:center"><strong><jstl:out value="${announcementItem.title}"/></strong></h2>
			    </div>
				<div class="panel-body">
		     		<h4 style="margin-bottom:25px"><jstl:out value="${announcementItem.description}"/></h4>
				</div>
				<div class="panel-footer" style="padding: 5px 15px 35px 15px;">
					<div class="col-md-7" style="padding:0">
						<small class="text-primary"><h5><fmt:formatDate value="${announcementItem.creationMoment}" type="both" dateStyle="long" timeStyle="long"/></h5></small>
					</div>
					<security:authorize access="hasRole('ADMIN')">
						<div class="col-xl-5" style="padding:0">
					 		<button id="${announcementItem.id}" class="btn btn-danger deleteAnnouncementButton" >
					  			<spring:message code="rendezvous.adminDelete"/>
					 		</button>
					 	</div>
				 	</security:authorize>
 				</div>
			</div>
			</jstl:if>
			   <jstl:if test="${announcementItem.inappropriate}">
			   		<div class="alert alert-danger" style="margin-bottom: 0;text-align: center;"><i class="fas fa-ban"></i>&emsp;<spring:message code="announcement.inappropriate"/></div>
			   </jstl:if>
		    </div>
		 </div>
	</jstl:otherwise>
	</jstl:choose>
	</jstl:forEach>
</div>
		
<script>
$(function(){
	$('.deleteAnnouncementButton').click(function(e){
		e.preventDefault();
		$.post( "admin/ajax/announcement/delete.do",{announcementId: $(this).attr('id') }, function( data ) {
			if(data==1){
				notify('success','<spring:message code="rendezvous.announcement.delete.success"/>');
				reloadAnnouncements();
			}
			else notify('danger','<spring:message code="rendezvous.announcement.delete.error"/>');
			});
	});
});
</script>