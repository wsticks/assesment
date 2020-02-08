package com.williams.assessment.Configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
@ConfigurationProperties(prefix = "assessment")
@Data

public class AssessmentApiConfiguration {

    private String webUrl;
    private String contactEmail;
    private String environment;
    private String resetPasswordPath;
}
