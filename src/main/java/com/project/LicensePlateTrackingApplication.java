package com.project;

import com.project.entity.enums.RoleType;
import com.project.payload.request.AdminRequest;
import com.project.service.AdminService;
import com.project.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class LicensePlateTrackingApplication implements CommandLineRunner {

	private final AdminService adminService;
	private final UserRoleService userRoleService;
	private final PasswordEncoder passwordEncoder;

	public LicensePlateTrackingApplication(AdminService adminService, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
		this.adminService = adminService;
		this.userRoleService = userRoleService;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(LicensePlateTrackingApplication.class, args);
	}


	@Override
	public void run(String... args){

		if (userRoleService.getAllUSerRole().isEmpty()){
			userRoleService.save(RoleType.ADMIN);
			userRoleService.save(RoleType.USER);
		}
		if(adminService.countAllAdmins()==0){
			AdminRequest adminRequest  = new AdminRequest();
			adminRequest.setEmail("fnursari96@gmail.com");
			adminRequest.setPlate("42ABT411");
			adminRequest.setPassword(passwordEncoder.encode("Abcd1234!"));
			adminRequest.setName("Fatma");
			adminRequest.setSurname("Sarı");
			adminRequest.setPhoneNumber("+905375199653");
			adminService.save(adminRequest);
		}

	}
}
