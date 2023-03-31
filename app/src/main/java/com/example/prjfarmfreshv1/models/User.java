package com.example.prjfarmfreshv1.models;

public class User {
    private static int Id=0;

    private String name;
    private String Email;
    private String Password;

    public User() {
        Id++;
    }

    public User(int id, String name, String email, String password) {
        Id = id;
        this.name = name;
        this.Email = email;
        this.Password = password;

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.Email = email;
        this.Password = password;
        Id++;
    }

    public int getId() {
        return Id;
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
