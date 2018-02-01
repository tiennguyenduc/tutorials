package com.tnd.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tnd.domain.User;

public interface UserService extends UserDetailsService {

    User findUserByUsername(String username);

}
