package com.tnd.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String CSRF_TOKEN_COOKIE = "XSRF-TOKEN";
    private final static String CSRF_TOKEN_HEADER = "X-XSRF-TOKEN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
//            .csrf().csrfTokenRepository(csrfTokenRepository())
//            .and()
//            .addFilterAfter(csrfHeaderFilter(), SessionManagementFilter.class)
        ;
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                            HttpServletResponse httpServletResponse,
                                            FilterChain filterChain) throws ServletException, IOException {
                CsrfToken csrfToken = (CsrfToken) httpServletRequest.getAttribute(CsrfToken.class.getName());
                if (csrfToken != null) {
                    Cookie cookie = WebUtils.getCookie(httpServletRequest, CSRF_TOKEN_COOKIE);
                    String token = csrfToken.getToken();
                    if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie(CSRF_TOKEN_COOKIE, token);
                        cookie.setPath("/");
//                        cookie.setSecure(true);
//                        cookie.setHttpOnly(true);
                        httpServletResponse.addCookie(cookie);
                    }
                }
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(CSRF_TOKEN_HEADER);
        return repository;
    }

}
