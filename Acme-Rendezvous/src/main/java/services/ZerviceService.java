
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

import repositories.ZerviceRepository;
import domain.Announcement;
import domain.Comment;
import domain.Request;
import domain.Zervice;
import domain.Rsvp;
import domain.User;

@Service
@Transactional
public class ZerviceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ZerviceRepository	zerviceRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private ManagerService				managerService;
	@Autowired
	private AdminService			adminService;


	// Simple CRUD methods ----------------------------------------------------

	public Zervice create() {
		Zervice res = new Zervice();
		res.setManager(managerService.findByPrincipal());
		res.setInappropriate(false);
		res.setRequests(new ArrayList<Request>());
		return res;
	}

	public Zervice findOne(final int zerviceId) {
		Assert.isTrue(zerviceId != 0);
		Zervice res = this.zerviceRepository.findOne(zerviceId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Zervice> findAll() {
		return zerviceRepository.findAll();
	}


	public Zervice save(final Zervice zervice) {
		Assert.notNull(zervice);
		Assert.isTrue(zervice.getManager().equals(this.managerService.findByPrincipal()));
		return zerviceRepository.save(zervice);
	}

	public Zervice deleteByAdmin(final int zerviceId) {
		Assert.isTrue(zerviceId != 0);
		Zervice zervice = findOne(zerviceId);
		Assert.notNull(zervice);
		Assert.notNull(this.adminService.findByPrincipal());
		zervice.setInappropriate(true);
		zervice.setPicture(null);
		return zerviceRepository.save(zervice);
	}

	public void deleteByUser(final int zerviceId) {
		Assert.isTrue(zerviceId != 0);
		Zervice zervice = this.findOne(zerviceId);
		Assert.notNull(zervice);
		Assert.isTrue(zervice.getManager().equals(managerService.findByPrincipal()));
		Assert.isTrue(zervice.getRequests().isEmpty());
		zerviceRepository.delete(zervice);
	}

	public Zervice reconstructNew(Zervice zervice, BindingResult binding) {
		zervice.setId(0);
		zervice.setVersion(0);
		zervice.setInappropriate(false);
		zervice.setManager(managerService.findByPrincipal());
		zervice.setRequests(new ArrayList<Request>());
		return zervice;
	}

	public Zervice reconstruct(Zervice zervice, BindingResult binding) {
		zervice.setInappropriate(false);
		zervice.setManager(managerService.findByPrincipal());
		zervice.setRequests(findOne(zervice.getId()).getRequests());
		return zervice;
	}

	//Other Business Methods --------------------------------


}
