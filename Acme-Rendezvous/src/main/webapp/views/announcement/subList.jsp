<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="container well" style="padding: 30px 40px 30px 40px;">
	  	<h2 style="text-align:center;"><spring:message code='announcements'/></h2>
	  	<p style="text-align:center;"><spring:message code='announcements.description'/></p><br>
		<hr>
	  	<jstl:forEach var="announcement" items="${announcements}" varStatus="x" >
	  		<hr><hr>
	  		
	  		<jstl:choose>
				<jstl:when test="${x.count mod 2 eq 1}">
					<div class="media" >
				  		<div class="media-left" >
				  			<jstl:if test="${announcement.rendezvous.picture eq null}">
								<div class="nopicContainer media-object" style="width:120px;height:85px;">
									<img src="images/nopic.jpg" style="object-fit:cover;width:100%" class="nopic"/>
									<div class="nopicCaption alert alert-warning"><spring:message code="master.page.nopic"/></div>
								</div>
							</jstl:if>
							<jstl:if test="${announcement.rendezvous.picture ne null}">
				      			<img src="${announcement.rendezvous.picture}" class="media-object" style="width:120px;height:85px;">
				      		</jstl:if>
				    	</div>
				    	<div class="media-body">
				      		<h3 class="media-heading"><jstl:out value="${announcement.title}"/>
				      		</h3>
				      		<p><jstl:out value="${announcement.description}"/></p>
				      		<h5 style="text-align:right;"><a href="user-display.do?userId=${announcement.rendezvous.user.id}" ><jstl:out value="${announcement.rendezvous.user.name} ${announcement.rendezvous.user.surnames}"/></a>
				      			<small><i>
				      				<spring:message code='announcements.postedOn' /> <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${announcement.creationMoment}"/>
				      				<spring:message code='announcements.about' /> <a href="rendezvous/display.do?rendezvousId=${announcement.rendezvous.id}" ><jstl:out value="${announcement.rendezvous.name}"/></a>.
				      			</i></small>
				      		</h5>
				    	</div>
				    	<security:authorize access="hasRole('ADMIN')">
							  <button id="${announcement.id}" class="btn btn-danger deleteAnnouncementButton" style="margin-top:5px">
							  	<spring:message code="rendezvous.adminDelete"/>
							  </button>
				 		</security:authorize>
					</div>
				</jstl:when>
				
				
				
				<jstl:otherwise>
					<div class="media" >
				  		
				    	<div class="media-body">
				      		<h3 class="media-heading" style="text-align:right;">
				 			 <jstl:out value="${announcement.title}"/></h3>
				      		<p style="text-align:right;"><jstl:out value="${announcement.description}"/></p>
				      		
				      		 <h5 style="text-align:left;">
				      			<small><i>
				      				<spring:message code='announcements.postedOn' /> <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${announcement.creationMoment}"/>
				      				<spring:message code='announcements.about' /> <a href="rendezvous/display.do?rendezvousId=${announcement.rendezvous.id}" ><jstl:out value="${announcement.rendezvous.name}"/></a>
				      				<spring:message code='announcements.by' />
				      			</i></small>
				      			<a href="user-display.do?userId=${announcement.rendezvous.user.id}" ><jstl:out value="${announcement.rendezvous.user.name} ${announcement.rendezvous.user.surnames}"/></a>
				      		</h5>
				    	</div>
				    	<div class="media-right">
				      		<jstl:if test="${announcement.rendezvous.picture eq null}">
								<div class="nopicContainer media-object" style="width:120px;height:85px;">
									<img src="images/nopic.jpg" style="object-fit:cover;width:100%" class="nopic"/>
									<div class="nopicCaption alert alert-warning"><spring:message code="master.page.nopic"/></div>
								</div>
							</jstl:if>
							<jstl:if test="${announcement.rendezvous.picture ne null}">
				      			<img src="${announcement.rendezvous.picture}" class="media-object" style="width:120px;height:85px;">
				      		</jstl:if>
				    	</div>
				    	<div class="buttonDiv" style="text-align:right;">
				      		<security:authorize access="hasRole('ADMIN')">
							  <button id="${announcement.id}" class="btn btn-danger deleteAnnouncementButton" style="right:0; margin-top:5px;">
							  	<spring:message code="rendezvous.adminDelete"/>
							  </button>
							</security:authorize>
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
				$.get('ajax/loadAnnouncements.do?type=0',function(data){
					$('#announcementContainer').html(data);
				});
			}
			else notify('danger','<spring:message code="rendezvous.announcement.delete.error"/>');
			});
	});
});
</script>