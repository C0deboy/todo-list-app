package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.daos.UserDAO;
import todolist.entities.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public User getUser(String userName) {
        return userDAO.getUser(userName);
    }

    @Override
    @Transactional
    public boolean isUserNameValid(String userName) {
        User user = userDAO.getUser(userName);
        return user != null;
    }

    @Override
    @Transactional
    public boolean isPasswordValid(String userName, String password) {
        User user = userDAO.getUser(userName, password);
        return user != null;
    }

    @Transactional
    @Override
    public boolean isEmailAvailable(String email) {
        return userDAO.isEmailAvailable(email);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }
}
