package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

//    PasswordEncoder getPasswordEncoder();

    void save(User user, List<Long> roles);

    void delete(long id);

    void update(User user, Long id, List<Long> roles);

    User getById(Long id);

    List<User> listUsers();
}
