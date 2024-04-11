package com.itr.codechallenge.controller;

import com.itr.codechallenge.entities.User;
import com.itr.codechallenge.repository.UserRepository;
import com.itr.codechallenge.utils.Utils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            if (!Utils.emailValidation(user.getEmail())) {
                return new ResponseEntity<>("El correo no es válido", HttpStatus.BAD_REQUEST);
            }

            if (Utils.passwordValidation(user.getPassword())) {
                return new ResponseEntity<>("El password no cumple la validación", HttpStatus.BAD_REQUEST);
            }
            user.setCreated(new Date());
            user.setIs_active(Boolean.TRUE);
            user.setToken(UUID.randomUUID());
            User savedUser = userRepository.save(user);
            return new ResponseEntity<>(savedUser.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            Throwable t = e.getCause();
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
            if (t instanceof ConstraintViolationException) {
                return new ResponseEntity<>("El correo ya esta registrado", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
