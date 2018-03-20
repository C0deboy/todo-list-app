package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import todolist.entities.TodoList;
import todolist.entities.User;
import todolist.services.TodoListService;
import todolist.services.UserService;

import java.io.Serializable;
import java.util.ArrayList;

@RestController
public class TodoListRestController {
  private final TodoListService todoListService;

  private final UserService userService;

  @Autowired
  public TodoListRestController(TodoListService todoListService, UserService userService) {
    this.todoListService = todoListService;
    this.userService = userService;
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/api/{username}/your-todo-lists")
  public @ResponseBody Object getListsForUser(@PathVariable String username) {

    User user = userService.getUserByName(username);

    ArrayList<TodoList> todoLists = todoListService.getTodolistsFor(user);

    return todoLists;
  }

  @PutMapping("/api/{username}/your-todo-lists")
  public @ResponseBody Serializable addList(@RequestBody TodoList todoList) {
    return todoListService.addTodoList(todoList);

  }

  @DeleteMapping("/api/{username}/your-todo-lists/{id}")
  public void deleteList(@PathVariable("id") int id) {
    todoListService.deleteTodoList(id);
  }

  @GetMapping("/api/{username}/user")
  public @ResponseBody Object getUserDetails(@PathVariable String username) {

    User user = userService.getUserByName(username);

    return user;
  }
}
