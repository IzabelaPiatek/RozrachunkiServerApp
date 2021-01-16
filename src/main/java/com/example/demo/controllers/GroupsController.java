package com.example.demo.controllers;

import com.example.demo.RozrachunkiServerAppApplication;
import com.example.demo.dao.GroupMembersRepository;
import com.example.demo.dao.GroupsRepository;
import com.example.demo.entity.Group;
import com.example.demo.entity.GroupMember;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import com.example.demo.entity.User;
import com.example.demo.json.GroupJson;
import com.example.demo.json.GroupWithStringImageJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialBlob;

@RestController
@RequestMapping("groups")
public class GroupsController {

    /*static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3308/rozrachunki?useSSL=false&serverTimezone=UTC";

    static final String USER = "admin";
    static final String PASS = "admin";*/
    
    @Autowired
    private GroupsRepository groupsRepository;
    
    @Autowired
    private GroupMembersRepository groupMembersRepository;
    
    @Transactional
    @GetMapping(value = "getUserGroups/{idUser}")
    public ArrayList<GroupJson> getUserGroups(@PathVariable Integer idUser) throws SQLException, IOException {

        Connection connection = DriverManager.getConnection(RozrachunkiServerAppApplication.DB_URL, RozrachunkiServerAppApplication.USER, RozrachunkiServerAppApplication.PASS);

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

                    if (rs.getBinaryStream("image") == null) {
                        gr.setImage(null);
                    } else {
                        gr.setImage(rs.getBinaryStream("image").readAllBytes());
                    }

                    groups.add(gr);
                }
            }
            rs.close();
        }
        connection.close();
        
        return groups;
    }
    
    @PostMapping(value="add/{idUser}")
    public GroupJson add(@RequestBody GroupJson group, @PathVariable Integer idUser) throws SQLException {

        Blob blob = null;
        Connection connection = DriverManager.getConnection(RozrachunkiServerAppApplication.DB_URL, RozrachunkiServerAppApplication.USER, RozrachunkiServerAppApplication.PASS);

        if (group.getImage() != null)
        {
            blob = new SerialBlob(group.getImage());
        }

        PreparedStatement pre = connection.prepareStatement("insert into rozrachunki.groups values (NULL, ?, ?, ?, ?);");
        pre.setString(1, group.getName());
        pre.setInt(2, group.getType());
        pre.setBoolean(3, group.isSettled());
        pre.setBlob(4, blob);
        int count = pre.executeUpdate();

        if (count != 0)
        {
            Group g = groupsRepository.findMaxId();

            pre.close();
            connection.close();

            groupMembersRepository.save(new GroupMember(null, idUser, g.getId()));
        }
        return group;
    }

    @GetMapping(value = "get/{idGroup}")
    public GroupWithStringImageJson get(@PathVariable Integer idGroup) throws SQLException, IOException {

        Connection connection = DriverManager.getConnection(RozrachunkiServerAppApplication.DB_URL, RozrachunkiServerAppApplication.USER, RozrachunkiServerAppApplication.PASS);

        PreparedStatement pre = connection.prepareStatement("select * from rozrachunki.groups where id_group = ?;");
        pre.setInt(1, idGroup);
        ResultSet rs = pre.executeQuery();
        rs.next();
        GroupWithStringImageJson gr = new GroupWithStringImageJson();
        gr.setId(rs.getInt("id_group"));
        gr.setName(rs.getString("name"));
        gr.setType(rs.getInt("type"));
        gr.setSettled(rs.getBoolean("settled"));

        if (rs.getBinaryStream("image") == null) {
            gr.setImage(null);
        } else {
            byte[] b = rs.getBinaryStream("image").readAllBytes();
            //gr.setImage(b);
            String base64String = Base64.getEncoder().encodeToString(b);

            //byte[] backToBytes = Base64.getDecoder().decode(base64String);

            gr.setImage(base64String);
        }

        pre.close();
        connection.close();

        return gr; //new ObjectMapper().writeValueAsString(gr);
    }

    @Transactional
    @PostMapping(value = "delete/{idGroup}")
    public int deleteGroup(@PathVariable Integer idGroup) throws SQLException {

        Connection connection = DriverManager.getConnection(RozrachunkiServerAppApplication.DB_URL, RozrachunkiServerAppApplication.USER, RozrachunkiServerAppApplication.PASS);

        PreparedStatement pre = connection.prepareStatement("delete from rozrachunki.groups where id_group = ?;");
        pre.setInt(1, idGroup);
        int count = pre.executeUpdate();

        pre.close();
        connection.close();

        return 1;
    }

    @RequestMapping(value = "updateGroup", method = RequestMethod.POST)
    public int updateGroup(@RequestBody GroupJson group) throws SQLException {

        Connection connection = DriverManager.getConnection(RozrachunkiServerAppApplication.DB_URL, RozrachunkiServerAppApplication.USER, RozrachunkiServerAppApplication.PASS);

        PreparedStatement pre = connection.prepareStatement("update rozrachunki.groups set name = ?, type = ?, settled = ? where id_group = ?;");
        pre.setString(1, group.getName());
        pre.setInt(2, group.getType());
        pre.setBoolean(3, group.isSettled());
        pre.setInt(4, group.getId());
        int count = pre.executeUpdate();

        pre.close();
        connection.close();

        return 0;
    }
}
