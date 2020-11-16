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
import com.example.demo.dao.UsersRepository;

@Controller
@RequestMapping("/users")
public class UsersController {
    
    @Autowired
    private UsersRepository userRepository;
    
    @GetMapping("login/{username}/{password}")
    public UserJson login(@PathVariable String username, @PathVariable("password") String password) {
        UserJson json;
        User user = userRepository.findByUsername(username);
        if (null != user && user.getPassword() == password) { //TODO dodac warunek logowania
            json = new UserJson(user.getId(), user.getUsername(), user.getEmail(),
                    user.getPassword(), user.getPhoneNumber());
        } else {
            json = null;
        }
        return json;
    }
    
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public UserJson add(@RequestBody User user) {
        UserJson json = null;
        User foundUser = userRepository.findByUsername(user.getUsername());
            if (foundUser == null) {
                    //String password = PasswordGenerator.generatePassword(20);
                    //User user = new User(theInstructor.getLogin(), encoder.encode(password), "INSTRUCTOR", "");
                    System.out.println("dodaj użytkownika");
                    json = new UserJson(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhoneNumber());
                    userRepository.save(user);
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
            return json;
    }
    /*
    @GetMapping("add/{username}/{password}")
    public UserJson addUser(@PathVariable String username, @PathVariable("password") String password) {
        UserJson json;
        User user = userRepository.findByUsername(username);
        if (null == user) {
            json = new UserJson();
        } else {
            json = new UserJson();
        }
        return json;
    }*/
}
