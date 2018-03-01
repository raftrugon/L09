
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RsvpRepository;
import domain.Rendezvous;
import domain.Rsvp;
import domain.User;

@Service
@Transactional
public class RsvpService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RsvpRepository			rsvpRepository;
	@Autowired
	private RendezvousService		rendezvousService;
	@Autowired
	private UserService				userService;

	// Supporting services ----------------------------------------------------


	// Simple CRUD methods ----------------------------------------------------

	public Rsvp create(int rendezvousId) {
		Rsvp res = new Rsvp();
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Rendezvous r = rendezvousService.findOne(rendezvousId);
		Assert.notNull(r);
		Assert.isTrue(!userService.isRsvpd(rendezvousId));
		Assert.isTrue(!r.getDeleted());
		Assert.isTrue(!r.getinappropriate());
		Assert.isTrue(r.getOrganisationMoment().after(new Date()));
		Assert.isTrue(!r.getAdultOnly() || userService.isAdult());
		res.setUser(u);
		res.setRendezvous(r);
		Map<String,String> questions = new HashMap<String,String>();
		if(!(r.getQuestions().isEmpty())) {
			for(String question: r.getQuestions()){
				questions.put(question, "");
			}
		}
		res.setQuestionsAndAnswers(questions);
		return res;
	}

	public Rsvp findOne(int rsvpId) {
		Assert.isTrue(rsvpId != 0);
		Rsvp res = rsvpRepository.findOne(rsvpId);
		Assert.notNull(res);
		return res;
	}

	public Rsvp save(Rsvp rsvp) {
		Assert.isTrue(rsvp.getUser().equals(userService.findByPrincipal()));
		if(rsvp.getId() == 0)
			Assert.isTrue(!userService.isRsvpd(rsvp.getRendezvous().getId()));
		Map<String,String> auxMap = new HashMap<String,String>();
		//Encoding issue with Â character patch
		for(Entry<String,String> entry: rsvp.getQuestionsAndAnswers().entrySet()){
			auxMap.put(entry.getKey().replace("Â", ""), entry.getValue());
		}
		rsvp.setQuestionsAndAnswers(auxMap);
		return rsvpRepository.save(rsvp);
	}

	public void delete(final int rendezvousId) {
		User u = userService.findByPrincipal();
		Rsvp rsvp = rsvpRepository.findForUserAndRendezvous(rendezvousId,u);
		Assert.notNull(rsvp);
		rsvpRepository.delete(rsvp);
	}

	//Other Business Methods --------------------------------
	
	public Collection<String> getPendingQuestions(Rsvp rsvp){
		Assert.notNull(rsvp);
		Collection<String> res = new ArrayList<String>(rsvp.getRendezvous().getQuestions());
		res.removeAll(rsvp.getQuestionsAndAnswers().keySet());	
		return res;
	}
	
	public Rsvp rsvpForRendezvousCreator(Rendezvous rendezvous){
		Assert.notNull(rendezvous);
		Rsvp res = new Rsvp();
		res.setRendezvous(rendezvous);
		res.setUser(userService.findByPrincipal());
		res.setQuestionsAndAnswers(new HashMap<String,String>());
		return rsvpRepository.save(res);
	}
	
}
