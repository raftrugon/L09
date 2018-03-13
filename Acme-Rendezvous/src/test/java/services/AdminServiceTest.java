
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Admin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private AdminService		adminService;

	//Supporting services -----------------------------------------------------
	@Autowired
	private UserAccountService	userAccountService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driverFindOne() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST FIND ONE ADMIN==================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				getEntityId("admin"), null
			},

			//Find one admin using another role id
			{
				getEntityId("user1"), IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateFindOne(int adminId, Class<?> expected) {

		Class<?> caught = null;

		try {
			adminService.findOne(adminId);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void driverFindByUserAccount() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST FINDBYUSERACCOUNT ADMIN==================================================");
		System.out.println("===============================================================================================================\r");
		
		Object testingData[][] = {
			//Positive test
			{
				getEntityId("userAccount1"), null
			},
			//UserAccount param is null
			{
				null, NullPointerException.class
			},
			//UserAccount param belongs to a different role than Admin
			{
				getEntityId("userAccount2"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindByUserAccount((Integer) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateFindByUserAccount(Integer entityId, Class<?> expected) {

		Class<?> caught = null;

		try {
			UserAccount userAccount = null;

			userAccount = userAccountService.findOne(entityId);
			Admin res = adminService.findByUserAccount(userAccount);
			Assert.notNull(res);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void driverFindByPrincipal() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST FINDBYPRINCIPAL ADMIN==================================================");
		System.out.println("===============================================================================================================\r");
		
		Object testingData[][] = {
			//Positive test
			{
				"admin", null
			},
			//FindByPrincipal with a different role than Admin
			{
				"user1", null
			},
			//FindByPrincipal being anonymous
			{
				null, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateFindByPrincipal((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateFindByPrincipal(String username, Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(username);
			adminService.findByPrincipal();
			this.unauthenticate();

		} catch (Throwable oops) {

			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

}
