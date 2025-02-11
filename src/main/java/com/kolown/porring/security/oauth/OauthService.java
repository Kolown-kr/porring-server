package com.kolown.porring.security.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kolown.porring.security.dto.TokenResponseDTO;
import com.kolown.porring.security.dto.UserProfileDTO;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface OauthService {

    String getAuthorizationUrl() throws UnsupportedEncodingException;

    Optional<TokenResponseDTO> getAccessToken(String code);

    UserProfileDTO getUserProfile(String accessToken) throws JsonProcessingException;

}
