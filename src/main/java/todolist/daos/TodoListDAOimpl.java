package todolist.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;

import java.util.ArrayList;


@Repository
public class TodoListDAOimpl implements TodoListDAO {

  private final SessionFactory sessionFactory;

  @Autowired
  public TodoListDAOimpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  @Override
  public ArrayList<TodoList> getTodolistsFor(User user) {
    Session currentSession = sessionFactory.getCurrentSession();
    Query<TodoList> theQuery = currentSession.createQuery("from TodoList where ownerID =:ownerID",
        TodoList.class);
    theQuery.setParameter("ownerID", user.getId());
    return (ArrayList<TodoList>) theQuery.getResultList();
  }

  @Override
  public void addTask(Task task) {

    Session currentSession = sessionFactory.getCurrentSession();

    currentSession.save(task);

  }

  @Override
  public void changeTaskName(int taskId, String task) {

    Session currentSession = sessionFactory.getCurrentSession();
    Query query = currentSession.createQuery("update Task set task = :task where id = :taskID");
    query.setParameter("task", task);
    query.setParameter("taskID", taskId);

    query.executeUpdate();

  }

  @Override
  public void addTodoList(TodoList todoList) {

    Session currentSession = sessionFactory.getCurrentSession();

    currentSession.save(todoList);

  }

  @Override
  public void deleteTodoList(TodoList todoList) {
    Session currentSession = sessionFactory.getCurrentSession();
    currentSession.delete(todoList);
  }

  @Override
  public void changeListName(TodoList todoList) {
    Session currentSession = sessionFactory.getCurrentSession();
    currentSession.update(todoList);
  }

  @Override
  public void deleteTask(int taskId) {
    Session currentSession = sessionFactory.getCurrentSession();
    Query query = currentSession.createQuery("delete Task where id=:taskID");
    query.setParameter("taskID", taskId);
    query.executeUpdate();
  }

  @Override
  public void toggleDone(byte done, int taskId) {
    Session currentSession = sessionFactory.getCurrentSession();
    Query query = currentSession.createQuery("update Task set done = :done where id = :taskID");
    query.setParameter("done", done);
    query.setParameter("taskID", taskId);
    query.executeUpdate();
  }


}
