package todolist.entities;

import todolist.validators.ValidEmail;
import todolist.validators.ValidPassword;
import todolist.validators.ValidUsername;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema = "spring-todo-list")
public class User {
  private int id;

  @ValidUsername
  private String username;

  @ValidPassword
  private String password;

  @ValidEmail
  private String email;

  private String role = "USER";

  private String resetPasswordToken = null;

  //private List<TodoList> todoLists = new ArrayList<>();

  public User() {
  }

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
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
  @Column(name = "username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String login) {
    this.username = login;
  }

  @Basic
  @Column(name = "password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Basic
  @Column(name = "email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Basic
  @Column(name = "resetPasswordToken")
  public String getResetPasswordToken() {
    return resetPasswordToken;
  }

  public void setResetPasswordToken(String resetPasswordToken) {
    this.resetPasswordToken = resetPasswordToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;
    if (id != user.id) {
      return false;
    }
    if (username != null ? !username.equals(user.username) : user.username != null) {
      return false;
    }
    if (password != null ? !password.equals(user.password) : user.password != null) {
      return false;
    }
    if (email != null ? !email.equals(user.email) : user.email != null) {
      return false;
    }
    return role != null ? role.equals(user.role) : user.role == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (username != null ? username.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (role != null ? role.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password
        + '\'' + ", email='" + email + '\'' + ", role='" + role + '\'' + ", resetPasswordToken='"
        + resetPasswordToken + '\'' + '}';
  }

  @Basic
  @Column(name = "role")
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

//    @OneToMany(mappedBy = "tasks")
//    public List<TodoList> getTodoLists() {
//        return todoLists;
//    }
//
//    public void setTodoLists(List<TodoList> todoLists) {
//        this.todoLists = todoLists;
//    }
}
