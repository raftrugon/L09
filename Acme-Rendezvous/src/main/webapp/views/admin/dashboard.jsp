<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<script>
	$(function(){
		var ctx = document.getElementById('radarChart');
		var myRadarChart = new Chart(ctx, {
			type: 'radar',
			data: {
				labels: ['<spring:message code="admin.dash1"/>','<spring:message code="admin.dash2"/>','<spring:message code="admin.dash3"/>',
				         '<spring:message code="admin.dash4"/>','<spring:message code="admin.dash5"/>','<spring:message code="admin.dash6"/>',
				         '<spring:message code="admin.dash7"/>'],
		        datasets: [
	                	{
                		label: '<spring:message code="admin.avg"/>',
                        fill: false,
                        backgroundColor: "rgba(179,181,198,0.2)",
                        borderColor: "rgba(179,181,198,1)",
                        pointBorderColor: "#fff",
                        pointBackgroundColor: "rgba(179,181,198,1)",
			        	data: [<jstl:forEach items="${list}" var="item" varStatus="i">${item[0]}<jstl:if test="${not i.last}">,</jstl:if></jstl:forEach>]
			         	},{
		         		label: '<spring:message code="admin.std"/>',
		                fill: false,
		                backgroundColor: "rgba(255,99,132,0.2)",
		                borderColor: "rgba(255,99,132,1)",
		                pointBorderColor: "#fff",
		                pointBackgroundColor: "rgba(255,99,132,1)",
			        	data: [<jstl:forEach items="${list}" var="item" varStatus="i">${item[1]}<jstl:if test="${not i.last}">,</jstl:if></jstl:forEach>]
			        	}
	         		]
				},
				options: {
				      title: {
				        display: true,
				        text: '<spring:message code="admin.title1"/>'
				      }
				    }
		});
		var havenotCreated = 1-<jstl:out value="${ratioOfUsersWhoCreatedRendezvouses}"></jstl:out>;
		var donut = Morris.Donut({
			element: 'morrisDonut',
			data: [
			   {label: '<spring:message code="admin.ratio.have"/>', value: <jstl:out value="${ratioOfUsersWhoCreatedRendezvouses}"></jstl:out>*100}    ,
			   {label: '<spring:message code="admin.ratio.havenot"/>', value: havenotCreated*100}
		       ],
	       colors: ['#38bc31','#ad2b2b'],
	       resize:true
		});
		donut.select(0);
	});	
</script>
<script>
	$(function(){
		$('.pillBtn').click(function(){
			if($(this).attr('href') === '#pill1'){
				$('#pill1').show();
			}else{
				$('#pill1').hide();
			}
		});
	});
</script>

<div class="col-xs-12" style="margin-bottom:10px">
	<ul class="nav nav-pills nav-justified">
    <li class="active"><a class="pillBtn" data-toggle="pill" href="#pill1"><spring:message code="admin.dashboard.graphs"/></a></li>
    <li><a class="pillBtn" data-toggle="pill" href="#pill2"><spring:message code="admin.dashboard.lists"/></a></li>
  </ul>
