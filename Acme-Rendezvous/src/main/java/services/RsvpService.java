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
	private RsvpRepository rsvpRepository;
	@Autowired
	private RendezvousService rendezvousService;
	@Autowired
	private UserService userService;

	// Supporting services ----------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	public Rsvp create(final int rendezvousId) {
		Rsvp res = new Rsvp();
		User u = this.userService.findByPrincipal();
		Assert.notNull(u);
		Rendezvous r = this.rendezvousService.findOne(rendezvousId);
		Assert.notNull(r);
		Assert.isTrue(!this.userService.isRsvpd(rendezvousId));
		Assert.isTrue(!r.getDeleted());
		Assert.isTrue(!r.getinappropriate());
		Assert.isTrue(r.getOrganisationMoment().after(new Date()));
		Assert.isTrue(!r.getAdultOnly() || this.userService.isAdult());
		res.setUser(u);
		res.setRendezvous(r);
		Map<String, String> questions = new HashMap<String, String>();
		if (!(r.getQuestions().isEmpty()))
			for (String question : r.getQuestions())
				questions.put(question, "");

		res.setQuestionsAndAnswers(questions);
		return res;
	}

	public Rsvp findOne(final int rsvpId) {
		Assert.isTrue(rsvpId != 0);
		Rsvp res = this.rsvpRepository.findOne(rsvpId);
		Assert.notNull(res);
		return res;
	}

	public Rsvp save(final Rsvp rsvp) {

		User u = this.userService.findByPrincipal();
		Assert.notNull(u);
		Rendezvous r = rsvp.getRendezvous();
		if(rsvp.getId()==0)
			Assert.isTrue(!this.userService.isRsvpd(r.getId()));
		Assert.isTrue(!r.getDeleted());
		Assert.isTrue(!r.getinappropriate());
		Assert.isTrue(r.getOrganisationMoment().after(new Date()));
		Assert.isTrue(!r.getAdultOnly() || this.userService.isAdult());
		Assert.isTrue(rsvp.getUser().equals(u));

		// Quitado de preguntas con respuestas vacías, con espacios o nulas
		for (Entry<String, String> entry : rsvp.getQuestionsAndAnswers()
			.entrySet())
			if (entry.getValue() != null) {
				if (entry.getValue().replace(" ", "").isEmpty())
					rsvp.getQuestionsAndAnswers().remove(entry.getKey());
			} else
				rsvp.getQuestionsAndAnswers().remove(entry.getKey());

		// Encoding issue with Â character patch
		Map<String, String> auxMap = new HashMap<String, String>();
		for (Entry<String, String> entry : rsvp.getQuestionsAndAnswers()
			.entrySet())
			auxMap.put(entry.getKey().replace("Â", ""), entry.getValue());
		rsvp.setQuestionsAndAnswers(auxMap);
		return this.rsvpRepository.save(rsvp);
	}

	public void delete(final int rendezvousId) {
		User u = this.userService.findByPrincipal();
		Rsvp rsvp = this.rsvpRepository.findForUserAndRendezvous(rendezvousId,
			u);
		Assert.notNull(rsvp);
		this.rsvpRepository.delete(rsvp);
	}

	// Other Business Methods --------------------------------

	public Collection<String> getPendingQuestions(final Rsvp rsvp) {
		Assert.notNull(rsvp);
		Collection<String> res = new ArrayList<String>(rsvp.getRendezvous()
			.getQuestions());
		res.removeAll(rsvp.getQuestionsAndAnswers().keySet());
		return res;
	}

	public Rsvp rsvpForRendezvousCreator(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		User user = this.userService.findByPrincipal();
		Assert.notNull(user);

		Rsvp res = new Rsvp();
		res.setQuestionsAndAnswers(new HashMap<String, String>());

		//Relaciones hacia rsvp
		res.setRendezvous(rendezvous);
		res.setUser(user);

		Rsvp saved = this.rsvpRepository.save(res);

		//Relaciones desde rsvp
		user.getRsvps().add(saved);
		rendezvous.getRsvps().add(saved);

		return saved;
	}

}
