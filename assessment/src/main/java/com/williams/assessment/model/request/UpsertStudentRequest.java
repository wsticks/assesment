package com.williams.assessment.model.request;

import com.williams.assessment.model.entity.Student;
//import com.williams.assessment.model.entity.SubjectRecord;
import com.williams.assessment.model.entity.SubjectRecord;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class UpsertStudentRequest {

    private String uniqueKey;
    private String firstName;
    private String lastName;
    private String age;
    private String phone;
    private String email;
    private String sex;
    private String department;
    private String form;
    private String religion;
    private String stateOfOrigin;
    private String address;
//    private List<SubjectRecord> subjectRecord;
    private String nameOfNextOfKin;
    private String phoneNumberOfNextOfKin;

    public Student toStudent(){
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setAge(age);
        student.setPhone(phone);
        student.setEmail(email);
        student.setSex(sex);
        student.setDepartment(department);
        student.setForm(form);
        student.setStateOfOrigin(stateOfOrigin);
        student.setReligion(religion);
        student.setAddress(address);
//        student.setSubjectRecords(subjectRecord);
        student.setNameOfNextOfKin(nameOfNextOfKin);
        student.setPhoneNumberOfNextOfKin(phoneNumberOfNextOfKin);
        return student;
    }
}
