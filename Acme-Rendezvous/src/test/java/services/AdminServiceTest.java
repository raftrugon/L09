
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Admin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
@Transactional
public class AdminServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private AdminService adminService;
	
	//Supporting services -----------------------------------------------------
	@Autowired
	private UserAccountService userAccountService;


	// Tests ------------------------------------------------------------------
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE ADMIN ==");
		System.out.println("========================");
		
		Admin res = adminService.create();
		res.setName("TEST NAME");
		res.setSurnames("TEST SURNAMES");
		
		System.out.println("NOMBRE: " + res.getName() + " APELLIDOS: " + res.getSurnames());
	}
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE ADMIN ==");
		System.out.println("========================");
		
		Admin res = adminService.findOne(getEntityId("admin"));
		
		System.out.println("NOMBRE: " + res.getName() + " APELLIDOS: " + res.getSurnames());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindOneID0(){
		System.out.println("========================");
		System.out.println("== FIND ONE ADMIN (ID 0) ==");
		System.out.println("========================");
		
		Admin admin = adminService.create();
		Admin res = adminService.findOne(admin.getId());
		
		System.out.println("NOMBRE: " + res.getName() + " APELLIDOS: " + res.getSurnames());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindOneWrongID(){
		System.out.println("========================");
		System.out.println("== FIND ONE ADMIN (Wrong ID) ==");
		System.out.println("========================");
		
		
		Admin res = adminService.findOne(getEntityId("user1"));
		
		System.out.println("NOMBRE: " + res.getName() + " APELLIDOS: " + res.getSurnames());
	}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE ADMIN ==");
		System.out.println("========================");
		
		Admin res = adminService.findOne(getEntityId("admin"));
		res.setName("TEST NAME");
		res.setSurnames("TEST SURNAMES");
		res.setEmail("test@gmail.com");
		adminService.save(res);
		
		System.out.println("NOMBRE: " + res.getName() + " APELLIDOS: " + res.getSurnames());
	}

	@Test(expected=ConstraintViolationException.class)
	public void testSaveFailValidation(){
		System.out.println("========================");
		System.out.println("== SAVE ADMIN (FAIL VALIDATION) ==");
		System.out.println("========================");
		
		Admin res = adminService.findOne(getEntityId("admin"));
		res.setName("TEST NAME");
		res.setSurnames("");
		res.setEmail("");
		adminService.save(res);
		adminService.flush();
		
		System.out.println("NOMBRE: " + res.getName() + " APELLIDOS: " + res.getSurnames());
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE ADMIN ==");
		System.out.println("========================");
		
		Admin res = adminService.findOne(getEntityId("admin"));
		adminService.delete(res);
		
		System.out.println("DELETED ADMIN");
	}
	
	@Test
	public void testFindByUserAccount(){
		System.out.println("========================");
		System.out.println("== FINDBYUSERACCOUNT ==");
		System.out.println("========================");
		
		UserAccount userAccount = userAccountService.findOne(getEntityId("userAccount1"));
		Admin res = adminService.findByUserAccount(userAccount);
		
		System.out.println("NOMBRE: " + res.getName() + " APELLIDOS: " + res.getSurnames());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindByUserAccountNull(){
		System.out.println("========================");
		System.out.println("== FINDBYUSERACCOUNT NULL ==");
		System.out.println("========================");
		
		adminService.findByUserAccount(null);
				
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindByUserAccountWrong(){
		System.out.println("========================");
		System.out.println("== FINDBYUSERACCOUNT WRONG ==");
		System.out.println("========================");
		
		UserAccount userAccount = userAccountService.findOne(getEntityId("userAccount2"));
		Admin admin = adminService.findByUserAccount(userAccount);
		Assert.notNull(admin);
				
	}
	
	@Test
	public void testFindByPrincipal(){
		System.out.println("========================");
		System.out.println("== FINDBYPRINCIPAL ==");
		System.out.println("========================");
		
		authenticate("admin");
		Admin res = adminService.findByPrincipal();
		authenticate(null);
		
		System.out.println("NOMBRE: " + res.getName() + " APELLIDOS: " + res.getSurnames());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindByPrincipalWrong(){
		System.out.println("========================");
		System.out.println("== FINDBYPRINCIPAL WRONG ==");
		System.out.println("========================");
		
		authenticate("user1");
		Admin admin = adminService.findByPrincipal();
		Assert.notNull(admin);
		authenticate(null);
				
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindByPrincipalAnonymous(){
		System.out.println("========================");
		System.out.println("== FINDBYPRINCIPAL ANONYMOUS ==");
		System.out.println("========================");
		
		Admin admin = adminService.findByPrincipal();
		Assert.notNull(admin);

	}

}
