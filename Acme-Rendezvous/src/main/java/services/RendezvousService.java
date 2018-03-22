
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
import utilities.internal.SchemaPrinter;
import domain.Announcement;
import domain.Category;
import domain.Comment;
import domain.Rendezvous;
import domain.Request;
import domain.Rsvp;
import domain.User;
import domain.Zervice;

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
	@Autowired
	private CategoryService			categoryService;


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
		res.setRequests(new ArrayList<Request>());
		res.setOrganisationMoment(new Date(System.currentTimeMillis()+180000));

		return res;
	}

	public Rendezvous findOne(final int rendezvousId) {
		Assert.isTrue(rendezvousId != 0);
		Rendezvous res = this.rendezvousRepository.findOne(rendezvousId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Rendezvous> findAll() {
		return this.rendezvousRepository.findAll();
	}

	public Collection<Rendezvous> findAllUnder18() {
		return this.rendezvousRepository.findAllUnder18();
	}

	public Rendezvous save(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getUser().equals(this.userService.findByPrincipal()));
		Assert.isTrue(rendezvous.getOrganisationMoment().after(new Date()));
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
			this.rsvpService.rsvpForRendezvousCreator(saved);
		}
		return saved;
	}

	public void flush(){
		this.rendezvousRepository.flush();
	}

	public Rendezvous deleteByAdmin(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getId() != 0);
		Assert.notNull(this.adminService.findByPrincipal());
		rendezvous.setinappropriate(true);
		rendezvous.setPicture(null);
		return this.rendezvousRepository.save(rendezvous);
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

	public void link(final int sourceId, final int targetId){
		Rendezvous source = this.findOne(sourceId);
		Rendezvous target = this.findOne(targetId);
		Assert.isTrue(source.getUser().equals(this.userService.findByPrincipal()));
		source.getRendezvouses().add(target);
	}

	public Collection<Rendezvous> getRendezvousesToLink(final int rendezvousId) {
		User u = this.userService.findByPrincipal();
		Assert.notNull(u);
		Rendezvous r = this.findOne(rendezvousId);
		return this.rendezvousRepository.getRendezvousesToLink(u,r);
	}

	public Collection<Rendezvous> getRendezvousesToLink() {
		return this.rendezvousRepository.getRendezvousesToLink();
	}

	public Rendezvous reconstructNew(final Rendezvous res, final BindingResult binding){
		res.setId(0);
		res.setVersion(0);
		res.setDeleted(false);
		res.setAnnouncements(new ArrayList<Announcement>());
		res.setComments(new ArrayList<Comment>());
		res.setRsvps(new ArrayList<Rsvp>());
		res.setinappropriate(false);
		res.setUser(this.userService.findByPrincipal());
		res.setRequests(new ArrayList<Request>());

		if(res.getRendezvouses() == null)
			res.setRendezvouses(new ArrayList<Rendezvous>());


		if(!this.userService.isAdult())
			res.setAdultOnly(false);
		this.validator.validate(res, binding);

		return res;
	}

	public Rendezvous reconstruct(final Rendezvous res, final BindingResult binding) {
		Rendezvous rendezvous = this.findOne(res.getId());
		Rendezvous copy = new Rendezvous();

		copy.setName(res.getName());
		copy.setDescription(res.getDescription());
		copy.setOrganisationMoment(res.getOrganisationMoment());
		copy.setPicture(res.getPicture());
		copy.setLatitude(res.getLatitude());
		copy.setLongitude(res.getLongitude());
		copy.setFinalMode(res.getFinalMode());
		copy.setDeleted(false);
		if(!this.userService.isAdult())
			copy.setAdultOnly(false);
		else
			copy.setAdultOnly(true);
		copy.setQuestions(res.getQuestions());
		copy.setinappropriate(rendezvous.getinappropriate());

		copy.setUser(rendezvous.getUser());
		copy.setComments(rendezvous.getComments());
		copy.setAnnouncements(rendezvous.getAnnouncements());
		copy.setRendezvouses(rendezvous.getRendezvouses());
		copy.setRequests(rendezvous.getRequests());
		copy.setRsvps(rendezvous.getRsvps());

		copy.setVersion(res.getVersion());
		copy.setId(res.getId());

		this.validator.validate(copy, binding);
		return copy;
	}


	public void deleteLink(final int rendezvousId, final int linkId) {
		Rendezvous r = this.rendezvousRepository.findOne(rendezvousId);
		Rendezvous link = this.rendezvousRepository.findOne(linkId);
		List<Rendezvous> rendezvouses = new ArrayList<Rendezvous>(r.getRendezvouses());
		rendezvouses.remove(link);
		r.setRendezvouses(rendezvouses);

	}

	//Rendezvous list with filters

	public Collection<Rendezvous> listRendezvouses(Integer type, final Collection<Category> categories){
		Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
		User u = this.userService.findByPrincipal();
		if(type==null)type=0;
		switch(type){
		case 1:
			rendezvouses.addAll(u.getRendezvouses());
			break;
		case 2:
			rendezvouses.addAll(this.rendezvousRepository.getRSVPRendezvousesForUser(u));
			break;
		case 3:
			rendezvouses.addAll(this.rendezvousRepository.getExploreRendezvouses());
			rendezvouses.removeAll(this.rendezvousRepository.getRSVPRendezvousesForUser(u));
			SchemaPrinter.print(this.rendezvousRepository.getExploreRendezvouses());
			SchemaPrinter.print(this.rendezvousRepository.getRSVPRendezvousesForUser(u));
			SchemaPrinter.print(rendezvouses);
			break;
		default:
			rendezvouses.addAll(this.rendezvousRepository.findAll());
		}
		if(!this.userService.isAdult()) rendezvouses.removeAll(this.rendezvousRepository.findAllOver18());
		if(categories != null && !categories.isEmpty()){
			Collection<Category> categoriesAndNested = this.categoryService.getSelectedTree(categories);
			rendezvouses.retainAll(this.rendezvousRepository.getRendezvousForCategories(categoriesAndNested));
		}
		return rendezvouses;
	}

	public Collection<Rendezvous> listRendezvousesAnonymous(final Collection<Category> categories) {
		Collection<Rendezvous> rendezvouses = this.rendezvousRepository.findAllUnder18();
		if(categories != null && !categories.isEmpty()){
			Collection<Category> categoriesAndNested = this.categoryService.getSelectedTree(categories);
			rendezvouses.retainAll(this.rendezvousRepository.getRendezvousForCategories(categoriesAndNested));
		}
		return rendezvouses;
	}

	public Double getAvgCategoriesPerRendezvous(){
		return this.rendezvousRepository.getAvgCategoriesPerRendezvous();
	}

	public Collection<Zervice> getZervicesForRendezvous(final int rendezvousId) {
		Rendezvous r = this.findOne(rendezvousId);
		return this.rendezvousRepository.getServicesForRendezvous(r);
	}
}
