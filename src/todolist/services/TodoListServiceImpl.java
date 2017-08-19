package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.daos.TodoListDAO;
import todolist.entities.TodoList;

import java.util.ArrayList;

@Service
public class TodoListServiceImpl implements TodoListService {

    @Autowired
    private TodoListDAO todoListDAO;

    @Override
    @Transactional
    public ArrayList<TodoList> getLists() {
        return todoListDAO.getLists();
    }

    @Override
    @Transactional
    public void addTask(TodoList todoList, String task) {
        todoListDAO.addTask(todoList, task);
    }

    @Override
    @Transactional
    public void addTodoList(TodoList todoList) {
        todoListDAO.addTodoList(todoList);
    }

    @Override
    @Transactional
    public void deleteTodoList(TodoList todoList) {
        todoListDAO.deleteTodoList(todoList);
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
