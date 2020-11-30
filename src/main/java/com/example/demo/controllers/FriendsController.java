package com.example.demo.controllers;

import com.example.demo.dao.FriendsRepository;
import com.example.demo.dao.UsersRepository;
import com.example.demo.entity.Friendship;
import com.example.demo.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("friends")
public class FriendsController {
    
    @Autowired
    private FriendsRepository friendRepository;
    
    @Autowired
    private UsersRepository userRepository;
    
    UsersController usersController = new UsersController();
    
    public FriendsController() {
    }
    
    @GetMapping(value = "getUserFriends/{idUser}")
    public ArrayList<User> getUserFriends(@PathVariable Integer idUser) {
        ArrayList<User> friends = new ArrayList<>();
        ArrayList<Friendship> friendships = (ArrayList<Friendship>) friendRepository.findByIdUser(idUser);
        for (Friendship friendship : friendships) {
            userRepository.findById(friendship.getIdFriend());
            friends.add(userRepository.findById(friendship.getIdFriend()).get());
        }
        return friends;
    }
    
    
}
