<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:useBean id="now" class="java.util.Date" />
<jstl:forEach items="${zervices}" var="zervice">

<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12 cardContainer" >
			<jstl:if test="${zervice.inappropriate eq true}">
				<div class="alert alert-danger" style="position:absolute;top:40%;right:10%;left:10%;text-align:center;z-index:500;"><strong><spring:message code="zervice.inappropriate.alert"/></strong></div>
			</jstl:if>
			
	<div class="card" style="${inappropriateStyle}">
		<div onclick="${rendClick}" style="height:100%;${rendStyle}">
			<jstl:if test="${empty zervice.picture}">
				<div class="nopicContainer">
					<img src="images/nopic2.jpg" style="object-fit:cover;height:200px;width:100%" class="nopic"/>
					<div class="nopicCaption alert alert-warning"><spring:message code="master.page.nopic"/></div>
				</div>
			</jstl:if>
			<jstl:if test="${not empty zervice.picture}">
				<img src="${zervice.picture}" style="object-fit:cover;height:200px;width:100%">
			</jstl:if>
	        <h1>
	        	<jstl:out value="${zervice.name}"/>
	        </h1>
	        <div style="text-align:center" class="cardDate">
				<jstl:out value="${zervice.description}"/>
	        </div>
		</div>
		<security:authorize access="hasRole('USER')">
			<input class="cardButton" type="button" name="cancel"
					value="XXXXXX"	
			onclick="${userClick}"/>
		</security:authorize>
	</div>
</div>
</jstl:forEach>