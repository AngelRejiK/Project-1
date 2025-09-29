package com.ty.ToDo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.ToDo.model.TodoItem;
import com.ty.ToDo.model.User;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
	 List<TodoItem> findByUser(User user);
}
