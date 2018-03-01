<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<security:authorize access="hasRole('USER')"> 
<div style="padding-right:15px;padding-left:15px;margin-bottom:10px">
<ul class="nav nav-pills nav-justified">
  <li id="button1" class="active "><a href="javascript:all()"><spring:message code="rendezvous.list.all"/></a></li>
  <li id="button2" class=""><a class="" href="javascript:mine()"><spring:message code="rendezvous.list.mine"/></a></li>
  <li id="button3" class=""><a class="" href="javascript:rsvp()"><spring:message code="rendezvous.list.rsvpd"/></a></li>
  <li id="button4" class=""><a class="" href="javascript:non_rsvp()"><spring:message code="rendezvous.list.non-rsvpd"/></a></li>
</ul>
</div>
</security:authorize>
<jsp:useBean id="now" class="java.util.Date" />
<jstl:forEach items="${rendezvouss}" var="rendezvous">
<jstl:set var="rendClick" value=""/>
<jstl:set var="rendStyle" value=""/>
<jstl:set var="inappropriateStyle" value=""/>
<jstl:set var="userClick" value=""/>
<jstl:set var="pastRend" value=""/>
<jstl:set var="rsvp" value=""/>
<jstl:set var="mine" value=""/>
<jstl:set var="inappropriateClass" value=""/>
<jstl:if test="${fn:contains(rsvpdRendezvouses, rendezvous)}">
	<jstl:set var="rsvp" value="rsvp"/>
</jstl:if>
<jstl:if test="${rendezvous.user.userAccount.username eq pageContext.request.userPrincipal.name }">
	<jstl:set var="mine" value="mine"/>
</jstl:if>
<jstl:if test="${rendezvous.inappropriate eq true}">
	<jstl:set var="inappropriateStyle" value="filter:blur(5px);-webkit-filter:blur(5px);"/>
	<jstl:set var="inappropriateClass" value="inappropriate"/>
</jstl:if>
<jstl:if test="${rendezvous.inappropriate ne true}">
	<jstl:set var="rendClick" value="location.href = 'rendezvous/display.do?rendezvousId=${rendezvous.id}'"/>
	<jstl:set var="rendStyle" value="cursor:pointer;"/>
	<jstl:set var="userClick" value="location.href = 'user-display.do?userId=${rendezvous.user.id}'"/>
</jstl:if>
<jstl:if test="${rendezvous.organisationMoment lt now}">
	<jstl:set var="pastRend" value="color:red;"/>
</jstl:if>
<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12 ${rsvp} ${mine} ${inappropriate} cardContainer" >
			<jstl:if test="${rendezvous.inappropriate eq true}">
				<div class="alert alert-danger" style="position:absolute;top:40%;right:10%;left:10%;text-align:center;z-index:500;"><strong><spring:message code="rendezvous.inappropriate.alert"/></strong></div>
			</jstl:if>
	<div class="card" style="${inappropriateStyle}">
		<div onclick="${rendClick}" style="height:100%;${rendStyle}">
			<jstl:if test="${empty rendezvous.picture}">
				<div class="nopicContainer">
					<img src="images/nopic.jpg" style="object-fit:cover;height:200px;width:100%" class="nopic"/>
					<div class="nopicCaption alert alert-warning"><spring:message code="master.page.nopic"/></div>
				</div>
			</jstl:if>
			<jstl:if test="${not empty rendezvous.picture}">
				<img src="${rendezvous.picture}" style="object-fit:cover;height:200px;width:100%">
			</jstl:if>
	        <h1>
	        	<jstl:out value="${rendezvous.name}"/>
	        </h1>
	        <div style="text-align:center;${pastRend}" class="cardDate">
				<fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${rendezvous.organisationMoment}"/>
				<jstl:if test="${rendezvous.organisationMoment lt now}"><br/><strong><i><spring:message code="rendezvous.list.past"/></i></strong></jstl:if>
	        </div>
		</div>
		<input class="cardButton" type="button" name="cancel"
				value="${rendezvous.user.name} ${rendezvous.user.surnames} "	
		onclick="${userClick}"/>
	</div>
</div>
</jstl:forEach>

<script>
function rsvp(){
	$('#mainContainer').find('.cardContainer').not('.rsvp').hide();
	$('#mainContainer').find('.cardContainer.rsvp').show();
	document.getElementById("button1").className = "";
	document.getElementById("button2").className = "";
	document.getElementById("button3").className = "active";
	document.getElementById("button4").className = "";
};

function non_rsvp(){
	$('#mainContainer').find('.cardContainer').not('.rsvp').show();
	$('#mainContainer').find('.cardContainer.rsvp').hide();
	$('#mainContainer').find('.cardContainer.inappropriate').hide();
	document.getElementById("button1").className = "";
	document.getElementById("button2").className = "";
	document.getElementById("button3").className = "";
	document.getElementById("button4").className = "active";
};

function mine(){
	$('#mainContainer').find('.cardContainer').not('.mine').hide();
	$('#mainContainer').find('.cardContainer.mine').show();
	document.getElementById("button1").className = "";
	document.getElementById("button2").className = "active";
	document.getElementById("button3").className = "";
	document.getElementById("button4").className = "";
};

function all(){
	$('#mainContainer').find('.cardContainer').show();
	document.getElementById("button1").className = "active";
	document.getElementById("button2").className = "";
	document.getElementById("button3").className = "";
	document.getElementById("button4").className = "";
};

</script>