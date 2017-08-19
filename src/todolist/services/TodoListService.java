package todolist.services;

import org.springframework.transaction.annotation.Transactional;
import todolist.entities.TodoList;

import java.util.ArrayList;

public interface TodoListService {
    @Transactional
    ArrayList<TodoList> getLists();

    @Transactional
    void addTask(TodoList todoList, String task);

    @Transactional
    void addTodoList(TodoList todoList);

    @Transactional
    void deleteTodoList(TodoList todoList);

    @Transactional
    void changeTodoListName(TodoList todoList);

    @Transactional
    void deleteTask(int taskID);

    @Transactional
    void changeTaskName(int taskID, String task);

    @Transactional
    void toggleDone(byte done, int taskID);
}
