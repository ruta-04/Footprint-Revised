package com.software.team2.footprint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    TextView mTextViewReset;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mTextViewRegister = (TextView) findViewById(R.id.textview_register);
        mTextViewReset = (TextView) findViewById(R.id.textview_reset);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        mTextViewReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this,email.class);
                startActivity(registerIntent);
            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if(res == true)
                {
                    Toast.makeText(Login.this,"Successfully Logged In", Toast.LENGTH_SHORT).show();
                    Intent testIntent = new Intent(Login.this,home.class);
                    startActivity(testIntent);
                }
                else
                {
                    Toast.makeText(Login.this,"Login Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
