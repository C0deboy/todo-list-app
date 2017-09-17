package todolist.daos;

import org.hibernate.SessionFactory;
import todolist.entities.User;

public interface UserDAO {
    User getUserByName(String username);
    User getUser(String username, String password);
    User getUserByEmail(String email);
    void addUser(User user);
    void removeUser(User user);

    void insertResetTokenForEmail(String token, String email);

    SessionFactory getSessionFactory();

    User getUserByResetPasswordToken(String token);

    void changePassword(int userId, String newPassword);
}
