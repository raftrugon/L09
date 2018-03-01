<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<jsp:useBean id="now" class="java.util.Date" />

	<div id="loaderWrapper">
		<div id="loaderDiv"><img id="loaderGif" src="images/loadergif.gif"/></div>
	</div>
	
	<div id="callbackModal" class="modal fade" role="dialog">
  		<div class="modal-dialog" style="margin-top:45vh">
		    <div class="modal-content">
		     	<div id="callbackAlert" class="alert" style="margin:0;text-align:center;font-weight:bold;"></div>
		    </div>
	 	</div>
	</div>
	
<jstl:if test="${rendezvous.inappropriate eq true}">
	<script>
		$(function(){
			$('#inappropriateBlur').css("filter","blur(5px)").css("-webkit-filter","blur(5px)");
			$('#inappropriateModal').modal({backdrop: 'static', keyboard: false});
		});
	</script>

	<div id="inappropriateModal" class="modal fade" role="dialog">
  		<div class="modal-dialog" style="margin-top:45vh">
		    <div class="modal-content">
		     	<div class="alert alert-danger" style="margin:0;text-align:center"><strong><spring:message code="rendezvous.inappropriate.alert"/></strong></div>
		    </div>
	 	</div>
	</div>
</jstl:if>
<jstl:if test="${isAdult eq false and rendezvous.adultOnly eq true}">
	<script>
		$(function(){
			$('#inappropriateBlur').css("filter","blur(5px)").css("-webkit-filter","blur(5px)");
			$('#under18Modal').modal({backdrop: 'static', keyboard: false});
		});
	</script>

	<div id="under18Modal" class="modal fade" role="dialog">
  		<div class="modal-dialog" style="margin-top:45vh">
		    <div class="modal-content">
		     	<div class="alert alert-danger" style="margin:0;text-align:center"><strong><spring:message code="rendezvous.under18.alert"/></strong></div>
		    </div>
	 	</div>
	</div>
</jstl:if>
<div id="inappropriateBlur">
<!--  LEFT  -->
<div class="col-md-2">
<security:authorize access="hasRole('USER')">
	<jstl:if test="${rendezvous.user.userAccount.username eq pageContext.request.userPrincipal.name }">
	<div class="panel panel-default">
		<div class="well" style="margin-bottom:10px;text-align:center"><strong><i class="fas fa-bullhorn"></i> <spring:message code="rendezvous.announcement.new"/></strong></div>
		<form:form action="user/ajax/announcement/save.do" modelAttribute="announcement" style="margin-bottom:10px !important;width:95%;margin:auto">		
			<jstl:set var="model" value="announcement" scope="request"/>
			<lib:input type="hidden" name="rendezvous"/>
			<spring:message code='announcement.title' var="titlePlaceholder"/>
			<lib:input type="text" name="title" placeholder="${titlePlaceholder}" noLabel="true" required="true"/>
			<lib:input type="textarea" name="description" rows="4" required="true"/>
			<lib:button id="0" noDelete="true" />
		</form:form>
	</div>
	</jstl:if>
</security:authorize>
	<jstl:set var="model" value="rendezvous" scope="request"/>
	<fmt:formatDate var="formatedBirthDate" value="${rendezvous.user.birthDate}" pattern="dd/MM/yyyy"/>
	
		
	<jstl:if test="${rendezvous.user.userAccount.username ne pageContext.request.userPrincipal.name }">
		<div id="userCardDiv"></div>
	</jstl:if>
	<jstl:if test="${rendezvous.user.userAccount.username eq pageContext.request.userPrincipal.name }">
		<a style="margin-bottom:10px" id="${rendezvous.id}" class="btn btn-block btn-primary editQAButton" id="${rendezvous.id}" ><spring:message code="rendezvous.questions.edit" /></a>
	</jstl:if>
	
	<div id="chipsDiv"></div>
	
</div>


