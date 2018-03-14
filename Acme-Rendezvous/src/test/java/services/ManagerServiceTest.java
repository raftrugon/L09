
package services;

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
				null
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
				getEntityId("manager1"), null
			},

			//Find one no exist manager
			{
				0, IllegalArgumentException.class
			},
			//Find one other entity
			{
				getEntityId("comment1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateFindOne(int managerId, Class<?> expected) {

		Class<?> caught = null;

		try {
			managerService.findOne(managerId);

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
			managerService.findAll();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void driverSave() {

		Object testingData[][] = {
			//Positive test
			{
				super.getEntityId("manager1"), "NAME", "SURNAME", "ADDRESS", "627027569", "pepe@gmail.com", "ASD24-test", null
			},
			//Name Blank
			{
				super.getEntityId("manager1"), "", "SURNAME", "ADDRESS", "627027569", "pepe@gmail.com", "ASD24-test", ConstraintViolationException.class
			},

			//surname Blank
			{
				super.getEntityId("manager1"), "NAME", "", "ADDRESS", "627027569", "pepe@gmail.com", "ASD24-test", ConstraintViolationException.class
			},
			//address blank
			{
				super.getEntityId("manager1"), "NAME", "SURNAME", "", "627027569", "pepe@gmail.com", "ASD24-test", null
			},
			//phone blank
			{
				super.getEntityId("manager1"), "NAME", "SURNAME", "ADDRESS", "", "pepe@gmail.com", "ASD24-test", null
			},
			//email null
			{
				super.getEntityId("manager1"), "NAME", "SURNAME", "ADDRESS", "627027569", null, "ASD24-test", ConstraintViolationException.class
			},
			//no email
			{
				super.getEntityId("manager1"), "NAME", "SURNAME", "ADDRESS", "627027569", "pepegmail.com", "ASD24-test", ConstraintViolationException.class
			},
			//VAT error
			{
				super.getEntityId("manager1"), "NAME", "SURNAME", "ADDRESS", "627027569", "pepe@gmail.com", "ASD24-test", ConstraintViolationException.class
			},
			//Manager NULL
			{
				null, "NAME", "SURNAME", "ADDRESS", "627027569", "pepe@gmail.com", "ASD24-test", ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateSave((Integer) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	protected void templateSave(Integer managerId, String name, String surnames, String address, String phone, String email, String vat, final Class<?> expected) {

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

	}

	@Test
	public void driverDeleteManager() {

		Object testingData[][] = {
			//Positive test
			{
				super.getEntityId("manager3"), null
			},
			//Manager NULL
			{
				null, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateDeleteManager((Integer) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateDeleteManager(Integer managerId, Class<?> expected) {

		Class<?> caught = null;

		try {
			Manager m = managerService.findOne(managerId);
			managerService.delete(m);
			managerService.flush();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void driverFindByUserAccount() {

		Object testingData[][] = {
			//Positive test
			{
				getEntityId("userAccount11"), null
			},
			//UserAccount param is null
			{
				null, NullPointerException.class
			},
			//UserAccount param belongs to a different role than manager
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
			Manager res = managerService.findByUserAccount(userAccount);
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
				"manager1", null
			},
			//FindByPrincipal with a different role than manager
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
			managerService.findByPrincipal();
			this.unauthenticate();
			managerService.flush();
		} catch (Throwable oops) {

			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

}
