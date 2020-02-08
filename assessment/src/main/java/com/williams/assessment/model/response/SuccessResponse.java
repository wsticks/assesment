package com.williams.assessment.model.response;

import com.williams.assessment.model.constants.Status;
import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SuccessResponse extends AssessmentApiResponse {

    private Status status;
    private Map<String, Object> data;
}
