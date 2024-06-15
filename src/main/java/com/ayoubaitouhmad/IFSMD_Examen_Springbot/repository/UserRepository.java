package com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
