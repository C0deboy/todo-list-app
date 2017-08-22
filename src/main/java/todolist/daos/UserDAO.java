package todolist.daos;

import todolist.entities.User;

public interface UserDAO {
    User getUser(String username);
    User getUser(String username, String password);
    void addUser(User user);

    boolean isEmailAvailable(String email);
}
