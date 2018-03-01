
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
	private UserService			userService;
	@Autowired
	private RendezvousService			rendezvousService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private Validator	validator;
	
	

	// Supporting services ----------------------------------------------------


	// Simple CRUD methods ----------------------------------------------------

	public Announcement create(int rendezvousId) {
		Announcement res = new Announcement();
		Rendezvous r = rendezvousService.findOne(rendezvousId);
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Assert.isTrue(u == r.getUser());
		res.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		res.setRendezvous(r);
		res.setinappropriate(false);
		return res;
	}

	public Announcement findOne(int announcementId) {
		Assert.isTrue(announcementId != 0);
		Announcement res = announcementRepository.findOne(announcementId);
		Assert.notNull(res);
		return res;
	}
	


	public Announcement save(Announcement announcement) {
		Assert.notNull(announcement);
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		
		announcement.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		Announcement res = announcementRepository.save(announcement);
		res.getRendezvous().getAnnouncements().add(res);
		return res;
	}

	public Announcement deleteByAdmin(final Announcement announcement) {
		Assert.notNull(announcement);
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		announcement.setinappropriate(true);
		return announcementRepository.save(announcement);

	}
	
	public Announcement reconstructNew(final Announcement announcement, final BindingResult binding){
		announcement.setId(0);
		announcement.setVersion(0);
		announcement.setCreationMoment(new Date(System.currentTimeMillis()-1000));
		announcement.setinappropriate(false);
		validator.validate(announcement,binding);
		return announcement;
	}
	//Other Business Methods --------------------------------
	

	public Collection<Announcement> findAllOrdered() {
		return announcementRepository.findAllOrdered();
	}
	
	public Collection<Announcement> getRSVPAnnouncementsForUser(User user) {
		return announcementRepository.getRSVPAnnouncementsForUser(user);
	}
	
	public Collection<Announcement> getMyAnnouncements(User user) {
		return announcementRepository.getMyAnnouncements(user);
	}
	
	public Collection<Announcement> getRendezvousAnnouncementsSorted(int rendezvousId){
		return announcementRepository.getRendezvousAnnouncementsSorted(rendezvousId);
	}
	
	public Collection<Announcement> findAllOrderedNotInappropriate(){
		return announcementRepository.findAllOrderedNotInappropriate();
	}

	public Collection<Announcement> getRSVPAnnouncementsForUserNotInappropriate(User user){
		return announcementRepository.getRSVPAnnouncementsForUserNotInappropriate(user);
	}

	public Collection<Announcement> getMyAnnouncementsNotInappropriate(User user){
		return announcementRepository.getMyAnnouncementsNotInappropriate(user);
	}

	public Collection<Announcement> getRendezvousAnnouncementsSortedNotInappropriate(int rendezvousId){
		return announcementRepository.getRendezvousAnnouncementsSortedNotInappropriate(rendezvousId);
	}

	
}
