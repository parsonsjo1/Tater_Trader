package com.joshwa.tater_trader;

import android.os.AsyncTask;

/**
 * Created by Joshua on 4/9/2016.
 */
public class RegisterUserTask extends AsyncTask<Void, Void, Boolean>
{
    private User user;

    public RegisterUserTask(User user)
    {
        this.user = user;
    }


    @Override
    protected Boolean doInBackground(Void... params)
    {
        ServerProxy.getInstance().registerUser(user);
        return true;
    }
}
