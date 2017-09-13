package todolist.daos;

import todolist.entities.User;

public interface UserDAO {
    User getUserByName(String username);
    User getUser(String username, String password);
    User getUserByEmail(String email);
    void addUser(User user);
    void removeUser(User user);
}
