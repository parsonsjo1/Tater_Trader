package com.joshwa.tater_trader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;
    EditText etName, etAge, etUsername;
    LocalUser localUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etUsername = (EditText) findViewById(R.id.etUsername);

        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);

        localUser = new LocalUser(this);
    }

    //Everytime the activity opens authenticate the user
    @Override
    protected void onStart()
    {
        super.onStart();

        //If the user is logged in then do something
        if(authenticate() == true)
        {
            displayUserDetails();
        }
        else
        {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }

    private boolean authenticate()
    {
        return localUser.getUserLoggedIn();
    }

    private void displayUserDetails()
    {
        User user = localUser.getLoggedInUser();

        etUsername.setText(user.name);
        etName.setText(user.name);
        etAge.setText(user.age + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bLogout:
                localUser.clearUserData();
                localUser.setLoggedInUser(false);

                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}
