package todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tasks", schema = "spring-todo-list")
public class Task {
  private int id;
  private String task;
  private byte done;
  @JsonIgnore
  private TodoList listReference;

  public Task() {
  }
  public Task(String task, TodoList listReference) {
    this.task = task;
    this.listReference = listReference;
  }

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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Task task1 = (Task) o;

    if (id != task1.id) {
      return false;
    }
    if (done != task1.done) {
      return false;
    }
    return task != null ? task.equals(task1.task) : task1.task == null;
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
    return "Task{id=" + id + ", task='" + task + '\'' + ", done=" + done + '}';
  }
}
