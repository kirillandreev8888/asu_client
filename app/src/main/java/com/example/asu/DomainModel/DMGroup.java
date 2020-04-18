package com.example.asu.DomainModel;

public class DMGroup {
    public int id;
    public String name;

    public DMGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public DMGroup() {
    }

    public DMGroup(String name) {
        this.name = name;
    }
}
