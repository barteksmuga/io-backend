package io.models;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private int id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId"))
    private Set<Role> roles;

    public User(){}

    public User (String email){
        this.email = email;
        this.isActive = false;
    }

    public void setHashedPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        setPassword(hashedPassword);
    }
    private void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return this.email; }

    public String getPassword() {
        return this.password;
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


}
