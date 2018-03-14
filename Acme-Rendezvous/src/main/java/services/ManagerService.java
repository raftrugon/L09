
package services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Manager;
import domain.Zervice;

@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerRepository	managerRepository;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private Validator			validator;


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

	public void flush() {
		managerRepository.flush();
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

		if (manager.getId() == 0) {
			Md5PasswordEncoder password = new Md5PasswordEncoder();
			String encodedPassword = password.encodePassword(manager.getUserAccount().getPassword(), null);
			manager.getUserAccount().setPassword(encodedPassword);
			manager.setUserAccount(userAccountService.save(manager.getUserAccount()));
		}
		return managerRepository.save(manager);
	}

	public void delete(final Manager manager) {
		Assert.notNull(manager);
		managerRepository.delete(manager);

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

	public Manager reconstruct(Manager manager, final BindingResult binding) {

		manager.setId(0);
		manager.setVersion(0);

		//Collections
		manager.setZervices(new ArrayList<Zervice>());

		//Authority
		Collection<Authority> authorities = manager.getUserAccount().getAuthorities();
		Authority authority = new Authority();

		authority.setAuthority(Authority.MANAGER);
		authorities.add(authority);
		manager.getUserAccount().setAuthorities(authorities);

		validator.validate(manager, binding);

		return manager;
	}

	public Collection<Manager> getManagersWhoProvideMoreServicesThanAvg() {
		return managerRepository.getManagersWhoProvideMoreServicesThanAvg();
	}

	public Map<Manager, Integer> getManagersWithMoreCancelledZervices(int limit) {
		List<Object[]> query = managerRepository.getManagersWithMoreCancelledZervices(limit);
		Map<Manager, Integer> res = new LinkedHashMap<Manager, Integer>();
		for (Object[] obj : query)
			res.put(findOne(((Integer) obj[0]).intValue()), ((BigDecimal) obj[1]).intValueExact());
		return res;
	}

}