<!--  CENTER  -->
<div class="center-text col-md-8">
<div class="well well-lg" style="text-align:center"><h1><strong><jstl:out value="${rendezvous.name}"/></strong> <small><fmt:formatDate type = "both" dateStyle = "long" timeStyle = "long" value = "${rendezvous.organisationMoment}" /></small></h1></div>
<div class="col-md-7" style="text-align:right">
	<security:authorize access="hasRole('USER')">
		<jstl:if test="${pageContext.request.userPrincipal.name eq rendezvous.user.userAccount.username and not rendezvous.deleted and rendezvous.organisationMoment gt now and not rendezvous.finalMode}">
			<div class="btn-group btn-group-justified" style="margin-bottom:10px">
			<a class="btn btn-warning" href="user/rendezvous/edit.do?rendezvousId=${rendezvous.id}"><spring:message code='rendezvous.edit'/></a>
			<a class="btn btn-danger" href="user/rendezvous/cancel.do?rendezvousId=${rendezvous.id}"><spring:message code='rendezvous.cancel'/></a>
			</div>
		</jstl:if>
	</security:authorize>
	<blockquote class="blockquote-reverse">
	    <p><jstl:out value="${rendezvous.description}"/></p>
	    <footer><jstl:out value="${rendezvous.user.name} ${rendezvous.user.surnames}"/></footer>
  	</blockquote>
  	<jstl:if test="${rendezvous.inappropriate eq false}">
	  	<security:authorize access="hasRole('ADMIN')">
		    <button id="${rendezvous.id}" class="btn btn-danger deleteRendezvousButton">
				<spring:message code="rendezvous.adminDelete"/>
			</button>
		</security:authorize>
	</jstl:if>
</div>
<div class="col-md-5" style="text-align:center">
	<jstl:if test="${not empty rendezvous.picture}">
		<img src="<jstl:out value='${rendezvous.picture}'/>" style="max-height:200px;width:100%;object-fit:cover"/>
	</jstl:if>
	<jstl:if test="${empty rendezvous.picture}">
		<div class="nopicContainer">
			<img src="images/nopic.jpg" style="max-height:200px;width:100%;object-fit:cover" class="nopic"/>
			<div class="nopicCaption alert alert-warning"><spring:message code="master.page.nopic"/></div>
		</div>
	</jstl:if>
</div>

<!-- buttons & alerts -->
<div id="buttonsAlertsDiv" class="col-md-12" style="margin-top:20px">
	
</div>



<!-- Comments & Announcements -->
<ul class="nav nav-tabs nav-justified col-md-12" style="margin-top:20px;padding-left:15px">
  <li class="active"><a data-toggle="tab" href="#accordion"><spring:message code="rendezvous.comments.tab"/></a></li>
  <li><a data-toggle="tab" href="#announcementTab"><spring:message code="rendezvous.announcements.tab"/></a></li>
</ul>
<div class="tab-content">
	<div class="container col-md-12 panel-group tab-pane fade in active" id="accordion" style="margin-top:20px;">
	
	</div>
	<div id="announcementTab" class="tab-pane fade col-md-12">
		
	</div>
</div> 
</div>
<!--  RIGHT  -->
<div class="col-md-2">
<jstl:if test="${not empty rendezvous.latitude and not empty rendezvous.longitude}">
<div id="map" style="height:300px;width:100%"></div>
</jstl:if>
<security:authorize access="hasRole('USER')">
	<div class="dropdown" style="margin:20px 0 10px 0">
    <button class="btn btn-primary dropdown-toggle btn-block" type="button" data-toggle="dropdown">
    <span class="caret"></span> <spring:message code="rendezvous.linkTo"/>
    </button>
    <ul class="dropdown-menu" style="width:100%">
      <input class="form-control" id="linkSearchInput" type="text" placeholder="Search..">
      <jstl:forEach items="${myRendezvouses}" var="myRendezvous">
     		<li id="linkli${myRendezvous.id}"><a href="javascript:link(${myRendezvous.id},${rendezvous.id})" >${myRendezvous.name}</a></li>
      </jstl:forEach>
    </ul>
  </div>

