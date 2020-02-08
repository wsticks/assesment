package com.williams.assessment.controller;

import com.williams.assessment.exception.AssessmentApiException;
import com.williams.assessment.model.entity.User;
import com.williams.assessment.model.request.AuthenticateUserRequest;
import com.williams.assessment.model.request.UpsertUserRequest;
import com.williams.assessment.model.request.UserSearchRequest;
import com.williams.assessment.model.response.AssessmentApiResponse;
import com.williams.assessment.model.response.SuccessResponse;
import com.williams.assessment.service.facade.AccountFacade;
import com.williams.assessment.validator.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {


    private final AccountFacade accountFacade;

    @Autowired
    public UserController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> toCreateUser(
            @Valid @RequestBody UpsertUserRequest upsertUserRequest,
            BindingResult bindingResult){
        InputValidator.validate(bindingResult);
        SuccessResponse response = accountFacade.createUser(upsertUserRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT, path = "/{userKey}", produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> update(
            @PathVariable String userKey,
            @RequestBody UpsertUserRequest upsertUserRequest,
            @RequestHeader("S-User-Token") String userToken) {
        SuccessResponse response = accountFacade.updateUser(upsertUserRequest, userKey, userToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path = "/{userKey}", produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> getUserInfo(
            @RequestHeader("S-User-Token") String userToken,
            @PathVariable String userKey) {
        User actor = accountFacade.getAuthenticatedUser(userToken);
        User user = accountFacade.fetchUserByUniqueKey(userKey);
        SuccessResponse successResponse =
                accountFacade.getUserInfo(user, actor);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/authenticate",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> authenticate(
            @Valid @RequestBody AuthenticateUserRequest request,
            BindingResult bindingResult) throws AssessmentApiException {
        InputValidator.validate(bindingResult);
        SuccessResponse response = accountFacade.authenticateUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @RequestMapping(
//            method = RequestMethod.POST,
//            path = "/passwordreset",
//            produces = "application/json")
//    public ResponseEntity<AppraisalApiResponse> resetPassword(
//            @RequestBody UpsertUserRequest upsertUserRequest
//    ) {
//        SuccessResponse response = accountFacade.resetPassword(upsertUserRequest);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/passwordchange",
            produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> changePassword(
            @RequestBody UpsertUserRequest upsertUserRequest,
            @RequestHeader("S-User-Token") String userToken
    ) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse response = accountFacade.changeUserPassword(upsertUserRequest, user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/logout",
            produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> logout(
            @RequestHeader("S-User-Token") String userToken
    ) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse response = accountFacade.logoutUser(user, userToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @RequestMapping(
//            method = RequestMethod.POST,
//            path = "/confirmemail",
//            produces = "application/json")
//    public ResponseEntity<AppraisalApiResponse> confirmEmail(
//            @RequestBody ConfirmEmailRequest request) {
//        SuccessResponse response = accountFacade.confirmUserEmail(request);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<AssessmentApiResponse> getUsers(
            @RequestHeader("S-User-Token") String userToken,
            @ModelAttribute UserSearchRequest userSearchRequest)
            throws AssessmentApiException {
        User authenticatedUser = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse response = accountFacade.getAllUsers(authenticatedUser, userSearchRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
