package com.williams.assessment.model.entity;

import com.williams.assessment.model.constants.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "role_permission")
public class RolePermission implements Serializable {

    private Integer id;
    private Integer roleId;
    private Integer permissionId;
    private Status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role_id", nullable = false)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "permission_id", nullable = false)
    public Integer getPermssionId() {
        return permissionId;
    }

    public void setPermssionId(Integer permssionId) {
        this.permissionId = permssionId;
    }

    @Basic
    @Column(name = "status", nullable = false, length = 16)
    @Enumerated(value = EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = true)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(roleId, that.roleId) &&
                Objects.equals(permissionId, that.permissionId) &&
                status == that.status &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, roleId, permissionId, status, createdAt, updatedAt);
    }
}
