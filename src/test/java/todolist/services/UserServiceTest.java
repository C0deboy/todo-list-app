package todolist.services;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import todolist.daos.UserDAO;
import todolist.entities.User;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/appTestconfig-root.xml")
@WebAppConfiguration
public class UserServiceTest {
    @Autowired
    private UserDAO userDAO;

    private User user;

    @Before
    @Transactional
    public void addUser() throws Exception {
        User testUser = new User("Test", "Test123$", "test@test.com");

        userDAO.addUser(testUser);

        user = userDAO.getUserByName(testUser.getUsername());

        assertNotNull(user);
    }

    @Test
    @Transactional
    public void userIsAdded() throws Exception {

        boolean isUserAdded = userDAO.getUser(user.getUsername(), user.getPassword()) != null;

        assertTrue("User wasn't added successfully.", isUserAdded);
    }

    @Test
    @Transactional
    public void usernameShouldBeUnavailable() throws Exception {

        boolean isUsernameAvailable = userDAO.getUserByName(user.getUsername()) == null;

        assertFalse("Username is available, but should not.", isUsernameAvailable);
    }

    @Test
    @Transactional
    public void emailShouldBeUnavailable() throws Exception {

        boolean isEmailAvailable = userDAO.getUserByEmail(user.getEmail()) == null;
        assertFalse("Email is available, but should not.", isEmailAvailable);
    }

    @Test
    @Transactional
    public void addedUserShouldBeEqualToRetrivedUser() throws Exception {
        User retrievedUser = userDAO.getUserByName(user.getUsername());

        assertEquals("Added user is not equal to retrived user", user, retrievedUser);
    }

    @After
    @Transactional
    public void removeUser() throws Exception {
        userDAO.removeUser(user);
        boolean userRemoved = userDAO.getUserByName(user.getUsername()) == null;

        assertTrue("User couldn't be removed", userRemoved);
    }


}