package fi.haagahelia.bookstoreheroku.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import fi.haagahelia.bookstoreheroku.domain.SignupForm;
import fi.haagahelia.bookstoreheroku.domain.User;
import fi.haagahelia.bookstoreheroku.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
    private UserRepository urepository; 

	 @RequestMapping(value = "signup")
	    public String addStudent(Model model){
	    	model.addAttribute("signupform", new SignupForm());
	        return "signup";
	    }	
	 
	 	/**
	     * Create new user
	     * Check if user already exists & form validation
	     * 
	     * @param signupForm
	     * @param bindingResult
	     * @return
	     */
	 
	    @RequestMapping(value = "saveuser", method = RequestMethod.POST)
	    public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
	    	System.out.println("SAVESAVE");
	    	if (!bindingResult.hasErrors()) { // validation errors
	    		System.out.println("ERRORS");
	    		System.out.println(bindingResult);
	    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check password match		
	    			System.out.println("NOTERRORS");	
	    			String pwd = signupForm.getPassword();
			    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			    	String hashPwd = bc.encode(pwd);
		
			    	User newUser = new User();
			    	newUser.setPasswordHash(hashPwd);
			    	newUser.setUsername(signupForm.getUsername());
			    	newUser.setRole("USER");
			    	
			    
			    	if (urepository.findByUsername(signupForm.getUsername()) == null) { // Check if user exists
			    		System.out.println("fetch all USERS");
			    		urepository.save(newUser);
			    		for (User user : urepository.findAll()) {
							System.out.println(user.toString());
						}
			    	}
			    	else {
		    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
		    			return "signup";		    		
			    	}
	    		}
	    		else {
	    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
	    			return "signup";
	    		}
	    	}
	    	else {
	    		return "signup";
	    	}
	    	return "redirect:/login";    	
	    }    
	
}
