package com.itr.codechallenge.controller;

import com.itr.codechallenge.config.UserControllerApi;
import com.itr.codechallenge.entities.ERole;
import com.itr.codechallenge.entities.Role;
import com.itr.codechallenge.entities.User;
import com.itr.codechallenge.repository.RoleRepository;
import com.itr.codechallenge.repository.UserRepository;
import com.itr.codechallenge.utils.Utils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserController implements UserControllerApi {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/add-user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            if (!Utils.emailValidation(user.getEmail())) {
                return new ResponseEntity<>("El correo no es válido", HttpStatus.BAD_REQUEST);
            }

            if (Utils.passwordValidation(user.getPassword())) {
                return new ResponseEntity<>("El password no cumple la validación", HttpStatus.BAD_REQUEST);
            }
            user.setUsername(user.getUsername());
            user.setCreated(new Date());
            user.setIs_active(Boolean.TRUE);
            user.setPassword(encoder.encode(user.getPassword()));

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

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> getUser(@PathVariable("id") Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            return user.map(value -> new ResponseEntity<>(value.toString(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>("No se encontró el usuario", HttpStatus.OK));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-user/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User userToUpdate) {
        try {
            if (!Utils.emailValidation(userToUpdate.getEmail())) {
                return new ResponseEntity<>("El correo no es válido", HttpStatus.BAD_REQUEST);
            }

            if (Utils.passwordValidation(userToUpdate.getPassword())) {
                return new ResponseEntity<>("El password no cumple la validación", HttpStatus.BAD_REQUEST);
            }
            Optional<User> user = userRepository.findById(id);
            user.map(userFound -> {
                        userFound.setModified(new Date());
                        userFound.setIs_active(Boolean.TRUE);
                        userFound.setToken(UUID.randomUUID());
                        User userUpdated = userRepository.save(userFound);
                        return new ResponseEntity<>(userUpdated.toString(), HttpStatus.OK);
                    })
                    .orElseGet(() -> new ResponseEntity<>("No se encontró el usuario", HttpStatus.OK));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @DeleteMapping("/delete-user/{user_id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") Long user_id) {
        try {
            Optional<User> user = userRepository.findById(user_id);
            return user.map(value ->{
                        userRepository.delete(value);
                        return new ResponseEntity<>("usuario eleminado", HttpStatus.OK);
                    })
                    .orElseGet(() -> new ResponseEntity<>("No se encontró el usuario", HttpStatus.OK));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users.toString(), HttpStatus.OK);
    }

}
