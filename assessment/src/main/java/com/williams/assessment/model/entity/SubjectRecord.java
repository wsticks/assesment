package com.williams.assessment.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table (name = "subject_record")
public class SubjectRecord implements Serializable{

    private Long id;
    private String uniqueKey;
    private String subjectKey;
    private String studentKey;
    private String firstAssessmentTest;
    private String secondAssessmentTest;
    private String thirdAssessmentTest;
    private String fourthAssessmentTest;
    private String examinationScore;
    private int totalScore;
    private String grade;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Student student;




    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @Basic
    @Column(name = "unique_key", nullable = false)
    public String getUniqueKey() { return uniqueKey; }

    public void setUniqueKey(String uniqueKey) { this.uniqueKey = uniqueKey; }

    @Basic
    @Column(name = "subject_key", nullable = false)
    public String getSubjectKey() { return subjectKey; }

    public void setSubjectKey(String subjectKey) { this.subjectKey = subjectKey; }

    @Basic
    @Column(name = "student_key", nullable = false)
    public String getStudentKey() { return studentKey; }

    public void setStudentKey(String studentKey) { this.studentKey = studentKey; }


    @Basic
    @Column(name = "first_assessment", nullable = true)
    public String getFirstAssessmentTest() { return firstAssessmentTest; }

    public void setFirstAssessmentTest(String firstAssessmentTest) { this.firstAssessmentTest = firstAssessmentTest; }

    @Basic
    @Column(name = "second_assessment", nullable = true)
    public String getSecondAssessmentTest() { return secondAssessmentTest; }

    public void setSecondAssessmentTest(String secondAssessmentTest) { this.secondAssessmentTest = secondAssessmentTest; }

    @Basic
    @Column(name = "third_assessment", nullable = true)
    public String getThirdAssessmentTest() { return thirdAssessmentTest; }

    public void setThirdAssessmentTest(String thirdAssessmentTest) { this.thirdAssessmentTest = thirdAssessmentTest; }


    @Basic
    @Column(name = "fourth_assessment", nullable = true)
    public String getFourthAssessmentTest() { return fourthAssessmentTest; }

    public void setFourthAssessmentTest(String forthAssessmentTest) { this.fourthAssessmentTest = forthAssessmentTest; }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Basic
    @Column(name = "updated_at", nullable = true)
    public Timestamp getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    @Basic
    @Column(name = "examination", nullable = false)
    public String getExaminationScore() { return examinationScore; }

    public void setExaminationScore(String examinationScore) { this.examinationScore = examinationScore; }

    @Basic
    @Column(name = "total_score", nullable = false)
    public int getTotalScore() { return totalScore; }

    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    @Basic
    @Column(name = "grade", nullable = false)
    public String getGrade() { return grade; }

    public void setGrade(String grade) { this.grade = grade; }

    @PrePersist
    public void beforeSave() {
        this.createdAt = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = new Timestamp(new Date().getTime());
    }

    @ManyToOne
    @JoinColumn
    public Student getStudent() { return student; }

  public void setStudent(Student student) { this.student = student; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectRecord that = (SubjectRecord) o;
        return totalScore == that.totalScore &&
                Objects.equals(id, that.id) &&
                Objects.equals(uniqueKey, that.uniqueKey) &&
                Objects.equals(subjectKey, that.subjectKey) &&
                Objects.equals(studentKey, that.studentKey) &&
                Objects.equals(firstAssessmentTest, that.firstAssessmentTest) &&
                Objects.equals(secondAssessmentTest, that.secondAssessmentTest) &&
                Objects.equals(thirdAssessmentTest, that.thirdAssessmentTest) &&
                Objects.equals(fourthAssessmentTest, that.fourthAssessmentTest) &&
                Objects.equals(examinationScore, that.examinationScore) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uniqueKey, subjectKey, studentKey, firstAssessmentTest, secondAssessmentTest, thirdAssessmentTest, fourthAssessmentTest, examinationScore, totalScore, grade, createdAt, updatedAt, student);
    }
}
