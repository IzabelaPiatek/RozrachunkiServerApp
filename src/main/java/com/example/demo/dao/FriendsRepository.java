package com.example.demo.dao;

import com.example.demo.entity.Friendship;
import com.example.demo.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepository extends JpaRepository<Friendship, Integer> {

    public List<Friendship> findByIdUser(int idUser);

    public void deleteByIdUserAndIdFriend(Integer idUser, Integer idFriend);

    public Friendship findByIdUserAndIdFriend(Integer idUser, Integer idFriend);

    public void deleteByIdUserAndEmail(Integer idUser, String email);

    public void deleteByIdUserAndPhoneNumber(Integer idUser, String phoneNumber);

    public Friendship findByIdUserAndPhoneNumber(Integer idUser, String phoneNumber);

    public Friendship findByIdUserAndEmail(Integer idUser, String email);
    
}