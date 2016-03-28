package com.joshwa.tater_trader;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Joshua on 3/27/2016.
 * This class allows user data to be stored on a local file in the database
 */
public class LocalUser
{
    public static final String SP_NAME = "userDetials";
    SharedPreferences localUserDatabase;

    public LocalUser(Context context)
    {
        localUserDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user)
    {
        SharedPreferences.Editor spEditor = localUserDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.putString("name", user.name);
        spEditor.putInt("age", user.age);
        spEditor.commit();
    }

    public User getLoggedInUser()
    {
        String username = localUserDatabase.getString("age", "");
        String password = localUserDatabase.getString("passwrod", "");
        String name = localUserDatabase.getString("name", "");
        int age = localUserDatabase.getInt("age", -1);

        return new User(username, password, name, age);
    }

    /**
     * Set the user's login status to true or false
     * @param loggedIn
     */
    public void setLoggedInUser(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor = localUserDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn()
    {
        if(localUserDatabase.getBoolean("loggedIn", false))
            return true;
        return false;
    }

    public void clearUserData()
    {
        SharedPreferences.Editor spEditor = localUserDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
