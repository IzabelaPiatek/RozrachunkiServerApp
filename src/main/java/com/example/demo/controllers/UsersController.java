package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.json.UserJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dao.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UsersController {
    
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @Autowired
    private UsersRepository userRepository;
    
    public UsersController() {
    }
    
    //public UsersController(PasswordEncoder encoder) {
    //    this.encoder = encoder;
    //}
    
    @GetMapping("login/{username}/{password}")
    public UserJson login(@PathVariable String username, @PathVariable("password") String password) {
        UserJson json;
        User user = userRepository.findByUsername(username);
        //user.setPassword(encoder.encode("emilka"));
        if (null != user && encoder.matches(password, user.getPassword())) { //TODO dodac warunek logowania
            json = new UserJson(user.getId(), user.getUsername(), user.getEmail(),
                    user.getPassword(), user.getPhoneNumber());
        } else {
            json = null;
        }
        return json;
    }
    
    @PostMapping(value="save")
    public User save(@RequestBody User user) {
        UserJson json = null;
        User foundUser = userRepository.findByUsername(user.getUsername());
        System.out.println(foundUser);
        if (foundUser == null) {
                String pass = encoder.encode(user.getPassword());
                user.setPassword(pass);
                userRepository.save(user);
                json = new UserJson(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhoneNumber());
                //UserPrincipal principal = new UserPrincipal(user);
                //user.setUserId(principal.getId());
        } else {
                //User user = userRepository.findById(theInstructor.getUserId());
                //user.setUsername(theInstructor.getLogin());
        }

        //theInstructor.setDeleted(0);
        //instructorRepository.save(theInstructor);
        //theModel.addAttribute("saved", true);
        //return user == null? null : user.getId();
        System.out.println(json.getId());
        return user;
    } 
    
    @RequestMapping(value = "updateUserData", method = RequestMethod.POST)
    public int updateUserData(@RequestBody User user) {
        //User foundUser = userRepository.findById(user.getId());
        //user.setId(foundUser.getId());
        //user.setPassword(foundUser.getPassword());
        userRepository.save(user);
        return user.getId();
    }
    
    @GetMapping(value = "get/{username}")
    public UserJson get(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        UserJson json = new UserJson(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhoneNumber());
        return json;
    }
    
    @RequestMapping(value="changePassword/{username}/{oldPassword}/{password}", method=RequestMethod.POST)
    public UserJson changePassword(@PathVariable String username, @PathVariable("oldPassword") String oldPassword, @PathVariable("password") String password) {
        User user = userRepository.findByUsername(username);
        UserJson json = null;
        if (user.getPassword().equals(encoder.encode(oldPassword))) {
            user.setPassword(encoder.encode(password));
            userRepository.save(user);
            json = new UserJson(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhoneNumber());
        }
        return json;
    }
}