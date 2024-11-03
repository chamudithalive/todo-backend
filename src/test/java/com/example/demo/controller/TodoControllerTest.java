package com.example.demo.controller;

import com.example.demo.dto.TodoCreateDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.entity.User;
import com.example.demo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TodoControllerTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnCreated_WhenTodoIsCreated() {
        // Arrange
        TodoCreateDTO todoCreateDTO = new TodoCreateDTO("Test Todo", "Description", false);
        TodoDTO createdTodo = new TodoDTO("Test Todo", "Description", false, LocalDateTime.now());
        when(todoService.createTodo(todoCreateDTO, user)).thenReturn(createdTodo);

        // Act
        ResponseEntity<TodoDTO> result = todoController.create(todoCreateDTO, user);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(createdTodo, result.getBody());
    }

    @Test
    void getAll_ShouldReturnOk_WhenTodosAreRetrieved() {
        // Arrange
        List<TodoDTO> todos = new ArrayList<>();
        todos.add(new TodoDTO("Test Todo", "Description", false, LocalDateTime.now()));
        Page<TodoDTO> pagedResponse = new PageImpl<>(todos); // Creating a mock Page<TodoDTO>

        when(todoService.getTodosByUser(user, 0, 10, "title", true)).thenReturn(pagedResponse);

        // Act
        ResponseEntity<List<TodoDTO>> result = todoController.getAll(user, 0, 10, "title", true);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(todos, result.getBody());
    }

    @Test
    void update_ShouldReturnOk_WhenTodoIsUpdated() {
        // Arrange
        Long todoId = 1L;
        TodoCreateDTO todoCreateDTO = new TodoCreateDTO("Updated Todo", "Updated Description", false);
        TodoDTO updatedTodo = new TodoDTO("Updated Todo", "Updated Description", false, LocalDateTime.now());
        when(todoService.updateTodo(todoId, todoCreateDTO, user)).thenReturn(updatedTodo);

        // Act
        ResponseEntity<TodoDTO> result = todoController.update(todoId, todoCreateDTO, user);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedTodo, result.getBody());
    }

    @Test
    void delete_ShouldReturnNoContent_WhenTodoIsDeleted() {
        // Arrange
        Long todoId = 1L;

        // Act
        ResponseEntity<Void> result = todoController.delete(todoId, user);

        // Assert
        verify(todoService, times(1)).deleteTodo(todoId, user);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}
