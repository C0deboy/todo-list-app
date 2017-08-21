package todolist.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lists", schema = "spring-todo-list")
public class TodoList {
    private int id;
    private String name;
//    private User usersByUserId;
    private List<Task> tasks = new ArrayList<>();

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoList todoList = (TodoList) o;

        if (id != todoList.id) return false;
        return name != null ? name.equals(todoList.name) : todoList.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    public User getUsersByUserId() {
//        return usersByUserId;
//    }
//
//    public void setUsersByUserId(User usersByUserId) {
//        this.usersByUserId = usersByUserId;
//    }

    @OneToMany(mappedBy = "listReference", fetch = FetchType.EAGER)
    @OrderBy("done")
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasksById) {
        this.tasks = tasksById;
    }
}
