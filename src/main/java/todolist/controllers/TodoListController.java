package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;
import todolist.services.TodoListService;
import todolist.services.UserService;

import java.util.ArrayList;

/**
 * Servlet implementation class TodoListController
 */
@Controller
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private UserService userService;

    @GetMapping("/{user}//your-todo-lists")
    public String getListsForUser(@PathVariable("user") String userName, Model model) {
        User user = userService.getUser(userName);

        model.addAttribute(user);

        ArrayList<TodoList> todoLists = todoListService.getTodolistsFor(user);

        model.addAttribute("todoLists", todoLists);

        TodoList todoList = new TodoList();
        todoList.setOwnerID(user.getId());

        model.addAttribute(todoList);

        return "app";
    }

    @PostMapping("/{user}/addList")
    public String addList(@PathVariable("user") String userName, @ModelAttribute("todoList") TodoList todoList) {
        todoListService.addTodoList(todoList);
        return "redirect:/"+ userName + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "deleteList")
    public String deleteList(@PathVariable("user") String userName, @ModelAttribute("todoList") TodoList todoList) {
        todoListService.deleteTodoList(todoList);
        return "redirect:/"+ userName + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "changeListName")
    public String changeListName(@PathVariable("user") String userName, @ModelAttribute("todoList") TodoList todoList) {
        todoListService.changeTodoListName(todoList);
        return "redirect:/"+ userName + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "addTask")
    public String addTask(@PathVariable("user") String userName, @ModelAttribute("todoList") TodoList todoList, @RequestParam("task") String task) {
        Task newTask = new Task();
        newTask.setTask(task);
        newTask.setListReference(todoList);
        todoListService.addTask(newTask);
        return "redirect:/"+ userName + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "changeTaskName")
    public String changeTaskName(@RequestParam("changeTaskName") int taskIndex, @PathVariable("user") String userName, @ModelAttribute("todoList") TodoList todoList) {
        Task task = todoList.getTasks().get(taskIndex);
        int taskID = task.getId();
        String taskText = task.getTask();
        todoListService.changeTaskName(taskID, taskText);
        return "redirect:/"+ userName + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "deleteTask")
    public String deleteTask(@RequestParam("deleteTask") int taskID, @PathVariable("user") String userName) {
        todoListService.deleteTask(taskID);

        return "redirect:/"+ userName + "/your-todo-lists";
    }

    @PostMapping(value = "/{user}/manageTodoList", params = "toggleDone")
    public String setAsTodo(@RequestParam("toggleDone") int taskIndex, @PathVariable("user") String userName, @ModelAttribute("todoList") TodoList todoList) {
        Task task = todoList.getTasks().get(taskIndex);
        byte done = task.getDone();
        if (done == 1) {
            done = 0;
        } else {
            done = 1;
        }
        todoListService.toggleDone(done, task.getId());
        return "redirect:/"+ userName + "/your-todo-lists";
    }

}
