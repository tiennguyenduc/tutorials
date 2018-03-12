package com.tnd.uaa.service;

import com.tnd.uaa.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findUserByUsername(String username);

}
