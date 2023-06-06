package pdadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pdadmin.dao.UserDAO;
import pdadmin.error.ErrorEnums;
import pdadmin.model.User;
import pdadmin.service.UserService;

import java.util.List;

@RestController
@RequestMapping
public class UserController {

    // TODO
    @Autowired private UserService userService;

    @GetMapping("/role")
    public ResponseEntity<UserDAO> getUserRole(
            @RequestParam(name = "userId", required = false) long id) {
        UserDAO userDAO = userService.getUserRole(id);
        if (userDAO == null || userDAO.getRole() == null || userDAO.getRole().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<UserDAO>(userDAO, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {List<User> userList = userService.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/add")
    public ResponseEntity<Long> addUser(@RequestBody User user) {
        try{
            Long id = userService.addUser(user);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/add")
    public ResponseEntity<Integer> addUsers(@RequestBody List<User> users) {
        Integer countUsersAdded = userService.addUsers(users);
        return new ResponseEntity<>(countUsersAdded, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long userId, @RequestBody User updatedUser) {
        boolean isUpdated = userService.updateUser(userId, updatedUser);
        if (isUpdated) {
            String success = ErrorEnums.ErrorCode.USER_SUCCESS.getMessage();
            return new ResponseEntity<>(success, HttpStatus.OK);
        } else {
            String error = ErrorEnums.ErrorCode.USER_NOT_FOUND.getMessage();
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("users/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {

        boolean isDeleted = userService.deleteUser(userId);
        if(isDeleted) {
            String success = ErrorEnums.ErrorCode.USER_DELETED.getMessage();
            return new ResponseEntity<>(success, HttpStatus.OK);
        }else {
            String error = ErrorEnums.ErrorCode.USER_NOT_FOUND.getMessage();
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}