package com.project.repository;

import com.project.entity.concretes.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    boolean existsByEmail(String email);
    boolean existsByPlate(String plate);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByName(String name);
    boolean existsBySurname(String surname);

    Admin getAdminsByPlate(String plate);
    List<Admin> getAdminsByName(String name);
    List<Admin> getAdminsBySurname(String surname);
    Admin getAdminsByPhoneNumber(String phoneNumber);
    Admin getAdminsByEmail(String email);

    List<Admin> findByNameContaining(String name);
}
