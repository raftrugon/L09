<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<input id="pageNumber" type="hidden" value="${pageNumber}"/>
<ul class="pager">
  <jstl:if test="${not isFirst}"><li class="previous"><a id="prevPage" href="javascript:void(0)"><spring:message code="admin.prev"/></a></li></jstl:if>
  <li class="alert alert-info">
	  <spring:message code="admin.showing"/>
	  <input type="number" id="pageSize" value="${pageSize}" style="width:50px;" min="1">
	  <spring:message code="admin.showing.of"/> <strong><jstl:out value="${pageNumber+1}"/></strong>
	  <spring:message code="admin.showing.of.end"/> <strong><jstl:out value="${totalPages}"/></strong>
  </li>
  <jstl:if test="${not isLast}"><li class="next"><a id="nextPage" href="javascript:void(0)"><spring:message code="admin.next"/></a></li></jstl:if>
</ul>
<jstl:forEach items="${zervices}" var="zervice">
	<a class="list-group-item">
		<h4><span class="label label-info" style="position:absolute;right:10px"><spring:message code="admin.requests"/>: ${fn:length(zervice.requests)}</span></h4>
		<h4 class="list-group-item-heading"><strong>${zervice.name}</strong></h4>
		<p class="list-group-item-text">${zervice.description}</p>
	</a>					
</jstl:forEach>