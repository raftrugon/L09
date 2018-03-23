<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>
<%@taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions"%>


<div class="col-md-12">
<div id="numberAlert" style="display:none" class="alert alert-danger">
	<strong>Error!</strong> <spring:message code="request.creditCard.invalidNumber"/>
</div>
<div id="dateAlert" style="display:none" class="alert alert-danger">
	<strong>Error!</strong> <spring:message code="request.creditCard.timedOut"/>
</div>

<form:form id="form" modelAttribute="request">
		
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<input type="hidden" name="creditCard.expirationMonth" id="expirationMonth"/>
	<input type="hidden" name="creditCard.expirationYear" id="expirationYear"/>
	
	<div class="form-group">
	<form:label class="control-label" path="rendezvous">
		<spring:message code="request.rendezvous" />:
	</form:label>
	<select class="selectpicker form-control" id="rendezvousSelect" name="rendezvous" data-live-search="true">
		<option selected="selected" class="default" disabled="disabled">--- <spring:message code="request.selectARendezvous"/> ---</option>
		<jstl:forEach items="${rendezvouses}" var="rendezvous">
			<jstl:choose>
				<jstl:when test="${selectedRendezvous ne null and selectedRendezvous eq rendezvous }">
					<option selected="selected" value="${rendezvous.id}">${rendezvous.name}</option>
				</jstl:when>
				<jstl:when test="${request.rendezvous ne null and request.rendezvous eq rendezvous }">
					<option selected="selected" value="${rendezvous.id}">${rendezvous.name}</option>
				</jstl:when>
				<jstl:otherwise>
					<option value="${rendezvous.id}">${rendezvous.name}</option>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:forEach>
	</select>
	<form:errors cssClass="error" path="rendezvous" />
	<span id="rendezvousError" class="error" style="display:none"><spring:message code="javax.validation.constraints.NotNull.message"/></span>
	</div>
	
	<div class="form-group">
	<form:label class="control-label" path="zervice">
		<spring:message code="request.zervice" />:
	</form:label>
	<select class="selectpicker form-control" id="zerviceSelect" name="zervice" data-live-search="true">
		<option selected="selected" class="default" disabled="disabled">--- <spring:message code="request.selectAZervice"/> ---</option>
		<jstl:forEach items="${zervices}" var="zervice">
			<jstl:choose>
				<jstl:when test="${selectedZervice ne null and selectedZervice eq zervice }">
					<option selected="selected" value="${zervice.id}">${zervice.name}</option>
				</jstl:when>
				<jstl:when test="${request.zervice ne null and request.zervice eq zervice }">
					<option selected="selected" value="${zervice.id}">${zervice.name}</option>
				</jstl:when>
				<jstl:otherwise>
					<option value="${zervice.id}">${zervice.name}</option>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:forEach>
	</select>
	<form:errors cssClass="error" path="zervice" />
	<span id="zerviceError" style="display:none" class="error"><spring:message code="javax.validation.constraints.NotNull.message"/></span>
	</div>
	
	<form:label class="control-label" path="comment">
		<spring:message code="request.comment" />:
	</form:label>
	<lib:input type="textarea" name="comment" model="request"/>
	
	
	<div id="paymentMethodError" style="display:none" class="alert alert-danger">
		<strong>Error!</strong> <spring:message code="request.paymentMethod.error"/>
	</div>
	 <div class="panel-group" id="accordion">
	 
	<jstl:if test="${request.creditCard ne null}">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4 class="panel-title">
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse1"><i class="far fa-credit-card"></i> <jstl:out value="${fn:toUpperCase(fn:substring(request.creditCard.brandName,0,1))}${fn:toLowerCase(fn:substring(request.creditCard.brandName,1,fn:length(request.creditCard.brandName)))}"/> <spring:message code="request.creditCard.old"/> <jstl:out value="${fn:substring(request.creditCard.number,fn:length(request.creditCard.number)-4,fn:length(request.creditCard.number))}"/> </a>
        </h4>
      </div>
      <div id="collapse1" class="panel-collapse collapse in">
        <div class="panel-body">
			<div class="creditCardContainer"></div>
        	<div class="form-group">
			<form:label class="control-label" path="creditCard.holderName">
				<spring:message code="request.creditCard.holderName" />:
			</form:label>
			<input id="holderNameOld" name="creditCard.holderName" class="form-control old" disabled="disabled" value="${creditCard.holderName}" />
			<form:errors cssClass="error" path="creditCard.holderName" />	
			</div>
			
			<div class="form-group col-sm-9" style="padding-left:0 !important;">
				<form:label class="control-label" path="creditCard.number">
					<spring:message code="request.creditCard.number" />:
				</form:label>
				<input id="numberOld" name="creditCard.number" class="form-control old" disabled="disabled"  value="${creditCard.number}" />
				<form:errors cssClass="error" path="creditCard.number" />	
			</div>
			
			<div class="form-group col-sm-3" style="padding-right:0 !important;">
				<form:label class="control-label" path="creditCard.brandName">
					<spring:message code="request.creditCard.brandName" />:
				</form:label>
				<input class="form-control old" id="brandNameOld" name="creditCard.brandName" disabled="disabled" value="${creditCard.brandName}"/>
				<form:errors cssClass="error" path="creditCard.brandName" />	
			</div>
			
			<div class="form-group col-sm-6" style="padding-left:0 !important;">
				<label class="control-label">
					<spring:message code="request.creditCard.expirationDate" />:
				</label>
				<input id="expiryOld" name="expiry" class="form-control old" disabled="disabled" value="${creditCard.expirationMonth}/${creditCard.expirationYear}"/>
				<form:errors cssClass="error"/>	
			</div>
			
			<div class="form-group col-sm-6" style="padding-right:0 !important;">
				<form:label class="control-label" path="creditCard.cvvCode">
					<spring:message code="request.creditCard.cvvCode" />:
				</form:label>
				<input id="cvvCodeOld" name="creditCard.cvvCode" class="form-control old" disabled="disabled" value="${creditCard.cvvCode}" />
				<form:errors cssClass="error" path="creditCard.cvvCode" />	
			</div>
		</div>
      </div>
    </div>
    </jstl:if>
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4 class="panel-title">
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse2"><i class="fas fa-plus-circle"></i> <spring:message code="request.creditCard.new"/> </a>
        </h4>
      </div>
      <div id="collapse2" class="panel-collapse collapse">
        <div class="panel-body">
			<div class="newcreditCardContainer"></div>
        	<div class="form-group">
			<form:label class="control-label" path="creditCard.holderName">
				<spring:message code="request.creditCard.holderName" />:
			</form:label>
			<input id="holderName" name="creditCard.holderName" class="form-control" value="${creditCard.holderName}" />
			<form:errors cssClass="error" path="creditCard.holderName" />	
			</div>
			
			<div class="form-group col-sm-9" style="padding-left:0 !important;">
				<form:label class="control-label" path="creditCard.number">
					<spring:message code="request.creditCard.number" />:
				</form:label>
				<input id="number" name="creditCard.number" class="form-control" value="${creditCard.number}" />
				<form:errors cssClass="error" path="creditCard.number" />	
			</div>
			
			<div class="form-group col-sm-3" style="padding-right:0 !important;">
				<form:label class="control-label" path="creditCard.brandName">
					<spring:message code="request.creditCard.brandName" />:
				</form:label>
				<input class="form-control" id="brandName" name="creditCard.brandName" readonly="readonly" value="${creditCard.brandName}"/>
				<form:errors cssClass="error" path="creditCard.brandName" />	
			</div>
			
			<div class="form-group col-sm-6" style="padding-left:0 !important;">
				<label class="control-label">
					<spring:message code="request.creditCard.expirationDate" />:
				</label>
				<input id="expiry" name="expiry" class="form-control" value="${creditCard.expirationMonth}/${creditCard.expirationYear}"/>
				<form:errors cssClass="error"/>	
			</div>
			
			<div class="form-group col-sm-6" style="padding-right:0 !important;">
				<form:label class="control-label" path="creditCard.cvvCode">
					<spring:message code="request.creditCard.cvvCode" />:
				</form:label>
				<input id="cvvCode" name="creditCard.cvvCode" class="form-control" value="${creditCard.cvvCode}" />
				<form:errors cssClass="error" path="creditCard.cvvCode" />	
			</div>
       	</div>
      </div>
    </div>
    </div>
	
	
	
	
	
	<div class="btn-group btn-group-justified">
		<div class="btn-group">
			<input class="btn btn-success" type="submit" id="saveButton" name="save" value="<spring:message code="request.save"/>" />
		</div>
	</div>

