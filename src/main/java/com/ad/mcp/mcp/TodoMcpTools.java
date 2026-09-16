/**
 * 
 */
package com.ad.mcp.mcp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
/**
 * 
 */
import org.springframework.stereotype.Component;

import com.ad.mcp.model.TodoItem;
import com.ad.mcp.service.TodoService;

@Component
public class TodoMcpTools {

	private static final Logger log = LoggerFactory.getLogger(TodoMcpTools.class);
	private final TodoService todoService;

	public TodoMcpTools(TodoService todoService) {
		this.todoService = todoService;
	}

	@Tool(name = "create_todo", description = "Creates a new todo item in the task list")
	public TodoItem createTodo(
			@ToolParam(description = "The title or description of the task to be done") String title) {
		log.info(">>> [MCP TOOL EXECUTED] create_todo called with title: '{}'", title);
		TodoItem item = todoService.createTodo(title);
		log.info(">>> [MCP TOOL RESULT] Created item: {}", item);
		return item;
	}

	@Tool(name = "complete_todo", description = "Marks an existing todo item as completed using its numeric ID")
	public TodoItem completeTodo(@ToolParam(description = "The numeric ID of the todo task") Long id) {
		log.info(">>> [MCP TOOL EXECUTED] complete_todo called with id: {}", id);
		TodoItem item = todoService.markAsCompleted(id);
		log.info(">>> [MCP TOOL RESULT] Completed item: {}", item);
		return item;
	}

	@Tool(name = "get_all_todos", description = "Retrieves all current active and completed tasks")
	public List<TodoItem> getAllTodos() {
		log.info(">>> [MCP TOOL EXECUTED] get_all_todos called");
		List<TodoItem> todos = todoService.listAllTodos();
		log.info(">>> [MCP TOOL RESULT] Found {} items", todos.size());
		return todos;
	}
}