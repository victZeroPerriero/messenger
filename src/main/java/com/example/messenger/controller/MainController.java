package com.example.messenger.controller;

import com.example.messenger.model.Message;
import com.example.messenger.model.User;
import com.example.messenger.repo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MessageRepository messageRepository;
    @GetMapping("/")
    public String greeting(Map<String, Object> model){
         return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter,  Map<String, Object> map){
        List<Message> messages;
        if(filter!=null && !filter.isEmpty()){
            messages = messageRepository.findByTag(filter);
        } else{
            messages = messageRepository.findAll();
        }
        map.put("messages",messages);
        map.put("filter", filter);

        return "main";
    }
    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> map){
        Message message = new Message(text,tag,user);
        messageRepository.save(message);
        map.put("messages", messageRepository.findAll());
        return "main";
    }

}
