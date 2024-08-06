package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAdminPanel(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.listUsers());
        model.addAttribute("authUser", (User) userService.loadUserByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("activeTable", "usersTable");
        return "admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("updateUser") @Valid User updateUser,
//                             @RequestParam("id") Long id,
                             @RequestParam("roles") List<Long> roles) {
        userService.update(updateUser, updateUser.getId(), roles);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("newUser") @Valid User newUser,
                          @RequestParam("roles") List<Long> roles) {
        userService.save(newUser, roles);
        return "redirect:/admin";
    }

}
