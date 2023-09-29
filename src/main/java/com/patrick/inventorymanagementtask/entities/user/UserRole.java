package com.patrick.inventorymanagementtask.entities.user;

import javax.persistence.*;

/**
 * @author patrick on 6/19/19
 * @project pos
 */
@Entity
@Table(name ="user_role" )
public class UserRole {

    @Column(name = "role_id")
    private int roleId;

    @Id
    @Column(name = "user_id")
    private int userId;

    @JoinColumn(name ="role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    Role roleLink;

    public int getRoleId() {
        return roleId;
    }

    public UserRole setRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserRole setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Role getRoleLink() {
        return roleLink;
    }

}
