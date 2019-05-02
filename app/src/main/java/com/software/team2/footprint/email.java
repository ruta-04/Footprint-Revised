package com.software.team2.footprint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class email extends AppCompatActivity {

    DatabaseHelper db;
    EditText mTextEmail;
    Button mButtonVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        db = new DatabaseHelper(this);
        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mButtonVerify = (Button) findViewById(R.id.button_verify);

        mButtonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mTextEmail.getText().toString().trim();
                boolean email_exist = db.check_email(email);
                if(!email_exist)
                {
                    Intent intent = new Intent(email.this, newPassword.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(email.this,"Email does not exist", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
