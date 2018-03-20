package todolist.services;

import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;

import java.io.Serializable;
import java.util.ArrayList;

public interface TodoListService {
  ArrayList<TodoList> getTodolistsFor(User user);

  void addTask(Task task);

  Serializable addTodoList(TodoList todoList);

  void deleteTodoList(int id);

  void changeTodoListName(TodoList todoList);

  void deleteTask(int taskID);

  void changeTaskName(int taskID, String task);

  void toggleDone(byte done, int taskID);
}
