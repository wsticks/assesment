package com.williams.assessment.model.request;

import com.williams.assessment.model.constants.Status;
import com.williams.assessment.model.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpsertUserRequest {

    @NotNull
    @org.hibernate.validator.constraints.NotEmpty
    private String firstName;

    @NotNull
    @org.hibernate.validator.constraints.NotEmpty
    private String lastName;

    @NotNull
    @org.hibernate.validator.constraints.NotEmpty
    @Email
    private String email;

    @NotNull
    @org.hibernate.validator.constraints.NotEmpty
    private String password;

    @NotNull
    @org.hibernate.validator.constraints.NotEmpty
    private String address;

    @NotNull
    @org.hibernate.validator.constraints.NotEmpty
    private String phone;
    private String roleId;
    private Status status;

    public User toUser(){
        User user = new User();
        user.setAddress(address);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRoleId(roleId);
        if(status != null){
            user.setStatus(status);
        }
        return user;
    }
}
