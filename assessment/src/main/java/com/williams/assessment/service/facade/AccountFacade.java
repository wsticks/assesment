package com.williams.assessment.service.facade;

import com.williams.assessment.model.entity.*;
import com.williams.assessment.model.request.*;
import com.williams.assessment.model.response.*;
import com.williams.assessment.service.*;
import com.williams.assessment.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component
public class AccountFacade extends RequestFacade {

    private final UserService userService;
    private final RoleService roleService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final PermissionHandler permissionHandler;
    private  final SubjectRecordService subjectRecordService;

    private static final Integer USER_ROLE_ID = 2;
    private static final Integer GLOBAL_ADMIN_ROLE_ID = 1;

    @Autowired
    public AccountFacade(UserService userService,
                         RoleService roleService,
                         StudentService studentService,
                         SubjectService subjectService,
                         PermissionHandler permissionHandler,
                         SubjectRecordService subjectRecordService)
                          {


                              Assert.notNull(subjectService);
        Assert.notNull(userService);
        Assert.notNull(roleService);
        Assert.notNull(studentService);
        Assert.notNull(permissionHandler);
        Assert.notNull(subjectRecordService);
        this.userService = userService;
        this.roleService = roleService;
        this.studentService = studentService;
        this.permissionHandler = permissionHandler;
        this.subjectService = subjectService;
        this.subjectRecordService = subjectRecordService;
                          }

