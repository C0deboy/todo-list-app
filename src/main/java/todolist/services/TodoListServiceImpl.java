package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.daos.TodoListDAO;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;

import java.io.Serializable;
import java.util.ArrayList;


@Service
public class TodoListServiceImpl implements TodoListService {

  private final TodoListDAO todoListDAO;

  @Autowired
  public TodoListServiceImpl(TodoListDAO todoListDAO) {
    this.todoListDAO = todoListDAO;
  }

  @Override
  @Transactional
  public ArrayList<TodoList> getTodolistsFor(User user) {
    return todoListDAO.getTodolistsFor(user);
  }

  @Override
  @Transactional
  public void addTask(Task task) {
    todoListDAO.addTask(task);
  }

  @Override
  @Transactional
  public Serializable addTodoList(TodoList todoList) {
    return todoListDAO.addTodoList(todoList);
  }

  @Override
  @Transactional
  public void deleteTodoList(int id) {
    todoListDAO.deleteTodoList(id);
  }

  @Override
  @Transactional
  public void changeTodoListName(TodoList todoList) {
    todoListDAO.changeListName(todoList);
  }

  @Override
  @Transactional
  public void deleteTask(int taskID) {
    todoListDAO.deleteTask(taskID);
  }

  @Override
  @Transactional
  public void changeTaskName(int taskID, String task) {
    todoListDAO.changeTaskName(taskID, task);
  }

  @Override
  @Transactional
  public void toggleDone(byte done, int taskID) {
    todoListDAO.toggleDone(done, taskID);
  }
}
