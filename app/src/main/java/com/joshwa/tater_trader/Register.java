package com.joshwa.tater_trader;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity
{
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);

        mFirstName = (EditText) findViewById(R.id.etFirstName);
        mLastName = (EditText) findViewById(R.id.etLastName);
        mEmail = (EditText) findViewById(R.id.etEmail);
        mPassword = (EditText) findViewById(R.id.etPassword);
        mConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        mConfirmPassword.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                if(password.equals(confirmPassword) && mConfirmPassword.length() > 0)
                {
                    mPassword.setBackgroundColor(Color.GREEN);
                    mConfirmPassword.setBackgroundColor(Color.GREEN);
                }
                else if(mConfirmPassword.length() > 0)
                {
                    mPassword.setBackgroundColor(Color.RED);
                    mConfirmPassword.setBackgroundColor(Color.RED);
                }
                else
                {
                    mPassword.setBackgroundColor(Color.TRANSPARENT);
                    mConfirmPassword.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        Button mRegister = (Button) findViewById(R.id.bRegister);
        mRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                User user = checkFields();
                if(user != null)
                {
                    System.out.println("Registering the user to the database");
                    //Send information to the server
                    // Show a progress spinner, and kick off a background task to
                    // perform the user login attempt.
                    //showProgress(true);
                    RegisterUserTask mAuthTask = new RegisterUserTask(user);
                    mAuthTask.execute((Void) null);
                    finish();
                }
            }
        });
    }

    private User checkFields()
    {
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();

        if(firstName.equals(""))
            return null;
        if(lastName.equals(""))
            return null;
        if(email.equals("") || !email.contains("@") || !email.contains(".com"))
            return null;
        if(!password.equals(confirmPassword))
            return null;

        return new User(firstName, lastName, email, password);
    }
}
