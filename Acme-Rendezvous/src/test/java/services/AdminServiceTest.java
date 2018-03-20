
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
				getEntityId("admin"), null, "Búsqueda del find One correcto"
			},

			//Find one admin using another role id
			{
				getEntityId("user1"), IllegalArgumentException.class, "Intentando buscar un user"
			},
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((int) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateFindOne(int adminId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			adminService.findOne(adminId);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
		
		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("AdminId: " + adminId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverFindByUserAccount() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST FINDBYUSERACCOUNT ADMIN==================================================");
		System.out.println("===============================================================================================================\r");
		
		Object testingData[][] = {
			//Positive test
			{
				getEntityId("userAccount1"), null, "FindByUserAccount correcto"
			},
			//UserAccount param is null
			{
				null, NullPointerException.class, "Se intenta buscar un userAccount nulo"
			},
			//UserAccount param belongs to a different role than Admin
			{
				getEntityId("userAccount2"), IllegalArgumentException.class, "Se intenta buscar un userAccount que no es de Admin"
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
			Admin res = adminService.findByUserAccount(userAccount);
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
		System.out.println("UserAccount: " + entityId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverFindByPrincipal() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST FINDBYPRINCIPAL ADMIN==================================================");
		System.out.println("===============================================================================================================\r");
		
		Object testingData[][] = {
			//Positive test
			{
				"admin", null, "FindByPrincipal correcto"
			},
			//FindByPrincipal with a different role than Admin
			{
				"user1", null, "Se intenta logear con un user"
			},
			//FindByPrincipal being anonymous
			{
				null, IllegalArgumentException.class, "Se intenta logear con un null"
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateFindByPrincipal((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateFindByPrincipal(String username, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			this.authenticate(username);
			adminService.findByPrincipal();
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
		System.out.println("Username: " + username);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

}
