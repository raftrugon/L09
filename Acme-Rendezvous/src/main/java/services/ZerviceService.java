package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ZerviceRepository;
import domain.Manager;
import domain.Request;
import domain.Zervice;
import exceptions.ZerviceRequestsNotEmptyException;

@Service
@Transactional
public class ZerviceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ZerviceRepository zerviceRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private ManagerService managerService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private Validator validator;

	// Simple CRUD methods ----------------------------------------------------

	public Zervice create() {
		Zervice res = new Zervice();
		Manager m = managerService.findByPrincipal();
		Assert.notNull(m);
		res.setManager(managerService.findByPrincipal());
		res.setInappropriate(false);
		res.setRequests(new ArrayList<Request>());
		return res;
	}

	public Zervice findOne(final int zerviceId) {
		Assert.isTrue(zerviceId != 0);
		Zervice res = zerviceRepository.findOne(zerviceId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Zervice> findAll() {
		return zerviceRepository.findAll();
	}

	public Zervice save(final Zervice zervice) {
		Assert.notNull(zervice);
		Assert.isTrue(zervice.getManager().equals(
				managerService.findByPrincipal()));
		return zerviceRepository.save(zervice);
	}

	public Zervice deleteByAdmin(final int zerviceId) {
		Assert.isTrue(zerviceId != 0);
		Zervice zervice = findOne(zerviceId);
		Assert.notNull(zervice);
		Assert.notNull(adminService.findByPrincipal());
		zervice.setInappropriate(true);
		zervice.setPicture(null);
		return zerviceRepository.save(zervice);
	}

	public void deleteByManager(final int zerviceId) throws ZerviceRequestsNotEmptyException {
		Assert.isTrue(zerviceId != 0);
		Zervice zervice = findOne(zerviceId);
		Assert.notNull(zervice);
		Assert.isTrue(zervice.getManager().equals(
				managerService.findByPrincipal()));
		if(!zervice.getRequests().isEmpty())
			throw new ZerviceRequestsNotEmptyException();
		zerviceRepository.delete(zervice);
	}

	public Zervice reconstructNew(Zervice zervice, BindingResult binding) {
		zervice.setId(0);
		zervice.setVersion(0);
		zervice.setInappropriate(false);
		zervice.setManager(managerService.findByPrincipal());
		zervice.setRequests(new ArrayList<Request>());
		validator.validate(zervice, binding);
		return zervice;
	}

	public Zervice reconstruct(Zervice zervice, BindingResult binding) {
		Zervice bd = findOne(zervice.getId());
		Zervice copy = new Zervice();

		copy.setName(zervice.getName());
		copy.setDescription(zervice.getDescription());
		copy.setPicture(zervice.getPicture());
		copy.setInappropriate(bd.getInappropriate());
		copy.setPrice(zervice.getPrice());
		copy.setCategory(zervice.getCategory());

		copy.setManager(bd.getManager());
		copy.setRequests(bd.getRequests());

		copy.setVersion(bd.getVersion());
		copy.setId(zervice.getId());

		validator.validate(copy, binding);
		return copy;
	}

	public Collection<Zervice> findAllNotInappropriate() {
		return zerviceRepository.findAllNotInappropriate();
	}

	public void flush() {
		zerviceRepository.flush();
	}

	// Other Business Methods --------------------------------

	public Collection<Zervice> getBestSellingZervices(){
		return zerviceRepository.getBestSellingZervices();
	}

	public Double[] getZerviceAvgStdPerRendezvous(){
		return zerviceRepository.getZerviceAvgStdPerRendezvous();
	}

	public Double[] getZerviceMinMaxPerRendezvous(){
		return zerviceRepository.getZerviceMinMaxPerRendezvous();
	}

	public Page<Zervice> getTopSellingZervices(int pageNumber, int pageSize){
		PageRequest page = new PageRequest(pageNumber,pageSize);
		return zerviceRepository.findAllOrderedByRequestsSize(page);
	}
}
