package com.tnd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private String signingKey;

    private Integer accessTokenValidityPeriod;

    private Integer refreshTokenValidityPeriod;

}
