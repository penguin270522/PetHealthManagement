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

    @Query("select  count (i) from User i")
    Long getTotalUser();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    @Query("select u from User u where u.fullName like %?1% or u.username like %?1%")
    List<User> findByFullName(String name);


}
