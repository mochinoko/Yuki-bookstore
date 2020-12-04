package fi.haagahelia.bookstoreheroku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.bookstoreheroku.domain.Book;
import fi.haagahelia.bookstoreheroku.domain.BookRepository;
import fi.haagahelia.bookstoreheroku.domain.Category;
import fi.haagahelia.bookstoreheroku.domain.CategoryRepository;
import fi.haagahelia.bookstoreheroku.domain.User;
import fi.haagahelia.bookstoreheroku.domain.UserRepository;

@SpringBootApplication
public class BookstoreherokuApplication {
	private static final Logger log = LoggerFactory.getLogger(BookstoreherokuApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(BookstoreherokuApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner bookDemo(BookRepository repository, CategoryRepository crepo, UserRepository urepository) {
		return (args) -> {
			log.info("save a couple of students");
			repository.deleteAll();
			crepo.deleteAll();
			urepository.deleteAll();
			
			crepo.save(new Category(""));
			crepo.save(new Category("ScienceFiction"));
			crepo.save(new Category("Classics"));
			crepo.save(new Category("Horror"));
			crepo.save(new Category("Action & Adventure"));
			crepo.save(new Category("Comic Book"));
			crepo.save(new Category("Detective and Mystery"));
			crepo.save(new Category("Fantasy"));
			crepo.save(new Category("Romance"));
			crepo.save(new Category("Short Stories"));
			crepo.save(new Category("Thrillers"));
			crepo.save(new Category("Realism"));
			crepo.save(new Category("Political Satire"));
			crepo.save(new Category("Academic and Professional"));
			
			repository.save(new Book("Animal Farm", "George Orwell", 1945, "123131-321", 10.25,  crepo.findByName("Political Satire").get(0)));
			repository.save(new Book("The Problem of Increasing Human Energy", "Nikola Tesla", 1900, "6543231-121", 45.95,  crepo.findByName("Academic and Professional").get(0)));
			repository.save(new Book("The Alchemist", "Homer", 1993, "1212341-112", 13.55,  crepo.findByName("Fantasy").get(0)));			
			
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("yuki", "$2a$10$RCsJXrLwXF5P76aCLN1hAen776G9D2KRWlpeXQullGfIpycvwienC", "USER"); //pw: useryuki
			User user3 = new User("nanashiadmin", "$2a$10$H/jl4Xnd7ZrYtE.4oDzpPu8h/d4XRW87qzViol8cvYczeJmgo8Oha", "ADMIN"); // pw: nanashinogonbei
			User user4 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			
			urepository.save(user1);
			urepository.save(user2);
			urepository.save(user3);
			urepository.save(user4);
			
			log.info("fetch all students");
			for (Book book : repository.findAll()) {
				log.info(book.toString());
			}
		};
	}
}
