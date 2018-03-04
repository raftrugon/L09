<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="shortcut icon" href="favicon.ico"/> 


  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script type="text/javascript" src="scripts/jquery-ui.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.20.1/moment-with-locales.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/card/2.4.0/jquery.card.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.payment/3.0.0/jquery.payment.js"></script>

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<link rel="stylesheet" href="styles/other.css" type="text/css">
<link rel="stylesheet" href="styles/timeline.css" type="text/css">
<link rel="stylesheet" href="styles/card.css" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
<script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
	
	function relativeRedir(loc) {	
		var b = document.getElementsByTagName('base');
		if (b && b[0] && b[0].href) {
  			if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
    		loc = loc.substr(1);
  			loc = b[0].href + loc;
		}
		window.location.replace(loc);
	}
</script>
</head>

<body>
	<div>
		<tiles:insertAttribute name="header" />
	</div>
	
	<%-- <div class="page-header" style="padding-left:10px;margin-top:60px;"> 
		<h1><tiles:insertAttribute name="title" /></h1>
	</div> --%>
	
	<div id="mainContainer" class="container-fluid" style="margin-top:70px;margin-bottom:40px">
	<jstl:if test="${message != null}">
	</jstl:if>	
	<tiles:insertAttribute name="body" />	
	</div>
	
	<div class="footer">
		<tiles:insertAttribute name="footer" />
	</div>
	
	<div id="tycModal" class="modal fade" role="dialog">
	  <div class="modal-dialog modal-lg" style="margin-top:10vh">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title"><spring:message code="masterpage.terms"/></h4>
	      </div>
	      <div class="modal-body" style="height:70vh;overflow-y:scroll">
	      	<tiles:insertAttribute name="tyc"/>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>

  </div>
</div>

</body>
<script>
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
 
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});
</script>
</html>