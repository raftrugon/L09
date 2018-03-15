<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags/myTagLib" %>


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
		<option selected="selected" disabled="disabled">--- <spring:message code="request.selectARendezvous"/> ---</option>
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
		<option selected="selected" disabled="disabled">--- <spring:message code="request.selectAZervice"/> ---</option>
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
	
	<div class="creditCardContainer"></div>
	
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
	
	<div class="btn-group btn-group-justified">
		<div class="btn-group">
			<input class="btn btn-success" type="submit" id="saveButton" name="save" value="<spring:message code="request.save"/>" />
		</div>
	</div>

</form:form>
</div>
<script>
$(document).ready(function(){
	$('#form').card({
	    // a selector or DOM element for the container
	    // where you want the card to appear
	    container: '.creditCardContainer', // *required*
		
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
		renSelect.parent().parent().removeClass('has-error');
		zerSelect.parent().parent().removeClass('has-error');
		var submitting = true;
		$('#numberAlert').hide();
		$('#dateAlert').hide();
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
		
		if(submitting){
			var noSpaceNumber = $('#number').val().replace(/ /g,"");
			$('#number').val(noSpaceNumber);
			$.post('user/request/save.do',$('#form').serialize(), function(data){
				if(data === '0') notify('danger','binding');
				else if(data === '1'){
					notify('success','<spring:message code="request.create.success"/>');
					$('#requestModal').modal('hide');
				}
				else notify('danger','commit');
			});
		}
	});
});
</script>

