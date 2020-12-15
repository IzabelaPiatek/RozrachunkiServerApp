package com.example.demo.dao;

import com.example.demo.entity.GroupMember;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMembersRepository extends JpaRepository<GroupMember, Integer> {

    public ArrayList<GroupMember> findByIdUser(Integer idUser);
    
}
