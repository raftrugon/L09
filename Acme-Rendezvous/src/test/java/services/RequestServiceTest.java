
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Request;
import domain.User;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private RequestService	requestService;
	//Supporting services -----------------------------------------------------
	@Autowired
	private UserService		userService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverCreate() {

		Object testingData[][] = {
			//Positive test
			{
				"user1", null, "Creación correcta"
			},
			//Create with user
			{
				"manager1", IllegalArgumentException.class, "Se intenta crear un manager"
			},
			//Create with no login
			{
				null, IllegalArgumentException.class, "Se intenta crear con un usuario no logeado"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateCreate(String userId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			this.authenticate(userId);
			requestService.create();
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
		System.out.println("UserId: " + userId);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void driverFindOne() {

		Object testingData[][] = {
			//Positive test
			{
				getEntityId("request1"), null, "Búsqueda del FindOne correcta"
			},

			//Find one no exist service
			{
				0, IllegalArgumentException.class, "Se intenta buscar con una Id inexistente"
			},
			//Find one other entity
			{
				getEntityId("comment1"), IllegalArgumentException.class, "Se intenta buscar otra entidad diferente a la de request"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((int) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateFindOne(int userId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			requestService.findOne(userId);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		if (expected == null)
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
			requestService.findAll();

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
				super.getEntityId("request1"), "Prueba", "user1", null, "Guarda perfectamente"
			},
			//Name Blank
			{
				super.getEntityId("request1"), "", "user1", null, "Se intenta guardar con un name en blanco"
			},

			//description Blank
			{
				super.getEntityId("request1"), null, "user1", null, "Se intenta guardar con el name en null"
			}
		/*
		 * ,
		 * //no URL
		 * {
		 * super.getEntityId("user1"), "Prueba", "Prueba", "kldjasvhlaksdf", 2000.0, "user1", ConstraintViolationException.class
		 * },
		 * //Price negative
		 * {
		 * super.getEntityId("user1"), "Prueba", "Prueba", "http://www.test.com", -1.0, "user1", ConstraintViolationException.class
		 * },
		 * //Price null
		 * {
		 * super.getEntityId("user1"), "Prueba", "Prueba", "http://www.test.com", null, "user1", ConstraintViolationException.class
		 * },
		 * //Price zero
		 * {
		 * super.getEntityId("user1"), "Prueba", "Prueba", "http://www.test.com", 0.0, "user1", null
		 * },
		 * //Other rol
		 * {
		 * super.getEntityId("user1"), "Prueba", "Prueba", "http://www.test.com", 2000.0, "user2", IllegalArgumentException.class
		 * }
		 */

		};

		for (int i = 0; i < testingData.length; i++)
			templateSave((Integer) testingData[i][0], (String) testingData[i][1], (Integer) super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3], (String) testingData[i][4]);
	}
	protected void templateSave(Integer requestId, String comment, Integer rolId, final Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			User u = userService.findOne(rolId);
			super.authenticate(u.getUserAccount().getUsername());

			Request r = requestService.findOne(requestId);
			r.setComment(comment);
			this.requestService.save(r);
			super.unauthenticate();
			this.requestService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("RequestId: " + requestId);
		System.out.println("Comentario: " + comment);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

}
