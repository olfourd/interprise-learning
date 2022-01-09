package com.olfd.userservice.model.repository;

import com.olfd.userservice.model.entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String login);

    List<Role> findAllByNameIn(List<String> names);
}
