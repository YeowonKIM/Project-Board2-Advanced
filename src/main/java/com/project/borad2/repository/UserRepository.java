package com.project.borad2.repository;

import com.project.borad2.entity.Board;
import com.project.borad2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
