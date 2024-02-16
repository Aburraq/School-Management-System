package com.burak.backendproject.initialwork;
import com.burak.backendproject.initialwork.entity.concretes.user.UserRole;
import com.burak.backendproject.initialwork.entity.enums.Gender;
import com.burak.backendproject.initialwork.entity.enums.RoleType;
import com.burak.backendproject.initialwork.payload.request.user.UserRequest;
import com.burak.backendproject.initialwork.repository.user.UserRoleRepository;
import com.burak.backendproject.initialwork.service.user.UserRoleService;
import com.burak.backendproject.initialwork.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SchoolManagementApplication implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserRoleRepository userRoleRepository;
    private final UserService userService;

    public SchoolManagementApplication(UserRoleService userRoleService, UserRoleRepository userRoleRepository, UserService userService) {
        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        if (userRoleService.getAllUserRole().isEmpty()){

            UserRole admin = new UserRole();
            admin.setRoleType(RoleType.ADMIN);
            admin.setRoleName("Admin");
            userRoleRepository.save(admin);

            UserRole dean = new UserRole();
            dean.setRoleType(RoleType.MANAGER);
            dean.setRoleName("Dean");
            userRoleRepository.save(dean);

            UserRole viceDean = new UserRole();
            viceDean.setRoleType(RoleType.ASSISTANT_MANAGER);
            viceDean.setRoleName("ViceDean");
            userRoleRepository.save(viceDean);

            UserRole student = new UserRole();
            student.setRoleType(RoleType.STUDENT);
            student.setRoleName("Student");
            userRoleRepository.save(student);

            UserRole teacher = new UserRole();
            teacher.setRoleType(RoleType.TEACHER);
            teacher.setRoleName("Teacher");
            userRoleRepository.save(teacher);
        }

        if (userService.getAllUsers().isEmpty()){

            UserRequest userRequest = getUserRequest();
            userService.saveUser(userRequest, "Admin");


        }
    }

    private static UserRequest getUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("UserAdmin");
        userRequest.setEmail("admin@gmail.com");
        userRequest.setSsn("111-11-1111");
        userRequest.setPassword("Ankara06");
        userRequest.setName("adminName");
        userRequest.setSurname("adminSurname");
        userRequest.setPhoneNumber("111-111-1111");
        userRequest.setGender(Gender.FEMALE);
        userRequest.setBirthDay(LocalDate.of(1980, 2, 2));
        userRequest.setBirthPlace("Istanbul");
        return userRequest;
    }
}









