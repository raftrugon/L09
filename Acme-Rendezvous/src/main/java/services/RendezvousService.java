
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RendezvousRepository;
import domain.Announcement;
import domain.Comment;
import domain.Rendezvous;
import domain.Rsvp;
import domain.User;

@Service
@Transactional
public class RendezvousService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RendezvousRepository	rendezvousRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService				userService;
	@Autowired
	private AdminService			adminService;
	@Autowired
	private Validator				validator;
	@Autowired
	private RsvpService				rsvpService;


	// Simple CRUD methods ----------------------------------------------------

	public Rendezvous create() {
		Rendezvous res = new Rendezvous();
		User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		res.setUser(user);
		res.setAdultOnly(false);
		res.setFinalMode(false);
		res.setDeleted(false);
		res.setQuestions(new ArrayList<String>());
		res.setRendezvouses(new ArrayList<Rendezvous>());
		res.setAnnouncements(new ArrayList<Announcement>());
		res.setComments(new ArrayList<Comment>());
		res.setRsvps(new ArrayList<Rsvp>());
		res.setinappropriate(false);
		return res;
	}

	public Rendezvous findOne(final int rendezvousId) {
		Assert.isTrue(rendezvousId != 0);
		Rendezvous res = this.rendezvousRepository.findOne(rendezvousId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Rendezvous> findAll() {
		return rendezvousRepository.findAll();
	}
	
	public Collection<Rendezvous> findAllUnder18() {
		return rendezvousRepository.findAllUnder18();
	}

	public Rendezvous save(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getUser().equals(this.userService.findByPrincipal()));

		//Checkeos contra base de datos
		if (rendezvous.getId() != 0) {
			Rendezvous bd = this.findOne(rendezvous.getId());
			Assert.isTrue(!bd.getFinalMode() && !bd.getDeleted());
			Assert.isTrue(bd.getUser().equals(this.userService.findByPrincipal()));
		}

		Rendezvous saved = this.rendezvousRepository.save(rendezvous);

		//Añadir rendezvous al user si es nuevo
		//RSVP automático para el creador
		if (rendezvous.getId() == 0){
			rendezvous.getUser().getRendezvouses().add(saved);
			rsvpService.rsvpForRendezvousCreator(saved);
		}
		return saved;
	}

	public Rendezvous deleteByAdmin(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getId() != 0);
		Assert.notNull(this.adminService.findByPrincipal());
		rendezvous.setinappropriate(true);
		rendezvous.setPicture(null);
		return rendezvousRepository.save(rendezvous);
	}

	public Rendezvous deleteByUser(final int rendezvousId) {
		Rendezvous rendezvous = this.findOne(rendezvousId);
		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getUser().equals(this.userService.findByPrincipal()));
		Assert.isTrue(!rendezvous.getFinalMode());
		Assert.isTrue(rendezvous.getOrganisationMoment().after(new Date()));
		rendezvous.setDeleted(true);
		return this.rendezvousRepository.save(rendezvous);
	}

	//Other Business Methods --------------------------------

	public Collection<Rendezvous> getRSVPRendezvousesForUser(final User user) {
		return this.rendezvousRepository.getRSVPRendezvousesForUser(user);
	}

	public Double[] getRendezvousStats() {
		return this.rendezvousRepository.getRendezvousStats();
	}

	public Double getRatioOfUsersWhoHaveCreatedRendezvouses() {
		return this.rendezvousRepository.getRatioOfUsersWhoHaveCreatedRendezvouses();
	}

	public Double[] getRendezvousUserStats() {
		return this.rendezvousRepository.getRendezvousUserStats();
	}


	public Double[] getUserRendezvousesStats() {
		return this.rendezvousRepository.getUserRendezvousesStats();
	}

	public Collection<Rendezvous> getTop10RendezvousByRSVPs() {
		return this.rendezvousRepository.getTop10RendezvousByRSVPs();
	}

	public Double[] getRendezvousAnnouncementStats() {
		return this.rendezvousRepository.getRendezvousAnnouncementStats();
	}

	public Collection<Rendezvous> getRendezvousesWithNumberOfAnnouncementsOver75PerCentAvg() {
		return this.rendezvousRepository.getRendezvousesWithNumberOfAnnouncementsOver75PerCentAvg();
	}

	public Collection<Rendezvous> getRendezvousesLinkedToMoreThan10PerCentAVGNumberOfRendezvouses() {
		return this.rendezvousRepository.getRendezvousesLinkedToMoreThan10PerCentAVGNumberOfRendezvouses();
	}

	public Double[] getRendezvousQuestionStats() {
		return this.rendezvousRepository.getRendezvousQuestionStats();
	}

	public Double[] getAnswersToQuestionsStats() {
		return this.rendezvousRepository.getAnswersToQuestionsStats();
	}

	public void link(int sourceId, int targetId){
		Rendezvous source = findOne(sourceId);
		Rendezvous target = findOne(targetId);
		Assert.isTrue(source.getUser().equals(userService.findByPrincipal()));
		source.getRendezvouses().add(target);
	}
	
	public Collection<Rendezvous> getRendezvousesToLink(int rendezvousId) {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Rendezvous r = findOne(rendezvousId);
		return rendezvousRepository.getRendezvousesToLink(u,r);
	}
	
	public Collection<Rendezvous> getRendezvousesToLink() {
		return rendezvousRepository.getRendezvousesToLink();
	}
	
	public Rendezvous reconstructNew(Rendezvous res, BindingResult binding){
		res.setId(0);
		res.setVersion(0);
		res.setDeleted(false);
		res.setAnnouncements(new ArrayList<Announcement>());
		res.setComments(new ArrayList<Comment>());
		res.setRsvps(new ArrayList<Rsvp>());
		res.setinappropriate(false);
		res.setUser(userService.findByPrincipal());
		if(!userService.isAdult()){
			res.setAdultOnly(false);
		}
		this.validator.validate(res, binding);
		
		return res;
	}
	
	public Rendezvous reconstruct(Rendezvous res, BindingResult binding) {
		Rendezvous rendezvous = findOne(res.getId());
		res.setVersion(rendezvous.getVersion());
		res.setDeleted(false);
		res.setAnnouncements(rendezvous.getAnnouncements());
		res.setComments(rendezvous.getComments());
		res.setRsvps(rendezvous.getRsvps());
		res.setinappropriate(false);
		res.setUser(rendezvous.getUser());
		if(!userService.isAdult()){
			res.setAdultOnly(false);
		}
		this.validator.validate(res, binding);
		return res;
	}
	
	
	public void deleteLink(int rendezvousId, int linkId) {
		Rendezvous r = rendezvousRepository.findOne(rendezvousId);
		Rendezvous link = rendezvousRepository.findOne(linkId);
		List<Rendezvous> rendezvouses = new ArrayList<Rendezvous>(r.getRendezvouses());
		rendezvouses.remove(link);
		r.setRendezvouses(rendezvouses);
		
	}
}
