package com.example.pethealth.repositories.auth;

import com.example.pethealth.model.Role;
import com.example.pethealth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count(u) > 0 from User u where u.username = :username")
    boolean findByUsernameBoolean(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);



}
