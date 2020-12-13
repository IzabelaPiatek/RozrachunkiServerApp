package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groupmembers")
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_groupmember")
    private Integer id;
    
    @Column(name = "id_user")
    private Integer idUser;
    
    @Column(name = "id_group")
    private Integer idGroup;
    
    public void GroupMember(Integer id, Integer idUser, Integer idGroup) {
        this.id = id;
        this.idUser = idUser;
        this.idGroup = idGroup;
    }
    
    public void GroupMember() {}

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

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }
}
