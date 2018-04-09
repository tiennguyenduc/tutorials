package com.tnd.gateway.controller;

import lombok.Data;

@Data
public class JwtToken {

    private String access_token;

    private String refresh_token;

    private String token_type;

    private int expires_in;

    private String scope;

    private String jti;

}