</security:authorize>
<jstl:forEach items="${rendezvous.rendezvouses}" var="rend">
	<div class="cardDisplay col-xs-12" id="cardLink${rend.id}">
		<div onclick="location.href = 'rendezvous/display.do?rendezvousId=${rend.id}'" style="cursor:pointer;height:100%">
			<jstl:if test="${rendezvous.user.userAccount.username eq pageContext.request.userPrincipal.name }">
				<button style="position:absolute;right:0;top:0;" class="btn btn-danger removeLinkButton" id="${rend.id}"><i class="far fa-trash-alt"></i></button>
			</jstl:if>
			<jstl:if test="${rend.picture eq null}">
				<div class="nopicContainer">
					<img src="images/nopic.jpg" style="object-fit:cover;width:100%;max-height:150px" class="nopic"/>
					<div class="nopicCaption alert alert-warning"><spring:message code="master.page.nopic"/></div>
				</div>
			</jstl:if>
			<jstl:if test="${rend.picture ne null}">
				<img src="${rend.picture}" style="object-fit:cover;width:100%;max-height:150px">
			</jstl:if>
	        <h1>
	        	<jstl:out value="${rend.name}"/>
	        </h1>
	        <div style="text-align:center" class="cardDate">
				<fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${rend.organisationMoment}"/>
	        </div>
		</div>
		<input class="cardButton" type="button" name="cancel" style="position:inherit"
				value="${rend.user.name} ${rend.user.surnames} "	
		onclick="location.href = 'user-display.do?userId=${rend.user.id}'" />
	</div>
</jstl:forEach>
</div>

<!-- Modal -->
<div id="qaModal" class="modal fade" role="dialog">
  <div class="modal-dialog" style="margin:100px auto;">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 id="modalTitle" class="modal-title"></h4>
      </div>
      <div id="modalBody" class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
<jstl:if test="${not empty rendezvous.latitude and not empty rendezvous.longitude}">
<script defer>
function initMap() {
    var uluru = {lat: <jstl:out value="${rendezvous.latitude}"/>, lng: <jstl:out value="${rendezvous.longitude}"/>};
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 4,
      center: uluru
    });
    var marker = new google.maps.Marker({
      position: uluru,
      map: map
    });
  }
</script>
<script async defer
src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0VftX0iPRA4ASNgBh4qcjuzBWU8YBUwI&callback=initMap">
</script>
</jstl:if>
<script>
	function reloadComments(replyingTo){
		return $.get('ajax/showComments.do?rendezvousId=<jstl:out value="${rendezvous.id}"/>',function(data){
			$('#accordion').html(data);
			if(typeof replyingTo !== "undefined") $('#collapse'+replyingTo).collapse("show");
		});
	}
	function reloadAnnouncements(){
		return $.get('ajax/showAnnouncements.do?rendezvousId=<jstl:out value="${rendezvous.id}"/>',function(data){
			$('#announcementTab').html(data);
		});
	}
	function reloadButtons(){
		return $.get('ajax/showButtons.do?rendezvousId=<jstl:out value="${rendezvous.id}"/>',function(data){
			$('#buttonsAlertsDiv').html(data);
		});
	}
	function reloadChips(){
		return $.get("ajax/rsvp/showChips.do?rendezvousId=<jstl:out value='${rendezvous.id}'/>",function(data){
			$('#chipsDiv').html(data);
		});
	}
	function link(sourceIdval,targetIdval){
		$.post('user/ajax/rendezvous/link.do',{sourceId:sourceIdval,targetId:targetIdval},function(data){
			if(data==1){
				notify('success','<spring:message code="rendezvous.link.success"/>');
				$('#linkli'+sourceIdval).remove();
			}else{
				notify('danger','<spring:message code="rendezvous.link.error"/>');
			}
		});
	}
	function showQAModal(id){
		$.get("ajax/qa.do?rsvpId=" + id, function(data){
			$('#modalBody').html(data);
			$('#modalTitle').html('<spring:message code="rendezvous.qaHeader"/>');
			$('#qaModal').modal('show');
		});
	};
	$(document).ready(function(){
		$("#linkSearchInput").on("keyup", function() {
		    var value = $(this).val().toLowerCase();
		    $(".dropdown-menu li").filter(function() {
		      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
		    });
		  });
		$('.editQAButton').click(function(e){
			e.preventDefault();
			$.get("user/ajax/rendezvous/qa/edit.do?rendezvousId=" + $(this).attr('id'), function(data){
				$('#modalBody').html(data);
				$('#modalTitle').html('<spring:message code="rendezvous.qaHeader"/>');
				$('#qaModal').modal('show');
			});
		});
		$('.commentpanel').click(function(e){
			$(this).children('span').each(function(){
				$(this).toggle();
			});
		});
		$.get("ajax/user-card.do?userId=<jstl:out value='${rendezvous.user.id}'/>", function(data){
			$('#userCardDiv').html(data);
		});
		reloadComments();
		reloadAnnouncements();
		reloadButtons();
		reloadChips();
		$.when(reloadAnnouncements(), reloadComments(), reloadChips(), reloadButtons()).done(function(){
			$('#loaderWrapper').fadeOut();
		});
	});
