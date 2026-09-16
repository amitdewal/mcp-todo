/**
 * 
 */
package com.ad.mcp.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ad.mcp.model.TodoItem;
import com.ad.mcp.repository.InMemoryTodoRepository;

@Service
public class TodoService {

    private final InMemoryTodoRepository repository;

    public TodoService(InMemoryTodoRepository repository) {
        this.repository = repository;
    }

    public TodoItem createTodo(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Todo title cannot be empty");
        }
        return repository.save(title.trim());
    }

    public TodoItem markAsCompleted(Long id) {
        return repository.complete(id)
                .orElseThrow(() -> new NoSuchElementException("Todo not found with ID: " + id));
    }

    public List<TodoItem> listAllTodos() {
        return repository.findAll();
    }
}