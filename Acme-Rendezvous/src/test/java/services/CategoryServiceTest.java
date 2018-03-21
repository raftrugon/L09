
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

import com.google.gson.JsonArray;

import domain.Category;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CategoryService	categoryService;


	//Supporting services -----------------------------------------------------

	// Tests ------------------------------------------------------------------

	@Test
	public void driverJson() {

		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST JSON CATEGORY==================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test.
			{
				getEntityId("category1"), false, 1, null, "Test positivo"
			},

			//Try to find a category of id=0.
			{
				getEntityId("category2"), false, null, null, "Level null"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateJson((Integer) testingData[i][0], (Boolean) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3], (String) testingData[i][4]);
	}

	protected void templateJson(Integer id, Boolean bool, Integer level, Class<?> expected, String explanation) {

		Class<?> caught = null;
		Category c = categoryService.findOne(id);
		try {
			JsonArray res = categoryService.getCategoriesJson(c, bool, level);

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
		//System.out.println("Username: " + username);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");
	}

	@Test
	public void driverCreate() {

		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST CREATE CATEGORY==================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test.
			{
				"admin", null, "Creación correcta de categoria"
			},

			//Try to create a category as an anonymous user.
			{
				null, IllegalArgumentException.class, "Creación incorrecta, intentar crear una categoria sin estar logeado"
			},

			//Try to create a category as a different role.
			{
				"user1", IllegalArgumentException.class, "Creación incorrecta, intentar crear una categoria estando logeado como user"
			},
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void templateCreate(String username, Class<?> expected, String explanation) {

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

		// --------------------------------- CONSOLA ---------------------------------

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Username: " + username);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");
	}

	@Test
	public void driverFindAll() {

		System.out.println("===============================================================================================================");
		System.out.println("=====================================TEST FINDALL CATEGORY==================================================");
		System.out.println("===============================================================================================================\r");

		Object testingData[][] = {
			//Positive test.
			{
				null, "Find all correcto"
			},

		};

		for (int i = 0; i < testingData.length; i++)
			templateFindAll((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	protected void templateFindAll(Class<?> expected, String explanation) {

		Class<?> caught = null;
		Collection<Category> res = null;

		try {
			res = categoryService.findAll();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

		// --------------------------------- CONSOLA ---------------------------------

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		for (Category c : res) {
			System.out.println("Name: " + c.getName());
			System.out.println("Description: " + c.getDescription());
			System.out.println("Explicación: " + explanation);

			System.out.println("\r¿Correcto? " + (expected == caught));
			System.out.println("-----------------------------------------------------------------\r");
		}

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

		if (res != null) {
			System.out.println("NAME: " + res.getName() + "\nDESCRIPTION: " + res.getDescription());
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
				"admin", categoryService.findOne(getEntityId("category1")), null, null, "Guardado correctamente"
			},

			//Positive test with name field changed.
			{
				"admin", categoryService.findOne(getEntityId("category1")), "NEW NAME", null, "Guardado correctamente"
			},

			//Try to save a category being anonymous.
			{
				null, categoryService.findOne(getEntityId("category1")), "ANONYMOUS", IllegalArgumentException.class, "Intento de guardar una categoria sin estar logeado"
			},

			//Try to save a category being a role different than Admin.
			{
				"user1", categoryService.findOne(getEntityId("category1")), "USER", IllegalArgumentException.class, "Intento de guardar una categoria siendo un user"
			},

			//Try to save a null category.
			{
				"admin", null, "NULL CATEGORY", IllegalArgumentException.class, "Intento de guardar una categoria nula"
			},

			//Restriction NotBlank.
			{
				"admin", categoryService.findOne(getEntityId("category1")), "", ConstraintViolationException.class, "Intento de guardar una categoria con el nombre en blanco"
			},

		};

		authenticate(null);

		for (int i = 0; i < testingData.length; i++)
			templateSave((String) testingData[i][0], (Category) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3], (String) testingData[i][4]);
	}

	protected void templateSave(String username, Category category, String name, Class<?> expected, String explanation) {

		Class<?> caught = null;
		Category res = null;

		authenticate(username);
		if (category != null) {

			if (name != null) {
				category.setName(name);
				category.setDescription(name);
			}

			if (category.getId() == 0) {
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

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Category: " + category);
		System.out.println("User: " + username);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");
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
				"admin", getEntityId("category14"), null, "Borrado de una categoria correctamente"
			},

			//Try to delete a category being anonymous.
			{
				null, getEntityId("category14"), IllegalArgumentException.class, "Intento de borrar una categoria sin estar logeado"
			},

			//Try to delete a category as an user.
			{
				"user1", getEntityId("category14"), IllegalArgumentException.class, "Intento de borrar una categoria siendo user"
			},

			//Try to delete a null category.
			{
				"admin", getEntityId("user1"), IllegalArgumentException.class, "Intento de borrar una categoria con otra entidad"
			},

			//Try to delete a non persisted category.
			{
				"admin", categoryService.create().getId(), IllegalArgumentException.class, "Intento de borrar una categoria que no esta persistida"
			},
		};

		authenticate(null);

		for (int i = 0; i < testingData.length; i++)
			templateDelete((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}

	protected void templateDelete(String username, Integer entityId, Class<?> expected, String explanation) {

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
		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("CategoriaId: " + entityId);
		System.out.println("User: " + username);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

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
				"admin", getEntityId("category14"), "TEST NAME", null, "Editar el nombre correctamente"
			},

			//Introduce a null categoryId.
			{
				"admin", null, "TEST NAME", NullPointerException.class, "Intento de editar una categoria siendo nula"
			},

			//Introduce a not valid categoryId.
			{
				"admin", getEntityId("user1"), "TEST NAME", IllegalArgumentException.class, "Intento de editar una categoria siendo la entidad user en vez de categoria"
			},

			//Introduce a null name.
			{
				"admin", getEntityId("category14"), null, IllegalArgumentException.class, "Intento de editar una categoria con el nombre a null"
			},

			//Introduce a categoryId = 0.
			{
				"admin", categoryService.create().getId(), "TEST NAME", IllegalArgumentException.class, "Inetnto de editar una categoria que no esta persistida"
			},

			//Introduce a blank category name.
			{
				"admin", getEntityId("category14"), "", IllegalArgumentException.class, "Intento de editar una categoria con el nombre en blanco"
			},

		};

		authenticate(null);

		for (int i = 0; i < testingData.length; i++)
			templateEditName((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3], (String) testingData[i][4]);
	}

	protected void templateEditName(String username, Integer entityId, String categoryName, Class<?> expected, String explanation) {

		Class<?> caught = null;
		authenticate(username);
		Category res = null;

		try {
			res = categoryService.findOne(entityId);
			if (res != null) {
				System.out.println("BEFORE");
				System.out.println("NAME: " + res.getName() + "\nDESCRIPTION: " + res.getDescription());
			}
			categoryService.editName(entityId, categoryName);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		authenticate(null);
		checkExceptions(expected, caught);

		if (expected == null)
			System.out.println("---------------------------- POSITIVO ---------------------------");
		else
			System.out.println("---------------------------- NEGATIVO ---------------------------");
		System.out.println("Explicación: " + explanation);
		System.out.println("Categoria: " + entityId);
		System.out.println("User: " + username);
		System.out.println("\r¿Correcto? " + (expected == caught));
		System.out.println("-----------------------------------------------------------------\r");

	}

	@Test
	public void testnameClashes() {

		System.out.println("===============================================================================================================");
		System.out.println("==================================TEST nameClashes=============================================");
		System.out.println("===============================================================================================================\r");

		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query con category1.");
		System.out.println("-----------------------------------------------------------------\r");

		Category category = categoryService.findOne(super.getEntityId("category1"));

		super.authenticate(null);

		@SuppressWarnings("unused")
		Boolean var = this.categoryService.nameClashes(category);

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	@Test
	public void testgetSelectedTree() {

		System.out.println("===============================================================================================================");
		System.out.println("==================================TEST getSelectedTree=============================================");
		System.out.println("===============================================================================================================\r");

		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("Explicación: Llamada al método de la query con el conjunto de categorias.");
		System.out.println("-----------------------------------------------------------------\r");

		Collection<Category> category = categoryService.findAll();

		super.authenticate(null);

		@SuppressWarnings("unused")
		Collection<Category> res = this.categoryService.getSelectedTree(category);

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}

	@Test
	public void testGetAvgRatioOfZervicesInEachCategory() {

		System.out.println("===============================================================================================================");
		System.out.println("==================================TEST getAvgRatioOfZervicesInEachCategory=============================================");
		System.out.println("===============================================================================================================\r");

		System.out.println("-------------------------- POSITIVO -----------------------------");
		System.out.println("-----------------------------------------------------------------\r");

		super.authenticate(null);

		@SuppressWarnings("unused")
		Double var = this.categoryService.getAvgRatioOfZervicesInEachCategory();

		super.unauthenticate();
		System.out.println("----------------------------PASSED-------------------------------\r");

	}
}
