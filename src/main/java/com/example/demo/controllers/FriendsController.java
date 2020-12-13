package com.example.demo.controllers;

import com.example.demo.dao.BreakdownsRepository;
import com.example.demo.dao.FriendsRepository;
import com.example.demo.dao.PaymentsRepository;
import com.example.demo.dao.UsersRepository;
import com.example.demo.entity.Breakdown;
import com.example.demo.entity.Friendship;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("friends")
public class FriendsController {
    
    @Autowired
    private FriendsRepository friendRepository;
    
    @Autowired
    private UsersRepository userRepository;
    
    @Autowired
    private PaymentsRepository paymentsRepository;
    
    @Autowired
    private BreakdownsRepository breakdownsRepository;
    
    UsersController usersController = new UsersController();
    
    public FriendsController() {
    }
    
    @GetMapping(value = "getUserFriends/{idUser}")
    public ArrayList<Friendship> getUserFriends(@PathVariable Integer idUser) {
        
        ArrayList<Friendship> friendships = (ArrayList<Friendship>) friendRepository.findByIdUser(idUser);
        /*for (Friendship friendship : friendships) {
            userRepository.findById(friendship.getIdFriend());
            friends.add(userRepository.findById(friendship.getIdFriend()).get());
        }*/
        return friendships;
    }
    
    @GetMapping(value = "getUserFriendsWhoOweHim/{idUser}")
    public ArrayList<Friendship> getUserFriendsWhoOweHim(@PathVariable Integer idUser) {
        ArrayList<Friendship> filtered = new ArrayList<Friendship>();
        ArrayList<Friendship> friendships = (ArrayList<Friendship>) friendRepository.findByIdUser(idUser);
        for (Friendship friendship : friendships) {
            
            if (friendship.isHasAccount())
            {
                ArrayList<Payment> payments = paymentsRepository.findByPaidByAndSettled(idUser, false);
                
                for (Payment payment : payments) {
                    ArrayList<Breakdown> breakdowns = breakdownsRepository.findByIdPaymentAndSettled(payment.getId(), false);
                }
                
                filtered.add(friendship);
            }
            //niedokończone ?
            
            
            //userRepository.findById(friendship.getIdFriend());
            //friends.add(userRepository.findById(friendship.getIdFriend()).get());
        }
        return filtered;
    }
    
    @GetMapping(value = "getUserFriendsHeOwes/{idUser}")
    public ArrayList<Friendship> getUserFriendsHeOwes(@PathVariable Integer idUser) {
        
        ArrayList<Friendship> friendships = (ArrayList<Friendship>) friendRepository.findByIdUser(idUser);
        for (Friendship friendship : friendships) {
            //userRepository.findById(friendship.getIdFriend());
            //friends.add(userRepository.findById(friendship.getIdFriend()).get());
        }
        return friendships;
    }
    
    @Transactional
    @PostMapping(value = "delete/{friendshipId}")
    public int delete(@PathVariable Integer friendshipId) {
        
        friendRepository.deleteById(friendshipId);
        return 1;
        
        //if (idFriend != null)
        //{
            //friendRepository.deleteByIdUserAndIdFriend(idUser, idFriend);
            //return 1;
        //}
        
        //if (phoneNumber != null)
        //{
            //friendRepository.deleteByIdUserAndPhoneNumber(idUser, phoneNumber);
            //return 1;
        //}
        
        //if (email != null)
        //{
            //friendRepository.deleteByIdUserAndEmail(idUser, email);
            //return 1;
        //}
        
        //return 0;
    }
    
    @PostMapping(value="add")
    public Friendship add(@RequestBody Friendship friendship) {
        
        if (friendship.getPhoneNumber() != null)
        {
            if (!friendship.getPhoneNumber().startsWith("+48"))
            {
                friendship.setPhoneNumber("+48" + friendship.getPhoneNumber());
            }
        }
        
        User user = userRepository.findByPhoneNumber(friendship.getPhoneNumber());
        
        if (user != null)
        {
            friendship.setIdFriend(user.getId());
            friendship.setHasAccount(true);
            friendship.setUsername(user.getUsername());
        }
        else
        {
            user = userRepository.findByEmail(friendship.getEmail());
            
            if (user != null)
            {
                friendship.setIdFriend(user.getId());
                friendship.setHasAccount(true);
                friendship.setUsername(user.getUsername());
            }
        }
        
        if (friendship.getIdFriend() != null)
        {
            Friendship found = friendRepository.findByIdUserAndIdFriend(friendship.getIdUser(), friendship.getIdFriend());
            
            if (found != null) {
                return null;
            }
        }
        
        if (friendship.getPhoneNumber()!= null)
        {
            Friendship found = friendRepository.findByIdUserAndPhoneNumber(friendship.getIdUser(), friendship.getPhoneNumber());

            if (found != null) {
                return null;
            }
        }
        
        if (friendship.getEmail()!= null)
        {
            Friendship found = friendRepository.findByIdUserAndEmail(friendship.getIdUser(), friendship.getEmail());

            if (found != null) {
                return null;
            }
        }

        friendRepository.save(friendship);
        
        return friendship;
    }
}
