package com.example.messenger.controller;

import com.example.messenger.model.Message;
import com.example.messenger.model.User;
import com.example.messenger.repo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MessageRepository messageRepository;
    @Value("${upload.path}")
    private String uploadPath;
    @GetMapping("/")
    public String greeting(Map<String, Object> model){
         return "greeting";
    }
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
            Map<String, Object> map){
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
                      Map<String, Object> map,
                      @RequestParam("file") MultipartFile file) throws IOException {
        Message message = new Message(text,tag,user);
        if(file != null && !file.getOriginalFilename().isEmpty()){
          File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
              uploadDir.mkdir();
            }

             String uuidFile = UUID.randomUUID().toString();
             String resultFileName = uuidFile + " " + file.getOriginalFilename();
             file.transferTo(new File(uploadPath +"/" + resultFileName));
             message.setFilename(resultFileName);
        }
        messageRepository.save(message);
        map.put("messages", messageRepository.findAll());
        return "main";
    }

}
