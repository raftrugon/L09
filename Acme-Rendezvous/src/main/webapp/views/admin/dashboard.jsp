<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>



<div class=center-text>
<display:table pagesize="7" class="displaytag" keepStatus="true" name="list" id="row">
	 <display:caption> 
	 	<spring:message code='admin.title1'/>
	 </display:caption>
	<display:column>
	<jstl:choose>
		<jstl:when test="${row_rowNum == 1}">
			<spring:message code="admin.dash1"/>
		</jstl:when>
		<jstl:when test="${row_rowNum == 2}">
			<spring:message code="admin.dash2"/>
		</jstl:when>
		<jstl:when test="${row_rowNum == 3}">
			<spring:message code="admin.dash3"/>
		</jstl:when>
		<jstl:when test="${row_rowNum == 4}">
			<spring:message code="admin.dash4"/>
		</jstl:when>
		<jstl:when test="${row_rowNum == 5}">
			<spring:message code="admin.dash5"/>
		</jstl:when>
		<jstl:when test="${row_rowNum == 6}">
			<spring:message code="admin.dash6"/>
		</jstl:when>
		<jstl:when test="${row_rowNum == 7}">
			<spring:message code="admin.dash7"/>
		</jstl:when>
	
	</jstl:choose>	
	</display:column>

	<jstl:forEach items="${row}" var="x" varStatus="count">
		<jstl:choose>
			<jstl:when test="${count.index==0 }">
				<spring:message code="admin.avg" var="title"/>
			</jstl:when>
			<jstl:when test="${count.index==1 }">
				<spring:message code="admin.std" var="title"/>
			</jstl:when>
		</jstl:choose>
		<display:column title="${title}">${x}</display:column>
	</jstl:forEach>
	
	<display:setProperty name="paging.banner.onepage" value=""/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>

</display:table>
</br>
	
	<div class="well well-sm" style="width:95%">
		<strong>
		<spring:message code="admin.ratio"/></strong> <jstl:out value="${ratioOfUsersWhoCreatedRendezvouses}"></jstl:out>
		
	</div>
	

<display:table pagesize="10" class="displaytag" keepStatus="true" name="top10RnedezVouses" id="row1">
	 <display:caption> 
	 	<spring:message code='admin.title2'/>
	 </display:caption>

	<jstl:set var="model" value="rendezvous" scope="request"/>
	<!-- Attributes -->
  	<lib:column name="name" link="rendezvous/display.do?rendezvousId=${row1.id}" linkName="${row1.name}"/>
	<lib:column name="description"/>
	<lib:column name="organisationMoment" format="{0,date,dd/MM/yy HH:mm}"/>
	<jstl:if test="${row1.picture ne null}">
		<lib:column name="picture" photoUrl="${row1.picture}"/>
	</jstl:if>
	<jstl:if test="${row1.picture eq null}">
		<lib:column name="picture" photoUrl="images/nopic.jpg" nopic="1"/>
	</jstl:if>
	<lib:column name="coordinates" value="[${row1.longitude},${row1.latitude}]"/>
	<lib:column name="user" link="user-display.do?userId=${row1.user.id}" linkName="${row1.user.name} ${row1.user.surnames}"/>

<display:setProperty name="paging.banner.onepage" value=""/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>

</display:table>

<display:table pagesize="10" class="displaytag" keepStatus="true" name="rendezvousWithAnnouncementsOverAvg" id="row2">
	 <display:caption> 
	 	<spring:message code='admin.title3'/>
	 </display:caption>
	<jstl:set var="model" value="rendezvous" scope="request"/>
	<!-- Attributes -->
  	<lib:column name="name" link="rendezvous/display.do?rendezvousId=${row2.id}" linkName="${row2.name}"/>
	<lib:column name="description"/>
	<lib:column name="organisationMoment" format="{0,date,dd/MM/yy HH:mm}"/>
	<jstl:if test="${row2.picture ne null}">
		<lib:column name="picture" photoUrl="${row2.picture}"/>
	</jstl:if>
	<jstl:if test="${row2.picture eq null}">
		<lib:column name="picture" photoUrl="images/nopic.jpg" nopic="1"/>
	</jstl:if>
	<lib:column name="coordinates" value="[${row2.longitude},${row2.latitude}]"/>
	<lib:column name="user" link="user-display.do?userId=${row2.user.id}" linkName="${row2.user.name} ${row2.user.surnames}"/>

<display:setProperty name="paging.banner.onepage" value=""/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>

</display:table>


<display:table pagesize="10" class="displaytag" keepStatus="true" name="rendezvousesLinkedToMoreThan110PerCent" id="row3">
	 <display:caption> 
	 	<spring:message code='admin.title4'/>
	 </display:caption>

	<jstl:set var="model" value="rendezvous" scope="request"/>
	<!-- Attributes -->
  	<lib:column name="name" link="rendezvous/display.do?rendezvousId=${row3.id}" linkName="${row3.name}"/>
	<lib:column name="description"/>
	<lib:column name="organisationMoment" format="{0,date,dd/MM/yy HH:mm}"/>
	<jstl:if test="${row3.picture ne null}">
		<lib:column name="picture" photoUrl="${row3.picture}"/>
	</jstl:if>
	<jstl:if test="${row3.picture eq null}">
		<lib:column name="picture" photoUrl="images/nopic.jpg" nopic="1"/>
	</jstl:if>
	<lib:column name="coordinates" value="[${row3.longitude},${row3.latitude}]"/>
	<lib:column name="user" link="user-display.do?userId=${row3.user.id}" linkName="${row3.user.name} ${row3.user.surnames}"/>

<display:setProperty name="paging.banner.onepage" value=""/>
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.one_item_found" value=""/>
    <display:setProperty name="paging.banner.no_items_found" value=""/>

</display:table> 


</div>
