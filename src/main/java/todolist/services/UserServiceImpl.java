package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.daos.UserDAO;
import todolist.entities.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public User getUser(String userName) {
        return userDAO.getUserByName(userName);
    }

    @Override
    @Transactional
    public boolean isUserNameValid(String userName) {
        if  (userDAO.getUserByName(userName) == null)
            return false;
        else
            return true;
    }

    @Override
    @Transactional
    public boolean isPasswordValid(String userName, String password) {
        User user = userDAO.getUserByName(userName);
        if  (user == null) {
            System.out.println("User " + userName + "not found. Password validation failed.");
            return false;
        }
        else if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean isEmailAvailable(String email) {
        if  (userDAO.getUserByEmail(email) == null)
            return true;
        else
            return false;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.addUser(user);
    }
}
