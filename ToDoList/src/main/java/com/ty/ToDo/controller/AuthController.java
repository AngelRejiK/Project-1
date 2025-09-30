package com.ty.ToDo.controller;

import com.ty.ToDo.model.User;
import com.ty.ToDo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(User user) {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        userService.save(user);
        return "redirect:/login";
    }
}
