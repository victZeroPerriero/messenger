package com.example.messenger.controller;

import com.example.messenger.model.Message;
import com.example.messenger.model.User;
import com.example.messenger.repo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam("file") MultipartFile file) throws IOException {
      message.setAuthor(user);
      if (bindingResult.hasErrors()){
          Map<String, String> errorMap = ControllerUtil.getErrors(bindingResult);
          model.addAttribute( errorMap);
          model.addAttribute("message", message);
      } else {

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

      }

        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }

}
