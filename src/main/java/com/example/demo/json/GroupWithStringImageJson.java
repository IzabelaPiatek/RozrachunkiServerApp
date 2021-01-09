package com.example.demo.json;

import com.example.demo.entity.Group;

public class GroupWithStringImageJson {
    private Integer id;
    private String name;
    private Integer type;
    private boolean settled;
    private String image = null;

    public GroupWithStringImageJson(Integer id, String name, Integer type, boolean settled, String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.settled = settled;
        this.image = image;
    }

    public GroupWithStringImageJson(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.type = group.getType();
        this.settled = group.isSettled();
        this.image = image;
    }

    public GroupWithStringImageJson() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
