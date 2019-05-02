package com.software.team2.footprint;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class performance extends AppCompatActivity {

    DatabaseHelper db;
final    ArrayList<Stock> stock_perf = new ArrayList<>();
    Button home_button;
    Button search;
    private static final String TAG = "Performance";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        Log.d(TAG, "onCreate: Started");
        home_button = findViewById(R.id.home_button);
        search = findViewById(R.id.search);
        ListView mListView = (ListView) findViewById(R.id.listView);
        db = new DatabaseHelper(this);



        Cursor cursor =  db.get_performance();

        if(cursor!= null  && cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                String stock_name = cursor.getString(2);
                String stock_symbol = cursor.getString(3);
                float stock_total_money = cursor.getFloat(6);
                float stock_bought = cursor.getFloat(9);
                float stock_shares = cursor.getFloat(5);
                float profit_dollars = stock_total_money-(stock_bought*stock_shares);
                String profit_dollars_s = Float.toString(profit_dollars);
                float percent = profit_dollars/(stock_bought*stock_shares);
                String percent_s = Float.toString(percent);
                String S = cursor.getString(7);
                if(S.equals("S"))
                {
                    stock_perf.add(new Stock(stock_name, stock_symbol,  percent_s,profit_dollars_s));
                }
                Log.i("Stock Name",stock_name);
                Log.i("Stock Symbol",stock_symbol);
                Log.i("profit price",profit_dollars_s);
                Log.i("profit percent",percent_s);
            }
            cursor.close();
        }
        else
        {
            Toast.makeText(performance.this,"No Stocks!", Toast.LENGTH_SHORT).show();
        }


        Stock_performance_adapter adapter = new  Stock_performance_adapter(this,  R.layout.adapter_performance,stock_perf);
        mListView.setAdapter(adapter);

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(performance.this, home.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(performance.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
