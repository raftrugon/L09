
package services;

import java.util.Collection;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Manager;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManagerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ManagerService		managerService;

	//Supporting services -----------------------------------------------------
	@Autowired
	private UserAccountService	userAccountService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverCreate() {

		Object testingData[][] = {
			//Positive test
			{
				null, "Create correcto"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((Class<?>) testingData[i][0]);
	}

	protected void templateCreate(Class<?> expected) {

		Class<?> caught = null;

		try {
			managerService.create();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void driverFindOne() {

		Object testingData[][] = {
			//Positive test
			{
				getEntityId("manager1"), null, "Busqueda correcta de FindOne"
			},

			//Find one no exist manager
			{
				0, IllegalArgumentException.class, "No existe esa Id"
			},
			//Find one other entity
			{
				getEntityId("comment1"), IllegalArgumentException.class, "Se intenta buscar otra entidad que no es un manager"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((int) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateFindOne(int managerId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			managerService.findOne(managerId);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("ManagerId: " + managerId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverFindAll() {

		Object testingData[][] = {
			//Positive test
			{
				null, "FindAll correcto"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateAll((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	protected void templateAll(Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			managerService.findAll();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverSave() {

		Object testingData[][] = {

			//Positive test
			{
				super.getEntityId("manager1"), "NAME", "SURNAME", "ADDRESS", "627027569", "pepe@gmail.com", "ASD24-test", null, "test positivo"
			},
			//email null
			{
				super.getEntityId("manager1"), "NAME", "SURNAME", "ADDRESS", "627027569", null, "ASD24-test", ConstraintViolationException.class, "Se intenta guardar con el email en nulo"
			},
			//no email
			{
				super.getEntityId("manager2"), "NAME", "SURNAME", "ADDRESS", "627027569", "pepegmail.com", "ASD24-test", ConstraintViolationException.class, "Se intenta guardar sin email, rellenando la opción pero sin el arroba"
			},
			//VAT error
			{
				super.getEntityId("manager3"), "NAME", "SURNAME", "ADDRESS", "627027569", "pepe@gmail.com", "ASD6548645-test", ConstraintViolationException.class, "Se intenta guardar con un VAT erroneo"
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateSave((Integer) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7],
				(String) testingData[i][8]);
	}
	protected void templateSave(Integer managerId, String name, String surnames, String address, String phone, String email, String vat, final Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {

			Manager m = managerService.findOne(managerId);
			m.setName(name);
			m.setSurnames(surnames);
			m.setAddress(address);
			m.setPhoneNumber(phone);
			m.setEmail(email);
			m.setVat(vat);
			this.managerService.save(m);
			this.managerService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Username: " + name);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverDeleteManager() {

		Object testingData[][] = {
			//Positive test
			{
				super.getEntityId("manager3"), null, "Se borra un manager correctamente"
			},
			//Manager NULL
			{
				null, NullPointerException.class, "Se intenta borrar un usuario nulo"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateDeleteManager((Integer) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateDeleteManager(Integer managerId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			Manager m = managerService.findOne(managerId);
			managerService.delete(m);
			managerService.flush();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("ManagerId: " + managerId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverFindByUserAccount() {

		Object testingData[][] = {
			//Positive test
			{
				getEntityId("userAccount11"), null, "Búsqueda del FindByUserAccount perfectamente"
			},
			//UserAccount param is null
			{
				null, NullPointerException.class, "El userAccount es nulo"
			},
			//UserAccount param belongs to a different role than manager
			{
				getEntityId("userAccount2"), IllegalArgumentException.class, "El userAccount es un user"
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
			Manager res = managerService.findByUserAccount(userAccount);
			Assert.notNull(res);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		if (expected == null)
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
				"manager1", null, "Se logea con un manager perfectamente"
			},
			//FindByPrincipal with a different role than manager
			{
				"user1", null, "Se intenta logear con un usuario y debería ser un manager"
			},
			//FindByPrincipal being anonymous
			{
				null, IllegalArgumentException.class, "Se intenta logear con un usuario nulo"
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateFindByPrincipal((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateFindByPrincipal(String username, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			this.authenticate(username);
			managerService.findByPrincipal();
			this.unauthenticate();
			managerService.flush();
		} catch (Throwable oops) {

			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Username: " + username);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void testgetManagersWhoProvideMoreServicesThanAvg() {

		System.out.println("===============================================================================================================");
		System.out.println("==================================TEST getManagersWhoProvideMoreServicesThanAvg=============================================");
		System.out.println("===============================================================================================================\r");

		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("-----------------------------------------------------------------\r");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Manager> var = this.managerService.getManagersWhoProvideMoreServicesThanAvg();

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	@Test
	public void testgetManagersWithMoreCancelledZervices() {

		System.out.println("===============================================================================================================");
		System.out.println("==================================TEST getManagersWithMoreCancelledZervices=============================================");
		System.out.println("===============================================================================================================\r");

		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("-----------------------------------------------------------------\r");

		int limit = (int) Math.random();
		super.authenticate(null);

		@SuppressWarnings("unused")
		Map<Manager, Integer> var = this.managerService.getManagersWithMoreCancelledZervices(limit);

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

}
