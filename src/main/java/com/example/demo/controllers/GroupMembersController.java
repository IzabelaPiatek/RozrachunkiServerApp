package com.example.demo.controllers;

import com.example.demo.dao.GroupMembersRepository;
import com.example.demo.dao.UsersRepository;
import com.example.demo.entity.GroupMember;
import com.example.demo.entity.User;
import com.example.demo.json.GroupJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("groupmembers")
public class GroupMembersController {

    @Autowired
    private GroupMembersRepository groupMembersRepository;

    @Autowired
    private UsersRepository userRepository;

    @GetMapping(value = "getGroupMembers/{idGroup}")
    public ArrayList<User> getGroupMembers(@PathVariable Integer idGroup) {
        ArrayList<User> users = new ArrayList<>();

        ArrayList<GroupMember> groupMembers = groupMembersRepository.findByIdGroup(idGroup);

        for(GroupMember gm : groupMembers) {
            users.add(userRepository.findById(gm.getIdUser()).get());
        }

        return users;
    }
}
