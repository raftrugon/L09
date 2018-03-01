
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Rendezvous;
import domain.Rsvp;
import domain.User;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserRepository	userRepository;
	@Autowired
	private UserAccountService userAccountService;


	// Supporting services ----------------------------------------------------

	@Autowired
	private Validator validator;

	// Simple CRUD methods ----------------------------------------------------

	public User create() {
		User res = new User();

		//Collections
		res.setRsvps(new ArrayList<Rsvp>());
		res.setRendezvouses(new ArrayList<Rendezvous>());

		//UserAccount
		UserAccount userAccount = new UserAccount();
		Collection<Authority> authorities = userAccount.getAuthorities();
		Authority authority = new Authority();

		authority.setAuthority(Authority.USER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		res.setUserAccount(userAccount);

		return res;
	}

	public User findOne(final int userId) {
		Assert.isTrue(userId != 0);
		User res = this.userRepository.findOne(userId);
		Assert.notNull(res);
		return res;
	}

	public Collection<User> findAll() {
		Collection<User> res = this.userRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public User save(final User user) {
		Assert.notNull(user);
		//Assert.isNull(LoginService.getPrincipal().getAuthorities());
		Assert.isTrue(user.getBirthDate().before(new Date()));
		
		if(user.getId() == 0){
			Md5PasswordEncoder password = new Md5PasswordEncoder();
			String encodedPassword = password.encodePassword(user.getUserAccount().getPassword(), null);
			user.getUserAccount().setPassword(encodedPassword);
			user.setUserAccount(userAccountService.save(user.getUserAccount()));
		}
		return this.userRepository.save(user);
	}

	public void delete(final User user) {
		this.userRepository.delete(user);

	}

	//Other Business Methods --------------------------------

	public User findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		User res;
		res = this.userRepository.findByUserAccount(userAccount.getId());
		return res;
	}

	public User findByPrincipal() {
		User res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		return res;
	}
	
	public Boolean isRsvpd(int rendezvousId){
		Assert.isTrue(rendezvousId != 0);
		return (userRepository.isRsvpd(rendezvousId, findByPrincipal()) == 1);
	}
	
	public Boolean isAdult(){
		User u = findByPrincipal();
		 DateTime user18 = new DateTime(u.getBirthDate()).plusYears(18);
		 return user18.isBeforeNow();
	}

	//RegisterUserForm ----> User

	public User reconstruct(User user, final BindingResult binding) {
		
		user.setId(0);
		user.setVersion(0);
		
		//Collections
		user.setRsvps(new ArrayList<Rsvp>());
		user.setRendezvouses(new ArrayList<Rendezvous>());

		//Authority
		Collection<Authority> authorities = user.getUserAccount().getAuthorities();
		Authority authority = new Authority();

		authority.setAuthority(Authority.USER);
		authorities.add(authority);
		user.getUserAccount().setAuthorities(authorities);
		
		this.validator.validate(user, binding);

		return user;
	}

}
