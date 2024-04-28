package com.MyblogNew.repo;

import com.MyblogNew.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role , Long> {

    Role findByRoleName(String name);
}
