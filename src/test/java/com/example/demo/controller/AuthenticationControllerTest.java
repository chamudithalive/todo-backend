package com.example.demo.controller;

import com.example.demo.dto.AuthenticationRequestDTO;
import com.example.demo.dto.AuthenticationResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldReturnOk_WhenRegistrationIsSuccessful() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO("test@example.com", "password123");
        AuthenticationResponseDTO response = new AuthenticationResponseDTO("token");
        when(authenticationService.register(request)).thenReturn(response);

        // Act
        ResponseEntity<AuthenticationResponseDTO> result = authenticationController.register(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void authenticate_ShouldReturnOk_WhenAuthenticationIsSuccessful() {
        // Arrange
        AuthenticationRequestDTO request = new AuthenticationRequestDTO("test@example.com", "password123");
        AuthenticationResponseDTO response = new AuthenticationResponseDTO("token");
        when(authenticationService.authenticate(request)).thenReturn(response);

        // Act
        ResponseEntity<AuthenticationResponseDTO> result = authenticationController.authenticate(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}
