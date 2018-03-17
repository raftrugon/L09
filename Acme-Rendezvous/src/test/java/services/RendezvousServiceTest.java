
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Announcement;
import domain.Rendezvous;

import utilities.AbstractTest;

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

	// Tests ------------------------------------------------------------------
	
	@Test
	public void driverFindOne() {
		Object testingData[][] = {
			//Positive test
			{
				getEntityId("rendezvous1"), null
			},

			//Find one no exist rendezvous
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

	protected void templateFindOne(int rendezvousId, Class<?> expected) {
		Class<?> caught = null;
		try {
			rendezvousService.findOne(rendezvousId);
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
	}
	
	@Test
	public void testDeleteByAdminDriver(){

		System.out.println("===============================================================================================================");
		System.out.println("=======================================TEST DELETE BY ADMIN====================================================");
		System.out.println("===============================================================================================================\r");


		Object testingData[][] = {
			{"rendezvous1","admin",null,"Correcto borrado virtual por administrador."},
			{"rendezvous2","admin",null,"Correcto borrado virtual por administrador."},
			{"rendezvous3","admin",null,"Correcto borrado virtual por administrador."},

			{"announcement8","admin",IllegalArgumentException.class,"Borrado con rendezvous como objeto."},
			{"rendezvous3",null,IllegalArgumentException.class,"Borrado sin actor registrado."},
			{"rendezvous3","user1",IllegalArgumentException.class,"Borrado por usuario."},
			{"rendezvous3","manager1",IllegalArgumentException.class,"Borrado por manager."}
		};


		for(int i = 0; i < testingData.length; i++)
			this.testDeleteByAdminTemplate(super.getEntityId((String) testingData [i][0]),
				(String) testingData [i][1],
				(Class<?>) testingData[i][2],
				(String) testingData [i][3]);
	}

	protected void testDeleteByAdminTemplate(final int rendezvousId, final String user, final Class<?> expected, final String explanation){

		Class<?> caught = null;

		try{
			super.authenticate(user);

			Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
			this.rendezvousService.deleteByAdmin(rendezvous);

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
		System.out.println("ObjectId: " + rendezvousId);
		System.out.println("User: " + user);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}
	
	@Test
	public void testDeleteByUserDriver(){

		System.out.println("===============================================================================================================");
		System.out.println("=======================================TEST DELETE BY ADMIN====================================================");
		System.out.println("===============================================================================================================\r");


		Object testingData[][] = {
			{"rendezvous1","admin",null,"Correcto borrado virtual por administrador."},
			{"rendezvous2","admin",null,"Correcto borrado virtual por administrador."},
			{"rendezvous3","admin",null,"Correcto borrado virtual por administrador."},

			{"announcement8","admin",IllegalArgumentException.class,"Borrado con rendezvous como objeto."},
			{"rendezvous3",null,IllegalArgumentException.class,"Borrado sin actor registrado."},
			{"rendezvous3","user1",IllegalArgumentException.class,"Borrado por usuario."},
			{"rendezvous3","manager1",IllegalArgumentException.class,"Borrado por manager."}
		};


		for(int i = 0; i < testingData.length; i++)
			this.testDeleteByUserTemplate(super.getEntityId((String) testingData [i][0]),
				(String) testingData [i][1],
				(Class<?>) testingData[i][2],
				(String) testingData [i][3]);
	}

	protected void testDeleteByUserTemplate(final int rendezvousId, final String user, final Class<?> expected, final String explanation){

		Class<?> caught = null;

		try{
			super.authenticate(user);

			Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
			this.rendezvousService.deleteByAdmin(rendezvous);

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
		System.out.println("ObjectId: " + rendezvousId);
		System.out.println("User: " + user);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

}
