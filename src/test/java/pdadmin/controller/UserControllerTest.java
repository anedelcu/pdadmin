package pdadmin.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pdadmin.dao.UserDAO;
import pdadmin.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetUserRole_UserFound_ReturnUserRole() {

        // Arrange
        long userId = 1L;
        UserDAO userDAO = new UserDAO(userId, "Doctor");

        Mockito.when(userService.getUserRole(userId)).thenReturn(userDAO);

        // Act
        ResponseEntity<UserDAO> response = userController.getUserRole(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDAO, response.getBody());
    }

    @Test
    void testGetUserRole_UserNotFound_ReturnsNotFound() {
        long userId = 1L;
        Mockito.when(userService.getUserRole(userId)).thenReturn(null);

        // Act
        ResponseEntity<UserDAO> response = userController.getUserRole(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUserRole_RoleIsEmpty_ReturnsNotFound() {
        long userId = 1L;
        UserDAO userDAO = new UserDAO(userId, "");

        Mockito.when(userService.getUserRole(userId)).thenReturn(userDAO);

        // Act
        ResponseEntity<UserDAO> response = userController.getUserRole(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUserRole_InvalidUserId_ReturnsNotFound() {
        // Arrange
        long userId = -1L; // Invalid user id
        Mockito.when(userService.getUserRole(userId)).thenReturn(null);

        // Act
        ResponseEntity<UserDAO> response = userController.getUserRole(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

}