package com.project.repository;

import com.project.entity.concretes.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
    boolean existsByPlate(String plate);
    boolean existsByPhoneNumber(String phoneNumber);

    Users findByEmailEquals(String email);
}
