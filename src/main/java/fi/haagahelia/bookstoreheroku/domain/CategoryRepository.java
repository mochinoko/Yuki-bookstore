package fi.haagahelia.bookstoreheroku.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.bookstoreheroku.domain.Category;


public interface CategoryRepository extends CrudRepository<Category, Long>{
	List<Category> findByName(String name);
}
