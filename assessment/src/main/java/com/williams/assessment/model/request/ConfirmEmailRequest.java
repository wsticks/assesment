package com.williams.assessment.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ConfirmEmailRequest {

    private String email;

}
