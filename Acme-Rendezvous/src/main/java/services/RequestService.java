
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import domain.Rendezvous;
import domain.Request;
import domain.User;
import domain.Zervice;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequestRepository	requestRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserService			userService;
	@Autowired
	private RendezvousService	rendezvousService;
	@Autowired
	private Validator validator;


	// Simple CRUD methods ----------------------------------------------------

	public Request create() {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Request res = new Request();
		Request last = findLastForUser();
		if(last != null)
			res.setCreditCard(last.getCreditCard());
		return res;
	}

	public Request findOne(final int requestId) {
		Assert.isTrue(requestId != 0);
		Request res = requestRepository.findOne(requestId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Request> findAll() {
		return requestRepository.findAll();
	}

	public Request save(final Request request) {
		Assert.notNull(request);
		Rendezvous rendezvous = request.getRendezvous();
		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getUser().equals(userService.findByPrincipal()));
		Assert.isTrue(!request.getZervice().getInappropriate());
		Assert.isTrue(!rendezvous.getDeleted() && !rendezvous.getinappropriate() && rendezvous.getOrganisationMoment().after(new Date()));
		return requestRepository.save(request);
	}

	//Other Business Methods --------------------------------
	public void flush() {
		requestRepository.flush();
	}

	public Request findLastForUser(){
		return requestRepository.findLastForUser(userService.findByPrincipal().getId());
	}

	public Request reconstruct(Request request, BindingResult binding) {
		if(request.getCreditCard().getBrandName().trim().isEmpty() && request.getCreditCard().getHolderName().trim().isEmpty() && request.getCreditCard().getNumber().trim().isEmpty())
			request.setCreditCard(findLastForUser().getCreditCard());
		validator.validate(request,binding);
		return request;
	}

	public Collection<Rendezvous> selectRequestableRendezvousesForService(Zervice zervice){
		return requestRepository.selectRequestableRendezvousesForService(zervice,userService.findByPrincipal());
	}

	public Collection<Zervice> selectRequestableServicesForRendezvous(Rendezvous rendezvous){
		return requestRepository.selectRequestableServicesForRendezvous(rendezvous);
	}
}
