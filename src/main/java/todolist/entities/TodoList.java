package todolist.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lists", schema = "spring-todo-list")
public class TodoList {


  private int id;
  private String name;
  private int ownerID;
  private List<Task> tasks = new ArrayList<>();

  public TodoList() {
  }

  public TodoList(String name, int ownerID) {
    this.name = name;
    this.ownerID = ownerID;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @Basic
  @Column(name = "user_id")
  public int getOwnerID() {
    return ownerID;
  }

  public void setOwnerID(int ownerID) {
    this.ownerID = ownerID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TodoList todoList = (TodoList) o;

    return id == todoList.id && (name != null ? name.equals(todoList.name) : todoList.name == null);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "TodoList{" + "id=" + id + ", name='" + name + '\''
        + ", ownerID=" + ownerID + ", tasks=" + tasks + '}';
  }

  @OneToMany(mappedBy = "listReference", fetch = FetchType.EAGER)
  @OrderBy("done")
  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasksById) {
    this.tasks = tasksById;
  }
}
