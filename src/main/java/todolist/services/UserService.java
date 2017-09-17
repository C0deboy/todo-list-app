package todolist.services;

import org.springframework.transaction.annotation.Transactional;
import todolist.entities.User;

public interface UserService {
    
    User getUserByName(String username);

    User getUserByResetPasswordToken(String token);

    void insertResetTokenForEmail(String token, String email);

    boolean isUsernameAvailable(String username);

    void addUser(User user);

    void removeUser(User user);

    boolean isEmailAvailable(String email);

    void changePassword(User user, String password);
}
