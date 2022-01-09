package com.olfd.userservice.controller;

import com.olfd.userservice.dto.CreateUserDto;
import com.olfd.userservice.model.entity.Role;
import com.olfd.userservice.model.entity.User;
import com.olfd.userservice.service.impl.UserServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//todo: write error handling without trace
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/user")
    public User createUser(@RequestBody @Validated CreateUserDto request) {
       return userService.createUser(request.getEmail(), request.getPassword(), request.getRoleName());
    }

    @GetMapping("/roles")
    public List<Role> getAvailableRoles() {
        return userService.getAvailableRoles();
    }
}