    public SuccessResponse createUser(UpsertUserRequest upsertUserRequest) {
        upsertUserRequest.setRoleId(fetchRoleId(upsertUserRequest));
        User user = userService.createUser(upsertUserRequest.toUser());
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse updateUser(
            UpsertUserRequest upsertUserRequest, String userKey, String userToken) {
        User actor = userService.getAuthenticatedUser(userToken);
        User userToUpdate = userService.fetchByUniqueKey(userKey);
        User updateUser = upsertUserRequest.toUser();
        if (!actor.getUniqueKey().equals(userKey)) {
            ArrayList permissions =
                    roleService.getPermissionCodesForRole(roleService.getRole(actor.getRoleId()).getId());
            permissionHandler.hasPermission(permissions, "user_update");
        }
        User savedUser = userService.updateUser(userToUpdate, updateUser, actor);
        return buildSuccessResponse(createUserResponseData(savedUser));
    }

    public User getAuthenticatedUser(String userToken) {
        return userService.getAuthenticatedUser(userToken);
    }

    public User fetchUserByUniqueKey(String userKey) {
        return userService.fetchByUniqueKey(userKey);
    }

    public SuccessResponse getUserInfo(User user, User actor) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(roleService.getRole(actor.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_view");
        Map<String, Object> data = createUserResponseData(user);
        return buildSuccessResponse(data);
    }

    public SuccessResponse authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        User userToAuthenticate = authenticateUserRequest.toUser();
        User authenticatedUser = userService.authenticateUser(userToAuthenticate);
        Role userRole = roleService.getRole(authenticatedUser.getRoleId());
        ArrayList permissions = roleService.getPermissionCodesForRole(userRole.getId());
        Token token = userService.createToken(authenticatedUser);
        Map<String, Object> data = createUserResponseData(authenticatedUser);
        data.put("permission", permissions);
        data.put("auth", AuthResponse.fromToken(token));
        return buildSuccessResponse(data);
    }

    public SuccessResponse resetPassword(UpsertUserRequest upsertUserRequest) {
        User user = userService.fetchByEmail(upsertUserRequest.getEmail());
        user = changePassword(upsertUserRequest, user);
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse changeUserPassword(UpsertUserRequest upsertUserRequest, User user) {
        user = changePassword(upsertUserRequest, user);
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse logoutUser(User user, String token) {
        userService.logout(token);
        user.setLastLoginDate(TimeUtil.now());
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse confirmUserEmail(ConfirmEmailRequest request) {
        User user = userService.fetchByEmail(request.getEmail());
        user = userService.confirmEmail(user);
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse getAllUsers(User authenticatedUser, UserSearchRequest request) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_index");
        if (!userService.isCurrentGlobal(authenticatedUser.getUniqueKey())) {
            request.setUniqueKey(authenticatedUser.getUniqueKey());
            return createPaymentPaginatedResponse(userService.findAllUsers(request));
        }
        Page<User> userPage = userService.findAllUsers(request);
        return createPaymentPaginatedResponse(userPage);
    } //End of create User!


    private String fetchRoleId(UpsertUserRequest upsertUserRequest){
//        if(upsertUserRequest.getRoleId()=="SHADMIN"){
//            upsertUserRequest.setRoleId(roleService.fetchRoleById(GLOBAL_ADMIN_ROLE_ID).getUniqueKey());
//        }
        if(upsertUserRequest.getRoleId()==null ){
            upsertUserRequest.setRoleId(roleService.fetchRoleById(USER_ROLE_ID).getUniqueKey());
        }
        return upsertUserRequest.getRoleId();
    }

    private Map<String, Object> createUserResponseData(User user) {
        UserResponse userResponse = UserResponse.fromUser(user);
        Map<String, Object> data = new HashMap<>(1);
        data.put("user", userResponse);
        return data;
    }

    private User changePassword(UpsertUserRequest upsertUserRequest, User user) {
        User updateUser = upsertUserRequest.toUser();
        updateUser.setPassword(upsertUserRequest.getPassword());
        user = userService.resetPassword(user, updateUser);
        return user;
    }

    private SuccessResponse createPaymentPaginatedResponse(Page<User> paymentsPage) {
        String contentName = "users";
        Page<UserResponse> userResponses = paymentsPage.map(UserResponse::fromUser);
        userResponses.forEach(
                userResponse -> {
                    userResponse.setRoleName(roleService.getRole(userResponse.getRoleId()).getName());
                });
        return buildPaginatedSuccessResponse(contentName, userResponses);
    }

//    public SuccessResponse createStudent(UpsertStudentRequest upsertStudentRequest, User authenticatedUser)
//    throws Exception{
//        ArrayList permissions = roleService.getPermissionCodesForRole(
//                roleService.getRole(authenticatedUser.getRoleId()).getId());
//        permissionHandler.hasPermission(permissions, "student_create");
//        Student student = studentService.prepareStudentForSave(upsertStudentRequest, upsertStudentRequest.toStudent());
//        return buildSuccessResponse(createStudentResponseData(student));
//    }

    public SuccessResponse createStudent (UpsertStudentRequest upsertStudentRequest, User user) throws Exception{
        ArrayList permissions =
                roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "student_create");
        Student student = studentService.createStudent(upsertStudentRequest, upsertStudentRequest.toStudent());
        return buildSuccessResponse(createStudentResponseData(student));
    }

  public  SuccessResponse updateStudent(UpsertStudentRequest upsertStudentRequest, User user, String studentKey) throws Exception{
      ArrayList permissions =
              roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
      permissionHandler.hasPermission(permissions, "user_update");
      Student updateStudent = upsertStudentRequest.toStudent();
      Student student = studentService.updateStudent(updateStudent, studentKey);
      return buildSuccessResponse(createStudentResponseData(student));
  }

//    public  SuccessResponse updateStudent(UpsertStudentRequest upsertStudentRequest, User user) throws Exception{
//        ArrayList permissions =
//                roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
//        permissionHandler.hasPermission(permissions, "user_update");
//      Student student = studentService.prepareStudentForUpdate(upsertStudentRequest.toStudent(),upsertStudentRequest.getUniqueKey());
//      StudentResponse studentResponse = StudentResponse.fromStudentUpdate(student);
//        Map<String, Object> data = new HashMap<>();
//        data.put("staff", studentResponse);
//        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
//        return successResponse;
//    }

    public SuccessResponse viewStudent(User user, String studentKey) {
        ArrayList permission =
                roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
        permissionHandler.hasPermission(permission, "user_view");
        Student student = studentService.viewStudent(studentKey);
        return buildSuccessResponse(createStudentResponseData(student));
    }

//    public SuccessResponse viewSubject(User user, String subjectKey){
////        ArrayList permission =
////                roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
////        permissionHandler.hasPermission(permission, "subject_view");
////        Subjects subjects = subjectService.viewSubject(subjectKey);
////        return buildSuccessResponse(createSubjectResponseData(subjects));
////
////    }

    public SuccessResponse viewStudents(User user, StudentSearchRequest request){
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(user.getRoleId()).getId());
        permissionHandler.hasPermission(permissions,"user_index");
        Page<Student> students = studentService.viewStudents(request);
        return createStudentPaginatedResponse(students);
    }

//    public SuccessResponse viewSubject(User user, String subjectKey){
//        ArrayList permission =
//                roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
//        permissionHandler.hasPermission(permission, "subject_view");
//        Subjects subjects = subjectService.viewSubject(subjectKey);
//        return buildSuccessResponse(createSubjectResponseData(subjects));
//
//    }

    private SuccessResponse createStudentPaginatedResponse(Page<Student> studentPage) {
        String contentName = "student";
        Page<StudentResponse> studentResponses = studentPage
                .map(StudentResponse::fromStudentUpdate);
        return buildPaginatedSuccessResponse(contentName, studentResponses);
    }

    private SuccessResponse createSubjectPaginatedResponse(Page<Subjects> subjectPage) {
        String contentName = "subject";
        Page<SubjectResponse> subjectResponses = subjectPage
                .map(SubjectResponse::fromSubject);
        return buildPaginatedSuccessResponse(contentName, subjectResponses);
    }

   public SuccessResponse createSubject(UpsertSubjectRequest upsertSubjectRequest, User user)
       throws Exception {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "subject_create");
        Subjects subjects = subjectService.createSubject(upsertSubjectRequest, upsertSubjectRequest.toSubjects());
        return buildSuccessResponse(createSubjectResponseData(subjects));
   }

   public SuccessResponse updateSubject (UpsertSubjectRequest upsertSubjectRequest, User user, String subjectKey) throws Exception{
       ArrayList permissions =
               roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
       permissionHandler.hasPermission(permissions, "subject_update");
       Subjects updateSubject = upsertSubjectRequest.toSubjects();
       Subjects subjects = subjectService.updateSubject(updateSubject, subjectKey);
       return buildSuccessResponse(createSubjectResponseData(subjects));
   }

   public SuccessResponse viewSubject(User user, String subjectKey){
       ArrayList permission =
               roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
       permissionHandler.hasPermission(permission, "subject_view");
       Subjects subjects = subjectService.viewSubject(subjectKey);
       return buildSuccessResponse(createSubjectResponseData(subjects));

   }

    public SuccessResponse viewSubjects(User user, SubjectSearchRequest subject) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "subject_index");
      Page<Subjects> subjects = subjectService.viewSubjects(subject);
      return createSubjectPaginatedResponse(subjects);
    }


    private Map<String, Object> createSubjectResponseData(Subjects subjects) {
        SubjectResponse subjectResponse = SubjectResponse.fromSubject(subjects);
        Map<String, Object> data = new HashMap<>(1);
        data.put("subject", subjectResponse);
        return data;
    }

    private Map<String, Object> createStudentResponseData(Student student) {
        StudentResponse subjectResponse = StudentResponse.fromStudentUpdate(student);
        Map<String, Object> data = new HashMap<>(1);
        data.put("student", subjectResponse);
        return data;
    }
