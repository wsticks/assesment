package com.williams.assessment.exception;

public class ProcessingException extends AssessmentApiException {

    public ProcessingException(String message) {
        super(message);
    }

    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
