package todolist.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import todolist.entities.Task;
import todolist.entities.TodoList;

import java.util.ArrayList;

@Repository
public class TodoListDAOimpl implements TodoListDAO {

    @Autowired
    private SessionFactory sessionFactory;

 	@Override
    public ArrayList<TodoList> getLists() {
        Session currentSession = sessionFactory.getCurrentSession();
		Query<TodoList> theQuery = currentSession.createQuery("from TodoList", TodoList.class);
        return (ArrayList<TodoList>) theQuery.getResultList();
	}

	@Override
    public void addTask(TodoList todoList, String task) {

		Session currentSession = sessionFactory.getCurrentSession();
		Task newTask = new Task();
		newTask.setTask(task);
		newTask.setListReference(todoList);

		currentSession.save(newTask);

	}

    @Override
    public void changeTaskName(int taskID, String task) {

        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update Task set task = :task where id = :taskID");
        query.setParameter("task", task);
        query.setParameter("taskID", taskID);

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
    public void deleteTask(int taskID) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery("delete Task where id=:taskID");
		query.setParameter("taskID", taskID);
		query.executeUpdate();
	}

	@Override
    public void toggleDone(byte done, int taskID) {
		Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update Task set done = :done where id = :taskID");
        query.setParameter("done", done);
        query.setParameter("taskID", taskID);
        query.executeUpdate();
	}
}
