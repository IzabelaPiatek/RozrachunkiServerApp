package com.example.demo.dao;

import com.example.demo.entity.GroupMember;
import java.util.ArrayList;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMembersRepository extends JpaRepository<GroupMember, Integer> {

    public ArrayList<GroupMember> findByIdUser(Integer idUser);

    public ArrayList<GroupMember> findByIdGroup(Integer idGroup);

    public GroupMember findByIdGroupAndIdUser(Integer idGroup, Integer userId);
}
