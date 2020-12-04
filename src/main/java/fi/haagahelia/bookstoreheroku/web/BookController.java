package fi.haagahelia.bookstoreheroku.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.bookstoreheroku.domain.Book;
import fi.haagahelia.bookstoreheroku.domain.BookRepository;
import fi.haagahelia.bookstoreheroku.domain.Category;
import fi.haagahelia.bookstoreheroku.domain.CategoryRepository;
import fi.haagahelia.bookstoreheroku.domain.SignupForm;


@Controller
public class BookController {

	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CategoryRepository crepo;
	
	
	@RequestMapping(value="/login")
	public String login() {
		return "loginpage";
	}
	
	@RequestMapping(value = {"/booklist"})
	public String bookList(Model model) {
		model.addAttribute("books", repository.findAll());
		return "booklist";
	}
	
	// REST --> get all books
	@RequestMapping(value="/books/", method = RequestMethod.GET)
	public @ResponseBody List<Book> booklistRest() {
		return (List<Book>) repository.findAll();
	}
	
	// REST --> get books by id
	@RequestMapping(value="/books/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bookId) {
		return repository.findById(bookId);
	}
	
	@RequestMapping(value = "/add")
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("categories", crepo.findAll());
		return "addbook";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(Book book) {
		repository.save(book);
		return "redirect:booklist";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)	
	public String deletBook(@PathVariable("id") Long id, Model model) {
		repository.deleteById(id);
		return "redirect:../booklist";
	}
	
	// REST --> get books by id and delete

		@RequestMapping(value="/api/del/{id}", method = RequestMethod.GET)
		public @ResponseBody List<Book> delBookRest(@PathVariable("id") Long bookId) {
			repository.deleteById(bookId);
			return (List<Book>) repository.findAll();
		}
//	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/edit/{id}")
	public String editBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", repository.findById(id));
		model.addAttribute("categories", crepo.findAll());
		return "edit";
	}
	
	// REST --> editing books
	@GetMapping(value="/editId/{id}")
	public @ResponseBody Optional<Book> editBookRest(@PathVariable("id") Long bookId, Model model) {
		repository.findById(bookId);
		model.addAttribute("categories", crepo.findAll());
		return repository.findById(bookId);
	}
	
	@GetMapping(value = "/saveEdit")
	public String saveEdit(Book book) {
		repository.save(book);
		return "redirect:booklist";
	}
}
