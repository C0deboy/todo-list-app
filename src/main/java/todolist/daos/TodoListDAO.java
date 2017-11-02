package todolist.daos;

import org.hibernate.SessionFactory;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;

import java.util.ArrayList;


public interface TodoListDAO {

  SessionFactory getSessionFactory();

  ArrayList<TodoList> getTodolistsFor(User owner);

  void addTask(Task task);

  void changeTaskName(int taskId, String task);

  void addTodoList(TodoList todoList);

  void deleteTodoList(TodoList todoList);

  void changeListName(TodoList todoList);

  void deleteTask(int taskId);

  void toggleDone(byte done, int taskId);
}
