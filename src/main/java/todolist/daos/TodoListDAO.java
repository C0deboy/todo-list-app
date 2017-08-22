package todolist.daos;

import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;

import java.util.ArrayList;

public interface TodoListDAO {

    ArrayList<TodoList> getTodolistsFor(User owner);

    void addTask(Task task);

    void changeTaskName(int taskID, String task);

    void addTodoList(TodoList todoList);

    void deleteTodoList(TodoList todoList);

    void changeListName(TodoList todoList);

    void deleteTask(int taskID);

    void toggleDone(byte done, int taskID);
}
