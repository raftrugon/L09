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
<%@taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div>

<div class="col-md-5">
		<div class="well " style="text-align:center">
			<h3><spring:message code="ownRendezvouses" /></h3>
		</div>
		<div class="table-responsive">
		<div class="well "><!----------------------------------- OWN RENDEZVOUSES ----------------------------------->
		
			
			<table class="table">
				<thead class="thead-dark">
					<tr>
				    	<th scope="col"></th>
				      	<th scope="col"><spring:message code="rendezvous.name" /></th>
				      	<th scope="col"><spring:message code="rendezvous.organisationMoment" /></th>
				      	<th scope="col"><spring:message code="rendezvous.coordinates" /></th>
				    </tr>
				</thead>
			  	<tbody>
			  		<jstl:forEach var="ownRendezvous" items="${user.rendezvouses}" varStatus="x" >
			  			<tr>
					      	<td>
								<jstl:if test="${ownRendezvous.picture eq null}">
									<img src="images/nopic.jpg" height="90" width="135" class="nopic"/>
								</jstl:if>
								<jstl:if test="${ownRendezvous.picture ne null}">
									<img src="${ownRendezvous.picture}" height="90" width="135" >
								</jstl:if>
					      	</td>
					      	<td><a href="rendezvous/display.do?rendezvousId=${ownRendezvous.id}"><jstl:out value="${ownRendezvous.name}"/></a></td>
					      	<td><fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${ownRendezvous.organisationMoment}"/></td>
					      	<td>(<jstl:out value="${ownRendezvous.latitude}"/>, <jstl:out value="${ownRendezvous.longitude}"/>)</td>
			    		</tr>
			  		</jstl:forEach>
			  	</tbody>
			</table>
			</div>
	</div>
	</div>
	
	
	<!--------------------------------------------- PERSONAL DATA -----------------------------------------------  -->
		<div class="userCard col-md-2" style="overflow:hidden;padding:0">
		  <img src="images/kS1.png" style="width:100%;margin-top:-25px;">
		  <button class="cardUserButton" style="margin-top:-25px;cursor:initial"><jstl:out value="${user.name} ${user.surnames}"/></button>
		  <p><strong><spring:message code="user.address" /></strong></p>
		  <p><jstl:if test="${user.address eq null}">-</jstl:if><jstl:out value="${user.address}"/></p>
		  <p><strong><spring:message code="user.phoneNumber" /></strong></p>
		  <p><jstl:if test="${user.phoneNumber eq null}">-</jstl:if><jstl:out value="${user.phoneNumber}"/></p>
		  <p><strong><spring:message code="user.email" /></strong></p>
		  <p><jstl:out value="${user.email}"/></p>
		  <p><strong><spring:message code="user.birthDate" /></strong></p>
		  <p><fmt:formatDate value="${user.birthDate}" type="date" dateStyle="long"/></p>
		</div>

	<!--------------------------------------------------- RENDEZVOUSES RSVP --------------------------------------------------------------------  -->
	<div class="col-md-5">
		<div class="well " style="text-align:center">
			<h3><spring:message code="rsvpRendezvouses" /></h3>
		</div>
		<div class="table-responsive">
		<div class="well">
			
			<table class="table">
				<thead class="thead-dark">
					<tr>
				    	<th scope="col"></th>
				      	<th scope="col"><spring:message code="rendezvous.name" /></th>
				      	<th scope="col"><spring:message code="rendezvous.organisationMoment" /></th>
				      	<th scope="col"><spring:message code="rendezvous.user" />
				      	
				    </tr>
				</thead>
				<tbody>
			  		<jstl:forEach var="rsvpRendezvous" items="${rendezvouses}" varStatus="x" >
			  			<tr>
					      	<td>
								<jstl:if test="${rsvpRendezvous.picture eq null}">
									<img src="images/nopic.jpg" height="90" width="135" class="nopic"/>
								</jstl:if>
								<jstl:if test="${rsvpRendezvous.picture ne null}">
									<img src="${rsvpRendezvous.picture}" height="90" width="135" >
								</jstl:if>
					      	</td>
					      	<td><a href="rendezvous/display.do?rendezvousId=${rsvpRendezvous.id}"><jstl:out value="${rsvpRendezvous.name}"/></a></td>
					      	<td><fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${rsvpRendezvous.organisationMoment}"/></td>
					      	<td><a href="user-display.do?userId=${rsvpRendezvous.user.id}"><jstl:out value="${rsvpRendezvous.user.name} ${rsvpRendezvous.user.surnames}"/></a></td>
			    		</tr>
			  		</jstl:forEach>
			  	</tbody>
			</table>

			.
		</div>
		</div>
	</div>

</div>
