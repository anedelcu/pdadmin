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
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource UserRepository userRepository;
    //TODO Add log4j, and add logs.info in the methods, log.warn and log.error
    //Do some research when you have to add each of them


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
            // log.error .... dont print stack trace
            e.printStackTrace();
        }
        return userDAO;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Long addUser(User user) {
        User newUser = userRepository.save(user);
        return newUser.getId();
    }

    @Override
    public Integer addUsers(List<User> users) {
        Integer count = 0;
        for(User user : users) {
            userRepository.save(user);
            count++;
        }
        return count;
    }

    @Override
    public boolean updateUser(Long userId, User updatedUser) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(userId));
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setDob(updatedUser.getDob());
            user.setRole(updatedUser.getRole());
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }
}