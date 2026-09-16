/**
 * 
 */
package com.ad.mcp.repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 */

import org.springframework.stereotype.Repository;

import com.ad.mcp.model.TodoItem;

@Repository
public class InMemoryTodoRepository {

    private final Map<Long, TodoItem> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TodoItem save(String title) {
        Long id = idGenerator.getAndIncrement();
        TodoItem item = new TodoItem(id, title, false, Instant.now());
        storage.put(id, item);
        return item;
    }

    public Optional<TodoItem> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<TodoItem> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<TodoItem> complete(Long id) {
        TodoItem existing = storage.get(id);
        if (existing == null) return Optional.empty();
        
        TodoItem updated = new TodoItem(existing.id(), existing.title(), true, existing.createdAt());
        storage.put(id, updated);
        return Optional.of(updated);
    }
}