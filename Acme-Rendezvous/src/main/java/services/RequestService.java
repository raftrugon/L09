
package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.RequestRepository;
import domain.Rendezvous;
import domain.Request;

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
	private RendezvousService				rendezvousService;


	// Simple CRUD methods ----------------------------------------------------

	public Request create(int rendezvousId) {
		Request res = new Request();
		Rendezvous rendezvous = rendezvousService.findOne(rendezvousId);
		Assert.isTrue(rendezvous.getUser().equals(userService.findByPrincipal()));
		res.setRendezvous(rendezvous);
		return res;
	}

	public Request findOne(final int requestId) {
		Assert.isTrue(requestId != 0);
		Request res = this.requestRepository.findOne(requestId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Request> findAll() {
		return requestRepository.findAll();
	}


	public Request save(final Request request) {
		Assert.notNull(request);
		Assert.isTrue(request.getRendezvous().equals(userService.findByPrincipal()));
		Assert.isTrue(!request.getZervice().getInappropriate());
		return requestRepository.save(request);
	}


	//Other Business Methods --------------------------------

}
