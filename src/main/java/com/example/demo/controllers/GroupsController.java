package com.example.demo.controllers;

import com.example.demo.RozrachunkiServerAppApplication;
import com.example.demo.dao.GroupMembersRepository;
import com.example.demo.dao.GroupsRepository;
import com.example.demo.entity.Group;
import com.example.demo.entity.GroupMember;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.json.GroupJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.sql.rowset.serial.SerialBlob;

@RestController
@RequestMapping("groups")
public class GroupsController {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3308/rozrachunki?useSSL=false&serverTimezone=UTC";

    static final String USER = "admin";
    static final String PASS = "admin";
    
    @Autowired
    private GroupsRepository groupsRepository;
    
    @Autowired
    private GroupMembersRepository groupMembersRepository;
    
    @Transactional
    @GetMapping(value = "getUserGroups/{idUser}")
    public ArrayList<GroupJson> getUserGroups(@PathVariable Integer idUser) throws SQLException, IOException {

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        ArrayList<GroupMember> groupsMember = groupMembersRepository.findByIdUser(idUser);
        ArrayList<GroupJson> groups = new ArrayList<>();
        
        for (GroupMember g : groupsMember) {
            ResultSet rs = connection.prepareStatement("SELECT * from rozrachunki.groups").executeQuery();
            while(rs.next()) {
                if (rs.getInt("id_group") == g.getIdGroup())
                {
                    GroupJson gr = new GroupJson();
                    gr.setId(rs.getInt("id_group"));
                    gr.setName(rs.getString("name"));
                    gr.setType(rs.getInt("type"));
                    gr.setSettled(rs.getBoolean("settled"));
                    gr.setImage(rs.getBinaryStream("image").readAllBytes());

                    groups.add(gr);
                }
            }
        }
        
        return groups;
    }
    
    @PostMapping(value="add")
    public GroupJson add(@RequestBody GroupJson group) throws SQLException {

        Blob blob = new SerialBlob(group.getImage());

        groupsRepository.save(new Group(null, group.getName(), group.getType(), group.isSettled(), blob));
        // od razu dodawac groupmembera - usera
        return group;
    }
}
