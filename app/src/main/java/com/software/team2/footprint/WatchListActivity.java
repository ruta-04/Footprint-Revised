package com.software.team2.footprint;

import android.arch.lifecycle.ViewModelStore;
import android.database.Cursor;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WatchListActivity extends AppCompatActivity {
    DatabaseHelper db;
    private static final String TAG = "WatchListActivity";
    public static final String BASE_URL = "https://www.alphavantage.co";
    public static final String API_KEY = "GVHMUUHWT2XJ9CAK";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Stock> arrayList = new ArrayList<>();
    ArrayList<Stock> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);

        db = new DatabaseHelper(this);
        db.addToWatchlist("MSFT", "Microsoft");
        db.addToWatchlist("AAPL", "Apple");
        db.addToWatchlist("GOOGL", "Alphabet");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlphavantageApi searchAlpha = retrofit.create(AlphavantageApi.class);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        Cursor cursor =  db.getWatchlistEntries();
        if(cursor!= null  && cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                String symbol = cursor.getString(0);
                String name = cursor.getString(1);
                Stock temp = new Stock(name, symbol, "", "");
                arrayList.add(temp);
            }
            cursor.close();
        }
        else
        {
            Toast.makeText(WatchListActivity.this,"Watch-list is empty!", Toast.LENGTH_SHORT).show();
        }

        for(final Stock stock: arrayList) {
            final String STOCK_NAME = stock.getName();
            Call<Example> call = searchAlpha.getStockInfo("GLOBAL_QUOTE", stock.getSymbol(), API_KEY);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    if (!response.isSuccessful()) {
                        String temp = "Code";
                        temp += response.code();
                        Toast.makeText(WatchListActivity.this,"Response unsuccessful. Code: " + temp, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    GlobalQuote best = response.body().getGlobalQuote();
                    String sym = best.get01Symbol();
                    String pr = best.get05Price();
                    String ch = best.get09Change();
                    Stock display = new Stock(STOCK_NAME, sym, pr, ch);
                    displayList.add(display);
                    mAdapter = new ExampleAdapter(displayList);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Toast.makeText(WatchListActivity.this," API response Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }



    }


}
