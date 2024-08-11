package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.util.UsernameOrEmailExistException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    @Transactional
    public void save(User user, List<Long> roles) {
        User savedUser = userRepository.save(user);
        savedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<Role> savedRoles = roleService.findAllById(roles);
        savedUser.setRoles(new HashSet<>(savedRoles));
        userRepository.save(savedUser);
    }

    @Override
    @Transactional
    public void save(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null || userRepository.findByEmail(user.getEmail()) != null) {
                throw new UsernameOrEmailExistException();
        }
        if (user.getRoles() == null) {
            List<Long> roleIds = new ArrayList<>();
            roleIds.add(2L);
            user.setRoles(new HashSet<>(roleService.findAllById(roleIds)));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(User user, Long id, List<Long> roles) {
        User savedUser = userRepository.getById(id);
        savedUser.setUsername(user.getUsername());
        savedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        savedUser.setEmail(user.getEmail());
        savedUser.setYearOfBirth(user.getYearOfBirth());
        List<Role> savedRoles = roleService.findAllById(roles);
        savedUser.setRoles(new HashSet<>(savedRoles));
        userRepository.save(savedUser);
    }

    @Override
    @Transactional
    public void update(User user) {
        if (userRepository.findById(user.getId()).isEmpty()){
            throw new UsernameNotFoundException("User whit ID " + user.getId() + " not found");
        }
        User savedUser = userRepository.getById(user.getId());

        savedUser.setUsername(user.getUsername());
        savedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        savedUser.setEmail(user.getEmail());
        savedUser.setYearOfBirth(user.getYearOfBirth());
        savedUser.setRoles(user.getRoles());

        userRepository.save(savedUser);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'", username));
            }
        }
        return user;
    }
}
