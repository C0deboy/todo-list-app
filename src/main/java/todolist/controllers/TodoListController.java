package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;
import todolist.services.TodoListService;
import todolist.services.UserService;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}/your-todo-lists")
    public String getListsForUser(@AuthenticationPrincipal Principal principal, @PathVariable String username, Model model) {
        String principalName = principal.getName();

        if (!principalName.equals(username)) {
            return "redirect:/"+principalName+"/your-todo-lists";
        }

        User user = userService.getUserByName(principalName);

        model.addAttribute(user);

        ArrayList<TodoList> todoLists = todoListService.getTodolistsFor(user);

        model.addAttribute("todoLists", todoLists);

        TodoList todoList = new TodoList();
        todoList.setOwnerID(user.getId());

        model.addAttribute(todoList);

        return "app";
    }

    @PostMapping("/{user}/addList")
    public String addList(@PathVariable("user") String username, @ModelAttribute("todoList") TodoList todoList) {
        todoListService.addTodoList(todoList);
        return "redirect:/"+ username + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "deleteList")
    public String deleteList(@PathVariable("user") String username, @ModelAttribute("todoList") TodoList todoList) {
        todoListService.deleteTodoList(todoList);
        return "redirect:/"+ username + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "changeListName")
    public String changeListName(@PathVariable("user") String username, @ModelAttribute("todoList") TodoList todoList) {
        todoListService.changeTodoListName(todoList);
        return "redirect:/"+ username + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "addTask")
    public String addTask(@PathVariable("user") String username, @ModelAttribute("todoList") TodoList todoList, @RequestParam("task") String task) {
        Task newTask = new Task();
        newTask.setTask(task);
        newTask.setListReference(todoList);
        todoListService.addTask(newTask);
        return "redirect:/"+ username + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "changeTaskName")
    public String changeTaskName(@RequestParam("changeTaskName") int taskIndex, @PathVariable("user") String username, @ModelAttribute("todoList") TodoList todoList) {
        Task task = todoList.getTasks().get(taskIndex);
        int taskID = task.getId();
        String taskText = task.getTask();
        todoListService.changeTaskName(taskID, taskText);
        return "redirect:/"+ username + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "deleteTask")
    public String deleteTask(@RequestParam("deleteTask") int taskID, @PathVariable("user") String username) {
        todoListService.deleteTask(taskID);

        return "redirect:/"+ username + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "toggleDone")
    public String setAsTodo(@RequestParam("toggleDone") int taskIndex, @PathVariable("user") String username, @ModelAttribute("todoList") TodoList todoList) {
        Task task = todoList.getTasks().get(taskIndex);
        byte done = task.getDone();
        if (done == 1) {
            done = 0;
        } else {
            done = 1;
        }
        todoListService.toggleDone(done, task.getId());
        return "redirect:/"+ username + "/your-todo-lists";
    }

}
