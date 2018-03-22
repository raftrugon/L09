package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CategoryRepository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Category;
import domain.Zervice;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository categoryRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdminService adminService;
	@Autowired
	private Validator validator;

	// Simple CRUD methods ----------------------------------------------------
	public Category create() {
		Assert.notNull(adminService.findByPrincipal());

		Category c = new Category();
		c.setCategories(new ArrayList<Category>());
		c.setZervices(new ArrayList<Zervice>());

		return c;
	}

	public Collection<Category> findAll() {
		Collection<Category> res = categoryRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Category findOne(int categoryId) {
		Assert.isTrue(categoryId != 0);
		Category res = categoryRepository.findOne(categoryId);
		Assert.notNull(res);
		return res;
	}

	public Category save(Category category) {
		Assert.notNull(adminService.findByPrincipal());
		Assert.notNull(category);
		if (category.getId() == 0)
			Assert.isTrue(!nameClashes(category));
		Category res = categoryRepository.save(category);
		return res;
	}

	public void delete(Category category) {
		Assert.notNull(adminService.findByPrincipal());
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		Assert.isTrue(findAll().contains(category));
		Assert.isTrue(category.getCategories().isEmpty());
		Assert.isTrue(category.getZervices().isEmpty());
		categoryRepository.delete(category);
	}

	public void flush() {
		categoryRepository.flush();
	}

	//Other Business Methods --------------------------------
	public Category editName(Integer categoryId, String categoryName) {
		Assert.notNull(categoryId);
		Assert.notNull(categoryName);
		Assert.isTrue(categoryId != 0);
		Assert.isTrue(categoryName != "");
		Category c = categoryRepository.findOne(categoryId);
		Assert.isTrue(!nameClashesAux(categoryName, c.getParent()));
		c.setName(categoryName);
		Category res = save(c);
		Assert.notNull(res);
		return res;
	}

	public boolean nameClashes(Category c) {
		Boolean res = false;
		if (categoryRepository.nameClashes(c.getParent(), c.getName()) != 0)
			res = true;
		return res;
	}

	public boolean nameClashesAux(String name, Category parent) {
		Category c = create();
		c.setName(name);
		c.setParent(parent);
		return nameClashes(c);
	}

	public JsonArray getCategoriesJson(Category category){
		return getCategoriesJson(category, false,0);
	}

	public JsonArray getCategoriesJson(Category category,Boolean admin){
		return getCategoriesJson(category, admin, 0);
	}

	public JsonArray getCategoriesJson(Category category, Boolean admin,Integer level){
		if(level == null)level = 0;
		JsonArray json = new JsonArray();
		Collection<Category> subCategories;
		if(category == null)
			subCategories = categoryRepository.getFirstLevelCategories();
		else
			subCategories = category.getCategories();
		for(Category c: subCategories){
			JsonObject subJson = new JsonObject();
			String text = "<strong>"+c.getName()+"</strong><br/>";
			for(int i=0;i<=level;i++) text += "<span class='indent'></span>";
			text += "<small>"+c.getDescription()+"</small>";
			subJson.addProperty("text", text);
			if(!admin)
				subJson.addProperty("selectedIcon", "glyphicon glyphicon-ok");
			subJson.addProperty("categoryId",c.getId());
			if(!c.getCategories().isEmpty()){
				subJson.add("nodes", getCategoriesJson(c,admin,level+1));
				JsonArray tags = new JsonArray();
				tags.add(c.getCategories().size());
				subJson.add("tags", tags);
			}
			json.add(subJson);
		}
		return json;
	}

	public Collection<Category> getSelectedTree(Collection<Category> categories){
		Collection<Category> res = new HashSet<Category>();
		res.addAll(categories);
		for(Category c: categories)
			try{
				res.addAll(getSelectedTree(c.getCategories()));
			}catch(Throwable oops){oops.printStackTrace();}
		return res;
	}

	public Collection<Integer> getSelectedTreeIds(Collection<Integer> categoriesId){
		Collection<Integer> res = new HashSet<Integer>();
		res.addAll(categoriesId);
		for(Integer id: categoriesId)
			try{
				Category c = findOne(id);
				Collection<Integer> ids = new ArrayList<Integer>();
				for(Category c2: c.getCategories())ids.add(c2.getId());
				res.addAll(getSelectedTreeIds(ids));
			}catch(Throwable oops){oops.printStackTrace();}
		return res;
	}

	public Double getAvgRatioOfZervicesInEachCategory() {
		return categoryRepository.getAvgRatioOfZervicesInEachCategory();
	}

	public Category reconstruct(Category category, BindingResult binding){
		Category copy = new Category();
		if(category.getId() == 0){
			category.setVersion(0);
			category.setCategories(new ArrayList<Category>());
			category.setZervices(new ArrayList<Zervice>());
			validator.validate(category,binding);
			return category;
		}else{
			Category bd = findOne(category.getId());

			copy.setName(category.getName());
			copy.setDescription(category.getDescription());
			copy.setParent(category.getParent());

			copy.setCategories(new ArrayList<Category>(bd.getCategories()));
			copy.setZervices(new ArrayList<Zervice>(bd.getZervices()));

			copy.setVersion(category.getVersion());
			copy.setId(category.getId());

			validator.validate(copy, binding);
			return copy;
		}
	}

}
