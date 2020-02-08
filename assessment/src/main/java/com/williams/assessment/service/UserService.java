package com.williams.assessment.service;

import com.williams.assessment.exception.*;
import com.williams.assessment.model.constants.Status;
import com.williams.assessment.model.entity.Role;
import com.williams.assessment.model.entity.Token;
import com.williams.assessment.model.entity.User;
import com.williams.assessment.model.request.UserSearchRequest;
import com.williams.assessment.repository.RoleRepository;
import com.williams.assessment.repository.TokenRepository;
import com.williams.assessment.repository.UserRepository;
import com.williams.assessment.util.GatewayBeanUtil;
import com.williams.assessment.util.SecurityUtil;
import com.williams.assessment.util.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final String NO_PASSWORD = "password may not be null or empty";
    private static final int SESSION_LIFE = 300;
    private static final String USER_NOT_FOUND = "User not found";
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private static final Integer GLOBAL_USER = 1;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       TokenRepository tokenRepository){
        Assert.notNull(userRepository);
        Assert.notNull(roleRepository);
        Assert.notNull(tokenRepository);
        this.roleRepository = roleRepository;
        this.tokenRepository=tokenRepository;
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        prepareUserForCreation(user);
        validateUserForCreation(user);
        setUserStatus(user);
        return userRepository.save(user);
    }

    public User updateUser(User userToUpdate, User updateUser, User authenticatedUser) {
        prepareUserForUpdate(updateUser);
        GatewayBeanUtil.copyProperties(updateUser, userToUpdate);
        prepareUserForSave(userToUpdate);
        return userRepository.save(userToUpdate);
    }

    public User resetPassword(User user, User updateUser) {
        if (updateUser.getPassword() == null || updateUser.getPassword().isEmpty()) {
            throw new BadRequestException(NO_PASSWORD);
        }
        user.setPassword(updateUser.getPassword());
        hashPassword(user);
        if (user.getStatus() == Status.NEW) {
            user.setStatus(Status.ACTIVE);
        }
        return userRepository.save(user);
    }

    public void logout(String tokenValue) {
        Token token = tokenRepository.findOneByToken(tokenValue);
        token.setStatus(Status.INACTIVE);
        tokenRepository.save(token);
    }

    public User confirmEmail(User user) {
        if (user.getStatus() == Status.NEW) {
            user.setStatus(Status.ACTIVE);
        }
        return userRepository.save(user);
    }

    public Page<User> findAllUsers(UserSearchRequest request) {
        return userRepository
                .findAll(request.getBooleanExpression(), request.getPaginationQuery());
    }

    public User authenticateUser(User user) throws AuthenticationException {
        User savedUser = userRepository.findOneByEmail(user.getEmail());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if ((savedUser == null)
                || !encoder.matches(
                user.getPassword() + savedUser.getUniqueKey(), savedUser.getPassword())) {
            throw new AuthenticationException("User not authenticated");
        } else if (savedUser != null) {
            if (savedUser.getStatus() == Status.NEW) {
                throw new AuthenticationException(
                        "User yet to complete registration. Kindly, check your mail and complete the registration");
            } else if (savedUser.getStatus() == Status.INACTIVE) {
                throw new AuthenticationException(
                        "Your account has been deactivated. Kindly, contact admin");
            }
        }
        savedUser.setLastLoginDate(TimeUtil.now());
        return savedUser;
    }

    public User getAuthenticatedUser(String token) throws AuthenticationException {
        Timestamp now = new Timestamp(new Date().getTime());
        Token userToken = tokenRepository.findOneByToken(token);
        if (userToken == null ||
                userToken.getExpiresAt().before(now) ||
                userToken.getStatus() != Status.ACTIVE) {
            if (userToken != null && userToken.getStatus() == Status.ACTIVE) {
                userToken.setStatus(Status.INACTIVE);
                tokenRepository.save(userToken);
            }
            throw new AuthenticationException("User not authenticated");
        }
        extendSession(userToken);
        return userRepository.findOneByUniqueKey(userToken.getUser());
    }

    public Token createToken(User user) throws ProcessingException {
        String rawKey = user.getUniqueKey() + new Date().toString();
        String tokenValue = SecurityUtil.hashWithMd5(rawKey);
        Token token = new Token();
        token.setStatus(Status.ACTIVE);
        token.setToken(tokenValue);
        token.setUser(user.getUniqueKey());
        token.setExpiresAt(TimeUtil.futureTime(SESSION_LIFE));
        return tokenRepository.save(token);
    }

    public User fetchByUniqueKey(String uniqueKey) {
        User user = userRepository.findOneByUniqueKey(uniqueKey);
        if (user == null) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return user;
    }

    public User fetchByEmail(String email) {
        User user = userRepository.findOneByEmail(email);
        if (user == null) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return user;
    }

    private void prepareUserForSave(User user) throws AssessmentApiException {
        normalizePhone(user);
    }

    private void prepareUserForUpdate(User user) {
        user.setPassword(null);
    }

    private void prepareUserForCreation(User user) throws AssessmentApiException {
        generateUniqueKey(user);
        normalizePhone(user);
        hashPassword(user);
    }

    private void generateUniqueKey(User user) throws ProcessingException {
        if (user.getUniqueKey() != null) {
            return;
        }
        String rawKey = user.getEmail() + LocalDateTime.now() + Math.random();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        user.setUniqueKey(uniqueKey);
    }

    private void normalizePhone(User user) throws BadRequestException {
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            return;
        }
//    String normalizedPhone = PhoneUtil.normalizePhone(user.getPhone());
//    user.setPhone(normalizedPhone);
    }

    private void hashPassword(User user) {
        if (user.getPassword() == null) {
            user.setPassword(NO_PASSWORD);
            return;
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword() + user.getUniqueKey());
        user.setPassword(encodedPassword);
    }

    private void validateUserForCreation(User user) throws ConflictException {
        verifyEmailIsUnique(user);
        verifyPhoneIsUnique(user);
        verifyRoleId(user);
    }

    private void verifyRoleId(User user) throws NotFoundException {
        Role savedRole = roleRepository.findOneByUniqueKey(user.getRoleId());
        if (savedRole == null) {
            throw new NotFoundException("Role doesn't exist");
        }

    }

    private void extendSession(Token token) {
        LocalDateTime tokenExpiryTime = token.getExpiresAt().toLocalDateTime();
        if (ChronoUnit.SECONDS.between(LocalDateTime.now(), tokenExpiryTime) < SESSION_LIFE / 2) {
            token.setExpiresAt(TimeUtil.futureTime(SESSION_LIFE));
            tokenRepository.save(token);
        }
    }

    private void setUserStatus(User user) {
        user.setStatus(Status.NEW);
    }

    private void verifyEmailIsUnique(User user) throws ConflictException {
        User savedUser = userRepository.findOneByEmail(user.getEmail());
        if (savedUser != null) {
            throw new ConflictException("User with this email already exists");
        }
    }

    private void verifyPhoneIsUnique(User user) throws ConflictException {
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            return;
        }
        User savedUser = userRepository.findOneByPhone(user.getPhone());
        if (savedUser != null) {
            throw new ConflictException("User with this phone number already exists");
        }
    }

    public boolean isCurrentGlobal(String userKey) {
        User user = fetchUserByUniqueKey(userKey);
        if (user.getId() == GLOBAL_USER) {
            return true;
        }
        return false;
    }

    public User fetchUserByUniqueKey(String uniqueKey) {
        User user = userRepository.findOneByUniqueKey(uniqueKey);
        if (user == null) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return user;
    }

}
