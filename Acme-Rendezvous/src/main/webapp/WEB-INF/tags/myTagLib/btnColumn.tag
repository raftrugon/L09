<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<%@ attribute name="edit"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Edit button" %>
<%@ attribute name="delete"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Delete button" %>
<%@ attribute name="other"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Other buttons" %>
<%@ attribute name="modal"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Other buttons" %>


<display:column sortable="false" style="vertical-align:middle; width:1px; white-space:nowrap; ${style}">
			<jstl:if test="${edit ne null}">
				<a href="${edit}" class="btn btn-warning btn-sm"><i class="fa fa-edit"></i>Edit</a>
			</jstl:if>
			<jstl:if test="${delete ne null}">
				<a href="${delete}" class="btn btn-danger btn-sm"><i class="fa fa-times"></i>Delete</a>
			</jstl:if>
			<jstl:if test="${other ne null}">
				<jstl:set var="otherBtns" value="${fn:split(other,'-')}"/>
				<jstl:forEach begin="0" end="${fn:length(otherBtns)-1}" var="i">
					<jstl:set var="button" value="${fn:split(otherBtns[i],',')}"/>
						<a href="${button[0]}" class="btn btn-${button[2]} btn-sm"><i class="fa fa-${button[3]}"></i>${button[1]}</a>
				</jstl:forEach>
			</jstl:if>
			<jstl:if test="${modal ne null}">
				<jstl:set var="modalArgs" value="${fn:split(modal,',')}"/>
				<button id="${modalArgs[3]}" type="button" class="btn btn-${modalArgs[1]}" data-toggle="modal" data-target="#${modalArgs[2]}"><spring:message code="${model}.${modalArgs[0]}"/></button>
			</jstl:if>
</display:column>