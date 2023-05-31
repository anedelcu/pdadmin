package pdadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdadmin.dao.UserDAO;
import pdadmin.model.User;
import pdadmin.repo.UserRepository;
import pdadmin.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping
public class UserController {

    //TODO
    @Autowired
    UserService userService;

    @GetMapping("/role")
    public ResponseEntity<UserDAO> getUserRole(@RequestParam (name = "userId", required = false) long id) {
        UserDAO userDAO = userService.getUserRole(id);
        System.out.println("in here");
        if (userDAO == null || userDAO.getRole() == null || userDAO.getRole().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<UserDAO>(userDAO,HttpStatus.OK);

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/user/add")
    public ResponseEntity<Long> addUser(@RequestBody User user) {
        Long id = userService.addUser(user);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


}