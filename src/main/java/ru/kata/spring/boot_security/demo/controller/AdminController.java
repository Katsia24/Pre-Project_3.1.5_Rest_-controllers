package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.UserNotSavedException;
import ru.kata.spring.boot_security.demo.util.UsernameOrEmailExistException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//    @GetMapping()
//    public String getAdminPanel(Model model, Principal principal) {
//        model.addAttribute("allUsers", userService.listUsers());
//        model.addAttribute("authUser", (User) userService.loadUserByUsername(principal.getName()));
//        model.addAttribute("newUser", new User());
//        model.addAttribute("roles", roleService.findAll());
//        model.addAttribute("activeTable", "usersTable");
//        return "admin";
//    }

    // !!!WORK!!!
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> findAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    // !!!WORK!!!
    @GetMapping("/authUser")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
            return ResponseEntity.ok(user);
    }

    // !!!WORK!!!
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
            return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    // !!!WORK!!!
    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> addUser(@Valid @RequestBody User newUser,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotSavedException(fieldErrors.toString());
        }
            userService.save(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // !!!WORK!!! admin/deleteUser?id=
    @PostMapping("/deleteUser")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody User user,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotSavedException(fieldErrors.toString());
        }
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserNotSavedException> handleException(UserNotSavedException ex) {
        return new ResponseEntity<>(new UserNotSavedException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UsernameOrEmailExistException ex) {
        UserErrorResponse response = new UserErrorResponse("That Username or Email already exists",
                                                            System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UsernameNotFoundException ex) {
        UserErrorResponse response = new UserErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @PostMapping("/update")
//    public String updateUser(@ModelAttribute("updateUser") @Valid User updateUser,
////                             @RequestParam("id") Long id,
//                             @RequestParam("roles") List<Long> roles) {
//        userService.update(updateUser, updateUser.getId(), roles);
//        return "redirect:/admin";
//    }

//    @PostMapping("/delete")
//    public String deleteUser1(@RequestParam("id") Long id) {
//        userService.delete(id);
//        return "redirect:/admin";
//    }

//    @PostMapping()
//    public String addUser(@ModelAttribute("newUser") @Valid User newUser,
//                          @RequestParam("roles") List<Long> roles) {
//        userService.save(newUser, roles);
//        return "redirect:/admin";
//    }

}
