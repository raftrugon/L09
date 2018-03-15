
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.User;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private UserService			userService;

	//Supporting services -----------------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;


	// Tests ------------------------------------------------------------------

	//FIND_BY_USER_ACCOUNT
	//FIND_BY_PRINCIPAL, IS_RSVPD, IS_ADULT

	@Test
	public void testFindeOneDriver() {
		Object testingData[][] = {
			//Positive test
			{
				getEntityId("user1"), null
			},
			//Find one user with id=0
			{
				userService.create().getId(), IllegalArgumentException.class
			},
			//Find one user using anothe role id
			{
				getEntityId("admin"), IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((Integer) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	protected void templateFindOne(Integer userId, Class<?> expected) {

		Class<?> caught = null;

		try {
			User res = userService.findOne(userId);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	@Test
	public void driverFindAll() {

		Object testingData[][] = {
			//Positive test
			{
				null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateAll((Class<?>) testingData[i][0]);
	}

	protected void templateAll(Class<?> expected) {

		Class<?> caught = null;

		try {
			userService.findAll();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void driverGetRequestableRendezvouses() {

		Object testingData[][] = {
			//Positive test
			{
				"user1", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetGequestableRendezvouses((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateGetGequestableRendezvouses(String userId, Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(userId);
			userService.getRequestableRendezvouses();
			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void driverCreate() {

		Object testingData[][] = {
			//Positive test
			{
				null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((Class<?>) testingData[i][0]);
	}

	protected void templateCreate(Class<?> expected) {

		Class<?> caught = null;

		try {
			userService.create();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}
	/*
	 * @Test
	 * public void testCreateUserDriver(){
	 * 
	 * Object testingData[][] = {
	 * {"Name", "Username", "adress", "123456789", "email@email.com", true, null}
	 * };
	 * 
	 * for(int i =0; i<testingData.length; i++)
	 * this.testCreateUserTemplate((String) testingData[i][0],
	 * (String) testingData[i][1],
	 * (String) testingData[i][2],
	 * (String) testingData[i][3],
	 * (String) testingData[i][4],
	 * (Boolean) testingData[i][5],
	 * (Class<?>) testingData[i][6]);
	 * }
	 * protected void testCreateUserTemplate(final String name, final String username, final String adress, final String phoneNumber, final String email, final Boolean date, final Class<?> expected){
	 * Class<?> caught = null;
	 * 
	 * try{
	 * User user = this.userService.create();
	 * if(date){
	 * user.setBirthDate(new Date(System.currentTimeMillis()-1000000000));
	 * }else{
	 * user.setBirthDate(new Date(System.currentTimeMillis()+2000000000));
	 * }
	 * 
	 * this.userService.save(user);
	 * }catch(Throwable oops){
	 * caught = oops.getClass();
	 * }
	 * this.checkExceptions(expected, caught);
	 * 
	 * if(expected == null)
	 * System.out.println("--------------POSITIVO---------------");
	 * else{
	 * System.out.println("--------------NEGATIVO---------------");
	 * }
	 * }
	 */

	@Test
	public void driverFindByUserAccount() {

		Object testingData[][] = {
			//Positive test
			{
				getEntityId("userAccount5"), null
			},
			//UserAccount param is null
			{
				null, NullPointerException.class
			},
			//UserAccount param belongs to a different role than manager
			{
				getEntityId("userAccount11"), IllegalArgumentException.class
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
			User res = userService.findByUserAccount(userAccount);
			Assert.notNull(res);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void driverFindByPrincipal() {

		Object testingData[][] = {
			//Positive test
			{
				"user1", null
			},
			//FindByPrincipal with a different role than user
			{
				"manager2", null
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
			userService.findByPrincipal();
			this.unauthenticate();
			userService.flush();
		} catch (Throwable oops) {

			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void testIsRsvpDriver() {

		Object testingData[][] = {
			//Positive test
			{
				"rendezvous1", "user1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.testIsRsvpTemplate(super.getEntityId((String) testingData[i][0]), ((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	protected void testIsRsvpTemplate(final int rendezvousId, final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(user);
			this.userService.isRsvpd(rendezvousId);
			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

		if (expected == null) {
			System.out.println("----------------POSITIVO--------------");
		} else
			System.out.println("----------------NEGATIVO--------------");

	}
	@Test
	public void testIsAdultDriver() {

		Object testingData[][] = {
			{
				"user1", null
			}, {
				"user2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.testIsAdultTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void testIsAdultTemplate(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(user);
			if (!this.userService.isAdult()) {
				caught = IllegalArgumentException.class;
			}
			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

		if (expected == null) {
			System.out.println("----------------POSITIVO--------------");
		} else
			System.out.println("----------------NEGATIVO--------------");
	}

}
