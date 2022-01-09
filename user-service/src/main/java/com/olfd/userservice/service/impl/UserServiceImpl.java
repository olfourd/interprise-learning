package com.olfd.userservice.service.impl;

import com.olfd.userservice.config.ApplicationProperties;
import com.olfd.userservice.exeptions.NotFoundException;
import com.olfd.userservice.model.entity.User;
import com.olfd.userservice.model.entity.Role;
import com.olfd.userservice.model.repository.UserRepository;
import com.olfd.userservice.model.repository.RoleRepository;
import com.olfd.userservice.service.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApplicationProperties applicationProperties;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    //todo: paging
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User createUser(String email, String password, String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException(String.format("Not found role by arg: %s", roleName)));

        return userRepository.save(
                User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .roles(Set.of(role))
                        .build()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getAvailableRoles() {
        return roleRepository.findAllByNameIn(applicationProperties.getAvailableRoles());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Not found user by email: %s", email)));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
