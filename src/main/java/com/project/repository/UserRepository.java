package com.project.repository;

import com.project.entity.concretes.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
    boolean existsByPlate(String plate);
    boolean existsByPhoneNumber(String phoneNumber);

    Users findByEmailEquals(String email);

    Users getUserByPlate(String plate);

    Users getUserByPhoneNumber(String phone);

    Users getUserByEmail(String email);

    boolean existsByName(String name);

    List<Users> findByName(String name);

    boolean existsBySurname(String surname);

    List<Users> findBySurname(String surname);

    Users getUserById(Long id);
}
