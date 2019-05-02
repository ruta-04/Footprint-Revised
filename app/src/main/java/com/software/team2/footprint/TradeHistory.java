package com.software.team2.footprint;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


public class TradeHistory extends AppCompatActivity {

    public final String TAG = "TradeHist";
    private TradeHistoryAdapter adapter;
    final ArrayList<TradeHistoryItem> tradeList = new ArrayList<>();
    private DatabaseHelper db;
    Button home_button;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Just created");
        setContentView(R.layout.activity_trade_history);
        db = new DatabaseHelper(this);
        Log.d(TAG, "After db");

        home_button = findViewById(R.id.home_button);
        search = findViewById(R.id.search);
        fillTradeList();

        setUpRecyclerView();

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TradeHistory.this, home.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TradeHistory.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }


    private void fillTradeList() {


        Cursor cursor = db.getAllTransactions();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int userKey = cursor.getInt(1);
                String stockName = cursor.getString(2);
                String stockSymbol = cursor.getString(3);
                float price = cursor.getFloat(4);
                int totalShares = cursor.getInt(5);
                float totalMoney = cursor.getFloat(6);
                String transactionType = cursor.getString(7);
                String date = cursor.getString(8);
                float eachPrice = cursor.getFloat(9);
                tradeList.add(new TradeHistoryItem(stockSymbol, stockName, eachPrice, price, totalMoney, totalShares, transactionType, date));
            }
            cursor.close();
        } else {
            findViewById(R.id.empty_trade).setVisibility(View.VISIBLE);
        }
    }


    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.tradeRecycle);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new TradeHistoryAdapter(tradeList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trade_menu, menu);

        MenuItem tradeItem = menu.findItem(R.id.action_trade_search);
        SearchView searchView = (SearchView) tradeItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}

