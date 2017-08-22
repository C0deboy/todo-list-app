package todolist.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import todolist.entities.User;

import javax.persistence.NoResultException;

@Repository
public class UserDAOimpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUser(String userName) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User  where login =:login", User.class);
        query.setParameter("login", userName);
        User user;
        try {
            user = query.getSingleResult();
        }
        catch (NoResultException e) {
            user = null;
        }

        return user;
    }

    @Override
    public User getUser(String userName, String password) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User  where login =:login and password =:password", User.class);
        query.setParameter("login", userName);
        query.setParameter("password", password);

        return query.uniqueResult();
    }

    @Override
    public void addUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(user);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User where email =:email", User.class);
        query.setParameter("email", email);
        return query.uniqueResult() == null;
    }
}
