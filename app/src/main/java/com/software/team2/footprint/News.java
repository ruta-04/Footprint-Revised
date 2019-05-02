package com.software.team2.footprint;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class News extends AppCompatActivity {
    public static final String BASE_URL = "https://api.iextrading.com/1.0/";
    ArrayList<Stock> newsarrayList = new ArrayList<>();
    private RecyclerView.Adapter mNewsAdapter;
    private RecyclerView mNewsRecyclerView;
    private RecyclerView.LayoutManager mNewsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IEXTradingApi searchNews = retrofit.create(IEXTradingApi.class);
        mNewsRecyclerView = findViewById(R.id.newsRecyclerView);
        mNewsRecyclerView.setHasFixedSize(true);

        int num = 10;
        Call<List<NewsMatches>> call = searchNews.getNewsMatch();
        call.enqueue(new Callback<List<NewsMatches>>() {
            @Override
            public void onResponse(Call<List<NewsMatches>> call, Response<List<NewsMatches>> response) {
                if (!response.isSuccessful()) {
                    String temp = "Code";
                    temp += response.code();
                    Toast.makeText(News.this,"Response unsuccessful. Code: " + temp, Toast.LENGTH_SHORT).show();
                    Log.i(" RESPONSE is ", temp);
                    return;
                }

                List<NewsMatches> newsMatches = response.body();

                for (NewsMatches newsMatch : newsMatches) {
                    Log.d("NEWS ACTIVITY ","adding");
                    newsarrayList.add(new Stock(newsMatch.getDatetime(), newsMatch.getHeadline(),newsMatch.getSummary(),newsMatch.getUrl()));
                    mNewsLayoutManager = new LinearLayoutManager(getParent());
                    mNewsAdapter = new NewsExampleAdapter(newsarrayList);
                    mNewsRecyclerView.setLayoutManager(mNewsLayoutManager);
                    mNewsRecyclerView.setAdapter(mNewsAdapter);

                }

            }

            @Override
            public void onFailure(Call<List<NewsMatches>> call, Throwable t) {
                Toast.makeText(News.this," API response Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        newsarrayList.clear();
    }

}