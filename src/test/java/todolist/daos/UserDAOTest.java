package todolist.daos;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import todolist.config.SpringRootConfiguration;
import todolist.entities.User;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringRootConfiguration.class})
@WebAppConfiguration
public class UserDAOTest {
  @Autowired
  private UserDAO userDAO;

  private Session currentSession;

  private User user;

  @Before
  @Transactional
  public void addUser() throws Exception {
    currentSession = userDAO.getSessionFactory().getCurrentSession();

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

    assertEquals("Added user is not equal to retrived user",
        user.getUsername(), retrievedUser.getUsername());
    assertEquals("Added user is not equal to retrived user",
        user.getPassword(), retrievedUser.getPassword());
    assertEquals("Added user is not equal to retrived user",
        user.getEmail(), retrievedUser.getEmail());
    assertEquals("Added user is not equal to retrived user",
        user.getRole(), retrievedUser.getRole());
  }

  @Test
  @Transactional
  public void insertingResetPaswordToken() throws Exception {
    String token = UUID.randomUUID().toString();
    String email = user.getEmail();

    userDAO.insertResetTokenForEmail(token, email);

    User retrievedUser = userDAO.getUserByName(user.getUsername());

    currentSession.refresh(retrievedUser);

    assertNotNull("Reset password token is not created",
        retrievedUser.getResetPasswordToken());
    assertEquals("Reset password token user is not equal to retrived one",
        token, retrievedUser.getResetPasswordToken());
  }

  @After
  @Transactional
  public void removeUser() throws Exception {
    userDAO.removeUser(user);
    boolean userRemoved = userDAO.getUserByName(user.getUsername()) == null;

    assertTrue("User couldn't be removed", userRemoved);
  }


}