package com.williams.assessment.controller;

import com.williams.assessment.exception.AssessmentApiException;
import com.williams.assessment.model.entity.Student;
import com.williams.assessment.model.entity.User;
import com.williams.assessment.model.request.StudentSearchRequest;
import com.williams.assessment.model.request.UpsertStudentRequest;
import com.williams.assessment.model.response.AssessmentApiResponse;
import com.williams.assessment.model.response.SuccessResponse;
import com.williams.assessment.service.facade.AccountFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

    private final AccountFacade accountFacade;

    public StudentController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST,
    consumes = "application/json",
    produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> createStudent(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertStudentRequest upsertStudentRequest) throws Exception{
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.createStudent(upsertStudentRequest, user);
        return  new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT, path = "/{studentKey}",
    consumes = "application/json",
    produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> updateStudent(
            @RequestHeader("S-User-Token") String userToken,
            @PathVariable String studentKey,
            @RequestBody UpsertStudentRequest upsertStudentRequest)
    throws Exception{
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.updateStudent(upsertStudentRequest, user, studentKey );
        return  new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "/{studentKey}",
    produces = "application/json")
  public  ResponseEntity<AssessmentApiResponse> getStudent (@RequestHeader("S-User-Token") String userToken, @PathVariable String studentKey )
    throws AssessmentApiException{
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.viewStudent(user, studentKey);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
  }

  @Transactional
  @RequestMapping(method = RequestMethod.GET,
  produces = "application/json")
  public ResponseEntity<AssessmentApiResponse> getAllStudent(@RequestHeader("S-User-Token") String userToken, StudentSearchRequest request)
  throws AssessmentApiException{
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.viewStudents( user,request);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
  }

//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    void sendMail(){
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("williamsondrums@gmail.com, akintoyekolawole@gmail.com");
//
//        msg.setSubject("School Result");
//        msg.setText("Hello Guardian, this is to notify you that your wards assessment report is ready for pick p, thanks for your usual cooperation.");
//
//        javaMailSender.send(msg);
}

