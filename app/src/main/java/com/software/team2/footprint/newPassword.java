package com.software.team2.footprint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newPassword extends AppCompatActivity {

    DatabaseHelper db;
    EditText mTextPassword;
    EditText mTextConPassword;
    Button mButtonUpdate;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Bundle bundle = getIntent().getExtras();
        email =  bundle.getString("email");

        db = new DatabaseHelper(this);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mTextConPassword = (EditText) findViewById(R.id.edittext_confpassword);
        mButtonUpdate = (Button) findViewById(R.id.button_update);

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = mTextPassword.getText().toString().trim();
                String confPassword = mTextConPassword.getText().toString().trim();
                if(password.equals(confPassword))
                {
                    boolean flag = db.updatePassword(email, password);
                    if(flag)
                    {
                        Toast.makeText(newPassword.this,"Successfully reseted passsword", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(newPassword.this, Login.class);
                        startActivity(intent);
                    }

                }
                else
                {
                    mTextConPassword.setError("Password does not match");
                }

            }
        });

    }
}
