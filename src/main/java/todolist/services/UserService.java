package todolist.services;

import org.springframework.transaction.annotation.Transactional;
import todolist.entities.User;

public interface UserService {
    
    User getUserByName(String username);

    boolean isUserNameValid(String username);

    void addUser(User user);

    boolean isEmailAvailable(String email);
}
