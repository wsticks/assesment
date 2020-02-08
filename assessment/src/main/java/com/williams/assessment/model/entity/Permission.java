package com.williams.assessment.model.entity;

import com.williams.assessment.model.constants.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "permission")
public class Permission implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private String code;
    private Status status;
    private Status isHidden;

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
    @Column(name = "code", nullable = false, length = 256)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    @Column(name = "is_hidden", nullable = false, length = 16)
    @Enumerated(value = EnumType.STRING)
    public Status getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Status isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(code, that.code) &&
                status == that.status &&
                isHidden == that.isHidden;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, code, status, isHidden);
    }
}
