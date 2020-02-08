package com.williams.assessment.model.response;

import com.williams.assessment.model.entity.Token;
import com.williams.assessment.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthResponse {

    private String token;
    private String expires;
    private String tokenType;

    public static AuthResponse fromToken(Token token) {
        return AuthResponse.builder()
                .token(token.getToken())
                .expires(TimeUtil.getIsoTime(token.getExpiresAt()))
                .tokenType("bearer")
                .build();
    }
}
