package com.tnd.uaa.repository;

import java.util.Optional;

import com.tnd.uaa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
