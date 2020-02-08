package com.williams.assessment.exception;

public class BadRequestException extends AssessmentApiException {

    public BadRequestException(String message) {
        super(message);
    }
}
