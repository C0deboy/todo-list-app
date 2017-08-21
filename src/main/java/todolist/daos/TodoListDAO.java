package todolist.daos;

import todolist.entities.TodoList;

import java.util.ArrayList;

public interface TodoListDAO {
    ArrayList<TodoList> getLists();

    void addTask(TodoList todoList, String task);

    void changeTaskName(int taskID, String task);

    void addTodoList(TodoList todoList);

    void deleteTodoList(TodoList todoList);

    void changeListName(TodoList todoList);

    void deleteTask(int taskID);

    void toggleDone(byte done, int taskID);
}
