package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);

    public User findById(int userId);

    public User findByEmail(String email);

    public User findByPhoneNumber(String phoneNumber);
}
