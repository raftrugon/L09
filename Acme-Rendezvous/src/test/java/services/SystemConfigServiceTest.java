
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Admin;
import domain.SystemConfig;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SystemConfigServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	SystemConfigService		systemConfigService;

	//Supporting services -----------------------------------------------------
	@Autowired
	private AdminService	adminService;


	// =====================================================================================================================
	// ===================================================== FIND GET ======================================================
	// =====================================================================================================================

	@Test
	public void testGetDriver() {

		System.out.println("===============================================================================================================");
		System.out.println("===============================================TEST GET===================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			{
				null, "Busqueda de system."
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testGetTemplate((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	protected void testGetTemplate(final Class<?> expected, final String explanation) {

		Class<?> caught = null;

		try {
			super.authenticate(null);

			@SuppressWarnings("unused")
			SystemConfig sc = this.systemConfigService.get();

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

		// --------------------------------- CONSOLA ---------------------------------

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("¿Objeto encontrado? " + (caught == null));
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	// =====================================================================================================================
	// =================================================== SAVE system config ==================================================
	// =====================================================================================================================
	@Test
	public void testSystemConfigDriver() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================system config====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			{
				"admin", "TESTCOMPANY", "http://www.test.com", "This is an english comment", "Esto es un comentario", null, "Correcta creacion system."
			}, {
				"user1", "TESTCOMPANY", "http://www.test.com", "This is an english comment", "Esto es un comentario", IllegalArgumentException.class, "Creación con otro rol."
			}, {
				"admin", "", "http://www.test.com", "This is an english comment", "Esto es un comentario", ConstraintViolationException.class, "Bussines company blank."
			}, {
				"admin", "TESTCOMPANY", "", "This is an english comment", "Esto es un comentario", ConstraintViolationException.class, "URL blank."
			}, {
				"admin", "TESTCOMPANY", "test.com", "This is an english comment", "Esto es un comentario", ConstraintViolationException.class, "No url."
			}, {
				"admin", "TESTCOMPANY", "http://www.test.com", "", "Esto es un comentario", ConstraintViolationException.class, "Bienvenida ingles blank."
			}, {
				"admin", "TESTCOMPANY", "http://www.test.com", "This is an english comment", "", ConstraintViolationException.class, "Bienvenida español blank."
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSystemConfigTemplate(super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5],
				(String) testingData[i][6]);
	}

	protected void testSystemConfigTemplate(final int adminAux, final String bussinessName, final String banner, final String welcomeMessageEN, final String welcomeMessageES, final Class<?> expected, final String explanation) {

		Class<?> caught = null;

		try {
			Admin admin = adminService.findOne(adminAux);
			super.authenticate(admin.getUserAccount().getUsername());

			SystemConfig sc = systemConfigService.get();
			sc.setBanner(banner);
			sc.setBussinessName(bussinessName);
			sc.setWelcomeMessageEN(welcomeMessageEN);
			sc.setWelcomeMessageES(welcomeMessageES);
			this.systemConfigService.save(sc);

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

		// --------------------------------- CONSOLA ---------------------------------

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

}
