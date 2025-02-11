package com.kolown.porring.security.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kolown.porring.account.entity.OAuthType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.class)
public class UserProfileDTO {

    private OAuthType oAuthType;

    private String oauthNumber;

    private String email;

}
