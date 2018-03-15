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
		$('#pill2').hide();
		var ctx = document.getElementById('radarChart');
		var myRadarChart = new Chart(ctx, {
			type: 'radar',
			data: {
				labels: ['<spring:message code="admin.dash1"/>','<spring:message code="admin.dash2"/>','<spring:message code="admin.dash3"/>',
				         '<spring:message code="admin.dash4"/>','<spring:message code="admin.dash5"/>','<spring:message code="admin.dash6"/>',
				         '<spring:message code="admin.dash7"/>','<spring:message code="admin.dash8"/>'],
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
				$('#pill2').hide();
			}else{
				$('#pill1').hide();
				$('#pill2').show();
			}
		});
	});
</script>

<div class="col-xs-12" style="margin-bottom:10px">
	<ul class="nav nav-pills nav-justified">
    <li class="active  col-sm-6"><a class="pillBtn" style="border: 1px solid #337ab7;" data-toggle="pill" href="#pill1"><spring:message code="admin.dashboard.graphs"/></a></li>
    <li class="col-sm-6"><a class="pillBtn" style="border: 1px solid #337ab7;" data-toggle="pill" href="#pill2"><spring:message code="admin.dashboard.lists"/></a></li>
  </ul>
</div>
<div id="tab-content">
	<div id="pill1" class="tab-pane fade in active">
		<div class="col-md-8" style="margin-top:10px;margin-bottom:10px;">
			<canvas id="radarChart"></canvas>
		</div>
		<div class="col-md-4">
		<div  class="col-md-12">
			<h2 style="text-align:center"><strong><spring:message code="admin.ratio"/></strong></h2>
			<div id="morrisDonut" style="height:300px;"></div>
		</div>
		<div style="text-align:center;margin-top:20px" class="col-md-12">
			<div class="col-md-6">
				<h4><strong><spring:message code="admin.title8"/></strong></h4>
				<h3 class="bg-primary"><jstl:out value="${ratioOfCategoriesPerRendezvous}"/></h3>
			</div>
			<div class="col-md-6">
				<h4><strong><spring:message code="admin.title9"/></strong></h4>
				<h3 class="bg-primary"><jstl:out value="${ratioOfServicesPerCategory}"/></h3>
			</div>
			<div class="col-md-12" style="margin-top:10px">
				<h4><strong><spring:message code="admin.title10b"/></strong></h4>
				<h3 class="bg-primary col-xs-6"><strong>Min:</strong> <jstl:out value="${minMaxZervicesPerRendezvous[0]}"/></h3>
				<h3 class="bg-primary col-xs-6"><strong>Max:</strong> <jstl:out value="${minMaxZervicesPerRendezvous[1]}"/></h3>
			</div>
		</div>
		</div>
	</div>	
	<div id="pill2" class="tab-pane fade">
		<div class="col-sm-3">
			<ul class="nav nav-tabs tabs-left">
			    <li class="active"><a data-toggle="tab" href="#tab1"><spring:message code='admin.title2'/></a></li>
			    <li><a data-toggle="tab" href="#tab2"><spring:message code='admin.title3'/></a></li>
			    <li><a data-toggle="tab" href="#tab3"><spring:message code='admin.title4'/></a></li>
			    <li><a data-toggle="tab" href="#tab4"><spring:message code='admin.title5'/></a></li>
			    <li><a data-toggle="tab" href="#tab5"><spring:message code='admin.title6'/></a></li>
			    <li><a data-toggle="tab" href="#tab6"><spring:message code='admin.title7'/></a></li>
			    <li><a data-toggle="tab" href="#tab7"><spring:message code='admin.title11'/></a></li>
			</ul>
		</div>
		<div class="col-sm-9">
			<div class="tab-content">
				<div id="tab1" class="tab-pane fade in active">
					<div class="list-group">
					<jstl:forEach items="${top10RnedezVouses}" var="r">
						<a href="rendezvous/display.do?rendezvousId=${r.id}" class="list-group-item">
							<h4><span class="label label-info" style="position:absolute;right:10px"><spring:message code="rendezvous.rsvp"/>s: ${fn:length(r.rsvps)}</span></h4>
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
							<h4><span class="label label-info" style="position:absolute;right:10px"><spring:message code="rendezvous.announcements.tab"/>: ${fn:length(r2.announcements)}</span></h4>
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
							<h4><span class="label label-info" style="position:absolute;right:10px"><spring:message code="rendezvous.announcements.tab"/>: ${fn:length(r3.rendezvouses)}</span></h4>
							<h4 class="list-group-item-heading"><strong>${r3.name}</strong></h4>
							<p class="list-group-item-text">${r3.description}</p>
						</a>
					</jstl:forEach>
					</div>
				</div>
				<div id="tab4" class="tab-pane fade">
					<jstl:forEach items="${bestSellingZervices}" var="zervice">
						<a class="list-group-item">
							<h4><span class="label label-info" style="position:absolute;right:10px"><spring:message code="admin.zervices"/>: ${fn:length(zervice.requests)}</span></h4>
							<h4 class="list-group-item-heading"><strong>${zervice.name}</strong></h4>
							<p class="list-group-item-text">${zervice.description}</p>
						</a>					
					</jstl:forEach>
				</div>
				<div id="tab5" class="tab-pane fade">
					<jstl:forEach items="${managersMoreZervicesAvg}" var="manager">
						<a class="list-group-item">
							<h4><span class="label label-info" style="position:absolute;right:10px"><spring:message code="admin.requests"/>: ${fn:length(manager.zervices)}</span></h4>
							<h4 class="list-group-item-heading"><strong>${manager.name} ${manager.surnames}</strong></h4>
							<p class="list-group-item-text">${manager.vat}</p>
						</a>					
					</jstl:forEach>
				</div>
				
				<div id="tab6" class="tab-pane fade">
					<jstl:forEach items="${managersCancelledZervices}" var="entry">
						<a class="list-group-item">
							<h4><span class="label label-info" style="position:absolute;right:10px"><spring:message code="admin.requests"/>: ${entry.value}</span></h4>
							<h4 class="list-group-item-heading"><strong>${entry.key.name} ${entry.key.surnames}</strong></h4>
							<p class="list-group-item-text">${entry.key.vat}</p>
						</a>			
					</jstl:forEach>
				</div>
				<div id="tab7" class="tab-pane fade"></div>
			</div>
		</div>
	</div>
</div>
<script>
	$(function(){
		getTopServices(0,10);
	});
	
	function getTopServices(pageNumber,pageSize){
		$.get('admin/ajax/dashboard/topzervices.do',{pageNumber:pageNumber,pageSize:pageSize},function(data){
			$('#tab7').html(data);
			$('#pageSize').change(function(){
				if(parseInt($('#pageSize').val())<1)return null;
				else getTopServices(parseInt($('#pageNumber').val()),parseInt($('#pageSize').val()));
			});
			$('#prevPage').click(function(e){
				e.preventDefault();
				getTopServices(parseInt($('#pageNumber').val())-1,parseInt($('#pageSize').val()));
			});
			$('#nextPage').click(function(e){
				e.preventDefault();				
				getTopServices(parseInt($('#pageNumber').val())+1,parseInt($('#pageSize').val()));
			});
		});
		
	}
</script>
