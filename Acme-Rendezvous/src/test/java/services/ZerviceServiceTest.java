
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Manager;
import domain.Zervice;
import exceptions.ZerviceRequestsNotEmptyException;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ZerviceServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ZerviceService	zerviceService;

	//Supporting services -----------------------------------------------------
	@Autowired
	private ManagerService	managerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverDeleteByManager() {

		Object testingData[][] = {
			//Positive test
			{
				"manager4", super.getEntityId("zervice4"), null
			},
			//Create with user
			{
				"manager1", 0, IllegalArgumentException.class
			},
			//Create with no login
			{
				"admin", super.getEntityId("zervice1"), IllegalArgumentException.class
			}, {

				"manager1", null, NullPointerException.class
			}, {

				"manager1", super.getEntityId("zervice1"), ZerviceRequestsNotEmptyException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateDeleteByManager((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDeleteByManager(String rol, Integer id, Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(rol);
			zerviceService.deleteByManager(id);
			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

	@Test
	public void driverDeleteByAdmin() {

		Object testingData[][] = {
			//Positive test
			{
				"admin", super.getEntityId("zervice1"), null
			},
			//Create with user
			{
				"admin", 0, IllegalArgumentException.class
			},
			//Create with no login
			{
				"manager1", super.getEntityId("zervice1"), IllegalArgumentException.class
			}, {

				"admin", null, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateDeleteByAdmin((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDeleteByAdmin(String rol, Integer id, Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(rol);
			zerviceService.deleteByAdmin(id);
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
				"manager1", null
			},
			//Create with user
			{
				"user1", IllegalArgumentException.class
			},
			//Create with no login
			{
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateCreate(String managerId, Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(managerId);
			zerviceService.create();
			this.unauthenticate();

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
				getEntityId("zervice1"), null
			},

			//Find one no exist service
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

	protected void templateFindOne(int zerviceId, Class<?> expected) {

		Class<?> caught = null;

		try {
			zerviceService.findOne(zerviceId);

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
				getEntityId("zervice1"), null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateAll(int zerviceId, Class<?> expected) {

		Class<?> caught = null;

		try {
			zerviceService.findAll();

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
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", 2000.0, "manager1", null
			},
			//Name Blank
			{
				super.getEntityId("zervice1"), "", "Prueba", "http://www.test.com", 2000.0, "manager1", ConstraintViolationException.class
			},

			//description Blank
			{
				super.getEntityId("zervice1"), "Prueba", "", "http://www.test.com", 2000.0, "manager1", ConstraintViolationException.class
			},
			//no URL
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "kldjasvhlaksdf", 2000.0, "manager1", ConstraintViolationException.class
			},
			//Price negative
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", -1.0, "manager1", ConstraintViolationException.class
			},
			//Price null
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", null, "manager1", ConstraintViolationException.class
			},
			//Price zero
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", 0.0, "manager1", null
			},
			//Other rol
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", 2000.0, "manager2", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateSave((Integer) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Double) testingData[i][4], (Integer) super.getEntityId((String) testingData[i][5]), (Class<?>) testingData[i][6]);
	}
	protected void templateSave(Integer zerviceId, String name, String description, String url, Double price, Integer rolId, final Class<?> expected) {

		Class<?> caught = null;

		try {
			Manager m = managerService.findOne(rolId);
			super.authenticate(m.getUserAccount().getUsername());

			Zervice z = zerviceService.findOne(zerviceId);
			z.setName(name);
			z.setDescription(description);
			z.setPicture(url);
			z.setPrice(price);
			this.zerviceService.save(z);

			super.unauthenticate();
			this.zerviceService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}

}
