package com.MyblogNew.Controller;

import com.MyblogNew.Entity.Role;
import com.MyblogNew.Entity.UserEntity;
import com.MyblogNew.exception.RecordNotFoundException;
import com.MyblogNew.payload.SignUpDto;
import com.MyblogNew.payload.signInDto;
import com.MyblogNew.repo.RoleRepo;
import com.MyblogNew.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepo roleRepo;

    // http://localhost:8080/api/user/sign-up
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto){
        if(userRepo.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email Id "+signUpDto.getEmail() +" is Already registered" , HttpStatus.NOT_ACCEPTABLE);
        }
        if(userRepo.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("username "+signUpDto.getUsername()+" is already taken" , HttpStatus.NOT_ACCEPTABLE);
        }
        UserEntity user = new UserEntity();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));


        Role defaultRole = roleRepo.findByRoleName("USER");
        if(defaultRole == null){
            defaultRole = new Role();
            defaultRole.setRoleName("USER");
            roleRepo.save(defaultRole);
        }

        user.getRoles().add(defaultRole);
        userRepo.save(user);
        return new ResponseEntity<>("Sign up successful" , HttpStatus.CREATED);
    }

    // http://localhost:8080/api/user/sign-in
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody signInDto signIn){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                signIn.getUsername() , signIn.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return new ResponseEntity<>("Successfully Signed In" , HttpStatus.OK);
    }

    // http://localhost:8080/api/user/sign-out
    @PostMapping("/sign-out")
    public ResponseEntity<String> signOut(){

        SecurityContextHolder.clearContext();

        return new ResponseEntity<>("Sign out Successful" , HttpStatus.OK);
    }



    // http://localhost:8080/api/user/assign-admin/1
    @PostMapping("/assign-admin/{id}")
    public ResponseEntity<String> assignUserToAdminByAdmin(@PathVariable long id){
        UserEntity user = userRepo.findById(id).orElseThrow(() ->
                new RecordNotFoundException("no user is present with this id: " + id));

        Role role = new Role();
        Role admin = roleRepo.findByRoleName("ADMIN");

        user.getRoles().add(admin);
        userRepo.save(user);
        return new ResponseEntity<>("user with this id: "+id+" is Now a Admin" , HttpStatus.OK);
    }








    // http://localhost:8080/api/user/delete/2
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable long id){
//        UserEntity user = userRepo.findById(id).orElseThrow(() ->
//                new RecordNotFoundException("No User Found"));
//
//        userRepo.deleteById(id);
//        return  new ResponseEntity<>("user data is Deleted" , HttpStatus.OK);
//    }
//
//
//    // http://localhost:8080/api/user/delete-role/1
//    @DeleteMapping("/delete-role/{id}")
//    public ResponseEntity<String> deleteRole(@PathVariable long id){
//        Role role = roleRepo.findById(id).orElseThrow(() ->
//                new RecordNotFoundException("No User Found"));
//
//        roleRepo.deleteById(id);
//        return  new ResponseEntity<>("Role data is Deleted" , HttpStatus.OK);
//    }
}
