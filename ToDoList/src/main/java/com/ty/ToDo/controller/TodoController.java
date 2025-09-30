package com.ty.ToDo.controller;

import com.ty.ToDo.model.TodoItem;
import com.ty.ToDo.model.User;
import com.ty.ToDo.service.TodoService;
import com.ty.ToDo.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping("/todos")
    public String getTodos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        model.addAttribute("todos", todoService.findByUsername(username));
        return "todos";
    }

    @PostMapping("/todos")
    public String addTodo(@ModelAttribute TodoItem todo, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        todo.setUser(user);
        todoService.save(todo);
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/complete")
    public String completeTodo(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        todoService.markAsCompleted(id, userDetails.getUsername());
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        todoService.deleteTodo(id, userDetails.getUsername());
        return "redirect:/todos";
    }
}
