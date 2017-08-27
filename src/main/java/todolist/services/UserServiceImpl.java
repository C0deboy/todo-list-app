package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.daos.UserDAO;
import todolist.entities.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public User getUserByName(String username) {
        return userDAO.getUserByName(username);
    }

    @Override
    @Transactional
    public boolean isUserNameValid(String username) {
        if  (userDAO.getUserByName(username) == null)
            return false;
        else
            return true;
    }

    @Override
    @Transactional
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
