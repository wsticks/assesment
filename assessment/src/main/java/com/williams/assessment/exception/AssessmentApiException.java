package com.williams.assessment.exception;

public class AssessmentApiException extends RuntimeException {

    AssessmentApiException(String message){super(message);}

    AssessmentApiException(String message, Throwable cause){
        super(message,cause);
        if(this.getCause() == null && cause != null){
            this.initCause(cause);
        }
    }
}
