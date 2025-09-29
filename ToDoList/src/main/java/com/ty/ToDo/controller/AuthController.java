package com.ty.ToDo.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ty.ToDo.model.User;
import com.ty.ToDo.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {
	private final UserService userService;
	final UserDetails userDetails;
	public AuthController(UserService userService,UserDetails userDetails) {
        this.userService = userService;
        this.userDetails=userDetails;
    }

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error,
	                    @RequestParam(required = false) String logout,
	                    Model model) {
	    if (error != null) {
	        model.addAttribute("errorMessage", "Invalid username or password");
	    }

	    if (logout != null) {
	        model.addAttribute("logoutMessage", "You have been logged out successfully");
	    }
	    
	    String username = userDetails.getUsername();
	    boolean exists = userService.existsByUsername(username);
        if(exists==true)
        	return "redirect:/todos";

	    return "login";  // <-- Return the login page here, e.g. login.html
	}


    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());  // add empty user for form binding
        return "register";  // return register page view
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid User user,
                               BindingResult bindingResult,
                               @RequestParam String confirmPassword,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "register";  // validation errors, show form again
        }

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "register";
        }

        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("usernameError", "Username already exists");
            return "register";
        }

        userService.save(user);


        return "redirect:/login?registered";  // redirect after successful registration
    }
}
