
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Comment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	CommentService			commentService;

	//Supporting services -----------------------------------------------------

	// Tests ------------------------------------------------------------------

	// =====================================================================================================================
	// =================================================== CREATE COMMENT ==================================================
	// =====================================================================================================================
	@Test
	public void testCreateCommentDriver(){

		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST CREATE COMMENT====================================================");
		System.out.println("===============================================================================================================\r");


		Object testingData[][] = {
			{"rendezvous1","user1",		"text",null,"Correcta creacion comentario."},
			{"rendezvous2","user4",		"text",null,"Correcta creacion comentario."},
			{"rendezvous3","user2",		"text",null,"Correcta creacion comentario."},
			{"rendezvous3","user3",		"text",null,"Correcta creacion comentario."},
			{"rendezvous4","user2",		"text",null,"Correcta creacion comentario."},
			{"rendezvous2","user1",		"text",null,"Correcta creacion comentario."},

			{"rendezvous3","user1",		"text",IllegalArgumentException.class,"Announcement en Rendezvous sin RSVP"},
			{"rendezvous1",null,		"text",IllegalArgumentException.class,"Creación sin usuario."},
			{"rendezvous1","manager1",	"text",IllegalArgumentException.class,"Creación por manager1."},
			{"rendezvous1","admin",		"text",IllegalArgumentException.class,"Creación por administrador."},
			{"rendezvous1","user1",		null,IllegalArgumentException.class,"Creación de comentario con texto nulo."},
			{"rendezvous1","user1",		"",IllegalArgumentException.class,"Creación de comentario con texto vacío."}

		};


		for(int i = 0; i < testingData.length; i++)
			this.testCreateCommentTemplate(super.getEntityId((String) testingData [i][0]),
				(String) testingData [i][1],
				(String) testingData [i][2],
				(Class<?>) testingData[i][3],
				(String) testingData [i][4]);
	}

	protected void testCreateCommentTemplate(final int rendezvousId, final String user, final String text, final Class<?> expected, final String explanation){

		Class<?> caught = null;

		try{
			super.authenticate(user);

			Comment comment = this.commentService.createComment(rendezvousId);
			comment.setText(text);
			this.commentService.save(comment);

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
		System.out.println("Texto: " + text);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}



	// =====================================================================================================================
	// ==================================================== CREATE REPLY ===================================================
	// =====================================================================================================================
	@Test
	public void testCreateReplyDriver(){

		System.out.println("===============================================================================================================");
		System.out.println("=========================================TEST CREATE REPLY=====================================================");
		System.out.println("===============================================================================================================\r");


		Object testingData[][] = {
			{"comment1","user1",		"text",null,"Correcta creacion reply."},
			{"comment13","user1",		"text",null,"Correcta creacion reply."},
			{"comment6","user3",		"text",null,"Correcta creacion reply."},
			{"comment13","user2",		"text",null,"Correcta creacion reply."},
			{"comment13","user4",		"text",null,"Correcta creacion reply."},
			{"comment6","user2",		"text",null,"Correcta creacion reply."},
			{"comment1","user2",		"text",null,"Autorespuesta a un reply."},

			{"comment4","user2",		"text",IllegalArgumentException.class,"Reply a comentario sin RSVP."},
			{"comment5","user1",		"text",IllegalArgumentException.class,"Reply a reply con RSVP."},
			{"comment1","user1",		null,IllegalArgumentException.class,"Reply con texto nulo."},
			{"comment1","user1",		"",IllegalArgumentException.class,"Reply con texto vacío."},
			{"comment1","manager1",		"text",IllegalArgumentException.class,"Reply por manager1."},
			{"comment1","admin",		"text",IllegalArgumentException.class,"Reply por administrador."},
			{"comment1",null,			null,IllegalArgumentException.class,"Reply con usuario nulo."},


		};


		for(int i = 0; i < testingData.length; i++)
			this.testCreateReplyTemplate(super.getEntityId((String) testingData [i][0]),
				(String) testingData [i][1],
				(String) testingData [i][2],
				(Class<?>) testingData[i][3],
				(String) testingData [i][4]);
	}

	protected void testCreateReplyTemplate(final int commentId, final String user, final String text, final Class<?> expected, final String explanation){

		Class<?> caught = null;

		try{
			super.authenticate(user);

			Comment reply = this.commentService.createReply(commentId);
			reply.setText(text);
			this.commentService.save(reply);

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
		System.out.println("CommentId: " + commentId);
		System.out.println("User: " + user);
		System.out.println("Texto: " + text);
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


		Integer commentId = super.getEntityId("comment2");	//Rendezvous1

		System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: Modificación de un comment.");
		System.out.println("CommentId: "+commentId);
		System.out.println("User: user1");
		System.out.println("-----------------------------------------------------------------\r");


		super.authenticate("user1");						//User del rendezvous1

		Comment comment = this.commentService.findOne(commentId);

		comment.setText("TEST");

		this.commentService.save(comment);

		super.unauthenticate();
		System.out.println("----------------------------FAILED-------------------------------\r");

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
			{"comment1",null,"Busqueda de comment1."},
			{"comment3",null,"Busqueda de comment3."},
			{"comment6",null,"Busqueda de comment6."},

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
			Comment comment = this.commentService.findOne(objectId);

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
			{"comment1","admin",null,"Correcto borrado virtual del comment1 por administrador."},
			{"comment3","admin",null,"Correcto borrado virtual del comment3 por administrador."},
			{"comment4","admin",null,"Correcto borrado virtual del comment4 por administrador."},
			{"comment8","admin",null,"Correcto borrado virtual del comment8 por administrador."},

			{"rendezvous1","admin",IllegalArgumentException.class,"Borrado con rendezvous como objeto."},
			{"comment1",null,IllegalArgumentException.class,"Borrado sin actor registrado."},
			{"comment1","user1",IllegalArgumentException.class,"Borrado por usuario."},
			{"comment1","manager1",IllegalArgumentException.class,"Borrado por manager."}
		};


		for(int i = 0; i < testingData.length; i++)
			this.testDeleteByAdminTemplate(super.getEntityId((String) testingData [i][0]),
				(String) testingData [i][1],
				(Class<?>) testingData[i][2],
				(String) testingData [i][3]);
	}

	protected void testDeleteByAdminTemplate(final int commentId, final String user, final Class<?> expected, final String explanation){

		Class<?> caught = null;

		try{
			super.authenticate(user);

			Comment comment = this.commentService.findOne(commentId);
			this.commentService.deleteByAdmin(comment);

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
		System.out.println("ObjectId: " + commentId);
		System.out.println("User: " + user);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	//Other Business Methods --------------------------------

	// =====================================================================================================================
	// =============================================== getCommentRepliesStats ==============================================
	// =====================================================================================================================
	@Test
	public void testGetCommentRepliesStats(){

		System.out.println("===============================================================================================================");
		System.out.println("====================================TEST getCommentRepliesStats================================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query.");
		System.out.println("-----------------------------------------------------------------\r");


		super.authenticate(null);

		@SuppressWarnings("unused")
		Double[] var = this.commentService.getCommentRepliesStats();

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}


	// =====================================================================================================================
	// =============================================== getCommentRepliesStats ==============================================
	// =====================================================================================================================
	@Test
	public void testGetRendezvousCommentsSorted(){

		System.out.println("===============================================================================================================");
		System.out.println("==================================TEST getRendezvousCommentsSorted=============================================");
		System.out.println("===============================================================================================================\r");




		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query con rendezvous1.");
		System.out.println("-----------------------------------------------------------------\r");

		Integer rendezvousId = super.getEntityId("rendezvous1");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Comment> var = this.commentService.getRendezvousCommentsSorted(rendezvousId);

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}
}
