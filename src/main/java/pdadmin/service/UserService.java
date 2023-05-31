package pdadmin.service;

import pdadmin.dao.UserDAO;
import pdadmin.model.User;

import java.util.List;

public interface UserService {
    UserDAO getUserRole(Long userId);
    List<User> findAll();

    Long addUser(User user);
}