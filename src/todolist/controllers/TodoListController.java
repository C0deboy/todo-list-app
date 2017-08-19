package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.services.TodoListService;

import java.util.ArrayList;

/**
 * Servlet implementation class TodoListController
 */
@Controller
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @GetMapping("/your-todo-lists")
    public String listCustomers(Model model) {

        ArrayList<TodoList> lists = todoListService.getLists();
        model.addAttribute("todoLists", lists);

        TodoList todoList = new TodoList();
        model.addAttribute(todoList);

        Task task = new Task();
        model.addAttribute(task);

        return "todo-list";
    }

    @PostMapping("/addList")
    public String addList(@ModelAttribute("todoList") TodoList todoList) {

        todoListService.addTodoList(todoList);
        return "redirect:/your-todo-lists";
    }

    @PostMapping(value = "manageTodoList", params = "deleteList")
    public String deleteList(@ModelAttribute("todoList") TodoList todoList) {

        todoListService.deleteTodoList(todoList);
        return "redirect:/your-todo-lists";
    }

    @PostMapping(value = "manageTodoList", params = "changeListName")
    public String changeListName(@ModelAttribute("todoList") TodoList todoList) {
        todoListService.changeTodoListName(todoList);
        return "redirect:/your-todo-lists";
    }

    @PostMapping(value = "manageTodoList", params = "addTask")
    public String addTask(@ModelAttribute("todoList") TodoList todoList, @RequestParam("task") String task) {
        System.out.println(task);
        System.out.println("żółć");
        todoListService.addTask(todoList, task);
        return "redirect:/your-todo-lists";
    }

    @PostMapping(value = "manageTodoList", params = "changeTaskName")
    public String changeTaskName(@RequestParam("changeTaskName") int taskIndex, @ModelAttribute("todoList") TodoList todoList) {
        Task task = todoList.getTasks().get(taskIndex);
        int taskID = task.getId();
        String taskText = task.getTask();
        todoListService.changeTaskName(taskID, taskText);
        return "redirect:/your-todo-lists";
    }

    @PostMapping(value = "manageTodoList", params = "deleteTask")
    public String deleteTask(@RequestParam("deleteTask") int taskID) {

        todoListService.deleteTask(taskID);

        return "redirect:/your-todo-lists";
    }

    @PostMapping(value = "manageTodoList", params = "toggleDone")
    public String setAsTodo(@RequestParam("toggleDone") int taskIndex, @ModelAttribute("todoList") TodoList todoList) {
        Task task = todoList.getTasks().get(taskIndex);
        byte done = task.getDone();
        if (done == 1) {
            done = 0;
        } else {
            done = 1;
        }
        todoListService.toggleDone(done, task.getId());
        return "redirect:/your-todo-lists";
    }

}
