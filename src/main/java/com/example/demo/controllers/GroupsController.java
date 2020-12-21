package com.example.demo.controllers;

import com.example.demo.dao.GroupMembersRepository;
import com.example.demo.dao.GroupsRepository;
import com.example.demo.entity.Group;
import com.example.demo.entity.GroupMember;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("groups")
public class GroupsController {
    
    @Autowired
    private GroupsRepository groupsRepository;
    
    @Autowired
    private GroupMembersRepository groupMembersRepository;
    
    @Transactional
    @GetMapping(value = "getUserGroups/{idUser}")
    public ArrayList<Group> getUserGroups(@PathVariable Integer idUser) {
        
        ArrayList<GroupMember> groupsMember = groupMembersRepository.findByIdUser(idUser);
        ArrayList<Group> groups = new ArrayList<>();
        
        for (GroupMember g : groupsMember) {
            Group gr = groupsRepository.findById(g.getIdGroup()).get();
            groups.add(gr);
        }
        
        return groups;
    }
    
    @PostMapping(value="add")
    public Group add(@RequestBody Group group) {
        /*
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
        
        return friendship;*/
        return new Group();
    }
}
