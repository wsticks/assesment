package com.williams.assessment;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class FeedBack {
    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Min(10)
    private String feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
