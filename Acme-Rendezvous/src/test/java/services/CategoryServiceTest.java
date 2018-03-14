
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Category;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CategoryService categoryService;
	
	//Supporting services -----------------------------------------------------

	// Tests ------------------------------------------------------------------
	@Test
	public void driverCreate() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST CREATE CATEGORY==================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test.
			{
				"admin", null
			},

			//Try to create a category as an anonymous user.
			{
				null, IllegalArgumentException.class
			},
			
			//Try to create a category as a different role.
			{
				"user1", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateCreate(String username, Class<?> expected) {

		Class<?> caught = null;
		Category res = null;

		try {
			authenticate(username);
			res = categoryService.create();
			res.setName("TEST NAME");
			res.setDescription("TEST DESCRIPTION");

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		authenticate(null);
		checkExceptions(expected, caught);
		
		if(res != null){
			System.out.println("NAME: " + res.getName() + "\nDESCRIPTION: " + 
					res.getDescription());
		}
	}
	
	@Test
	public void driverFindAll() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST FINDALL CATEGORY==================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test.
			{
				null
			},	

		};

		for (int i = 0; i < testingData.length; i++)
			templateFindAll((Class<?>) testingData[i][0]);
	}

	protected void templateFindAll(Class<?> expected) {

		Class<?> caught = null;
		Collection<Category> res = null;

		try {
			res = categoryService.findAll();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
		
		for(Category c: res)
			System.out.println("NAME: " + c.getName() + "\nDESCRIPTION: " + 
					c.getDescription());
		
	}
	
	@Test
	public void driverFindOne() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST FINDONE CATEGORY==================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test.
			{
				getEntityId("category1"), null
			},

			//Try to find a category of id=0.
			{
				0, IllegalArgumentException.class
			},
			
			//Try to find a category using a not valid id.
			{
				getEntityId("user1"), IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			templateFindOne((Integer) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	
	protected void templateFindOne(Integer entityId, Class<?> expected) {

		Class<?> caught = null;
		Category res = null;

		try {
			res = categoryService.findOne(entityId);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
		
		if(res != null){
			System.out.println("NAME: " + res.getName() + "\nDESCRIPTION: " + 
					res.getDescription());
		}
	}
	
	@Test
	public void driverSave() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST SAVE CATEGORY==================================================");
		System.out.println("===============================================================================================================\r");
		
		authenticate("admin");
		
		Object testingData[][] = {
			//Positive test.
			{
				"admin",categoryService.findOne(getEntityId("category1")), null, null
			},
			
			//Positive test with name field changed.
			{
				"admin",categoryService.findOne(getEntityId("category1")), "NEW NAME", null
			},
			
			//Try to save a category being anonymous.
			{
				null,categoryService.findOne(getEntityId("category1")), "ANONYMOUS", 
				IllegalArgumentException.class
			},
			
			//Try to save a category being a role different than Admin.
			{
				"user1",categoryService.findOne(getEntityId("category1")), "USER", 
				IllegalArgumentException.class
			},
			
			//Try to save a null category.
			{
				"admin",null, "NULL CATEGORY", IllegalArgumentException.class
			},
			
			//Clash two category names.
			{
				"admin", categoryService.create(),
				categoryService.findOne(getEntityId("category3")).getName(),
				IllegalArgumentException.class
			},
			
			//Restriction NotBlank.
			{
				"admin",categoryService.findOne(getEntityId("category1")), "",
				ConstraintViolationException.class
			},

		};
		
		authenticate(null);

		for (int i = 0; i < testingData.length; i++)
			templateSave((String) testingData[i][0], (Category) testingData[i][1],
					(String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	
	protected void templateSave(String username, Category category, String name,
			Class<?> expected) {

		Class<?> caught = null;	
		Category res = null;
		
		authenticate(username);
		if(category!=null){
			
			if(name!=null){
				category.setName(name);
				category.setDescription(name);
			}
			
			if(category.getId()==0){
				Category parent = categoryService.findOne(getEntityId("category2"));
				category.setParent(parent);
			}			
		}

		try {			
			res = categoryService.save(category);	
			categoryService.flush();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		authenticate(null);
		checkExceptions(expected, caught);
		
		if(res != null){
			System.out.println("NAME: " + res.getName() + "\nDESCRIPTION: " + 
					res.getDescription());
		}
	}
	
	@Test
	public void driverDelete() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST DELETE CATEGORY==================================================");
		System.out.println("===============================================================================================================\r");

		authenticate("admin");
		
		Object testingData[][] = {
			//Positive test.
			{
				"admin",getEntityId("category14"), null
			},

			//Try to delete a category being anonymous.
			{
				null,getEntityId("category14"), IllegalArgumentException.class
			},
			
			//Try to delete a category as an user.
			{
				"user1",getEntityId("category14"), IllegalArgumentException.class
			},
			
			//Try to delete a null category.
			{
				"admin",getEntityId("user1"), IllegalArgumentException.class
			},
			
			//Try to delete a non persisted category.
			{
				"admin", categoryService.create().getId(), IllegalArgumentException.class
			},
		};
		
		authenticate(null);

		for (int i = 0; i < testingData.length; i++)
			templateDelete((String) testingData[i][0], (Integer) testingData[i][1], 
					(Class<?>) testingData[i][2]);
	}
	
	protected void templateDelete(String username, Integer entityId, Class<?> expected) {

		Class<?> caught = null;
		Category res = null;
		authenticate(username);

		try {
			res = categoryService.findOne(entityId);
			categoryService.delete(res);
			categoryService.flush();
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		authenticate(null);
		checkExceptions(expected, caught);
		
	}
	
	@Test
	public void driverEditName() {
		
		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST EDITNAME CATEGORY==================================================");
		System.out.println("===============================================================================================================\r");

		authenticate("admin");
		
		Object testingData[][] = {
			//Positive test.
			{
				"admin",getEntityId("category14"), "TEST NAME", null
			},
			
			//Introduce a null categoryId.
			{
				"admin",null, "TEST NAME", NullPointerException.class
			},
			
			//Introduce a not valid categoryId.
			{
				"admin",getEntityId("user1"), "TEST NAME", IllegalArgumentException.class
			},
			
			//Introduce a null name.
			{
				"admin",getEntityId("category14"), null, IllegalArgumentException.class
			},
			
			//Introduce a categoryId = 0.
			{
				"admin",categoryService.create().getId(), "TEST NAME", 
				IllegalArgumentException.class
			},
			
			//Introduce a blank category name.
			{
				"admin",getEntityId("category14"), "", 
				IllegalArgumentException.class
			},
			
			//Try to introduce a category with two childrens with the same name.
			{
				"admin",getEntityId("category10"), categoryService.findOne(getEntityId("category8")).getName(), 
				IllegalArgumentException.class
			},

		};
		
		authenticate(null);

		for (int i = 0; i < testingData.length; i++)
			templateEditName((String) testingData[i][0], (Integer) testingData[i][1], 
					(String) testingData[i][2],(Class<?>) testingData[i][3]);
	}
	
	protected void templateEditName(String username, Integer entityId,
			String categoryName, Class<?> expected) {

		Class<?> caught = null;
		authenticate(username);
		Category res = null;

		try {
			res = categoryService.findOne(entityId);
			if(res!=null){
				System.out.println("BEFORE");
				System.out.println("NAME: " + res.getName() + "\nDESCRIPTION: " + 
						res.getDescription());
			}
			categoryService.editName(entityId, categoryName);
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		authenticate(null);
		checkExceptions(expected, caught);
		
		if(res != null){
			System.out.println("AFTER");
			System.out.println("NAME: " + res.getName() + "\nDESCRIPTION: " + 
					res.getDescription());
		}
		System.out.println("-------------------------");
		
	}

}