//
//    private Map<String, Object> createSubjectResponse(List<Subjects> subjects) {
//        List<SubjectResponse> subjectResponse = SubjectResponse.fromSubjects(subjects);
//        Map<String, Object> data = new HashMap<>(1);
//        data.put("subject", subjectResponse);
//        return data;
//    }

    public SuccessResponse createSubjectRecord(SubjectRecordRequest subjectRecordRequest, User user)
    throws Exception{
        ArrayList permissions=
                roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_create");
        SubjectRecordResponse subjectRecordResponse = subjectRecordService.prepareRecordForSave(subjectRecordRequest);
        Map<String, Object> data = new HashMap<>();
        data.put("subject_record", subjectRecordResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

//   public SuccessResponse createSubjectRecord(SubjectRecordRequest subjectRecordRequest, User user )
//           throws Exception{
//       ArrayList permissions =
//               roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
//       permissionHandler.hasPermission(permissions, "user_create");
//       SubjectRecord subjectRecord = subjectRecordService.createSubjectRecord(subjectRecordRequest, subjectRecordRequest.toSudentRecords());
//       return buildSuccessResponse(createStudentRecordResponseData(subjectRecord));
//   }

   public SuccessResponse updateSubjectRecord(SubjectRecordRequest subjectRecordRequest, User user)
           throws Exception{
       ArrayList permissions =
               roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
       permissionHandler.hasPermission(permissions, "user_update");
      SubjectRecord subjectRecord = subjectRecordService
              .updateSubjectRecord(subjectRecordRequest.toStudentRecords(), subjectRecordRequest.getUniqueKey());
      SubjectRecordResponse subjectRecordResponse = SubjectRecordResponse.fromSubjectRecord(subjectRecord);
       Map<String, Object> data = new HashMap<>();
       data.put("subject_record", subjectRecordResponse);
       SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
       return  successResponse;
   }

   public SuccessResponse viewRecord(User user, String recordKey){
       ArrayList permission =
               roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
       permissionHandler.hasPermission(permission, "user_view");
       SubjectRecord subjectRecord = subjectRecordService.viewRecord(recordKey);
       SubjectRecordResponse subjectRecordResponse = SubjectRecordResponse.fromSubjectRecord(subjectRecord);
       Map<String, Object> data = new HashMap<>();
       data.put("subject_record", subjectRecord);
       SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
       return successResponse;
   }

   public SuccessResponse viewRecords(User user, SubjectRecordSearchRequest request){
       ArrayList permissions =
               roleService.getPermissionCodesForRole(roleService.getRole(user.getRoleId()).getId());
       permissionHandler.hasPermission(permissions, "user_index");
       Page<SubjectRecord> subjectRecords = subjectRecordService.viewRecords(request);
       return createSubjectRecordPaginatedResponse(subjectRecords);
   }

//    public  SuccessResponse viewRecords(User user){
//        ArrayList permissions =
//                roleService.getPermissionCodesForRole(
//                        roleService.getRole(user.getRoleId()).getId());
//        permissionHandler.hasPermission(permissions, "user_index");
//        List<SubjectRecord> subjectRecords = subjectRecordService.viewRecords();
//        List<SubjectRecordResponse> subjectRecordResponses = SubjectRecordResponse.fromSubjectRecord(subjectRecords);
//        Map<String, Object> data = new HashMap<>();
//        data.put("subject_record", subjectRecordResponses);
//        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
//        return successResponse;
//
//    }

//    private Map<String, Object> createStudentRecordResponseData(SubjectRecord subjectRecord) {
//        SubjectRecordResponse subjectRecordResponse = SubjectRecordResponse.fromSubjectRecord(subjectRecord);
//        Map<String, Object> data = new HashMap<>(1);
//        data.put("student_record", subjectRecordResponse);
//        return data;
//    }

//    private Map<String, Object> createSubjectRecordResponseData(SubjectRecord subjectRecord) {
//        SubjectRecordResponse subjectRecordResponse = SubjectRecordResponse.fromSubjectRecord(subjectRecord);
//        Map<String, Object> data = new HashMap<>(1);
//        data.put("subjectRecord", subjectRecordResponse);
//        return data;
//    }

//    private SuccessResponse createSubjectRecordResponse(Page<SubjectRecord> subjectRecords) {
//        List<SubjectRecordResponse> subjectRecordResponsese = SubjectRecordResponse.fromSubjectRecord(subjectRecords);
//        Map<String, Object> data = new HashMap<>(1);
//        data.put("subject_record", subjectRecordResponsese);
//        return data;
//    }

    private SuccessResponse createSubjectRecordPaginatedResponse(Page<SubjectRecord> recordPage) {
        String contentName = "record";
        Page<SubjectRecordResponse> subjectRecordResponses = recordPage
                .map(SubjectRecordResponse::fromSubjectRecord);
        return buildPaginatedSuccessResponse(contentName, subjectRecordResponses);
    }
}
