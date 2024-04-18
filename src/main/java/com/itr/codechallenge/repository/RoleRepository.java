package com.itr.codechallenge.repository;

import com.itr.codechallenge.entities.ERole;
import com.itr.codechallenge.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
