package com.example.messenger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class GreetingController {
    @GetMapping("hello/{name}")
    public String greeting(@PathVariable("name") String name,
                           Map<String, Object> model){
         model.put("name", name);
         return "greeting";
    }
    @GetMapping
    public String main(Map<String, Object> map){
        map.put("some", "Hello Victor lets code");
        return "main";
    }

}
