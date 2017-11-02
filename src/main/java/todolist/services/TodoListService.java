package todolist.services;

import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;

import java.util.ArrayList;

public interface TodoListService {
  ArrayList<TodoList> getTodolistsFor(User user);

  void addTask(Task task);

  void addTodoList(TodoList todoList);

  void deleteTodoList(TodoList todoList);

  void changeTodoListName(TodoList todoList);

  void deleteTask(int taskID);

  void changeTaskName(int taskID, String task);

  void toggleDone(byte done, int taskID);
}
