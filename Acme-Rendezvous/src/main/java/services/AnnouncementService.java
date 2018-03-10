
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AnnouncementRepository;
import domain.Admin;
import domain.Announcement;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnnouncementRepository			announcementRepository;
	@Autowired
	private UserService						userService;
	@Autowired
	private RendezvousService				rendezvousService;
	@Autowired
	private AdminService 					adminService;
	@Autowired
	private Validator						validator;



	// Supporting services ----------------------------------------------------


	// Simple CRUD methods ----------------------------------------------------

	public Announcement create(final int rendezvousId) {
		Announcement res = new Announcement();
		Rendezvous r = this.rendezvousService.findOne(rendezvousId);
		User u = this.userService.findByPrincipal();
		Assert.notNull(u);
		Assert.isTrue(u == r.getUser());
		res.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		res.setRendezvous(r);
		res.setinappropriate(false);
		return res;
	}

	public Announcement findOne(final int announcementId) {
		Assert.isTrue(announcementId != 0);
		Announcement res = this.announcementRepository.findOne(announcementId);
		Assert.notNull(res);
		return res;
	}



	public Announcement save(final Announcement announcement) {
		Assert.notNull(announcement);
		User u = this.userService.findByPrincipal();
		Assert.notNull(u);
		Assert.isTrue(announcement.getId() == 0);

		announcement.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		Announcement res = this.announcementRepository.save(announcement);
		res.getRendezvous().getAnnouncements().add(res);
		return res;
	}

	public Announcement deleteByAdmin(final Announcement announcement) {
		Assert.notNull(announcement);
		Admin a = this.adminService.findByPrincipal();
		Assert.notNull(a);
		announcement.setinappropriate(true);
		return this.announcementRepository.save(announcement);

	}

	public Announcement reconstructNew(final Announcement announcement, final BindingResult binding){
		announcement.setId(0);
		announcement.setVersion(0);
		announcement.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		announcement.setinappropriate(false);
		this.validator.validate(announcement,binding);
		return announcement;
	}
	//Other Business Methods --------------------------------


	public Collection<Announcement> findAllOrdered() {
		return this.announcementRepository.findAllOrdered();
	}

	public Collection<Announcement> getRSVPAnnouncementsForUser(final User user) {
		return this.announcementRepository.getRSVPAnnouncementsForUser(user);
	}

	public Collection<Announcement> getMyAnnouncements(final User user) {
		return this.announcementRepository.getMyAnnouncements(user);
	}

	public Collection<Announcement> getRendezvousAnnouncementsSorted(final int rendezvousId){
		return this.announcementRepository.getRendezvousAnnouncementsSorted(rendezvousId);
	}

	public Collection<Announcement> findAllOrderedNotInappropriate(){
		return this.announcementRepository.findAllOrderedNotInappropriate();
	}

	public Collection<Announcement> getRSVPAnnouncementsForUserNotInappropriate(final User user){
		return this.announcementRepository.getRSVPAnnouncementsForUserNotInappropriate(user);
	}

	public Collection<Announcement> getMyAnnouncementsNotInappropriate(final User user){
		return this.announcementRepository.getMyAnnouncementsNotInappropriate(user);
	}

	public Collection<Announcement> getRendezvousAnnouncementsSortedNotInappropriate(final int rendezvousId){
		return this.announcementRepository.getRendezvousAnnouncementsSortedNotInappropriate(rendezvousId);
	}


}
