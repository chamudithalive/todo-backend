package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.entity.Todo;
import com.example.demo.entity.User;
import com.example.demo.repository.TodoRepository;
import com.example.demo.dto.TodoDTO;
import com.example.demo.dto.TodoCreateDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    @Autowired
    private TodoRepository todoRepository;

    public TodoDTO createTodo(TodoCreateDTO todoCreateDTO, User user) {
        try {
            Todo todo = new Todo();
            todo.setTitle(todoCreateDTO.getTitle());
            todo.setDescription(todoCreateDTO.getDescription());
            todo.setCompleted(todoCreateDTO.isCompleted());
            todo.setUser(user);

            Todo createdTodo = todoRepository.save(todo);
            logger.info("Todo created successfully: {}", createdTodo);
            return toDTO(createdTodo);
        } catch (Exception e) {
            logger.error("Error creating Todo: {}", e.getMessage());
            throw new RuntimeException("Failed to create Todo", e);
        }
    }

    public Page<TodoDTO> getTodosByUser(User user, int page, int size, String sortBy, boolean asc) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        Page<Todo> todos = todoRepository.findByUser(user, pageable);
        return todos.map(this::toDTO);
    }

    public List<TodoDTO> searchTodosByUser(User user, String keyword) {
        try {
            List<Todo> todos = todoRepository.findByUserAndTitleContainingIgnoreCase(user, keyword);
            return todos.stream().map(this::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error searching Todos: {}", e.getMessage());
            throw new RuntimeException("Error occurred while searching Todos", e);
        }
    }

    public TodoDTO updateTodo(Long id, TodoCreateDTO todoCreateDTO, User user) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!existingTodo.getUser().equals(user)) {
            logger.warn("Unauthorized update attempt by user: {}", user.getId());
            throw new RuntimeException("Not authorized to update this Todo");
        }

        existingTodo.setTitle(todoCreateDTO.getTitle());
        existingTodo.setDescription(todoCreateDTO.getDescription());
        existingTodo.setCompleted(todoCreateDTO.isCompleted());

        try {
            Todo updatedTodo = todoRepository.save(existingTodo);
            logger.info("Todo updated successfully: {}", updatedTodo);
            return toDTO(updatedTodo);
        } catch (Exception e) {
            logger.error("Error updating Todo: {}", e.getMessage());
            throw new RuntimeException("Failed to update Todo", e);
        }
    }

    public void deleteTodo(Long id, User user) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!existingTodo.getUser().equals(user)) {
            logger.warn("Unauthorized delete attempt by user: {}", user.getId());
            throw new RuntimeException("Not authorized to delete this Todo");
        }

        try {
            todoRepository.delete(existingTodo);
            logger.info("Todo deleted successfully: {}", existingTodo);
        } catch (Exception e) {
            logger.error("Error deleting Todo: {}", e.getMessage());
            throw new RuntimeException("Failed to delete Todo", e);
        }
    }

    private TodoDTO toDTO(Todo todo) {
        TodoDTO dto = new TodoDTO();
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setCompleted(todo.isCompleted());
        return dto;
    }
}
