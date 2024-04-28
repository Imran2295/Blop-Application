package com.MyblogNew.repo;

import com.MyblogNew.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity , Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);



}
