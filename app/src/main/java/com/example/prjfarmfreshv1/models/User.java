package com.example.prjfarmfreshv1.models;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String Email;
    private String Password;


    public User(){}


    public User(String name, String email, String password) {
        this.name = name;
        this.Email = email;
        this.Password = password;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
