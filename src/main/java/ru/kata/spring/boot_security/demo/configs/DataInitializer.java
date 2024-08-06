package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner init(UserService userService, RoleService roleService) {
        return args -> {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleService.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleService.save(userRole);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@mail.ru");
            admin.setYearOfBirth(40);
            userService.save(admin, List.of(adminRole.getId(), userRole.getId()));

            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setEmail("user@mail.ru");
            user.setYearOfBirth(20);
            userService.save(user, List.of(userRole.getId()));
        };
    }
}