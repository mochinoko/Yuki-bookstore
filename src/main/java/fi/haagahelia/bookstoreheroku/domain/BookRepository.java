package fi.haagahelia.bookstoreheroku.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fi.haagahelia.bookstoreheroku.domain.Book;


public interface BookRepository extends CrudRepository<Book, Long>{
	List<Book> findByTitle(String title);
//	List<Book> findByTitle(@Param("title") String titel);
}
