package com.example.messenger.service;

import com.example.messenger.model.Role;
import com.example.messenger.model.User;
import com.example.messenger.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MailSender mailSender;
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    public boolean addUser(User user){
        User userFromDb = userRepository.findUserByUsername(user.getUsername());
        if (userFromDb != null){
            return false;
        }
        user.setActive(true);
        user.setUsername(user.getUsername());
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
//        sendMessage(user);
        return true;
    }

    public boolean activateCode(String code) {
     User user  = userRepository.findByActivationCode(code);
     if(user == null) {
         return false;
     }
     user.setActivationCode(null);
     userRepository.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key: form.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public void updateProfile(User user, String password, String email) {
          String userEmail = user.getMail();

          boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                  (userEmail != null && !userEmail.equals(email));
          if (isEmailChanged){
              user.setMail(email);
              if (!StringUtils.isEmpty(email)){
                  user.setActivationCode(UUID.randomUUID().toString());
              }
          }
          if (!StringUtils.isEmpty(password)){
              user.setPassword(password);
          }
          userRepository.save(user);
          if (isEmailChanged){
              sendMessage(user);
          }
    }

    private void sendMessage(User user) {
        if(!StringUtils.isEmpty(user.getMail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Messenger. Please, visit next link http://localhost:8088/activate/%s", user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getMail(), "Activation code", message);
        }
    }
}
