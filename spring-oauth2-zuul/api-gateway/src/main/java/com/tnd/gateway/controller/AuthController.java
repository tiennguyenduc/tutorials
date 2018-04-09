package com.tnd.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AuthController {

    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/token")
    public JwtToken getToken(
            @RequestParam String username,
            @RequestParam String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic YWNtZTphY21lc2VjcmV0");

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JwtToken> response = restTemplate
                .exchange(accessTokenUri, HttpMethod.POST, request, JwtToken.class);

        if (HttpStatus.OK != response.getStatusCode()) {
            return null;
        }

        return response.getBody();
    }

}
