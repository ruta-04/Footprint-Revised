package com.software.team2.footprint;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.TextUtils;

public class Add_notes extends AppCompatActivity {

    DatabaseHelper db;
    EditText mTextTitle;
    EditText mTextContent;
    Button mButtonAddNote;
    Button mGoToNotes;
    public boolean exist;
    public String UpdateTitle;
    public String UpdateContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        try {
            Bundle bundle = getIntent().getExtras();
            UpdateTitle = bundle.getString("title");
            UpdateContent = bundle.getString("content");
        }
        catch(Exception e)
        {

        }



        db = new DatabaseHelper(this);
        mTextTitle = (EditText) findViewById(R.id.editext_title);
        mTextContent = (EditText) findViewById(R.id.edittext_content);
        mButtonAddNote = (Button) findViewById(R.id.add_note_button);
        mGoToNotes = (Button)findViewById(R.id.button_go_note);

        if(TextUtils.isEmpty(UpdateTitle) && TextUtils.isEmpty(UpdateContent))
        {
            exist = false;

        }
        else
        {
            exist = true;
            mTextTitle.setText(UpdateTitle);
            mTextContent.setText(UpdateContent);
        }

        mButtonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTextTitle.getText().toString().trim();
                String content = mTextContent.getText().toString().trim();
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content))
                {
                    if(exist)
                    {
                       if(db.updateNoteData(title,content))
                       {
                           Toast.makeText(Add_notes.this,"note updated successfully", Toast.LENGTH_SHORT).show();
                          // Intent notes = new Intent(Add_notes.this, notes.class);
                           //startActivity(notes);
                       }
                       else
                       {
                           Toast.makeText(Add_notes.this,"failed", Toast.LENGTH_SHORT).show();
                       }
                    }
                    else {
                        addNote(title, content);
                    }
                }
                else
                {
                    Toast.makeText(Add_notes.this,"Type note title and description", Toast.LENGTH_SHORT).show();
                }


            }
        });

        mGoToNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(Add_notes.this,notes.class);
                startActivity(addIntent);
            }
        });

    }

    public void addNote(String title, String content)
    {
        long res = db.addNote(db.transaction_user_key(),title, content);
        if(res>0)
        {
            Toast.makeText(Add_notes.this,"Note created successfully!", Toast.LENGTH_SHORT).show();
            //Intent addIntent = new Intent(Add_notes.this,notes.class);
            //startActivity(addIntent);
        }
        else
        {
            Toast.makeText(Add_notes.this,"Type note title and description", Toast.LENGTH_SHORT).show();
        }
    }


}
