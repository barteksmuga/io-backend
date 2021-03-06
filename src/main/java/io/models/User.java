package io.models;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false)
    @NotNull(message = "email is needed")
    @Email(message = "give valid email")
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min=7, message = "password should have at least 7 characters")
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Role> roles;

    public User(){}

    public User (String email){
        this.email = email;
        this.isActive = false;
    }


    public  void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() { return this.email; }

    public int getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(isActive, user.isActive);
    }

    public void addRoles(Role theRole){
        if(roles==null){
            roles=new ArrayList<>();
        }
        roles.add(theRole);
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", isActive=" + isActive +
                ", roles=" + roles +
                '}';
    }
}
