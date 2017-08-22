package todolist.entities;

import javax.persistence.*;

@Entity
@Table(name = "tasks", schema = "spring-todo-list")
public class Task {
    private int id;
    private String task;
    private byte done;
    private TodoList listReference;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "task")
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Basic
    @Column(name = "done")
    public byte getDone() {
        return done;
    }

    public void setDone(byte done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task1 = (Task) o;

        if (id != task1.id) return false;
        if (done != task1.done) return false;
        if (task != null ? !task.equals(task1.task) : task1.task != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (task != null ? task.hashCode() : 0);
        result = 31 * result + (int) done;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "list_id", referencedColumnName = "id", nullable = false)
    public TodoList getListReference() {
        return listReference;
    }

    public void setListReference(TodoList listReference) {
        this.listReference = listReference;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", done=" + done +
                '}';
    }
}