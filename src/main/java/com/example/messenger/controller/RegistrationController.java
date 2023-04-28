package com.example.messenger.controller;

import com.example.messenger.model.Role;
import com.example.messenger.model.User;
import com.example.messenger.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
   private final UserRepository userRepository;
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser( User user,
                          Map<String,Object> model) {
       User userFromDb = userRepository.findUserByUsername(user.getUsername());
       if (userFromDb != null){
           model.put("message", "User exists!");
           return "registration";
       }
       user.setActive(true);
       user.setUsername(user.getUsername());
       user.setRoles(Collections.singleton(Role.USER));
       userRepository.save(user);
        return "redirect:/login";
    }
}
