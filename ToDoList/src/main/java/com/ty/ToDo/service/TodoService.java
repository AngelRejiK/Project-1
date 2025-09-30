package com.ty.ToDo.service;

import com.ty.ToDo.model.TodoItem;
import com.ty.ToDo.model.User;
import com.ty.ToDo.repository.TodoRepository;
import com.ty.ToDo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public List<TodoItem> findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return todoRepository.findByUser(user);
    }

    public void save(TodoItem todo) {
        todoRepository.save(todo);
    }

    public void markAsCompleted(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        TodoItem todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Task not found or not yours"));
        todo.setCompleted(true);
        todoRepository.save(todo);
    }

    public void deleteTodo(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        TodoItem todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Task not found or not yours"));
        todoRepository.delete(todo);
    }
}
