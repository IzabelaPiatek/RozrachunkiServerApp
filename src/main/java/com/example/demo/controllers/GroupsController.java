package com.example.demo.controllers;

import com.example.demo.dao.GroupMembersRepository;
import com.example.demo.dao.GroupsRepository;
import com.example.demo.entity.Group;
import com.example.demo.entity.GroupMember;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("groups")
public class GroupsController {
    
    @Autowired
    private GroupsRepository groupsRepository;
    
    @Autowired
    private GroupMembersRepository groupMembersRepository;
    
    @GetMapping(value = "getUserGroups/{idUser}")
    public ArrayList<Group> getUserGroups(@PathVariable Integer idUser) {
        
        ArrayList<GroupMember> groupsMember = groupMembersRepository.findByIdUser(idUser);
        ArrayList<Group> groups = new ArrayList<>();
        
        for (GroupMember g : groupsMember) {
            Optional<Group> gr = groupsRepository.findById(g.getIdGroup());
            groups.add(gr.get());
        }
        
        return groups;
    }
}