</div>
<div id="tab-content">
	<div id="pill1" class="tab-pane fade in active">
		<div class="col-md-8" style="margin-top:10px;margin-bottom:10px;">
			<canvas id="radarChart"></canvas>
		</div>
		<div id="morrisDonut" class="col-md-4" style="height:300px;"></div>
	</div>	
	<div id="pill2" class="tab-pane fade">
		<div class="col-sm-3">
			<ul class="nav nav-tabs tabs-left">
			    <li class="active"><a data-toggle="tab" href="#tab1"><spring:message code='admin.title2'/></a></li>
			    <li><a data-toggle="tab" href="#tab2"><spring:message code='admin.title3'/></a></li>
			    <li><a data-toggle="tab" href="#tab3"><spring:message code='admin.title4'/></a></li>
			    <li><a data-toggle="tab" href="#tab4">asdfasd</a></li>
			    <li><a data-toggle="tab" href="#tab5">asdfasdfasdasd</a></li>
			</ul>
		</div>
		<div class="col-sm-9">
			<div class="tab-content">
				<div id="tab1" class="tab-pane fade in active">
					<div class="list-group">
					<jstl:forEach items="${top10RnedezVouses}" var="r">
						<a href="rendezvous/display.do?rendezvousId=${r.id}" class="list-group-item">
							<span class="label label-info" style="position:absolute;right:10px"><spring:message code="rendezvous.rsvp"/>s: ${fn:length(r.rsvps)}</span>
							<h4 class="list-group-item-heading"><strong>${r.name}</strong></h4>
							<p class="list-group-item-text">${r.description}</p>
						</a>
					</jstl:forEach>
					</div>
				</div>
				<div id="tab2" class="tab-pane fade">
					<div class="list-group">
					<jstl:forEach items="${rendezvousWithAnnouncementsOverAvg}" var="r2">
						<a href="rendezvous/display.do?rendezvousId=${r2.id}" class="list-group-item">
							<span class="label label-info" style="position:absolute;right:10px"><spring:message code="rendezvous.announcements.tab"/>: ${fn:length(r2.announcements)}</span>
							<h4 class="list-group-item-heading"><strong>${r2.name}</strong></h4>
							<p class="list-group-item-text">${r2.description}</p>
						</a>
					</jstl:forEach>
					</div>
				</div>
				<div id="tab3" class="tab-pane fade">
					<div class="list-group">
					<jstl:forEach items="${rendezvousesLinkedToMoreThan110PerCent}" var="r3">
						<a href="rendezvous/display.do?rendezvousId=${r3.id}" class="list-group-item">
							<span class="label label-info" style="position:absolute;right:10px"><spring:message code="rendezvous.announcements.tab"/>: ${fn:length(r3.rendezvouses)}</span>
							<h4 class="list-group-item-heading"><strong>${r3.name}</strong></h4>
							<p class="list-group-item-text">${r3.description}</p>
						</a>
					</jstl:forEach>
					</div>
				</div>
				<div id="tab4" class="tab-pane fade">
					<jsp:useBean id="now" class="java.util.Date" />
					<jstl:forEach items="${bestSellingZervices}" var="zervice">
					
					<jstl:set var="inappropriateStyle" value=""/>
					<div class="col-lg-3 col-sm-6 col-xs-12 cardContainer" id="zervicesContainer">
								<jstl:if test="${zervice.inappropriate eq true}">
									<jstl:set var="inappropriateStyle" value="filter: blur(5px);-webkit-filter: blur(5px);"/>
									<div class="alert alert-danger" style="position:absolute;top:40%;right:10%;left:10%;text-align:center;z-index:500;"><strong><spring:message code="zervice.inappropriate.alert"/></strong></div>
								</jstl:if>
								
						<div class="card" style="${inappropriateStyle}">
							<div style="height:100%;">
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
							</div>
						</div>
					</div>
					</jstl:forEach>
				</div>
				<div id="tab5" class="tab-pane fade">
					<jstl:forEach items="${managersMoreZervicesAvg}" var="manager">
					<jstl:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 9) + 1 %></jstl:set>
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="userCard" style="overflow:hidden;height:450px">
							  <img src="images/kS${rand}.png" style="width:100%;margin-top:-25px;max-height:55%">
							  <button class="cardUserButton" style="margin-top:-25px;"><jstl:out value="${manager.name} ${manager.surnames}"/></button>
							  <p><strong><spring:message code="user.address" /></strong></p>
							  <p><jstl:if test="${manager.address eq null}">-</jstl:if><jstl:out value="${manager.address}"/></p>
							  <p><strong><spring:message code="user.phoneNumber" /></strong></p>
							  <p><jstl:if test="${manager.phoneNumber eq null}">-</jstl:if><jstl:out value="${manager.phoneNumber}"/></p>
							  <p><strong><spring:message code="user.email" /></strong></p>
							  <p><jstl:out value="${manager.email}"/></p>
						</div>
					</div>
					</jstl:forEach>
				</div>
			</div>
		</div>
	</div>
</div>

