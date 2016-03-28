package com.joshwa.tater_trader;

/**
 * Created by Joshua on 3/27/2016.
 */
public class User {
    String name, username, password;
    int age;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.name = "";
        this.age = -1;
    }

    public User(String name, String username, String password, int age)
    {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
    }
}
