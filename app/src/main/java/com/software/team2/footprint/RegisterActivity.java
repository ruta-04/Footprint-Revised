package com.software.team2.footprint;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mTextFirstName;
    EditText mTextLastName;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextConfPassword;
    EditText mTextEmail;
    Button mButtonRegister;
    TextView mTextViewLogin;
    TextView mTextViewPassword;

    int setPtype =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        mTextFirstName = (EditText) findViewById(R.id.edittext_f_name);
        mTextLastName = (EditText) findViewById(R.id.edittext_l_name);
        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mTextConfPassword = (EditText) findViewById(R.id.edittext_confpassword);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);
        mTextViewPassword = (TextView) findViewById(R.id.view_password);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mTextViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setPtype == 1)
                {
                    setPtype = 0;
                    mTextPassword.setTransformationMethod(null);
                    if(mTextPassword.getText().length()>0)
                        mTextPassword.setSelection(mTextPassword.getText().length());
                    mTextViewPassword.setBackgroundResource(R.drawable.visibility_on);
                }
                else
                {
                    setPtype = 1;
                    mTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                    if(mTextPassword.getText().length()>0)
                        mTextPassword.setSelection(mTextPassword.getText().length());
                    mTextViewPassword.setBackgroundResource(R.drawable.visibility_off);
                }
            }
        }
        );
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, Login.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = mTextFirstName.getText().toString().trim();
                String last = mTextLastName.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String conf_pwd = mTextConfPassword.getText().toString().trim();
                boolean alreadyThereU = db.check_username(user);
                boolean alreadyThereE = db.check_email(email);
                if (alreadyThereE) {
                    if (alreadyThereU) {
                        if (validUsername(user)) {
                            if (validPassword(pwd)) {
                                if (pwd.equals(conf_pwd)) {
                                    long val = db.addUser(first, last, email, user, pwd);
                                    if (val > 0) {
                                        Toast.makeText(RegisterActivity.this, "Welcome! You are registered", Toast.LENGTH_SHORT).show();
                                        Intent moveToLogin = new Intent(RegisterActivity.this, Login.class);
                                        startActivity(moveToLogin);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    mTextConfPassword.setError("must match with password");
                                }
                            } else {
                                mTextPassword.setError("Password must contain: \n - At least eight characters long \n - At least one uppercase character, one number and one special character (@,#,$,%,_,&,+,=)");
                            }
                        } else {
                            mTextUsername.setError("Username must be at least six characters long with at least one letter and one number");
                        }
                    } else {
                        mTextUsername.setError("Username is already registered");
                    }
                } else {
                    mTextEmail.setError("Email is already registered");
                }
            }
        });

    }

    public boolean validUsername(final String username)
    {
        Pattern pattern;
        Matcher matcher;

        final String USERNAME_PATTERN ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);
        return matcher.matches();
    }
    public boolean validPassword(final String password)
    {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_&+=])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
