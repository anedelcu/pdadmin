package pdadmin.serviceImpl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pdadmin.dao.UserDAO;
import pdadmin.exception.UserNotFoundException;
import pdadmin.model.User;
import pdadmin.repo.UserRepository;
import pdadmin.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource UserRepository userRepository;


    @Override
    public UserDAO getUserRole(Long userId) {
        UserDAO userDAO = null;
        try {
            User user = userRepository.findById(userId);
            if (user != null) {
                String role = user.getRole();
                userDAO = new UserDAO(userId, role);
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return userDAO;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public Long addUser(User user) {
        User newUser = userRepository.save(user);
        return newUser.getId();
    }
}
