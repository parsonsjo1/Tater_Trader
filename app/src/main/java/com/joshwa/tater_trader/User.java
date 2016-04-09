package com.joshwa.tater_trader;

/**
 * Created by Joshua on 4/9/2016.
 */
public class User
{
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    public User(String firstName, String lastName, String emailAddress, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
    }

}
