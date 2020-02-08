package com.williams.assessment.model.entity;

import com.williams.assessment.model.constants.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    private Integer id;
    private String uniqueKey;
    private String name;
    private String description;
    private Status status;
    private Status isHidden;
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
    @Column(name = "unique_key", nullable = false, length = 32)
    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(uniqueKey, role.uniqueKey) &&
                Objects.equals(name, role.name) &&
                Objects.equals(description, role.description) &&
                status == role.status &&
                isHidden == role.isHidden &&
                Objects.equals(createdAt, role.createdAt) &&
                Objects.equals(updatedAt, role.updatedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uniqueKey, name, description, status, isHidden, createdAt, updatedAt);
    }

    @PrePersist
    public void beforeSave() {
        this.createdAt = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = new Timestamp(new Date().getTime());
    }

    @Basic
    @Column(name = "is_hidden", nullable = false, length = 16)
    @Enumerated(value = EnumType.STRING)
    public Status getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Status isHidden) {
        this.isHidden = isHidden;
    }
}
