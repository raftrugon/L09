
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;
import domain.Admin;

@Service
@Transactional
public class AdminService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdminRepository			adminRepository;

	// Supporting services ----------------------------------------------------


	// Simple CRUD methods ----------------------------------------------------

	public Admin create() {
		Admin res = new Admin();

		return res;
	}

	public Admin findOne(int adminId) {
		Assert.isTrue(adminId != 0);
		Admin res = adminRepository.findOne(adminId);
		Assert.notNull(res);
		return res;
	}

	public Admin save(Admin admin) {
		return adminRepository.save(admin);
	}

	public void delete(final Admin admin) {
		adminRepository.delete(admin);

	}
	
	//Other Business Methods --------------------------------
	
	public Admin findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Admin res;
		res = adminRepository.findByUserAccount(userAccount.getId());
		return res;
	}

	public Admin findByPrincipal() {
		Admin res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		return res;
	}
}
