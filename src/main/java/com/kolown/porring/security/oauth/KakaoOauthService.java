package com.kolown.porring.security.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolown.porring.account.entity.OAuthType;
import com.kolown.porring.security.dto.TokenResponseDTO;
import com.kolown.porring.security.dto.UserProfileDTO;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class KakaoOauthService implements OauthService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final KakaoConfig kakaoConfig;


    public String getAuthorizationUrl() throws UnsupportedEncodingException {
        return String.format(kakaoConfig.getAuthorizeRequestUrl(),
                kakaoConfig.getClientId(),
                URLEncoder.encode(kakaoConfig.getRedirectUri(), StandardCharsets.UTF_8)
        );
    }

    public Optional<TokenResponseDTO> getAccessToken(String code) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_secret", kakaoConfig.getClientSecret());
        params.add("redirect_uri", kakaoConfig.getRedirectUri());
        params.add("client_id", kakaoConfig.getClientId());
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenResponseDTO> response = restTemplate.postForEntity(
                kakaoConfig.getTokenRequestUrl(),
                requestEntity,
                TokenResponseDTO.class
        );

        return Optional.of(response.getBody());
    }


    public UserProfileDTO getUserProfile(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        List<String> properties = List.of("kakao_account.email");
        String jsonString = objectMapper.writeValueAsString(properties);

        URI uri = UriComponentsBuilder.fromUriString(kakaoConfig.getUserInfoRequestUrl())
                .queryParam("property_keys", jsonString)
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class
        );
        return getUserProfileDTO(response.getBody());

    }

    private UserProfileDTO getUserProfileDTO(String body) throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(body);
        return UserProfileDTO.builder()
                .oauthNumber(jsonNode.get("id").asText())
                .oAuthType(OAuthType.KAKAO)
                .email(jsonNode.get("kakao_account").get("email").asText())
                .build();
    }


}
