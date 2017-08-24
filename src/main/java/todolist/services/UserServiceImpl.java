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
    public boolean isUserValid(String userName, String password) {
        if  (userDAO.getUser(userName, password) == null)
            return false;
        else
            return true;
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
        userDAO.addUser(user);
    }
}
