package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Rendezvous;
import domain.Rsvp;

@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RsvpServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	RsvpService rsvpService;

	// Supporting services -----------------------------------------------------

	@Autowired
	RendezvousService rendezvousService;

	@Autowired
	UserService userService;




	// Tests ------------------------------------------------------------------

	// =====================================================================================================================
	// ===================================================== CREATE RSVP ===================================================
	// =====================================================================================================================
	@Test
	public void testCreateRsvpDriver() {

		System.out.println("===============================================================================================================");
		System.out.println("==========================================TEST CREATE RSVP=====================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			{"rendezvous1", "user7", 0, null, "Correcto RSVP."},
			{"rendezvous2", "user7", 1, null, "RSVP con respuestas vacías."},
			{"rendezvous2", "user8", 2, null,"RSVP con respuestas con espacios."},
			{"rendezvous2", "user5", 3, null, "RSVP con respuestas nulas."},

			{"rendezvous1", "user1", 0, IllegalArgumentException.class,	"RSVP en rendezvous con RSVP propio."},
			{"rendezvous1", null, 0, IllegalArgumentException.class,"RSVP sin usuario."},{"rendezvous1", "manager1", 0, IllegalArgumentException.class,"RSVP por manager1."},
			{"rendezvous1", "admin", 0, IllegalArgumentException.class,"RSVP por administrador."},
			{"rendezvous1", "manager1", 0, IllegalArgumentException.class,"RSVP por manager."},

		};

		for (int i = 0; i < testingData.length; i++)
			this.testCreateRsvpTemplate(
				super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3], (String) testingData[i][4]);
	}

	protected void testCreateRsvpTemplate(final int rendezvousId, final String user, final Integer questionContent,	final Class<?> expected, final String explanation) {

		Class<?> caught = null;

		// --------------------------------- CONSOLA ---------------------------------

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");

		System.out.println("Explicación: " + explanation);
		System.out.println("RendezvousId: " + rendezvousId);
		System.out.println("User: " + user);

		String answers = "";
		switch (questionContent) {
		case 0 :
			answers = "Normales";
			break;
		case 1 :
			answers = "Vacías";
			break;
		case 2 :
			answers = "Llena de espacios";
			break;
		case 3 :
			answers = "Nulas";
			break;
		}
		System.out.println("Respuestas introducidas: " + answers);



		// --------------------------------- Código ---------------------------------

		try {
			super.authenticate(user);

			Rsvp rsvp = this.rsvpService.create(rendezvousId);

			// Questions

			Collection<String> questions = rsvp.getRendezvous().getQuestions();

			switch (questionContent) {
			case 0 : // Introduccion de respuestas con texto
				Map<String, String> questionsAndAnswers0 = new HashMap<String, String>();

				for (String question0 : questions)
					questionsAndAnswers0.put(question0, "Test");

				rsvp.setQuestionsAndAnswers(questionsAndAnswers0);
				break;

			case 1 : // Introduccion de respuestas vacías
				Map<String, String> questionsAndAnswers1 = new HashMap<String, String>();

				for (String question1 : questions)
					questionsAndAnswers1.put(question1, "");

				rsvp.setQuestionsAndAnswers(questionsAndAnswers1);
				break;
			case 2 : // Introduccion de respuestas con espacios
				Map<String, String> questionsAndAnswers2 = new HashMap<String, String>();

				for (String question2 : questions)
					questionsAndAnswers2.put(question2, "      ");

				rsvp.setQuestionsAndAnswers(questionsAndAnswers2);
				break;
			case 3 : // Introduccion de respuestas nulas
				Map<String, String> questionsAndAnswers3 = new HashMap<String, String>();

				for (String question3 : questions)
					questionsAndAnswers3.put(question3, null);

				rsvp.setQuestionsAndAnswers(questionsAndAnswers3);
				break;

			}

			System.out.println("Respuestas antes: "	+ rsvp.getQuestionsAndAnswers());

			Rsvp saved = this.rsvpService.save(rsvp);

			System.out.println("Respuestas después: " + saved.getQuestionsAndAnswers());

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out
		.println("-----------------------------------------------------------------\r");

	}

	// =====================================================================================================================
	// ===================================================== FIND ONE ======================================================
	// =====================================================================================================================

	@Test
	public void testFindOneDriver() {

		System.out.println("===============================================================================================================");
		System.out.println("===============================================TEST FIND ONE===================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			{"rsvp1", true, null, "Busqueda de comment1."},
			{"rsvp3", true, null, "Busqueda de comment3."},
			{"rsvp6", true, null, "Busqueda de comment6."},

			{"admin", true, IllegalArgumentException.class, "Busqueda de administrador."},
			{"rendezvous1", true, IllegalArgumentException.class, "Busqueda de rendezvous1."},
			{"rendezvous1", false, IllegalArgumentException.class, "Busqueda con id = 0"}};

		for (int i = 0; i < testingData.length; i++)
			this.testFindOneTemplate(
				super.getEntityId((String) testingData[i][0]), (Boolean) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void testFindOneTemplate(final Integer objectId, final Boolean useId,	final Class<?> expected, final String explanation) {

		Class<?> caught = null;

		try {
			super.authenticate(null);

			if (useId)
				this.rsvpService.findOne(objectId);
			else
				this.rsvpService.findOne(0);

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
		if (useId)
			System.out.println("ObjectId: " + objectId);
		else
			System.out.println("ObjectId: 0");
		System.out.println("¿Objeto encontrado? " + (caught == null));
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	// =====================================================================================================================
	// ======================================================= DELETE ======================================================
	// =====================================================================================================================

	@Test
	public void testDeleteDriver() {

		System.out.println("===============================================================================================================");
		System.out.println("===============================================DELETE==========================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			{"rsvp1", "user1", null, "Correcto borrado."},
			{"rsvp2", "user4", null, "Correcto borrado."},
			{"rsvp3", "user2", null, "Correcto borrado."},
			{"rsvp4", "user4", null, "Correcto borrado."},

			{"rsvp1", "user1", IllegalArgumentException.class, "Borrado de en rendezvous sin rsvp."},
			{"rsvp5", null, IllegalArgumentException.class, "Borrado sin actor registrado."},
			{"rsvp6", "user2", IllegalArgumentException.class, "Borrado por usuario distinto."},
			{"rsvp6", "manager1", IllegalArgumentException.class, "Borrado por manager."}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteTemplate(super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void testDeleteTemplate(final int rsvpId, final String user, final Class<?> expected, final String explanation) {

		Class<?> caught = null;

		try {
			super.authenticate(user);

			Rsvp rsvp = this.rsvpService.findOne(rsvpId);
			this.rsvpService.delete(rsvp.getRendezvous().getId());
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
		System.out.println("RsvpId: " + rsvpId);
		System.out.println("User: " + user);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	//Other Business Methods --------------------------------

	// =====================================================================================================================
	// ================================================= getPendingQuestions ===============================================
	// =====================================================================================================================
	@Test
	public void testGetPendingQuestions(){

		System.out.println("===============================================================================================================");
		System.out.println("======================================TEST getPendingQuestions=================================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query para rsvp1.");
		System.out.println("-----------------------------------------------------------------\r");

		Integer rsvpId = super.getEntityId("rsvp1");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<String> var = this.rsvpService.getPendingQuestions(this.rsvpService.findOne(rsvpId));

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}


	// =====================================================================================================================
	// ================================================= rsvpForRendezvousCreator ===============================================
	// =====================================================================================================================
	@Test
	public void testRsvpForRendezvousCreator(){

		System.out.println("===============================================================================================================");
		System.out.println("======================================TEST rsvpForRendezvousCreator=================================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query para rendezvous1.");
		System.out.println("-----------------------------------------------------------------\r");


		super.authenticate("user1");



		Rendezvous test = this.rendezvousService.create();

		test.setLatitude(0.0);
		test.setLongitude(0.0);
		test.setDescription("test");
		test.setName("test");
		test.setOrganisationMoment(new Date(System.currentTimeMillis()+10000));

		Rendezvous saved = this.rendezvousService.save(test); //Aquí se llama al método




		if(saved.getRsvps().isEmpty())
			throw new RuntimeException();


		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}
}
