package com.williams.assessment.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "student")
public class Student implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Basic
    @Column(name="unique_key", nullable = true)
    private String uniquekey;

    @Basic
    @Column(name="first_name", nullable = true)
    private String firstName;

    @Basic
    @Column(name="last_name", nullable = true)
    private String lastName;

    @Basic
    @Column(name="age", nullable = true)
    private String age;

    @Basic
    @Column(name="phone", nullable = true)
    private String phone;

    @Basic
    @Column(name="email", nullable = true)
    private String email;

    @Basic
    @Column(name="sex", nullable = true)
    private String sex;

    @Basic
    @Column(name="department", nullable = true)
    private String department;

    @Basic
    @Column(name="form", nullable = true)
    private String form;


    @Basic
    @Column(name="state_of_origin", nullable = true)
    private String stateOfOrigin;

    @Basic
    @Column(name="religion", nullable = true)
    private String religion;

    @Basic
    @Column(name="address", nullable = true)
    private String address;

    @Basic
    @Column(name="name_of_next_of_kin", nullable = true)
    private String nameOfNextOfKin;

    @Basic
    @Column(name="phone_number_of_next_of_kin", nullable = true)
    private String phoneNumberOfNextOfKin;

//    @Basic
//    @Column(name = "subject_record_key", nullable = true)
//    private String studentRecord;



//    @OneToMany(mappedBy = "subjectRecord", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "student.", cascade = CascadeType.ALL)
    private List<SubjectRecord> subjectRecords;

    @Basic
    @Column(name="created_at", nullable = false)
    private Timestamp createdAt;

    @Basic
    @Column(name="updated_at", nullable = true)
    private Timestamp updatedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUniquekey() { return uniquekey; }

    public void setUniquekey(String uniquekey) { this.uniquekey = uniquekey; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAge() { return age; }

    public void setAge(String age) {this.age = age; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getSex() { return sex; }

    public void setSex(String sex) { this.sex = sex; }

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }

    public String getForm() { return form; }

    public void setForm(String form) { this.form = form; }

    public String getStateOfOrigin() { return stateOfOrigin; }

    public void setStateOfOrigin(String stateOfOrigin) { this.stateOfOrigin = stateOfOrigin; }

    public String getReligion() { return religion; }

    public void setReligion(String religion) { this.religion = religion; }

    public String getAddress() { return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameOfNextOfKin() { return nameOfNextOfKin; }

    public void setNameOfNextOfKin(String nameOfNextOfKin) { this.nameOfNextOfKin = nameOfNextOfKin; }

    public String getPhoneNumberOfNextOfKin() { return phoneNumberOfNextOfKin; }

    public void setPhoneNumberOfNextOfKin(String phoneNumberOfNextOfKin) {
        this.phoneNumberOfNextOfKin = phoneNumberOfNextOfKin;
    }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public List<SubjectRecord> getSubjectRecords() { return subjectRecords; }

    public void setSubjectRecords(List<SubjectRecord> subjectRecords) { this.subjectRecords = subjectRecords; }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(uniquekey, student.uniquekey) &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(age, student.age) &&
                Objects.equals(phone, student.phone) &&
                Objects.equals(email, student.email) &&
                Objects.equals(sex, student.sex) &&
                Objects.equals(department, student.department) &&
                Objects.equals(form, student.form) &&
                Objects.equals(stateOfOrigin, student.stateOfOrigin) &&
                Objects.equals(religion, student.religion) &&
                Objects.equals(address, student.address) &&
                Objects.equals(nameOfNextOfKin, student.nameOfNextOfKin) &&
                Objects.equals(phoneNumberOfNextOfKin, student.phoneNumberOfNextOfKin) &&
//                Objects.equals(studentRecord, student.studentRecord) &&
//                Objects.equals(subjectRecords, student.subjectRecords) &&
                Objects.equals(createdAt, student.createdAt) &&
                Objects.equals(updatedAt, student.updatedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uniquekey, firstName, lastName, age, phone, email, sex, department, form, stateOfOrigin, religion, address, nameOfNextOfKin, phoneNumberOfNextOfKin, /*studentRecord,*/  /*subjectRecords,*/ createdAt, updatedAt);
    }

    @PrePersist
    public void beforeSave() {
        this.createdAt = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = new Timestamp(new Date().getTime());}
}