</script>
<script>
	$('#saveButtonannouncement').click(function(e){
		e.preventDefault();
		$.ajax({
			type:'post',
			url:"user/ajax/announcement/save.do",
			data: $('#announcement').serialize(),
			processData: 'false',
			contentType: 'application/x-www-form-urlencoded',
			dataType: 'json',
			success: function(data){
				if(data===1){
					notify('success','<spring:message code="announcement.create.success"/>');
					reloadAnnouncements();
				}
				else if (data==0) notify('danger','<spring:message code="announcement.create.bindingError"/>');
				else notify('danger','<spring:message code="announcement.create.error"/>');
			}
		});
	});
</script>    

<script>
$(function(){
	$('.deleteRendezvousButton').click(function(e){
		e.preventDefault();
		$.post( "admin/ajax/rendezvous/delete.do",{rendezvousId: $(this).attr('id') }, function( data ) {
			if(data==1){ 
				notify('success','<spring:message code="rendezvous.delete.success"/>');
				location.reload();
			}else notify('danger','<spring:message code="rendezvous.delete.error"/>');
			});
	});
});

</script>

<script>
$(function(){
	$('.removeLinkButton').click(function(e){
		e.preventDefault();
		e.stopPropagation();
		var rendezvousId = ${rendezvous.id};
		var linkId = $(this).attr('id');
		$.post( "user/ajax/rendezvous/link/delete.do",{rendezvousId:rendezvousId ,linkId: linkId}, function( data ) {
			if(data==="1"){
				$('#cardLink'+linkId).remove();
				notify('success','<spring:message code="rendezvous.link.delete.success"/>');
			} else {

				notify('danger','<spring:message code="rendezvous.link.delete.error"/>');
			}
			});
	});
});

</script>

<script>
	function saveCommentButton(obj){
		var comment = {};
		$(obj).parent().parent().parent().find('input[type=text],input[type=hidden],textarea').each(function(){
			comment[$(this).attr('name')] = $(this).val();
		});
		$.ajax({
			type:'post',
			url:"user/comment/save.do",
			data: comment,
			processData: 'false',
			contentType: 'application/x-www-form-urlencoded',
			dataType: 'json',
			success: function(data){
				if(data===1){
					notify('success','<spring:message code="comment.create.success"/>');
					reloadComments($('#comment').find('input[name=replyingTo]').val());
					$('#qaModal').modal('hide');
				}
				else if (data==0) notify('danger','<spring:message code="comment.create.bindingError"/>');
				else notify('danger','<spring:message code="comment.create.error"/>');
			}
		});
	};
</script>
</div>