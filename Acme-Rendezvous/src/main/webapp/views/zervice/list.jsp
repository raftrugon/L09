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

<jstl:set var="inappropriateStyle" value=""/>
<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12 cardContainer" id="zervicesContainer">
			<jstl:if test="${zervice.inappropriate eq true}">
				<jstl:set var="inappropriateStyle" value="filter: blur(5px);-webkit-filter: blur(5px);"/>
				<div class="alert alert-danger" style="position:absolute;top:40%;right:10%;left:10%;text-align:center;z-index:500;"><strong><spring:message code="zervice.inappropriate.alert"/></strong></div>
			</jstl:if>
			
	<div class="card" style="${inappropriateStyle}">
		<div onclick="${rendClick}" style="height:100%;">
			<jstl:if test="${empty zervice.picture}">
				<div class="nopicContainer">
					<img src="images/nopic2.jpg" style="object-fit:cover;height:200px;width:100%" class="nopic"/>
					<div class="nopicCaption2 alert alert-warning"><spring:message code="master.page.nopic"/></div>
				</div>
			</jstl:if>
			<jstl:if test="${not empty zervice.picture}">
				<img src="${zervice.picture}" style="object-fit:cover;height:200px;width:100%">
			</jstl:if>
	        <h1>
	        	<jstl:out value="${zervice.name}"/>
	        </h1>
	        	<span class="label label-primary"><jstl:out value="${zervice.category.name}"/></span>
	        <div style="text-align:center;margin-top:5px;" class="cardDate">
				<jstl:out value="${zervice.description}"/>
	        </div>
	        <div style="position: absolute;bottom: 45px;width: 100%;">
	        	<h2><span class="label label-info">
	        		<jstl:if test="${locale == 'es'}">
	        		<fmt:formatNumber pattern="###.##" value="${zervice.price}" type="currency" />
	        		&euro;
	        		</jstl:if>
	        		<jstl:if test="${locale == 'en'}">
	        		&euro;
	        		<fmt:formatNumber pattern="###.##" value="${zervice.price}" type="currency" />        		
	        		</jstl:if>
	        	</span></h2>
	        </div>
		</div>
		<security:authorize access="hasRole('USER')">
			<jstl:if test="${not zervice.inappropriate}">
				<a class="cardButton newRequestCardBtn" id="${zervice.id}"><spring:message code="request.new"/></a>
			</jstl:if>
			<jstl:if test="${zervice.inappropriate}">
				<button type="button" class="cardButton disabled"><s><spring:message code="request.new"/></s></button>
			</jstl:if>
		</security:authorize>
		<security:authorize access="hasRole('MANAGER')">
			<jstl:if test="${pageContext.request.userPrincipal.name eq zervice.manager.userAccount.username }">
				<jstl:if test="${not zervice.inappropriate}">
					<a class="cardButton" href="manager/zervice/edit.do?zerviceId=${zervice.id}"><spring:message code="zervice.edit"/></a>
				</jstl:if>
				<jstl:if test="${zervice.inappropriate}">
					<button type="button" class="cardButton disabled"><s><spring:message code="zervice.edit"/></s></button>
				</jstl:if>
			</jstl:if>
		</security:authorize>
		<security:authorize access="hasRole('ADMIN')">
				<jstl:if test="${not zervice.inappropriate}">
					<a class="cardButton btn-danger deleteZerviceButton" id="${zervice.id}"><spring:message code="zervice.markAsInappropriate"/></a>
				</jstl:if>
		</security:authorize>
	</div>
</div>
</jstl:forEach>

<script>
	$(function(){
		$('.newRequestCardBtn').click(function(e){
			e.preventDefault();
			$.get('user/request/create.do',{zerviceId: $(this).attr('id')}, function(data){
				$('#requestModal .modal-body').html(data);
				$('#requestModal').modal('show');
				$('#rendezvousSelect').selectpicker('refresh');
				$('#zerviceSelect').selectpicker('refresh');
			});
		});
	});
</script>

<script>
$(function(){
	$('.deleteZerviceButton').click(function(e){
		e.preventDefault();
		var button = $(this);
		$.post( "admin/ajax/zervice/delete.do",{zerviceId: $(this).attr('id') }, function( data ) {
			if(data==1){
				button.parent().parent().prepend('<div class="alert alert-danger" style="position:absolute;top:40%;right:10%;left:10%;text-align:center;z-index:500;"><strong><spring:message code="zervice.inappropriate.alert"/></strong></div>')
				button.parent().css('filer','blur(5px)').css('-webkit-filter','blur(5px)');
				button.remove();
			}
			else notify('danger','<spring:message code="rendezvous.zervice.delete.error"/>');
			});
	});
});
</script>