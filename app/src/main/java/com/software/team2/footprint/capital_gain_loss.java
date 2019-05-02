package com.software.team2.footprint;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class capital_gain_loss extends AppCompatActivity {

    DatabaseHelper db;
    final ArrayList<Stock> stock_gain = new ArrayList<>();
    static ArrayList<Stock> display = new ArrayList();
    private static final String TAG = "capital_gain_loss";
    public static final String BASE_URL = "https://www.alphavantage.co";
    public static final String API_KEY = "GVHMUUHWT2XJ9CAK";
    private TextView textViewResult;
    private float live_price = 0;
    public String capital_gain_loss_percent_s = "";
    public String capital_gain_loss_dollars_s = "";
     static float capital_gain_loss_percent = 0;
     static float capital_gain_loss_dollars = 0;
    Button home_button;
    Button search;
    capital_gain_loss_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital_gain_loss);
        Log.d(TAG, "onCreate: Started");
        home_button = findViewById(R.id.home_button);
        search = findViewById(R.id.search);
        TextView mtextViewpercent = (TextView) findViewById(R.id.Gain_percent);
        TextView mtextViewDollars = (TextView) findViewById(R.id.Gain_dollars);
        final ListView mListView = (ListView) findViewById(R.id.listView_1);
        ImageView mimageView = (ImageView) findViewById(R.id.gain_loss_chart);
        db = new DatabaseHelper(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlphavantageApi searchAlpha = retrofit.create(AlphavantageApi.class);

        Cursor cursor = db.get_performance();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                final String S = cursor.getString(7);
                if (S.equals("B")) {
                    final String stock_name = cursor.getString(2);
                    final String stock_symbol = cursor.getString(3);

                    final float stock_bought = cursor.getFloat(4);
                    final float stock_shares = cursor.getFloat(5);


                    Call<Example> call = searchAlpha.getStockInfo("GLOBAL_QUOTE", stock_symbol, API_KEY);
                    call.enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(Call<Example> call, Response<Example> response) {
                            if (!response.isSuccessful()) {
                                String temp = "Code";
                                temp += response.code();
                                textViewResult.setText(temp);
                                return;
                            }

                            GlobalQuote best = response.body().getGlobalQuote();

                            //String content = "";

                            //content += "Open: " + best.get05Price() + "\n";

                            live_price = Float.parseFloat(best.get05Price());

                            //live_price = 2;

                            float profit_dollars = (stock_bought * stock_shares) - (live_price * stock_shares);

                            String profit_dollars_s = String.format("%.2f", profit_dollars);

                            float profit_percent = profit_dollars / (stock_bought * stock_shares);
                            String profit_percent_s = String.format("%.2f", profit_percent);

                            capital_gain_loss_percent += profit_percent;
                            capital_gain_loss_dollars += profit_dollars;

                         capital_gain_loss_percent_s = String.format("%.2f", capital_gain_loss_percent) + " %";
                            capital_gain_loss_dollars_s = String.format("%.2f", capital_gain_loss_dollars) + " $";

                            Log.i("live_price: ", Float.toString(live_price));
                            //textViewResult.append(content);
                            Log.i("Stock Name", stock_name);
                            Log.i("Stock Symbol", stock_symbol);
                            Log.i("profit price", profit_dollars_s);
                            Log.i("profit percent", profit_percent_s);


                            System.out.println("Hello");

                            // if(S.equals("B"))
                            //{
                            display.add(new Stock(stock_name, stock_symbol, profit_percent_s, profit_dollars_s));
                            System.out.println("Hello");
                            System.out.println(display);
                            adapter = new capital_gain_loss_adapter(getApplicationContext(), R.layout.adapter_capital, display);
                            mListView.setAdapter(adapter);

                        }

                        //}


                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {
                            textViewResult.setText(t.getMessage());
                        }
                    });


                }


                cursor.close();
                mtextViewDollars.requestFocus();
                mtextViewDollars.setTextSize(15);

                home_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(capital_gain_loss.this, home.class);
                        startActivity(intent);
                    }
                });

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(capital_gain_loss.this, SearchActivity.class);
                        startActivity(intent);
                    }
                });
            }
            if (capital_gain_loss_dollars < 0) {
                mimageView.setImageResource(R.drawable.economics);
            } else {
                mimageView.setImageResource(R.drawable.economic);
            }
            capital_gain_loss_percent_s = String.format("%.2f", capital_gain_loss_percent) + " %";
            capital_gain_loss_dollars_s = String.format("%.2f", capital_gain_loss_dollars) + " $";
            mtextViewpercent.setText(capital_gain_loss_percent_s);
            mtextViewDollars.setText(capital_gain_loss_dollars_s);
            Log.i("capital_dollars", capital_gain_loss_dollars_s);
            Log.i(" capital_percent ", capital_gain_loss_percent_s);
        }

    }
}