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
import pdadmin.model.User;
import pdadmin.service.UserService;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void testGetAllUsers_ReturnListOfUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "John", "Doe", "john@email.com", "2000-11-01", "DOCTOR"));
        userList.add(new User(2L, "Mary", "Nurse", "mary@email.com", "2002-10-01", "Patient"));

        Mockito.when(userService.findAll()).thenReturn(userList);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void testGetAllUsers_ReturnEmptyList() {
        List<User> userList = new ArrayList<>();
        Mockito.when(userService.findAll()).thenReturn(userList);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void testGetAllUsers_ReturnsServerError() {

        Mockito.when(userService.findAll()).thenThrow(new RuntimeException("Internal server error"));

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }


    @Test
    void testAddUser_ReturnUserId() {
        User user = new User(1L, "John", "Doe", "john@email.com", "2000-11-01", "DOCTOR");

        Mockito.when(userService.addUser(user)).thenReturn(user.getId());

        ResponseEntity<Long> response = userController.addUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user.getId(), response.getBody());
    }

    @Test
    void testAddUser_ThrowsException_ReturnsInternalServerError() {
        User user = new User(1L, "John", "Doe", "john@email.com", "2000-11-01", "DOCTOR");

        Mockito.doThrow(RuntimeException.class).when(userService).addUser(user);

        ResponseEntity<Long> response = userController.addUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());

    }

    @Test
    void testAddUser_NullUser_ReturnsBadRequest() {
        // Arrange
        User user = null;
        Mockito.when(userService.addUser(user)).thenThrow(new IllegalArgumentException());
        // Act
        ResponseEntity<Long> response = userController.addUser(user);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddUsers_UsersNotNull_ReturnsCreated () {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", "Doe", "john@email.com", "2000-11-01", "DOCTOR"));
        users.add(new User(2L, "Mary", "Nurse", "mary@email.com", "2002-10-01", "Patient"));

        Mockito.when(userService.addUsers(users)).thenReturn(users.size());

        ResponseEntity<Integer> response = userController.addUsers(users);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(users.size(), response.getBody());
    }

    @Test
    void testAddUsers_UsersEmpty_ReturnsCreated () {
        List<User> users = new ArrayList<>();
        Mockito.when(userService.addUsers(users)).thenReturn(0);

        ResponseEntity<Integer> response = userController.addUsers(users);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(0, response.getBody());
    }

}