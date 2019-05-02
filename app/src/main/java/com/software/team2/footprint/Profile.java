package com.software.team2.footprint;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    TextView name;
    TextView userName;
    Button logout;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new DatabaseHelper(this);
        name = (TextView)findViewById(R.id.name);
        userName = (TextView)findViewById(R.id.username);
        logout = (Button) findViewById(R.id.logout);
        Cursor cursor = db.getUser();


        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String fName = cursor.getString(1);
                String lName = cursor.getString(2);
                String uName = cursor.getString(3);
                name.setText(fName + " " + lName);
                userName.setText(uName);
            }
            cursor.close();
        } else {
            name.setText(" ");
            userName.setText(" ");
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(Profile.this,Login.class);
                startActivity(loginIntent);
            }
        }

        );
    }
}
