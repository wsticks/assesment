package com.williams.assessment.controller;

import com.williams.assessment.model.entity.User;
import com.williams.assessment.model.request.SubjectSearchRequest;
import com.williams.assessment.model.request.UpsertSubjectRequest;
import com.williams.assessment.model.response.AssessmentApiResponse;
import com.williams.assessment.model.response.SuccessResponse;
import com.williams.assessment.service.facade.AccountFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/subjects")
public class SubjectController {

    private final AccountFacade accountFacade;

    public SubjectController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> createSubject(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertSubjectRequest upsertSubjectRequest)
    throws Exception{
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.createSubject(upsertSubjectRequest, user);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT,
            path = "/{subjectKey}",
            consumes = "application/json")
    public ResponseEntity<AssessmentApiResponse> updateSubject(
            @RequestHeader("S-User-Token") String userToken,
            @PathVariable String subjectKey,
            @RequestBody UpsertSubjectRequest upsertSubjectRequest ) throws Exception {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.updateSubject(upsertSubjectRequest, user, subjectKey);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "/{subjectKey}",
            produces = "application/json")
    public  ResponseEntity<AssessmentApiResponse> viewSubject(
            @RequestHeader("S-User-Token") String userToken, @PathVariable String subjectKey){
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.viewSubject(user, subjectKey);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> viewSubjects(@RequestHeader("S-User-Token") String userToken, SubjectSearchRequest subject) throws Exception {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.viewSubjects(user,subject );
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
