package com.example.demo.controllers;

import com.example.demo.entity.User;
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
import java.util.Optional;
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
    public User login(@PathVariable String username, @PathVariable("password") String password) {
        User user = userRepository.findByUsername(username);
        if (null != user && encoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
    
    @PostMapping(value="save")
    public User save(@RequestBody User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        
        if (foundUser == null) {
            String pass = encoder.encode(user.getPassword());
            if (!user.getPhoneNumber().startsWith("+48"))
            {
                user.setPhoneNumber("+48" + user.getPhoneNumber());
            }
            user.setPassword(pass);
            userRepository.save(user);
        } else {
            user = null;
        }
        return user;
    } 
    
    @RequestMapping(value = "updateUserData", method = RequestMethod.POST)
    public int updateUserData(@RequestBody User user) {
        User userFromDb = null;
        Optional<User> u = userRepository.findById(user.getId());
        
        if (u != null)
        {
            userFromDb  = u.get();
        }
        if (!userFromDb.getUsername().equals(user.getUsername()))
        {
            if (userRepository.findByUsername(user.getUsername()) != null)
                return -1;
        }
        if (!userFromDb.getEmail().equals(user.getEmail()))
        {
            if (userRepository.findByEmail(user.getEmail()) != null)
                return -2;
        }
        userRepository.save(user);
        return user.getId();
    }
    
    @GetMapping(value = "get/{username}")
    public User get(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
    
    @RequestMapping(value="changePassword/{username}/{oldPassword}/{password}", method=RequestMethod.POST)
    public User changePassword(@PathVariable String username, @PathVariable("oldPassword") String oldPassword, @PathVariable("password") String password) {
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(encoder.encode(oldPassword))) {
            user.setPassword(encoder.encode(password));
            userRepository.save(user);
        }
        return user;
    }
}