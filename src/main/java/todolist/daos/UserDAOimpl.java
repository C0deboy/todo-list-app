package todolist.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import todolist.entities.User;

import javax.persistence.NoResultException;

@Repository
public class UserDAOimpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUserByName(String username) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User  where username =:username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    @Override
    public User getUserByResetPasswordToken(String token) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User  where resetPasswordToken =:token", User.class);
        query.setParameter("token", token);
        return query.uniqueResult();
    }

    @Override
    public User getUser(String username, String password) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User  where username =:username and password =:password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.uniqueResult();
    }

    @Override
    public void addUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(user);
    }

    @Override
    public void removeUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(user);
    }

    @Override
    public User getUserByEmail(String email) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User where email =:email", User.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    @Override
    public void insertResetTokenForEmail(String token, String email) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update User set resetPasswordToken =:token where email =:email");
        query.setParameter("token", token);
        query.setParameter("email", email);
        query.executeUpdate();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void changePassword(int userId, String newPassword) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update User set password =:password where id =:id");
        query.setParameter("password", newPassword);
        query.setParameter("id", userId);
        query.executeUpdate();
    }
}
