package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.daos.UserDAO;
import todolist.entities.User;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User getUserByName(String username) {
        return userDAO.getUserByName(username);
    }

    @Override
    @Transactional
    public User getUserByResetPasswordToken(String token) {
        return userDAO.getUserByResetPasswordToken(token);
    }

    @Override
    @Transactional
    public void insertResetTokenForEmail(String token, String email) {
        userDAO.insertResetTokenForEmail(token, email);
    }

    @Override
    @Transactional
    public boolean isUsernameAvailable(String username) {
        return userDAO.getUserByName(username) == null;
    }

    @Override
    @Transactional
    public boolean isEmailAvailable(String email) {
        return userDAO.getUserByEmail(email) == null;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.addUser(user);
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        userDAO.removeUser(user);
    }

    @Override
    @Transactional
    public void changePassword(User user, String password) {
        userDAO.changePassword(user, bCryptPasswordEncoder.encode(password));
    }

    @Override
    @Transactional
    public void changeEmail(String username, String newEmail) {
        userDAO.changeEmail(username, newEmail);
    }
}
