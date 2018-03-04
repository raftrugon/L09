<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ attribute name="model"  rtexprvalue="true"  required="false" type="java.lang.String"  description="Model attribute" %>
<%@ attribute name="noDelete"  rtexprvalue="true"  required="false" type="java.lang.String"  description="if set hides delete button" %>
<%@ attribute name="id"  rtexprvalue="true"  required="true" type="java.lang.String"  description="id of object" %>
<%@ attribute name="cancelUri"  rtexprvalue="true"  required="false" type="java.lang.String"  description="URI for redirect when cancel" %>

<div class="btn-group btn-group-justified">
	<div class="btn-group">
		<input class="btn btn-success saveButton${model}" type="submit" id="saveButton${model}" name="save" value="<spring:message code="${model}.save"/>" />
	</div>
	<jstl:if test="${id != 0 and noDelete eq null}">
		<div class="btn-group">
	   		<input class="btn btn-danger" type="submit" name="delete" value="<spring:message code="${model}.delete" />" 
	    	onclick="return confirm('<spring:message code="${model}.confirm.delete" />')" />
		</div>
  	</jstl:if>
  	<jstl:if test="${cancelUri ne null }">
		<div class="btn-group">
			<input class="btn btn-default" type="button" name="cancel"
				value="<spring:message code="${model}.cancel"/>"
				onclick="javascript: window.location.replace('${cancelUri}')" />
		</div>
	</jstl:if>
</div>
<jstl:if test="${message ne null }">
	<p style="error"><spring:message code="${message}"/></p>
</jstl:if>