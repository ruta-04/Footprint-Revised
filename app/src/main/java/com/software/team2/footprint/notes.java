package com.software.team2.footprint;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class notes extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;
    final ArrayList<NoteModel> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Button addNoteButton = (Button) findViewById(R.id.button_add_note);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        db = new DatabaseHelper(this);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent addIntent = new Intent(notes.this,Add_notes.class);
                    startActivity(addIntent);

            }
        });

        Cursor cursor =  db.getAllnotes();

        if(cursor!= null  && cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                String title = cursor.getString(2);
                String content = cursor.getString(3);
                arrayList.add(new NoteModel(title, content));
                Log.i("Note Title",title);
                Log.i("Note content",content);
            }
            cursor.close();
        }
        else
        {
            Toast.makeText(notes.this,"No notes!", Toast.LENGTH_SHORT).show();
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.i("Note co","23456");
        final MyAdapterNotes myAdapterNotes = new MyAdapterNotes(getApplicationContext(), arrayList);
        recyclerView.setAdapter(myAdapterNotes);


        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Toast.makeText(notes.this,"  delete!", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                String title = arrayList.get(position).getNoteTitle();
                String content = arrayList.get(position).getNoteContent();
                int a=db.deleteNoteData(title, content);
                if(a>0)
                    Log.i("DELETEEEEEE ","SUCESSS");
                arrayList.remove(position);
                myAdapterNotes.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
}
