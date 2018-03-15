
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Announcement;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AnnouncementServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	AnnouncementService announcementService;

	//Supporting services -----------------------------------------------------

	@Autowired
	RendezvousService rendezvousService;

	@Autowired
	UserService userService;

	// Tests ------------------------------------------------------------------

	// =====================================================================================================================
	// ================================================ CREATE ANNOUNCEMENT ================================================
	// =====================================================================================================================
	@Test
	public void testCreateAnnouncementDriver(){

		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST CREATE ANNOUNCEMENT==================================================");
		System.out.println("===============================================================================================================\r");


		Object testingData[][] = {
			{"rendezvous1","user1",null,"Correcta creacion announcement"},
			{"rendezvous2","user4",null,"Correcta creacion announcement"},
			{"rendezvous3","user2",null,"Correcta creacion announcement"},
			{"rendezvous4","user2",null,"Correcta creacion announcement"},

			{"rendezvous1","user2",IllegalArgumentException.class,"Announcement en Rendezvous no perteneciente al usuario"},
			{"rendezvous1",null,IllegalArgumentException.class,"Creación sin usuario."},
			{"rendezvous1","manager1",IllegalArgumentException.class,"Creación por manager1."},
			{"rendezvous1","admin",IllegalArgumentException.class,"Creación por administrador."}
		};


		for(int i = 0; i < testingData.length; i++)
			this.testCreateAnnouncementTemplate(super.getEntityId((String) testingData [i][0]),
				(String) testingData [i][1],
				(Class<?>) testingData[i][2],
				(String) testingData [i][3]);
	}

	protected void testCreateAnnouncementTemplate(final int rendezvousId, final String user, final Class<?> expected, final String explanation){

		Class<?> caught = null;

		try{
			super.authenticate(user);

			Announcement announcement = this.announcementService.create(rendezvousId);
			this.announcementService.save(announcement);

			super.unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);


		// --------------------------------- CONSOLA ---------------------------------

		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("RendezvousId: " + rendezvousId);
		System.out.println("User: " + user);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	// =====================================================================================================================
	// ================================================ MODIFY ANNOUNCEMENT ================================================
	// =====================================================================================================================
	@Test(expected = IllegalArgumentException.class)
	public void testModifyAnnouncement(){

		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST MODIFY ANNOUNCEMENT==================================================");
		System.out.println("===============================================================================================================\r");


		Integer announcementId = super.getEntityId("announcement1");

		System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: Modificación de un announcement.");
		System.out.println("AnnouncementId: "+announcementId);
		System.out.println("User: user1");
		System.out.println("-----------------------------------------------------------------\r");


		super.authenticate("user1");

		Announcement announcement = this.announcementService.findOne(announcementId);

		announcement.setTitle("TEST");
		announcement.setDescription("TEST");

		this.announcementService.save(announcement);

		super.unauthenticate();
		System.out.println("-----------------------------------------------------------------\r");

	}


	// =====================================================================================================================
	// ===================================================== FIND ONE ======================================================
	// =====================================================================================================================

	@Test
	public void testFindOneDriver(){

		System.out.println("===============================================================================================================");
		System.out.println("===============================================TEST FIND ONE===================================================");
		System.out.println("===============================================================================================================\r");


		Object testingData[][] = {
			{"announcement1",null,"Busqueda de announcement1."},
			{"announcement3",null,"Busqueda de announcement3."},
			{"announcement6",null,"Busqueda de announcement6."},

			{"admin",IllegalArgumentException.class,"Busqueda de administrador."},
			{"rendezvous1",IllegalArgumentException.class,"Busqueda de rendezvous1."},
			{"category1",IllegalArgumentException.class,"Busqueda de category1."}
		};


		for(int i = 0; i < testingData.length; i++)
			this.testFindOneTemplate(super.getEntityId((String) testingData [i][0]),
				(Class<?>) testingData[i][1],
				(String) testingData [i][2]);
	}

	protected void testFindOneTemplate(final int objectId, final Class<?> expected, final String explanation){

		Class<?> caught = null;

		try{
			super.authenticate(null);

			@SuppressWarnings("unused")
			Announcement announcement = this.announcementService.findOne(objectId);

			super.unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);


		// --------------------------------- CONSOLA ---------------------------------

		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("ObjectId: " + objectId);
		System.out.println("¿Objeto encontrado? " + (caught == null));
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}


	// =====================================================================================================================
	// ================================================== DELETE BY ADMIN ==================================================
	// =====================================================================================================================

	@Test
	public void testDeleteByAdminDriver(){

		System.out.println("===============================================================================================================");
		System.out.println("=======================================TEST DELETE BY ADMIN====================================================");
		System.out.println("===============================================================================================================\r");


		Object testingData[][] = {
			{"announcement1","admin",null,"Correcto borrado virtual por administrador."},
			{"announcement3","admin",null,"Correcto borrado virtual por administrador."},
			{"announcement4","admin",null,"Correcto borrado virtual por administrador."},
			{"announcement8","admin",null,"Correcto borrado virtual por administrador."},

			{"rendezvous1","admin",IllegalArgumentException.class,"Borrado con rendezvous como objeto."},
			{"announcement1",null,IllegalArgumentException.class,"Borrado sin actor registrado."},
			{"announcement1","user1",IllegalArgumentException.class,"Borrado por usuario."},
			{"announcement1","manager1",IllegalArgumentException.class,"Borrado por manager."}
		};


		for(int i = 0; i < testingData.length; i++)
			this.testDeleteByAdminTemplate(super.getEntityId((String) testingData [i][0]),
				(String) testingData [i][1],
				(Class<?>) testingData[i][2],
				(String) testingData [i][3]);
	}

	protected void testDeleteByAdminTemplate(final int announcementId, final String user, final Class<?> expected, final String explanation){

		Class<?> caught = null;

		try{
			super.authenticate(user);

			Announcement announcement = this.announcementService.findOne(announcementId);
			this.announcementService.deleteByAdmin(announcement);

			super.unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);


		// --------------------------------- CONSOLA ---------------------------------

		if(expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("ObjectId: " + announcementId);
		System.out.println("User: " + user);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	//Other Business Methods --------------------------------

	// =====================================================================================================================
	// =================================================== findAllOrdered ==================================================
	// =====================================================================================================================
	@Test
	public void testFindAllOrdered(){

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST findAllOrdered====================================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query.");
		System.out.println("-----------------------------------------------------------------\r");


		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Announcement> var = this.announcementService.findAllOrdered();

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	// =====================================================================================================================
	// ============================================== FindAllOrderedNotInappropriate =======================================
	// =====================================================================================================================
	@Test
	public void testFindAllOrderedNotInappropriate(){

		System.out.println("===============================================================================================================");
		System.out.println("================================TEST FindAllOrderedNotInappropriate============================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query.");
		System.out.println("-----------------------------------------------------------------\r");


		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Announcement> var = this.announcementService.findAllOrderedNotInappropriate();

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	// =====================================================================================================================
	// =========================================== getRSVPAnnouncementsForUser =============================================
	// =====================================================================================================================
	@Test
	public void testGetRSVPAnnouncementsForUser(){

		System.out.println("===============================================================================================================");
		System.out.println("==================================TEST getRSVPAnnouncementsForUser=============================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query con user1.");
		System.out.println("-----------------------------------------------------------------\r");

		Integer userId = super.getEntityId("user1");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Announcement> var = this.announcementService.getRSVPAnnouncementsForUser(this.userService.findOne(userId));

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	// =====================================================================================================================
	// =============================================== getMyAnnouncements ==================================================
	// =====================================================================================================================
	@Test
	public void testGetMyAnnouncements(){

		System.out.println("===============================================================================================================");
		System.out.println("======================================TEST getMyAnnouncements==================================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query con user1.");
		System.out.println("-----------------------------------------------------------------\r");

		Integer userId = super.getEntityId("user1");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Announcement> var = this.announcementService.getMyAnnouncements(this.userService.findOne(userId));

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	// =====================================================================================================================
	// ===================================== getRendezvousAnnouncementsSorted ==============================================
	// =====================================================================================================================
	@Test
	public void testGetRendezvousAnnouncementsSorted(){

		System.out.println("===============================================================================================================");
		System.out.println("===============================TEST getRendezvousAnnouncementsSorted==========================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query con rendezvous1.");
		System.out.println("-----------------------------------------------------------------\r");

		Integer rendezvousId = super.getEntityId("rendezvous1");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Announcement> var = this.announcementService.getRendezvousAnnouncementsSorted(rendezvousId);

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	// =====================================================================================================================
	// =============================================== getRSVPAnnouncementsForUserNotInappropriate ==================================================
	// =====================================================================================================================
	@Test
	public void testGetRSVPAnnouncementsForUserNotInappropriate(){

		System.out.println("===============================================================================================================");
		System.out.println("===========================TEST getRSVPAnnouncementsForUserNotInappropriate====================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query con user1.");
		System.out.println("-----------------------------------------------------------------\r");

		Integer userId = super.getEntityId("user1");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Announcement> var = this.announcementService.getRSVPAnnouncementsForUserNotInappropriate(this.userService.findOne(userId));

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	// =====================================================================================================================
	// =============================================== getMyAnnouncementsNotInappropriate ==================================================
	// =====================================================================================================================
	@Test
	public void testGetMyAnnouncementsNotInappropriate(){

		System.out.println("===============================================================================================================");
		System.out.println("===========================TEST getMyAnnouncementsNotInappropriate====================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query con user1.");
		System.out.println("-----------------------------------------------------------------\r");

		Integer userId = super.getEntityId("user1");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Announcement> var = this.announcementService.getMyAnnouncementsNotInappropriate(this.userService.findOne(userId));

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	// =====================================================================================================================
	// =============================================== getRendezvousAnnouncementsSortedNotInappropriate ==============================================
	// =====================================================================================================================
	@Test
	public void testGetRendezvousAnnouncementsSortedNotInappropriate(){

		System.out.println("===============================================================================================================");
		System.out.println("==================================TEST getRendezvousAnnouncementsSortedNotInappropriate=============================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query con rendezvous1.");
		System.out.println("-----------------------------------------------------------------\r");

		Integer rendezvousId = super.getEntityId("rendezvous1");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Announcement> var = this.announcementService.getRendezvousAnnouncementsSortedNotInappropriate(rendezvousId);

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}


}
