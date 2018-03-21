
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST DELETE BY MANAGER====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				"manager4", super.getEntityId("zervice4"), null, "Borrado por un manager correctamente"
			},
			//Create with user
			{
				"manager1", 0, IllegalArgumentException.class, "Intento de borrar un zervice vacio"
			},
			//Create with no login
			{
				"admin", super.getEntityId("zervice1"), IllegalArgumentException.class, "Intento de borrar un zervice siendo admin"
			}, {

				"manager1", null, NullPointerException.class, "Intento de borrar un zervice nulo"
			}, {

				"manager1", super.getEntityId("zervice1"), ZerviceRequestsNotEmptyException.class, ""
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateDeleteByManager((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateDeleteByManager(String rol, Integer id, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			this.authenticate(rol);
			zerviceService.deleteByManager(id);
			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		// --------------------------------- CONSOLA ---------------------------------

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Rol: " + rol);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverDeleteByAdmin() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST DELETE BY ADMIN====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				"admin", super.getEntityId("zervice1"), null, "Borrado de un zervice correctamente"
			},
			//Create with user
			{
				"admin", 0, IllegalArgumentException.class, "Intento de borrar un zervice con id = 0"
			},
			//Create with no login
			{
				"manager1", super.getEntityId("zervice1"), IllegalArgumentException.class, "Intento de borrar un zervice siendo manager"
			}, {

				"admin", null, NullPointerException.class, "Intento de borrar un zervice null"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateDeleteByAdmin((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateDeleteByAdmin(String rol, Integer id, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			this.authenticate(rol);
			zerviceService.deleteByAdmin(id);
			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Rol: " + rol);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverCreate() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST CREATE====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				"manager1", null, "Creación correcta"
			},
			//Create with user
			{
				"user1", IllegalArgumentException.class, "Intento de crear un zervice logeado como user"
			},
			//Create with no login
			{
				null, IllegalArgumentException.class, "Intento de crear un zervice sin estar logeado"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateCreate(String managerId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			this.authenticate(managerId);
			zerviceService.create();
			this.unauthenticate();

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
	public void driverFindOne() {
		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST FIND ONE====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				getEntityId("zervice1"), null, "Búsqueda correcta del find One"
			},

			//Find one no exist service
			{
				0, IllegalArgumentException.class, "Intento de buscar un zervice que no existe"
			},
			//Find one other entity
			{
				getEntityId("comment1"), IllegalArgumentException.class, "Intento de buscar otra entidad diferente a la de zervice"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((int) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateFindOne(int zerviceId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			zerviceService.findOne(zerviceId);

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
	public void driverFindAll() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST FIND ALL====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				null, "Búsqueda correcta del Find All", zerviceService.findAll().size()
			}, {
				IllegalArgumentException.class, "distintos rangos", zerviceService.findAll().size() + 1
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateAll((Class<?>) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2]);
	}

	protected void templateAll(Class<?> expected, String explanation, int index) {

		Class<?> caught = null;

		try {
			Collection<Zervice> z = zerviceService.findAll();
			Assert.isTrue(z.size() == index);

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

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST SAVE====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", 2000.0, "manager1", null, "Guardado correctamente"
			},
			//Name Blank
			{
				super.getEntityId("zervice1"), "", "Prueba", "http://www.test.com", 2000.0, "manager1", ConstraintViolationException.class, "Intento de guardar con el nombre vacio"
			},

			//description Blank
			{
				super.getEntityId("zervice1"), "Prueba", "", "http://www.test.com", 2000.0, "manager1", ConstraintViolationException.class, "Intento de guardar con la descripción vacia"
			},
			//no URL
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "kldjasvhlaksdf", 2000.0, "manager1", ConstraintViolationException.class, "Intento de guardar con una url erronea"
			},
			//Price negative
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", -1.0, "manager1", ConstraintViolationException.class, "Intento de guardar con un precio negativo"
			},
			//Price null
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", null, "manager1", ConstraintViolationException.class, "Intento de guardar con un precio nulo"
			},
			//Price zero
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", 0.0, "manager1", null, "Intento de guardar con un precio igual a cero"
			},
			//Other rol
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", 2000.0, "manager2", IllegalArgumentException.class, "Intento de guardar un zervice que no es tuyo"
			},
			//Other rol
			{
				super.getEntityId("zervice1"), "", "", "http://www.test.com", 2000.0, "manager1", ConstraintViolationException.class, "Nombre y descripcion en blanco"
			},
			//Other rol
			{
				super.getEntityId("zervice1"), "Prueba", "Prueba", "http://www.test.com", -0.0, "manager1", null, "Precio extraño"
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateSave((Integer) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Double) testingData[i][4], (Integer) super.getEntityId((String) testingData[i][5]), (Class<?>) testingData[i][6],
				(String) testingData[i][7]);
	}
	protected void templateSave(Integer zerviceId, String name, String description, String url, Double price, Integer rolId, final Class<?> expected, String explanation) {

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
		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Zervice: " + zerviceId);
		System.out.println("Name: " + name);
		System.out.println("Rol: " + rolId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverFindAllNotInappropriate() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST FIND ALL NOT INAPPROPRIATE====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				null, "Búsqueda realizada correctamente del Find All not inappropriate", zerviceService.findAllNotInappropriate().size()
			}, {
				IllegalArgumentException.class, "test distinto rango", zerviceService.findAllNotInappropriate().size() + 1
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindAllNotInappropriate((Class<?>) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2]);
	}

	protected void templateFindAllNotInappropriate(Class<?> expected, String explanation, int index) {

		Class<?> caught = null;

		try {
			Collection<Zervice> z = zerviceService.findAllNotInappropriate();
			Assert.isTrue(z.size() == index);
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
	public void driverGetBestSellingZervices() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST FIND BEST SELLING ZERVICE====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				null, "Best Selling zervice correctamente", zerviceService.getBestSellingZervices().size()
			}, {
				IllegalArgumentException.class, "Best Selling zervice correctamente", zerviceService.getBestSellingZervices().size() + 1
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetBestSellingZervices((Class<?>) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2]);
	}

	protected void templateGetBestSellingZervices(Class<?> expected, String explanation, int index) {

		Class<?> caught = null;

		try {
			int z = zerviceService.getBestSellingZervices().size();
			Assert.isTrue(z == index);

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
	public void driverGetZerviceAvgStdPerRendezvous() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST ZERVICE AVG, STD POR RENDEZVOUS====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				null, "Avg, Std por Rendezvous correctamente", zerviceService.getZerviceAvgStdPerRendezvous().length
			}, {
				IllegalArgumentException.class, "Avg, Std por Rendezvous correctamente", zerviceService.getZerviceAvgStdPerRendezvous().length + 1
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetZerviceAvgStdPerRendezvous((Class<?>) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2]);
	}

	protected void templateGetZerviceAvgStdPerRendezvous(Class<?> expected, String explanation, int index) {

		Class<?> caught = null;

		try {
			int z = zerviceService.getZerviceAvgStdPerRendezvous().length;
			Assert.isTrue(z == index);

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
	public void driverGetZerviceMinMaxPerRendezvous() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST MIN, MAX RENDEZVOUS====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				null, "Zervice Min Max por Rendezvous correctamente"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetZerviceMinMaxPerRendezvous((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	protected void templateGetZerviceMinMaxPerRendezvous(Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			zerviceService.getZerviceMinMaxPerRendezvous();

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
	public void driverGetTopSellingZervices() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST TOP SELLING ZERVICE====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				1, 2, null, "Top Selling Zervice correctamente"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetTopSellingZervices((int) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateGetTopSellingZervices(int inicio, int fin, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			zerviceService.getTopSellingZervices(inicio, fin);

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

}
