package pdadmin.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource private UserRepository userRepository;
    //TODO Add log4j, and add logs.info in the methods, log.warn and log.error
    //Do some research when you have to add each of them


    @Override
    public UserDAO getUserRole(Long userId) {
        UserDAO userDAO = null;
        try {
            if(userId == null || !isNumeric(userId.toString())) {
                throw new NumberFormatException("Invalid UserId format");
            }
            User user = userRepository.findById(userId);
            if (user != null) {
                String role = user.getRole();
                userDAO = new UserDAO(userId, role);
            }
        } catch (UserNotFoundException e) {
            // log.error .... don't print stack trace
            logger.error("UserNotFoundException occurred while retrieving user role: {}", e.getMessage());
        }
        catch (NumberFormatException e) {
            logger.error("IllegalArgumentException occurred: {}", e.getMessage());
        }
        return userDAO;
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        try{
            return userRepository.findAll();
        }
        catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching all users. ", e);
        }
    }

    @Override
    public Long addUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
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

    @Override
    public boolean deleteUser(Long userId) {

        User user = userRepository.findById(userId);
        if(user == null) {
            return false;
        }
        userRepository.delete(user);
        return true;
    }
}