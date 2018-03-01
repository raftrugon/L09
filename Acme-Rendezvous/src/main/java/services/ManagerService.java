package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import domain.Rendezvous;
import domain.Rsvp;
import domain.Manager;
import domain.User;
import domain.Zervice;


import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;


@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private Validator validator;

	// Simple CRUD methods ----------------------------------------------------

	public Manager create() {
		Manager res = new Manager();
		
		//Collections
		res.setZervices(new ArrayList<Zervice>());
		
		//UserAccount
		UserAccount managerAccount = new UserAccount();
		Collection<Authority> authorities = managerAccount.getAuthorities();
		Authority authority = new Authority();

		authority.setAuthority(Authority.MANAGER);
		authorities.add(authority);
		managerAccount.setAuthorities(authorities);

		res.setUserAccount(managerAccount);

		return res;
	}

	public Manager findOne(final int managerId) {
		Assert.isTrue(managerId != 0);
		Manager res = managerRepository.findOne(managerId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Manager> findAll() {
		Collection<Manager> res = managerRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager);
		
		if(manager.getId() == 0){
			Md5PasswordEncoder password = new Md5PasswordEncoder();
			String encodedPassword = password.encodePassword(manager.getUserAccount().getPassword(), null);
			manager.getUserAccount().setPassword(encodedPassword);
			manager.setUserAccount(userAccountService.save(manager.getUserAccount()));
		}
		return this.managerRepository.save(manager);
	}

	public void delete(final Manager manager) {
		this.managerRepository.delete(manager);

	}
	
	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager res;
		res = managerRepository.findByUserAccount(userAccount.getId());
		return res;
	}

	public Manager findByPrincipal() {
		Manager res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		return res;
	}
	
}
