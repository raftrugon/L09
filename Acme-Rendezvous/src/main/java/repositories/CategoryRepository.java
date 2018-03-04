package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>{

	@Query("select count(c) from Category c where c.parent = ?1 and c.name = ?2")
	int nameClashes(Category cP, String name);

	@Query("select concat(c.id,'$$',c.name,'$$',c.categories.size) from Category c where c.parent is null")
	Collection<String> getFirstLevelCategoriesMap();
	
	@Query("select concat(c.id,'$$',c.name,'$$',c.categories.size) from Category c where c.parent = ?1")
	Collection<String> getSubCategoriesMap(Category category);

}
