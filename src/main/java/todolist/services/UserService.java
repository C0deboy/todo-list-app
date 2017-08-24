package todolist.services;

import todolist.entities.User;

public interface UserService {
    
    User getUser(String userName);

    boolean isUserNameValid(String userName);
    
    boolean isUserValid(String userName, String password);

    void addUser(User user);

    boolean isEmailAvailable(String email);
}
