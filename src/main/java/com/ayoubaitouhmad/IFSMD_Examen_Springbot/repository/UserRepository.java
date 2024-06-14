package com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here
}
