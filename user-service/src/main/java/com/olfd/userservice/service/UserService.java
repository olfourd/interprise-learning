package com.olfd.userservice.service;

import com.olfd.userservice.model.entity.Role;
import com.olfd.userservice.model.entity.User;
import java.util.List;

public interface UserService {

    User createUser(String email, String password, String roleName);

    List<User> getUsers();

    List<Role> getAvailableRoles();
}
