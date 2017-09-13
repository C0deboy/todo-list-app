package todolist.services;

import org.springframework.transaction.annotation.Transactional;
import todolist.entities.User;

public interface UserService {
    
    User getUserByName(String username);

    boolean isUsernameAvailable(String username);

    void addUser(User user);

    void removeUser(User user);

    boolean isEmailAvailable(String email);
}
