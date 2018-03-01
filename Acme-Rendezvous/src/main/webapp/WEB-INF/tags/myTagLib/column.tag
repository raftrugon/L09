<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ attribute name="name"  rtexprvalue="true"  required="true" type="java.lang.String"  description="Column name" %>
<%@ attribute name="style"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Column CSS style" %>
<%@ attribute name="sortable"  rtexprvalue="true"  required="false" type="java.lang.String"  description="is sortable?" %>
<%@ attribute name="format"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Column jstl format" %>
<%@ attribute name="link"  rtexprvalue="true"  required="false" type="java.lang.String"  description="URL" %>
<%@ attribute name="linkName"  rtexprvalue="true"  required="false" type="java.lang.String"  description="URL display name" %>
<%@ attribute name="photoUrl"  rtexprvalue="true"  required="false" type="java.lang.String"  description="URL to display photo" %>
<%@ attribute name="value"  rtexprvalue="true"  required="false" type="java.lang.String"  description="value to display" %>
<%@ attribute name="map"  rtexprvalue="true"  required="false" type="java.lang.String"  description="True if attribute is a map" %>
<%@ attribute name="nopic"  rtexprvalue="true"  required="false" type="java.lang.String"  description="True if there is no image" %>

<jstl:choose>
<jstl:when test="${photoUrl ne null }">
	<jstl:if test="${nopic eq null}">
		<spring:message code="${model}.${name}" var="Header" />
		<display:column title="${Header}" sortable="${sortable}" style="${style}" format="${format}">
			<img style="" height="90" width="135"  src="${photoUrl}"></img>
		</display:column>
	</jstl:if>
	<jstl:if test="${nopic ne null}">
		<spring:message code="${model}.${name}" var="Header" />
		<display:column title="${Header}" sortable="${sortable}" style="${style}" format="${format}">
			<img style="opacity:0.5" height="90" width="135"  src="${photoUrl}"></img>
		</display:column>
	</jstl:if>
</jstl:when>
<jstl:when test="${photoUrl ne null }">
	<spring:message code="${model}.${name}" var="Header" />
	<display:column title="${Header}" sortable="${sortable}" style="${style}" format="${format}">
		<img style="" height="90" width="135"  src="${photoUrl}"></img>
	</display:column>
</jstl:when>
<jstl:when test="${value ne null }">
	<spring:message code="${model}.${name}" var="Header" />
	<display:column title="${Header}" sortable="${sortable}" style="${style}" format="${format}">
		<jstl:out value="${value}"/>
	</display:column>
</jstl:when>
<jstl:when test="${link ne null}">
	<spring:message code="${model}.${name}" var="Header" />
	<display:column title="${Header}" sortable="${sortable}" style="${style}" format="${format}">
		<a href="${link}">${linkName}</a>
	</display:column>
</jstl:when>
<jstl:otherwise>
	<spring:message code="${model}.${name}" var="Header" />
	<display:column property="${name}" title="${Header}" sortable="${sortable}" style="${style}" format="${format}"/>
</jstl:otherwise>

</jstl:choose>