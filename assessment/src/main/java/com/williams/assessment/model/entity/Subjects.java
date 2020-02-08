package com.williams.assessment.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subject")
public class Subjects implements Serializable{

    @javax.persistence.Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long Id;

    @Basic
    @Column(name = "unique_key", nullable = false)
    private String uniqueKey;

    @Basic
    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Basic
    @Column(name = "subject_code", nullable = true)
    private String subjectCode;

    @Basic
    @Column(name = "subject_teacher_name",nullable =false )
    private String subjectTeacherName;

    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectTeacherName() {
        return subjectTeacherName;
    }

    public void setSubjectTeacherName(String subjectTeacherName) {
        this.subjectTeacherName = subjectTeacherName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

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
        Subjects subjects = (Subjects) o;
        return Objects.equals(Id, subjects.Id) &&
                Objects.equals(uniqueKey, subjects.uniqueKey) &&
                Objects.equals(subjectName, subjects.subjectName) &&
                Objects.equals(subjectCode, subjects.subjectCode) &&
                Objects.equals(subjectTeacherName, subjects.subjectTeacherName) &&
                Objects.equals(createdAt, subjects.createdAt) &&
                Objects.equals(updatedAt, subjects.updatedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(Id, uniqueKey, subjectName, subjectCode, subjectTeacherName, createdAt, updatedAt);
    }

    @PrePersist
    public void beforeSave() {
        this.createdAt = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = new Timestamp(new Date().getTime());
    }
}
