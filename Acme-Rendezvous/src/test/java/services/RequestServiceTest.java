
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
				"user1", null
			},
			//Create with user
			{
				"manager1", IllegalArgumentException.class
			},
			//Create with no login
			{
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateCreate(String userId, Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(userId);
			userService.create();
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
				getEntityId("user1"), null
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

	protected void templateFindOne(int userId, Class<?> expected) {

		Class<?> caught = null;

		try {
			userService.findOne(userId);

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
	public void driverSave() {

		Object testingData[][] = {
			//Positive test
			{
				super.getEntityId("request1"), "Prueba", "user1", null
			},
			//Name Blank
			{
				super.getEntityId("request1"), "", "user1", null
			},

			//description Blank
			{
				super.getEntityId("request1"), null, "user1", null
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
			templateSave((Integer) testingData[i][0], (String) testingData[i][1], (Integer) super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);
	}
	protected void templateSave(Integer requestId, String comment, Integer rolId, final Class<?> expected) {

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

	}

}
