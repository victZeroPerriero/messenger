package com.example.messenger.controller;

import com.example.messenger.model.Role;
import com.example.messenger.model.User;
import com.example.messenger.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Map<String, Object> map){
        map.put("users", userRepository.findAll());
        return "userList";
    }
    @GetMapping("{user}")
    public String addEditForm(@PathVariable User user, Map<String, Object> model){
        model.put("user", user);
        model.put("roles", Role.values());
        return "userEdit";
    }
    @PostMapping
    public String userSave(@RequestParam String username,
            @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user){
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key: form.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:/user";
    }
}
