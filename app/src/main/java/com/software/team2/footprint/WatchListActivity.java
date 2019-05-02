package com.software.team2.footprint;

import android.arch.lifecycle.ViewModelStore;
import android.content.Intent;
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
import android.widget.LinearLayout;
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
    Button home_button;
    Button search;
    RecyclerView mRecyclerView;
   ExampleAdapter mAdapter;
    ArrayList<Stock> arrayList = new ArrayList<>();
    ArrayList<Stock> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);

        db = new DatabaseHelper(this);

        /* Adding is for testing purposes. Make button later */
        db.addToWatchlist("AAPL","Apple");
        db.addToWatchlist("MSFT","Microsoft");
        db.addToWatchlist("FB","Facebook");
        db.addToWatchlist("BAC","Bank of America");
        db.addToWatchlist("GS","Goldman Sachs");

        home_button = findViewById(R.id.home_button);
        search = findViewById(R.id.search);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlphavantageApi searchAlpha = retrofit.create(AlphavantageApi.class);
        mRecyclerView = findViewById(R.id.recyclerView);

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

                    Log.i("Check arraylist ",stock.getSymbol());

                    GlobalQuote best = response.body().getGlobalQuote();
                    Log.i("Check response", best.get01Symbol());
                    String sym = best.get01Symbol();
                    String pr = best.get05Price();
                    String ch = best.get09Change();
                    Stock display = new Stock(STOCK_NAME, sym, pr, ch);
                    displayList.add(display);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getParent());
                    mAdapter = new ExampleAdapter(displayList);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Toast.makeText(WatchListActivity.this," API response Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Toast.makeText(WatchListActivity.this,"  delete!", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                String symbol = arrayList.get(position).getSymbol();
                String name = arrayList.get(position).getName();
                int a=db.deleteWatchList(symbol, name);
                if(a>0)
                    Log.i("DELETE ","SUCESSS");
                displayList.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(mRecyclerView);

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WatchListActivity.this, home.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WatchListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }


}