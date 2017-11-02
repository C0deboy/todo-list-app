package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;
import todolist.services.TodoListService;
import todolist.services.UserService;

import java.util.ArrayList;


@Controller
public class TodoListController {

  private final TodoListService todoListService;

  private final UserService userService;

  @Autowired
  public TodoListController(TodoListService todoListService, UserService userService) {
    this.todoListService = todoListService;
    this.userService = userService;
  }

  @GetMapping("/{username}/your-todo-lists")
  public String getListsForUser(@PathVariable String username, Model model) {

    User user = userService.getUserByName(username);

    model.addAttribute(user);

    ArrayList<TodoList> todoLists = todoListService.getTodolistsFor(user);

    model.addAttribute("todoLists", todoLists);

    TodoList todoList = new TodoList();
    todoList.setOwnerID(user.getId());

    model.addAttribute(todoList);

    return "app";
  }

  @PostMapping("/{username}/addList")
  public String addList(@PathVariable String username, @ModelAttribute TodoList todoList) {
    todoListService.addTodoList(todoList);
    return "redirect:/" + username + "/your-todo-lists";
  }

  @PostMapping(value = "/{username}/manageTodoList", params = "deleteList")
  public String deleteList(@PathVariable String username, @ModelAttribute TodoList todoList) {
    todoListService.deleteTodoList(todoList);
    return "redirect:/" + username + "/your-todo-lists";
  }

  @PostMapping(value = "/{username}/manageTodoList", params = "changeListName")
  public String changeListName(@PathVariable String username, @ModelAttribute TodoList todoList) {
    todoListService.changeTodoListName(todoList);
    return "redirect:/" + username + "/your-todo-lists";
  }

  @PostMapping(value = "/{username}/manageTodoList", params = "addTask")
  public String addTask(@PathVariable String username, @ModelAttribute TodoList todoList,
                        @RequestParam("task") String task) {
    Task newTask = new Task();
    newTask.setTask(task);
    newTask.setListReference(todoList);
    todoListService.addTask(newTask);
    return "redirect:/" + username + "/your-todo-lists";
  }

  @PostMapping(value = "/{username}/manageTodoList", params = "changeTaskName")
  public String changeTaskName(@RequestParam("changeTaskName") int taskIndex,
                               @PathVariable String username, @ModelAttribute TodoList todoList) {
    Task task = todoList.getTasks().get(taskIndex);
    int taskId = task.getId();
    String taskText = task.getTask();
    todoListService.changeTaskName(taskId, taskText);
    return "redirect:/" + username + "/your-todo-lists";
  }

  @PostMapping(value = "/{username}/manageTodoList", params = "deleteTask")
  public String deleteTask(@RequestParam("deleteTask") int taskId, @PathVariable String username) {
    todoListService.deleteTask(taskId);

    return "redirect:/" + username + "/your-todo-lists";
  }

  @PostMapping(value = "/{username}/manageTodoList", params = "toggleDone")
  public String setAsTodo(@RequestParam("toggleDone") int taskIndex, @PathVariable String username,
                          @ModelAttribute TodoList todoList) {
    Task task = todoList.getTasks().get(taskIndex);
    byte done = task.getDone();
    if (done == 1) {
      done = 0;
    } else {
      done = 1;
    }
    todoListService.toggleDone(done, task.getId());
    return "redirect:/" + username + "/your-todo-lists";
  }

}
