package todolist.services;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import todolist.daos.TodoListDAO;
import todolist.daos.UserDAO;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/appTestconfig-root.xml")
@WebAppConfiguration
public class TodoListServiceTest {
    @Autowired
    private TodoListDAO todoListDAO;

    @Autowired
    private UserDAO userDAO;

    private Session currentSession;

    private User user;

    @Before
    @Transactional
    public void setUserAndCurrentSession() throws Exception {
        currentSession = todoListDAO.getSessionFactory().getCurrentSession();
        //currentSession.setCacheMode(CacheMode.IGNORE);

        User testUser = new User("Test", "Test123$", "test@test.com");

        userDAO.addUser(testUser);

        user = userDAO.getUserByName(testUser.getUsername());

        assertNotNull(user);
    }

    @Test
    @Transactional
    public void emptyTodolistsForNewUser() throws Exception {
        ArrayList<TodoList> todolists = todoListDAO.getTodolistsFor(user);

        assertTrue("Todolists for new user are not empty", todolists.isEmpty());
    }

    @Test
    @Transactional
    public void crudTask() throws Exception {
        TodoList newTodoList = new TodoList("Test", user.getId());

        System.out.println(newTodoList);
        todoListDAO.addTodoList(newTodoList);

        TodoList todoList = todoListDAO.getTodolistsFor(user).get(0);

        Task task = new Task("Test", todoList);


        todoListDAO.addTask(task);

        currentSession.flush();
        currentSession.refresh(todoList);

        todoList = todoListDAO.getTodolistsFor(user).get(0);

        assertTrue("Should be added 1 task but is not", todoList.getTasks().size() == 1);

        Task taskToBeChanged = todoList.getTasks().get(0);
        String newTaskName = "Changed task name";

        todoListDAO.toggleDone((byte) 1, taskToBeChanged.getId());
        todoListDAO.changeTaskName(taskToBeChanged.getId(), newTaskName);

        currentSession.flush();
        currentSession.refresh(taskToBeChanged);
        todoList = todoListDAO.getTodolistsFor(user).get(0);

        System.out.println("******************");
        Task retrivedTask = todoList.getTasks().get(0);

        assertEquals("Retrived task name should be changed", newTaskName, retrivedTask.getTask());
        assertEquals("Retrived task done state should be 1", 1, retrivedTask.getDone());

        todoListDAO.deleteTask(todoList.getTasks().get(0).getId());

        currentSession.flush();
        currentSession.refresh(todoList);
        todoList = todoListDAO.getTodolistsFor(user).get(0);

        assertTrue("Task should be deleted but is not",todoList.getTasks().size() == 0);
    }

    @Test
    @Transactional
    public void crudTodoList() throws Exception {
        TodoList todoList = new TodoList("Todo", user.getId());

        todoListDAO.addTodoList(todoList);

        ArrayList<TodoList> todolists = todoListDAO.getTodolistsFor(user);

        assertTrue("Should be added one todolist, but is not", todolists.size() == 1);


        String newName = "Changed name";
        TodoList addedTodolist = todolists.get(0);
        addedTodolist.setName(newName);

        todoListDAO.changeListName(addedTodolist);

        todolists = todoListDAO.getTodolistsFor(user);
        String retrivedName = todolists.get(0).getName();

        assertEquals("Retrived name should be changed but is not", newName, retrivedName);


        Task task = new Task("Test", todolists.get(0));
        todoListDAO.addTask(task);

        todoListDAO.deleteTodoList(todolists.get(0));

        todolists = todoListDAO.getTodolistsFor(user);

        assertTrue("Todolist should be removed with task, but is not", todolists.size() == 0);
    }

    @After
    public void cleanup() throws Exception {
        userDAO.removeUser(user);

        boolean userRemoved = userDAO.getUserByName(user.getUsername()) == null;

        assertTrue("User should be removed, but is not", userRemoved);
    }
}