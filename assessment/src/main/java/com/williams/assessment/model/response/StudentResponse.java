package com.williams.assessment.model.response;

import com.williams.assessment.model.entity.Student;
import com.williams.assessment.util.TimeUtil;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class StudentResponse {

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
    private String nameOfNextOfKin;
    private String phoneNumberOfNextOfKin;
    private String createdAt;
    private String updatedAt;

    public  static StudentResponse fromStudentUpdate(Student student){
        return StudentResponse.builder()
                .uniqueKey(student.getUniquekey())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .phone(student.getPhone())
                .email(student.getEmail())
                .sex(student.getSex())
                .department(student.getDepartment())
                .form(student.getForm())
                .stateOfOrigin(student.getStateOfOrigin())
                .religion(student.getReligion())
                .address(student.getAddress())
                .nameOfNextOfKin(student.getNameOfNextOfKin())
                .phoneNumberOfNextOfKin(student.getPhoneNumberOfNextOfKin())
                .createdAt(TimeUtil.getIsoTime(student.getCreatedAt()))
                .updatedAt(TimeUtil.getIsoTime(student.getUpdatedAt()))
                .build();
    }

    public static List<StudentResponse> fromStudent(List<Student> students){
        return students.stream().map(student -> {
            return fromStudentUpdate(student);
        }).collect(Collectors.toList());
    }
}
