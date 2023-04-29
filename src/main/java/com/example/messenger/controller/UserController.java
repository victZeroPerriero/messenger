package com.example.messenger.controller;

import com.example.messenger.model.Role;
import com.example.messenger.model.User;
import com.example.messenger.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Map<String, Object> map){
        map.put("users", userService.findAll());
        return "userList";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String addEditForm(@PathVariable User user, Map<String, Object> model){
        model.put("user", user);
        model.put("roles", Role.values());
        return "userEdit";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam String username,
            @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user){
        userService.saveUser(user,username,form);
        return "redirect:/user";
    }
    @GetMapping("profile")
    public String getProfile(Map<String,Object> model,
                             @AuthenticationPrincipal User user){
        model.put("username", user.getUsername());
        model.put("email", user.getMail());
        return "profile";

    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ){
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }
}
