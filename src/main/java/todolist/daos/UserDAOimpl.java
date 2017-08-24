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
    public User getUserByName(String userName) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User  where login =:login", User.class);
        query.setParameter("login", userName);
        System.out.println(query.uniqueResult());
        return query.uniqueResult();
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
    public User getUserByEmail(String email) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User where email =:email", User.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }
}
