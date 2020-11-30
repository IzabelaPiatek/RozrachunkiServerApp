package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "friendship")
public class Friendship {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_friendship")
    private Integer id;
    
    @Column(name = "id_user")
    private Integer idUser;
    
    @Column(name = "id_friend")
    private Integer idFriend;
    
    @Column(name = "accepted")
    private boolean accepted = false;

    public Friendship(Integer id, Integer idUser, Integer idFriend, boolean accepted) {
        this.id = id;
        this.idUser = idUser;
        this.idFriend = idFriend;
        this.accepted = accepted;
    }

    public Friendship() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(Integer idFriend) {
        this.idFriend = idFriend;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
