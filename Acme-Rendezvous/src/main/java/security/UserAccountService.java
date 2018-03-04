
package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.internal.SchemaPrinter;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public UserAccount findByUserName(String username) {

		Assert.notNull(username);
		UserAccount result = userAccountRepository.findByUsername(username);
		SchemaPrinter.print(result);
		return result;

	}

	public UserAccount findOne(int id) {
		Assert.notNull(id);
		UserAccount res = userAccountRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public UserAccount save(UserAccount ua) {
		Assert.notNull(ua);
		return userAccountRepository.save(ua);
	}


}
