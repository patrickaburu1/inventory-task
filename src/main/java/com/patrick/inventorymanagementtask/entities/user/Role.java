package com.patrick.inventorymanagementtask.entities.user;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role {

    public static String ROLE_CREATED="CREATED";
    public static String SELF_REGISTERED="SELF_REGISTRED";
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="role_id")
    private int id;

    @Column(name="role")
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

