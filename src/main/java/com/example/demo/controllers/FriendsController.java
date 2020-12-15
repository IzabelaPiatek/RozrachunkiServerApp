package com.example.demo.controllers;

import com.example.demo.dao.BreakdownsRepository;
import com.example.demo.dao.FriendsRepository;
import com.example.demo.dao.PaymentsRepository;
import com.example.demo.dao.UsersRepository;
import com.example.demo.entity.Breakdown;
import com.example.demo.entity.Friendship;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import com.example.demo.json.FriendJson;
import java.util.ArrayList;
import java.util.stream.Collectors;
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
    private BreakdownsRepository breakdownsRepository;
    
    @Autowired
    private PaymentsRepository paymentsRepository;
    
    UsersController usersController = new UsersController();
    
    public FriendsController() {
    }
    
    @GetMapping(value = "getUserFriends/{idUser}")
    public ArrayList<FriendJson> getUserFriends(@PathVariable Integer idUser) {
        
        ArrayList<Friendship> friendships = (ArrayList<Friendship>) friendRepository.findByIdUser(idUser);
        ArrayList<FriendJson> friends = new ArrayList<>();
        
        ArrayList<Payment> payments = paymentsRepository.findByPaidByAndSettled(idUser, false);
        
        for (Friendship friendship : friendships) {
            Integer owesYouSum = 0;
            for (Payment payment: payments) {
                ArrayList<Breakdown> b = breakdownsRepository.findByIdBorrowerAndIdPayment(friendship.getIdFriend(), payment.getId());
                for (Breakdown breakdown : b) {
                    if (breakdown.isSettled() == false) {
                        owesYouSum += breakdown.getAmount();
                    }
                }
            }

            payments = paymentsRepository.findByPaidByAndSettled(friendship.getIdFriend(), false);
            Integer youOweSum = 0;
            for (Payment payment: payments) {
                ArrayList<Breakdown> b = breakdownsRepository.findByIdBorrowerAndIdPayment(idUser, payment.getId());
                for (Breakdown breakdown : b) {
                    if (breakdown.isSettled() == false) {
                        youOweSum += breakdown.getAmount();
                    }
                }
            }
            
            friends.add(new FriendJson(friendship.getId(), friendship.getUsername(), owesYouSum, youOweSum, null));
            //userRepository.findById(friendship.getIdFriend());
            //friends.add(userRepository.findById(friendship.getIdFriend()).get());
        }
        return friends;
    }
    
    @GetMapping(value = "getUserFriendsWhoOweHim/{idUser}")
    public ArrayList<FriendJson> getUserFriendsWhoOweHim(@PathVariable Integer idUser) {
        
        ArrayList<FriendJson> friends = new ArrayList<>();
        
        friends = getUserFriends(idUser).stream().filter(f -> f.getOwesYou() != 0).collect(Collectors.toCollection(() -> new ArrayList<FriendJson>()));

        return friends;
    }
    
    @GetMapping(value = "getUserFriendsHeOwes/{idUser}")
    public ArrayList<FriendJson> getUserFriendsHeOwes(@PathVariable Integer idUser) {
        
        ArrayList<FriendJson> friends = new ArrayList<>();
        
        friends = getUserFriends(idUser).stream().filter(f -> f.getYouOwe()!= 0).collect(Collectors.toCollection(() -> new ArrayList<FriendJson>()));

        return friends;
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