</form:form>
</div>
<script defer>
$(document).ready(function(){
	$('#rendezvousSelect').change(function(){
		$.get('user/request/zervices.do',{rendezvousId:$(this).val()},function(data){
			var selectedZer = $('#zerviceSelect').val();
			$('#zerviceSelect').find('option:not(.default)').remove();
			$.each(JSON.parse(data),function(i,zervice){
				$('#zerviceSelect').append('<option value="'+zervice['id']+'">'+zervice['name']+'</option>');
			});
			$('#zerviceSelect').val(selectedZer);
			$('#zerviceSelect').selectpicker('refresh');
		});
	});
	$('#zerviceSelect').change(function(){
		$.get('user/request/rendezvouses.do',{zerviceId:$(this).val()},function(data){
			var selectedRend = $('#rendezvousSelect').val();
			$('#rendezvousSelect').find('option:not(.default)').remove();
			$.each(JSON.parse(data),function(i,rendezvous){
				$('#rendezvousSelect').append('<option value="'+rendezvous['id']+'">'+rendezvous['name']+'</option>');
			});
			$('#rendezvousSelect').val(selectedRend);
			$('#rendezvousSelect').selectpicker('refresh');
		});
	});
	$('.creditCardContainer').card({
	    // a selector or DOM element for the container
	    // where you want the card to appear
	    container: '.creditCardContainer', // *required*
		
	    formSelectors: {
	        numberInput: 'input#numberOld', 
	        expiryInput: 'input#expiryOld', 
	        cvcInput: 'input#cvvCodeOld',
	        nameInput: 'input#holderNameOld'
	    },
	});
	$('#form').card({
	    // a selector or DOM element for the container
	    // where you want the card to appear
	    container: '.newcreditCardContainer', // *required*
		
	    formSelectors: {
	        numberInput: 'input#number', 
	        expiryInput: 'input#expiry', 
	        cvcInput: 'input#cvvCode',
	        nameInput: 'input#holderName'
	    },
	});
	$('#number').keyup(function(){
		$('#brandName').val($.payment.cardType($('#number').val()));
	});
	$('#form').submit(function(e){
		e.preventDefault();
		var renSelect = $('#rendezvousSelect');
		var zerSelect = $('#zerviceSelect');
		$('#rendezvousError').hide();
		$('#zerviceError').hide();
		$('#paymentMethodError').hide();
		renSelect.parent().parent().removeClass('has-error');
		zerSelect.parent().parent().removeClass('has-error');
		var submitting = true;
		$('#numberAlert').hide();
		$('#dateAlert').hide();
		if(renSelect.val() === null){
			renSelect.parent().parent().addClass('has-error');	
			$('#rendezvousError').show();
			submitting = false;
		}
		if(zerSelect.val() === null){
			zerSelect.parent().parent().addClass('has-error');
			$('#zerviceError').show();
			submitting = false;
		}
		if(!$('#collapse2').hasClass('in') && !$('#collapse1').hasClass('in')){
			$('#paymentMethodError').show();
			submitting = false;
		}
		
		if($('#collapse2').hasClass('in')){
			var date = $("#expiry").val().split("/");
			$('#expirationMonth').val(date[0]);
			$('#expirationYear').val("20" + date[1]);
			if(!$.payment.validateCardNumber($('#number').val())){
				$('#numberAlert').show();
				submitting = false;
			}
			if(!$.payment.validateCardExpiry(date[0],date[1])){
				$('#dateAlert').show();
				submitting = false;
			}
		}
		if(submitting){
			if($('#collapse2').hasClass('in')){
				var noSpaceNumber = $('#number').val().replace(/ /g,"");
				$('#number').val(noSpaceNumber);
				$.post('user/request/save.do',$('#form').serialize(), function(data){
					if(data === '1'){
						notify('success','<spring:message code="request.create.success"/>');
						$('#requestModal').modal('hide');
					}
					else notify('danger','<spring:message code="request.commit.error"/>');
				});
			}else{
				$.post('user/request/save.do',$('#form').remove('#collapse2').serialize(), function(data){
					if(data === '1'){
						notify('success','<spring:message code="request.create.success"/>');
						$('#requestModal').modal('hide');
					}
					else notify('danger','<spring:message code="request.commit.error"/>');
				});
			}
		}else{
			$('.modal-body').scrollTop(0);
		}
	});
});
</script>
<jstl:if test="${request.creditCard ne null}">
<script defer>
	$(function(){
		var number = '<jstl:out value="**** **** **** ${fn:substring(request.creditCard.number,fn:length(request.creditCard.number)-4,fn:length(request.creditCard.number))}" />' ;
		var name = '<jstl:out value="${request.creditCard.holderName}" />' ;
		var date = '<jstl:out value="${request.creditCard.expirationMonth}/${fn:substring(request.creditCard.expirationYear,2,4)}" />' ;
		$('#numberOld').val(number);
		$('#holderNameOld').val(name);
		$('#expiryOld').val(date);
		$('#brandNameOld').val($.payment.cardType('<jstl:out value="${request.creditCard.number}" />'));
		$(".creditCardContainer").find(".jp-card").addClass("jp-card-" + $.payment.cardType('<jstl:out value="${request.creditCard.number}" />')).addClass("jp-card-identified");
		$(".creditCardContainer").find(".jp-card-number").addClass("jp-card-display").addClass("jp-card-focused").addClass("jp-card-valid").html(number);
		$(".creditCardContainer").find(".jp-card-expiry").addClass("jp-card-display").addClass("jp-card-focused").addClass("jp-card-valid").html(date);
		$(".creditCardContainer").find(".jp-card-name").addClass("jp-card-display").addClass("jp-card-focused").addClass("jp-card-valid").html(name);
	});
</script>
</jstl:if>

