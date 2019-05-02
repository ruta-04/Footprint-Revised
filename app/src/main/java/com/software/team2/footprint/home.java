package com.software.team2.footprint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class home extends AppCompatActivity {

    ImageView news;
   CardView watchlist;
    CardView notes;
    CardView history;
    CardView capital;
    CardView transaction;
    CardView performance;
    Button search;
    Button profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        news = findViewById(R.id.news_icon);
        watchlist = findViewById(R.id.watchlist_intent);
        notes =   findViewById(R.id.notes_intent);
        history =  findViewById(R.id.history_intent);
        capital =  findViewById(R.id.capital_intent);
        transaction =  findViewById(R.id.transaction_intent);
        performance =  findViewById(R.id.performance_intent);
        search = findViewById(R.id.search);
        profile = findViewById(R.id.home_button);
        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, WatchListActivity.class);
                startActivity(intent);
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, notes.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, TradeHistory.class);
                startActivity(intent);
            }
        });

        capital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, capital_gain_loss.class);
                startActivity(intent);
            }
        });

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Transaction.class);
                startActivity(intent);
            }
        });

        performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, performance.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, News.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Profile.class);
                startActivity(intent);
            }
        });


    }
}
