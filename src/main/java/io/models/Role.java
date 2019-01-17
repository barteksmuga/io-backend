package io.models;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long roleId;

    @Column(name = "authorities", nullable = false)
    //@org.hibernate.annotations.ColumnDefault("USER")
    private  String authorities;

    public Role() {

        authorities=new String("USER");
    }




    public String getAuthorities() {
        return authorities;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
