package com.software.team2.footprint;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapterNotes extends RecyclerView.Adapter<MyAdapterNotes.MyHolder> {

    public Context applicationContext;
    public ArrayList<NoteModel> arraylist;

    public MyAdapterNotes(Context applicationContext, ArrayList<NoteModel> arraylist) {
        this.applicationContext = applicationContext;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.note_layout, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int i) {
        NoteModel myModel = arraylist.get(i);
        holder.textTitle.setText(myModel.getNoteTitle());
        holder.textContent.setText(myModel.getNoteContent());
        holder.textContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(applicationContext,"Welcome! clickedddd", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(applicationContext, Add_notes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", holder.textTitle.getText().toString());
                intent.putExtra("content", holder.textContent.getText().toString());
                applicationContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public TextView textContent;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.note_title);
            textContent = (TextView) itemView.findViewById(R.id.note_content);
        }
    }

}
