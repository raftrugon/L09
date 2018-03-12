
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.CreditCard;
import domain.Rendezvous;
import domain.User;

import security.UserAccountService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	
	@Autowired
	private UserService		userService;
	
	//Supporting services -----------------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;
	
	// Tests ------------------------------------------------------------------
	
	//FIND_BY_USER_ACCOUNT
	//FIND_BY_PRINCIPAL, IS_RSVPD, IS_ADULT
	
	@Test
	public void testFindeOneDriver(){
		Object testingData[][] = {
				//Positive test
				{getEntityId("user1"), null},
				//Find one user with id=0
				{userService.create().getId(), IllegalArgumentException.class},
				//Find one user using anothe role id
				{getEntityId("admin"), IllegalArgumentException.class},
		};
		
		for(int i=0; i<testingData.length; i++)
			templateFindOne((Integer)testingData[i][0],(Class<?>)testingData[i][1]);
		
	}
	protected void templateFindOne(Integer userId, Class<?> expected){
		System.out.println("======================");
		System.out.println("== TEST FIND ONE =====");
		System.out.println("======================");
		
		Class<?> caught = null;
		
		try{
			User res = userService.findOne(userId);
			
			System.out.println("NOMBRE: " + res.getName() + " APELLIDOS: " + res.getSurnames());
			}
		catch(Throwable oops){
			caught =oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	@Test
	public void testCreateUserDriver(){
		System.out.println("===============================================================================================================");
		System.out.println("========================================TEST CREATE USER=======================================================");
		System.out.println("===============================================================================================================\r");
	
		Object testingData[][] = {
				{"Name", "Username", "adress", "123456789", "email@email.com", true, null}
		};
		
		for(int i =0; i<testingData.length; i++)
			this.testCreateUserTemplate((String) testingData[i][0],
			(String) testingData[i][1],
			(String) testingData[i][2],
			(String) testingData[i][3],
			(String) testingData[i][4],
			(Boolean) testingData[i][5],
			(Class<?>) testingData[i][6]);
	}
	protected void testCreateUserTemplate(final String name, final String username, final String adress, final String phoneNumber, final String email, final Boolean date, final Class<?> expected){
		Class<?> caught = null;
		
		try{
			User user = this.userService.create();
			if(date){
				user.setBirthDate(new Date(System.currentTimeMillis()-1000000000));
			}else{
				user.setBirthDate(new Date(System.currentTimeMillis()+2000000000));
			}

			this.userService.save(user);
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		
		if(expected == null)
			System.out.println("--------------POSITIVO---------------");
		else{
			System.out.println("--------------NEGATIVO---------------");
		}
	}
	public void testFindByUserAccountDriver(){
		System.out.println("===============================================================================================================");
		System.out.println("=======================================TEST FIND BY USERACCOUNT====================================================");
		System.out.println("===============================================================================================================\r");

		
	}
	public void testFindByPrincipalDriver(){
		System.out.println("===============================================================================================================");
		System.out.println("=======================================TEST FIND BY PRINCIPAL====================================================");
		System.out.println("===============================================================================================================\r");
	}
	
	@Test
	public void testIsRsvpDriver(){
		System.out.println("===============================================================================================================");
		System.out.println("=======================================TEST IS RSVP============================================================");
		System.out.println("===============================================================================================================\r");
		
		Object testingData[][] = {
				{"rendezvous1", "user1", null}
		};
		for(int i =0; i<testingData.length; i++)
			this.testIsRsvpTemplate(super.getEntityId((String) testingData [i][0]),
			((String) testingData[i][1]),
			(Class<?>) testingData[i][2]);
	}
	protected void testIsRsvpTemplate(final int rendezvousId, final String user, final Class<?> expected){
		Class<?> caught = null;
		
		try{
			super.authenticate(user);	
			this.userService.isRsvpd(rendezvousId);
			super.unauthenticate();
		}catch(Throwable oops){
			caught =oops.getClass();
		}
		this.checkExceptions(expected, caught);
		
		if(expected ==null){
			System.out.println("----------------POSITIVO--------------");
		}else
			System.out.println("----------------NEGATIVO--------------");
		
	}
	@Test
	public void testIsAdultDriver(){
		System.out.println("===============================================================================================================");
		System.out.println("=======================================TEST IS ADULT============================================================");
		System.out.println("===============================================================================================================\r");
		
		Object testingData[][] = {
				{"user1", null}
		};
		for(int i=0;i<testingData.length;i++)
			this.testIsAdultTemplate((String) testingData[i][0],(Class<?>) testingData[i][1]);
		
	}

		protected void testIsAdultTemplate(final String user, final Class<?> expected){
			Class<?> caught =null;
			
			try{
				super.authenticate(user);
				this.userService.isAdult();
				super.unauthenticate();
			}catch(Throwable oops){
				caught=oops.getClass();
			}
			this.checkExceptions(expected, caught);
			
			if(expected ==null){
				System.out.println("----------------POSITIVO--------------");
			}else
				System.out.println("----------------NEGATIVO--------------");
		}

	
	

}
