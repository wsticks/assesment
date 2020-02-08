package com.williams.assessment.controller;

import com.williams.assessment.exception.AssessmentApiException;
import com.williams.assessment.model.entity.User;
import com.williams.assessment.model.request.SubjectRecordRequest;
import com.williams.assessment.model.request.SubjectRecordSearchRequest;
import com.williams.assessment.model.response.AssessmentApiResponse;
import com.williams.assessment.model.response.SuccessResponse;
import com.williams.assessment.service.facade.AccountFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(path = "records")
public class SubjectRecordController {

    public final AccountFacade accountFacade;


    public SubjectRecordController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> createSubjectRecord(
            @RequestHeader ("S-User-Token") String userToken,
            @RequestBody SubjectRecordRequest subjectRecordRequest)
        throws Exception{
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.createSubjectRecord(subjectRecordRequest, user);
        return  new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @org.springframework.transaction.annotation.Transactional
    @RequestMapping(method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> updateRecord(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody SubjectRecordRequest subjectRecordRequest) throws Exception {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.updateSubjectRecord(subjectRecordRequest, user);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @org.springframework.transaction.annotation.Transactional
    @RequestMapping(path = "/{recordKey}", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<AssessmentApiResponse> viewRecord(
            @RequestHeader("S-User-Token") String userToken,
            @PathVariable String recordKey
    ) throws AssessmentApiException{
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.viewRecord(user, recordKey);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @org.springframework.transaction.annotation.Transactional
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<AssessmentApiResponse> viewRecords(
            @RequestHeader ("S-User-Token") String userToken, SubjectRecordSearchRequest request
    )throws AssessmentApiException{
        User user =  accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.viewRecords(user, request);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

}
