package com.melex.job.repository;


import com.melex.job.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    boolean existsByEmail(String email);
}
