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
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    // View all tasks for the logged-in user
    @GetMapping("/todos")
    public String getTodos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        model.addAttribute("todos", todoService.findByUsername(username));
        return "todos";
    }

    // Add a new task
    @PostMapping("/add")
    public String addTodo(@RequestParam("title") String title,
                          @RequestParam(value = "description", required = false) String description,
                          @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        User user=userService.findByUsername(username);
        
        TodoItem todo = new TodoItem();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(false);
        todo.setUser(user);

        todoService.save(todo);
        return "redirect:/todos";
    }

    // Mark a task as completed
    @PostMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id,
                             @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();  // Get username string
        User user = userService.findByUsername(username);  // Convert to User entity
        todoService.deleteById(id, user);  // Pass User, not String
        return "redirect:/todos";
    }

    @PostMapping("/{id}/complete")
    public String completeTodo(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        User user2=userService.findByUsername(username);
        todoService.markAsCompleted(id, user2);
        return "redirect:/todos";
    }


}
