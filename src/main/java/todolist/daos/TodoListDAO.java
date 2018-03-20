package todolist.daos;

import org.hibernate.SessionFactory;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;

import java.io.Serializable;
import java.util.ArrayList;


public interface TodoListDAO {

  SessionFactory getSessionFactory();

  ArrayList<TodoList> getTodolistsFor(User owner);

  void addTask(Task task);

  void changeTaskName(int taskId, String task);

  Serializable addTodoList(TodoList todoList);

  void deleteTodoList(int id);

  void changeListName(TodoList todoList);

  void deleteTask(int taskId);

  void toggleDone(byte done, int taskId);
}
