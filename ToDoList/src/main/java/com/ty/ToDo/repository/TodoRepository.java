package com.ty.ToDo.repository;

import com.ty.ToDo.model.TodoItem;
import com.ty.ToDo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByUser(User user);
    Optional<TodoItem> findByIdAndUser(Long id, User user);
}
