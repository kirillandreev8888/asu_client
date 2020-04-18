package com.example.asu.DomainModel;

import com.example.asu.RowDataGateway.User;

public class DMUser {

    public int _id;
    public String username;
    public String password;

    public DMUser(User user) {
        this._id = user._id;
        this.username = user.username;
        this.password = user.password;
    }

    public DMUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void insert(){

    }
}
