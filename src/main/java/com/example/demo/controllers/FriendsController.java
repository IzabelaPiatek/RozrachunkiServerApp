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
import java.util.Optional;
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
        ArrayList<FriendJson> friends = new ArrayList<>();
        
        ArrayList<Payment> payments = paymentsRepository.findByPaidByAndSettled(idUser, false);
        
        ArrayList<Friendship> friendships = (ArrayList<Friendship>) friendRepository.findByIdUser(idUser);
        //pobieraj nie tylko z firendships, ale też wszystkich, z którymi ma jakieś payments lub breakdowns
        
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
            
            Optional<User> user = userRepository.findById(friendship.getIdFriend());
            
            if (user.get().getUsername() == null)
            {
                user.get().setUsername(user.get().getEmail());
            }
            
            friends.add(new FriendJson(friendship.getId(), user.get().getUsername(), owesYouSum, youOweSum, null));

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
    
    @PostMapping(value="add/{userId}")
    public Friendship add(@PathVariable Integer userId, @RequestBody User user) {
        
        User u;
        Friendship friendship = new Friendship(null, userId, null);
        
        if (user.getUsername() != null && user.isHasAccount() == true)
        {         
            u = userRepository.findByUsernameAndHasAccount(user.getUsername(), true);
            
            if (u != null) {
                user.setId(u.getId());
            }
        } else if (user.getPhoneNumber() != null)
        {
            if (!user.getPhoneNumber().startsWith("+48"))
            {
                user.setPhoneNumber("+48" + user.getPhoneNumber());
            }
            
            u = userRepository.findByPhoneNumber(user.getPhoneNumber());
            
            if (u != null) {
                user.setId(u.getId());
            }
        } 
        else if (user.getEmail()!= null)
        {            
            u = userRepository.findByEmail(user.getEmail());
            
            if (u != null) {
                user.setId(u.getId());
            }
        }
        
        if (user.getId() == null) {
            userRepository.save(user);
        }
        
        friendship.setIdFriend(user.getId());
        
        Friendship found = friendRepository.findByIdUserAndIdFriend(friendship.getIdUser(), friendship.getIdFriend());

        if (found != null) {
            return null;
        }
        
        friendRepository.save(friendship);
        
        return friendship;
    }
}
