
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Rendezvous;
import domain.User;
import domain.Zervice;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RendezvousServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private RendezvousService rendezvousService;
	
	//Supporting services -----------------------------------------------------
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	// Tests ------------------------------------------------------------------
	
	@Test
	public void driverCreate() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST CREATE====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				"user1", null, "Creación correcta de un rendezvous."
			},
			//Create with wrong role
			{
				"manager1", IllegalArgumentException.class, "Intento de crear un rendezvous siendo mánager."
			},
			//Create with no login
			{
				null, IllegalArgumentException.class, "Intento de crear un rendezvous sin estar identificado."
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateCreate(String rol, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			this.authenticate(rol);
			rendezvousService.create();
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
	public void driverFindOne() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST FIND ONE====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				getEntityId("rendezvous1"), null, "Find one de un rendezvous correcto."
			},
			//Find one no persisted rendezvous
			{
				0, IllegalArgumentException.class, "Intento de crear un rendezvous todavía no persistido."
			},
			//Find one with another entity
			{
				getEntityId("comment1"), IllegalArgumentException.class, "Intento de encontrar un rendezvous usando la id de otra entidad."
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((Integer) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateFindOne(Integer rendezvousId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			rendezvousService.findOne(rendezvousId);

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
				null, "Búsqueda correcta del Find All", rendezvousService.findAll().size()
			}, {
				IllegalArgumentException.class, "Distintos rangos", rendezvousService.findAll().size() + 1
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindAll((Class<?>) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2]);
	}

	protected void templateFindAll(Class<?> expected, String explanation, int index) {

		Class<?> caught = null;

		try {
			Collection<Rendezvous> ren = rendezvousService.findAll();
			Assert.isTrue(ren.size() == index);

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
	public void driverFindAllUnder18() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST FIND ALL UNDER 18====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test
			{
				null, "Búsqueda correcta del Find All", rendezvousService.findAllUnder18().size()
			}, {
				IllegalArgumentException.class, "Distintos rangos", rendezvousService.findAllUnder18().size() + 1
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindAllUnder18((Class<?>) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2]);
	}

	protected void templateFindAllUnder18(Class<?> expected, String explanation, int index) {

		Class<?> caught = null;

		try {
			Collection<Rendezvous> ren = rendezvousService.findAllUnder18();
			Assert.isTrue(ren.size() == index);

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
			// Positive test
			{
				"user1", null, "Test Name", "Test Description", new Date(System.currentTimeMillis() + 200000), "http://www.picture.com", 33.0, 44.0, null, "Guardado correcto de Rendezvous."
			},
			// Wrong role
			{
				"admin", null, "Test Name", "Test Description", new Date(System.currentTimeMillis() + 200000), "http://www.picture.com", 33.0, 44.0, IllegalArgumentException.class, "Rol incorrecto para guardar."
			},
			// Wrong user owner
			{
				"user2", getEntityId("rendezvous1"), "Test Name", "Test Description", new Date(System.currentTimeMillis() + 200000), "http://www.picture.com", 33.0, 44.0, IllegalArgumentException.class, "Rol incorrecto para guardar."
			},
			// Date is past
			{
				"user1", getEntityId("rendezvous1"), "Test Name", "Test Description", new Date(), "http://www.picture.com", 33.0, 44.0, IllegalArgumentException.class, "Fecha pasada inválida."
			},
			// Try to save one rendezvous in final mode
			{
				"user4", getEntityId("rendezvous2"), "Test Name", "Test Description", new Date(System.currentTimeMillis() + 200000), "http://www.picture.com", 33.0, 44.0, IllegalArgumentException.class, "Intento de guardar un Rendezvous en modo final."
			},
			// Try to save one rendezvous marked as deleted
			{
				"user2", getEntityId("rendezvous3"), "Test Name", "Test Description", new Date(System.currentTimeMillis() + 200000), "http://www.picture.com", 33.0, 44.0, IllegalArgumentException.class, "Intento de guardar un Rendezvous en modo final."
			},
			// Try to save one rendezvous without name
			{
				"user1", null, "", "Test Description", new Date(System.currentTimeMillis() + 200000), "http://www.picture.com", 33.0, 44.0, ConstraintViolationException.class, "Intento de guardar un Rendezvous sin nombre."
			},
			// Try to save one rendezvous without description
			{
				"user1", null, "Test Name", "", new Date(System.currentTimeMillis() + 200000), "http://www.picture.com", 33.0, 44.0, ConstraintViolationException.class, "Intento de guardar un Rendezvous sin descripción."
			},
			// Try to save one rendezvous with null date
			{
				"user1", null, "Test Name", "Test Description", null, "http://www.picture.com", 33.0, 44.0, NullPointerException.class, "Intento de guardar un Rendezvous con fecha nula."
			},
			// Try to save one rendezvous with a wrong format in picture field
			{
				"user1", null, "Test Name", "Test Description", new Date(System.currentTimeMillis() + 200000), "picture", 33.0, 44.0, ConstraintViolationException.class, "Intento de guardar un Rendezvous con un formato inadecuado en Picture."
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateSave((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (String) testingData[i][5], 
					(Double) testingData[i][6], (Double) testingData[i][7], (Class<?>) testingData[i][8], (String) testingData[i][9]);
	}
	protected void templateSave(String role, Integer rendezvousId, String name, String description, Date organisationMoment, String picture, Double latitude, Double longitude, final Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			authenticate(role);
			
			Rendezvous ren = null;
			if(rendezvousId == null)
				ren = rendezvousService.create();
			else
				ren = rendezvousService.findOne(rendezvousId);
			ren.setName(name);
			ren.setDescription(description);
			ren.setOrganisationMoment(organisationMoment);
			ren.setPicture(picture);
			ren.setLatitude(latitude);
			ren.setLongitude(longitude);
			this.rendezvousService.save(ren);

			authenticate(null);
			this.rendezvousService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Rendezvous: " + rendezvousId);
		System.out.println("Name: " + name);
		System.out.println("Rol: " + role);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}
	
	@Test
	public void driverDeleteByAdmin() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST DELETE BY ADMIN====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			// Positive test
			{
				getEntityId("rendezvous1"), "admin", null, "Borrar con Admin un Rendezvous correctamente."
			},
			// Try to admin-delete being anonymous.
			{
				getEntityId("rendezvous1"), null, IllegalArgumentException.class, "Borrar un Rendezvous de Admin siendo anónimo."
			},
			// Try to admin-delete being user.
			{
				getEntityId("rendezvous1"), "user1", IllegalArgumentException.class, "Borrar un Rendezvous de Admin siendo user."
			},
			// Try to admin-delete a no persisted rendezvous.
			{
				0, "admin", IllegalArgumentException.class, "Borrar un Rendezvous de Admin sin persistir."
			},
		};

		for (int i = 0; i < testingData.length; i++)
			templateDeleteByAdmin((Integer) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateDeleteByAdmin(Integer rendezvousId, String role, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			authenticate(role);
			Rendezvous rendezvous = rendezvousService.findOne(rendezvousId);
			rendezvousService.deleteByAdmin(rendezvous);
			authenticate(role);
			rendezvousService.flush();
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
		System.out.println("Rendezvous: " + rendezvousId);
		System.out.println("Rol: " + role);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}
	
	@Test
	public void driverDeleteByUser() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST DELETE BY USER====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			// Positive test
			{
				getEntityId("rendezvous4"), "user2", null, "Borrar con User un Rendezvous correctamente."
			},
			// Try to user-delete being anonymous.
			{
				getEntityId("rendezvous4"), null, IllegalArgumentException.class, "Borrar un Rendezvous de User siendo anónimo."
			},
			// Try to user-delete being admin.
			{
				getEntityId("rendezvous4"), "admin", IllegalArgumentException.class, "Borrar un Rendezvous de User siendo admin."
			},
			// Try to user-delete a no persisted rendezvous.
			{
				0, "user1", IllegalArgumentException.class, "Borrar un Rendezvous de User sin persistir."
			},
			// Try to user-delete a rendezvous from another user.
			{
				getEntityId("rendezvous4"), "user1", IllegalArgumentException.class, "Borrar un Rendezvous de User perteneciente a otra persona."
			},
			// Try to user-delete a rendezvous in final mode.
			{
				getEntityId("rendezvous2"), "user4", IllegalArgumentException.class, "Borrar un Rendezvous de User en modo final."
			},
			// Try to user-delete a rendezvous with past date.
			{
				getEntityId("rendezvous6"), "user3", IllegalArgumentException.class, "Borrar un Rendezvous de User con una fecha pasada."
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateDeleteByUser((Integer) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateDeleteByUser(Integer rendezvousId, String role, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			authenticate(role);
			rendezvousService.deleteByUser(rendezvousId);
			authenticate(role);
			rendezvousService.flush();
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
		System.out.println("Rendezvous: " + rendezvousId);
		System.out.println("Rol: " + role);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}
	
	@Test
	public void driverGetRSVPRendezvousesForUser() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST GET RSVP RENDEZVOUSES FOR USER====================================================");
		System.out.println("===============================================================================================================\r");

		User user = userService.findOne(getEntityId("user1"));
		
		Object testingData[][] = {
			//Positive test
			{
				user, rendezvousService.getRSVPRendezvousesForUser(user).size(), null, "Búsqueda correcta de RSVP Rendezvouses de un usuario."
			}, {
				user, rendezvousService.getRSVPRendezvousesForUser(user).size() + 1, IllegalArgumentException.class, "Distintos rangos"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetRSVPRendezvousesForUser((User) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateGetRSVPRendezvousesForUser(User user, Integer index, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			Collection<Rendezvous> ren = rendezvousService.getRSVPRendezvousesForUser(user);
			Assert.isTrue(ren.size() == index);

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
	public void driverGetTop10RendezvousByRSVPs() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST GET TOP 10 RENDEZVOUS BY RSVPS====================================================");
		System.out.println("===============================================================================================================\r");
		
		Object testingData[][] = {
			//Positive test
			{
				rendezvousService.getTop10RendezvousByRSVPs().size(), null, "Búsqueda correcta Top 10 Rendezvouses por RSVPs."
			}, {
				rendezvousService.getTop10RendezvousByRSVPs().size() + 1, IllegalArgumentException.class, "Distintos rangos"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetRSVPRendezvousesForUser((Integer) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateGetRSVPRendezvousesForUser(Integer index, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			Collection<Rendezvous> ren = rendezvousService.getTop10RendezvousByRSVPs();
			Assert.isTrue(ren.size() == index);

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
	public void driverLink() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST LINK====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			// Positive test
			{
				"user1", getEntityId("rendezvous1"), getEntityId("rendezvous2"), null, "Enlace correcto de un rendezvous a otro."
			},
			// The user who tries to link one rendezvous is not its owner
			{
				"user2", getEntityId("rendezvous1"), getEntityId("rendezvous2"), IllegalArgumentException.class, "Intento de enlazar un rendezvous de otro dueño a otro."
			},
			// Authentication with another role
			{
				"manager1", getEntityId("rendezvous1"), getEntityId("rendezvous2"), IllegalArgumentException.class, "Intento de enlazar un rendezvous a otro siendo mánager."
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateLink((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3], (String) testingData[i][4]);
	}

	protected void templateLink(String username, Integer sourceId, Integer targetId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			authenticate(username);
			rendezvousService.link(sourceId, targetId);
			authenticate(null);

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
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}
	
	@Test
	public void driverGetRendezvousesToLink() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST GET RENDEZVOUSES TO LINK====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			// Positive test
			{
				"user1", getEntityId("rendezvous1"), null, "Rendezvouses a enlazar de un rendezvous correctamente."
			},
			// Try to get the rendezvouses being anonymous
			{
				null, getEntityId("rendezvous1"), IllegalArgumentException.class, "Intento de obtener los rendezvous siendo anónimo."
			},
			// Try to get the rendezvouses with another role
			{
				"manager1", getEntityId("rendezvous1"), IllegalArgumentException.class, "Intento de obtener los rendezvous siendo mánager."
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetRendezvousesToLink((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateGetRendezvousesToLink(String username, Integer rendezvousId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			authenticate(username);
			rendezvousService.getRendezvousesToLink(rendezvousId);
			authenticate(null);

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
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}
	
	@Test
	public void driverDeleteLink() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST DELETE LINK====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			// Positive test
			{
				getEntityId("rendezvous1"), getEntityId("rendezvous2"), null, "Borrar un enlace de un rendezvous exitosamente."
			},
			// Try to delete a link with a wrong rendezvous id
			{
				getEntityId("comment1"), getEntityId("rendezvous2"), IllegalArgumentException.class, "Intento de borrar un link de un rendezvous con id errónea."
			},
			// Try to delete a link with a wrong link id
			{
				getEntityId("rendezvous1"), getEntityId("comment1"), IllegalArgumentException.class, "Intento de borrar un link con id errónea de un rendezvous."
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateDeleteLink((Integer) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateDeleteLink(Integer rendezvousId, Integer linkId, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			rendezvousService.deleteLink(rendezvousId, linkId);

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
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}
	
	@Test
	public void driverListRendezvouses() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST LIST RENDEZVOUSES====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test 1
			{
				"user1", 1, null, "Listado correcto de rendezvouses (tipo 1)"
			},
			//Positive test 2
			{
				"user1", 2, null, "Listado correcto de rendezvouses (tipo 2)"
			},
			//Positive test 3
			{
				"user1", 3, null, "Listado correcto de rendezvouses (tipo 3)"
			},
			//Positive test 4
			{
				"user1", 4, null, "Listado correcto de rendezvouses (tipo 4)"
			},
			//Try to list rendezvouses being anonymous
			{
				null, 1, IllegalArgumentException.class, "Intento de listar los rendezvouses siendo anónimo"
			},
			//Try to list rendezvouses being another role
			{
				"manager1", 1, NullPointerException.class, "Intento de listar los rendezvouses siendo mánager"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateListRendezvouses((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateListRendezvouses(String username, Integer type, Class<?> expected, String explanation) {

		Class<?> caught = null;

		try {
			authenticate(username);
			Collection<Category> categories = categoryService.findAll();
			rendezvousService.listRendezvouses(type, categories);
			authenticate(null);

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
	public void driverListRendezvousesAnonymous() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST LIST RENDEZVOUSES ANONYMOUS====================================================");
		System.out.println("===============================================================================================================\r");

		Collection<Category> categories = categoryService.findAll();
		
		Object testingData[][] = {
			//Positive test
			{
				null, "Listado correcto de rendezvouses como anónimo", rendezvousService.listRendezvousesAnonymous(categories).size()
			},
			//Different range
			{
				IllegalArgumentException.class, "Distintos rangos", rendezvousService.listRendezvousesAnonymous(categories).size() + 1
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateListRendezvousesAnonymous((Class<?>) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2]);
	}

	protected void templateListRendezvousesAnonymous(Class<?> expected, String explanation, int index) {

		Class<?> caught = null;

		try {
			Collection<Category> categories = categoryService.findAll();
			Collection<Rendezvous> ren = rendezvousService.listRendezvousesAnonymous(categories);
			Assert.isTrue(ren.size() == index);

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
	public void driverGetZervicesForRendezvous() {

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST GET ZERVICES FOR RENDEZVOUS====================================================");
		System.out.println("===============================================================================================================\r");
		
		Object testingData[][] = {
			//Positive test
			{
				getEntityId("rendezvous1"), null, "Obtener servicios para rendezvous", rendezvousService.getZervicesForRendezvous(getEntityId("rendezvous1")).size()
			},
			//Different range
			{
				getEntityId("rendezvous1"), IllegalArgumentException.class, "Distintos rangos", rendezvousService.getZervicesForRendezvous(getEntityId("rendezvous1")).size() + 1
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateGetZervicesForRendezvous((Integer) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3]);
	}

	protected void templateGetZervicesForRendezvous(Integer rendezvousId, Class<?> expected, String explanation, int index) {

		Class<?> caught = null;

		try {
			Collection<Zervice> zerv = rendezvousService.getZervicesForRendezvous(rendezvousId);
			Assert.isTrue(zerv.size() == index);

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
