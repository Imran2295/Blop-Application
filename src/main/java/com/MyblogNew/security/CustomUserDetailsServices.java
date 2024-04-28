package com.MyblogNew.security;

import com.MyblogNew.Entity.Role;
import com.MyblogNew.Entity.UserEntity;
import com.MyblogNew.exception.RecordNotFoundException;
import com.MyblogNew.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsServices implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);
        if(user == null){
            throw new RecordNotFoundException("wrong username not available");
        }

        return  User.withUsername(username)
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::getRoleName).toArray(String[]::new))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
