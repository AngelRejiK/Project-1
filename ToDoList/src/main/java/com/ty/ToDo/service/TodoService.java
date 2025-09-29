package com.ty.ToDo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ty.ToDo.model.TodoItem;
import com.ty.ToDo.model.User;
import com.ty.ToDo.repository.TodoRepository;
@Service
public class TodoService {
	private final UserService userService;
	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository, UserService userService) {
	    this.todoRepository = todoRepository;
	    this.userService = userService;
	}

	public List<TodoItem> findByUsername(String username) {
        User user = userService.findByUsername(username);
        return todoRepository.findByUser(user);
    }

    public List<TodoItem> findByUser(User user) {
        return todoRepository.findByUser(user);
    }
	
	public void markAsCompleted(Long id, User user) {
		Optional<TodoItem> todoOpt = todoRepository.findById(id);

        if (todoOpt.isPresent()) {
            TodoItem todo = todoOpt.get();

            if (todo.getUser().equals(user)) {
                todo.setCompleted(true);
                todoRepository.save(todo);
            } else {
                throw new IllegalArgumentException("You don't have permission to complete this task.");
            }
        } else {
            throw new IllegalArgumentException("Task not found.");
        }
		
	}

	public void deleteById(Long id, User user) {
		Optional<TodoItem> todoOpt = todoRepository.findById(id);
		if (todoOpt.isPresent()) {
            TodoItem todo = todoOpt.get();

            if (todo.getUser().equals(user)) {
                todoRepository.delete(todo);
            } else {
                throw new IllegalArgumentException("You don't have permission to delete this task.");
            }
        } else {
            throw new IllegalArgumentException("Task not found.");
        }
		
	}

	public TodoItem save(TodoItem todo) {
		return todoRepository.save(todo);
		
	}


}
