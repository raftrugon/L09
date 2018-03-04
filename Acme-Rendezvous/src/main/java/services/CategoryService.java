package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.Zervice;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AdminService adminService;

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
		categoryRepository.delete(category);
	}

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

	public Collection<String> getSubCategoriesMap(Integer categoryId) {
		if (categoryId == null)
			return categoryRepository.getFirstLevelCategoriesMap();
		else
			return categoryRepository.getSubCategoriesMap(findOne(categoryId));
	}

	public Collection<Integer> getCategoryParents(Category category) {
		List<Integer> ids = new ArrayList<Integer>();
		Category cat = category;
		while (cat.getParent() != null) {
			ids.add(cat.getParent().getId());
			cat = cat.getParent();
		}
		Collections.reverse(ids);
		return ids;
	}
}
