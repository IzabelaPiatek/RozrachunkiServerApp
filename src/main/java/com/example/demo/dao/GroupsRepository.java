package com.example.demo.dao;

import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Group, Integer> {
    
}
