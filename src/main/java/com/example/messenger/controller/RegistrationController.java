package com.example.messenger.controller;

import com.example.messenger.model.User;
import com.example.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
   private final UserService userService;
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser( User user,
                          Map<String,Object> model) {

       if ( !userService.addUser(user)){
           model.put("message", "User exists!");
           return "registration";
       }
        return "redirect:/login";
    }
    @GetMapping("/activate/{code}")
    public String activate(Map<String, Object> model, @PathVariable String code) {
        boolean isActivated = userService.activateCode(code);
        if (isActivated){
            model.put("message", "User successfully activated");
        } else {
            model.put("message", "Activate code is not foimd");
        }
        return "login";
    }
}
