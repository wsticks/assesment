package com.williams.assessment.service;

import com.williams.assessment.exception.AssessmentApiException;
import com.williams.assessment.exception.ConflictException;
import com.williams.assessment.exception.NotFoundException;
import com.williams.assessment.exception.ProcessingException;
import com.williams.assessment.model.entity.Student;
import com.williams.assessment.model.request.StudentSearchRequest;
import com.williams.assessment.model.request.UpsertStudentRequest;
import com.williams.assessment.repository.StudentRepository;
import com.williams.assessment.util.GatewayBeanUtil;
import com.williams.assessment.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private final String STUDENT_EXISTS = "Student already exists";
    private final String STUDENT_NOT_FOUND = "Student to update was not found";



    public StudentService(StudentRepository studentRepository) {
        Assert.notNull(studentRepository);
        this.studentRepository = studentRepository;
    }


    public  Student createStudent (UpsertStudentRequest upsertStudentRequest, Student student)
        throws Exception{
        prepareForSave(student);
        Student student1 = studentRepository.findOneByUniquekey(student.getUniquekey());
        if(student1 != null){
            throw  new ConflictException(STUDENT_EXISTS);
        }
        return studentRepository.save(student);
    }

    public  Student updateStudent(Student updateStudent, String productKey) throws Exception{
        Student studentToUpdate = fetchStudentByUniqueKey(productKey);
        GatewayBeanUtil.copyProperties(updateStudent,studentToUpdate);
        return studentRepository.save(studentToUpdate);
    }

//    public Student prepareStudentForUpdate(Student studentToSave, String uniqueKey){
//        Student savedStudent = fetchStudentByUniqueKey(uniqueKey);
//        savedStudent.setStateOfOrigin(studentToSave.getStateOfOrigin());
//        savedStudent.setFirstName(studentToSave.getFirstName());
//        savedStudent.setLastName(studentToSave.getLastName());
//        savedStudent.setAge(studentToSave.getAge());
//        savedStudent.setEmail(studentToSave.getEmail());
//        savedStudent.setSex(studentToSave.getSex());
//        savedStudent.setDepartment(studentToSave.getDepartment());
//        savedStudent.setForm(studentToSave.getForm());
//        savedStudent.setReligion(studentToSave.getReligion());
//        savedStudent.setAddress(studentToSave.getAddress());
//        savedStudent.setNameOfNextOfKin(studentToSave.getNameOfNextOfKin());
//        savedStudent.setPhone(studentToSave.getPhone());
//        savedStudent.setPhoneNumberOfNextOfKin(studentToSave.getPhoneNumberOfNextOfKin());
//        return studentRepository.save(savedStudent);
//    }

    private void prepareForSave(Student student) throws AssessmentApiException {
        generateUniqueKey(student);
    }



    private void generateUniqueKey(Student student) throws ProcessingException {
        if (student.getUniquekey() != null) {
            return;
        }
        String rawKey = student.getEmail() + LocalDateTime.now() + Math.random() + student.getPhone();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        student.setUniquekey(uniqueKey);
    }

    public Student fetchStudentByUniqueKey(String studentKey){
        Student savedStudent = studentRepository.findOneByUniquekey(studentKey);
        if(savedStudent == null){
            throw new NotFoundException(STUDENT_NOT_FOUND);
        }
        return savedStudent;
    }

    public Student viewStudent(String studentKey){
        Student student = studentRepository.findOneByUniquekey(studentKey);
        return student;
    }

    public Page<Student> viewStudents (StudentSearchRequest student){
        return studentRepository.findAll(student.getBooleanExpression(), student.getPaginationQuery());

    }



}
