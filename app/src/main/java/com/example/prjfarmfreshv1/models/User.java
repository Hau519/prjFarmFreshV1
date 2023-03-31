package com.example.prjfarmfreshv1.models;

public class User {
    private static int Id=0;

    private String name;
    private String email;
    private String password;

    public User() {
        Id++;
    }

    public User(int id, String name, String email, String password) {
        Id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
