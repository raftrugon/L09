
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
				getEntityId("user1"), null, "Búsqueda correcta del FindOne"
			},
			//Find one user with id=0
			{
				userService.create().getId(), IllegalArgumentException.class, "Intento de findOne con una id = 0"
			},
			//Find one user using anothe role id
			{
				getEntityId("admin"), IllegalArgumentException.class, "Intento de findOne de admin en vez de user"
			},
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((Integer) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);

	}
	protected void templateFindOne(Integer userId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			User res = userService.findOne(userId);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("UserId: " + userId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");
	}

	@Test
	public void driverFindAll() {

		Object testingData[][] = {
			//Positive test
			{
				null, "Búsqueda correcta del FindAll"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateAll((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	protected void templateAll(Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			userService.findAll();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
		
		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverGetRequestableRendezvouses() {

		Object testingData[][] = {
			//Positive test
			{
				"user1", null, "RequestableRendezvouses correctamente"
			}, {
				null, IllegalArgumentException.class, "RequestableRendezvous incorrectamente"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetGequestableRendezvouses((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateGetGequestableRendezvouses(String userId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			this.authenticate(userId);
			userService.getRequestableRendezvouses();
			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
		
		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("UserId: " + userId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverCreate() {

		Object testingData[][] = {
			//Positive test
			{
				null, "Creación correcta del user"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	protected void templateCreate(Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			userService.create();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
		
		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

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
				getEntityId("userAccount5"), null, "FindByUserAccount correcta"
			},
			//UserAccount param is null
			{
				null, NullPointerException.class, "Se intenta buscar un userAccount nulo"
			},
			//UserAccount param belongs to a different role than manager
			{
				getEntityId("userAccount11"), IllegalArgumentException.class, "Se intenta buscar un userAccount que no es un user"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindByUserAccount((Integer) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateFindByUserAccount(Integer entityId, Class<?> expected, String explanation) {

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
		
		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("EntityId: " + entityId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverFindByPrincipal() {

		Object testingData[][] = {
			//Positive test
			{
				"user1", null, "FindByPrincipal correctamente"
			},
			//FindByPrincipal with a different role than user
			{
				"manager2", null, "Se intenta buscar un manager"
			},
			//FindByPrincipal being anonymous
			{
				null, IllegalArgumentException.class, "Se intenta buscar con una entidad nula"
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateFindByPrincipal((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateFindByPrincipal(String username, Class<?> expected, String explanation ) {

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
		
		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void testIsRsvpDriver() {

		Object testingData[][] = {
			//Positive test
			{
				"rendezvous1", "user1", null, "IsRsvp correctamente"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.testIsRsvpTemplate(super.getEntityId((String) testingData[i][0]), ((String) testingData[i][1]), (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}
	protected void testIsRsvpTemplate(final int rendezvousId, final String user, final Class<?> expected, String explanation) {
		Class<?> caught = null;

		try {
			super.authenticate(user);
			this.userService.isRsvpd(rendezvousId);
			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("RendezvousId: " + rendezvousId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}
	@Test
	public void testIsAdultDriver() {

		Object testingData[][] = {
			{
				"user1", null, "IsAdult correctamente"
			}, {
				"user2", IllegalArgumentException.class, "IsAdult incorrectamente"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.testIsAdultTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);

	}

	protected void testIsAdultTemplate(final String user, final Class<?> expected, String explanation) {
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

		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Username: " + user);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");
	}

}
