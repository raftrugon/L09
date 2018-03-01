<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>


<form:form modelAttribute="comment">		
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

