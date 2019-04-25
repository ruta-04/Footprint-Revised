package com.software.team2.footprint;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    final    ArrayList<Stock> stock_gain = new ArrayList<>();
    static  final ArrayList<Stock> display= new ArrayList();
    private static final String TAG = "capital_gain_loss";
    public static final String BASE_URL = "https://www.alphavantage.co";
    public static final String API_KEY = "GVHMUUHWT2XJ9CAK";
    private TextView textViewResult;
    private float live_price= 0;
    public String capital_gain_loss_percent_s ="";
    public String capital_gain_loss_dollars_s ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital_gain_loss);
        Log.d(TAG, "onCreate: Started");

        TextView mtextViewpercent = (TextView)findViewById(R.id.Gain_percent);
        TextView mtextViewDollars = (TextView)findViewById(R.id.Gain_dollars);
        ListView mListView = (ListView) findViewById(R.id.listView_1);
        ImageView mimageView = (ImageView)findViewById(R.id.gain_loss_chart);
        db = new DatabaseHelper(this);

        float capital_gain_loss_percent=0;
        float capital_gain_loss_dollars =0;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlphavantageApi searchAlpha = retrofit.create(AlphavantageApi.class);

        Cursor cursor =  db.get_performance();

        if(cursor!= null  && cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {

                final String S = cursor.getString(7);
                if(S.equals("B"))
                {
                    final String stock_name = cursor.getString(2);
                    final String stock_symbol = cursor.getString(3);

                    final float stock_bought = cursor.getFloat(4);
                    final float stock_shares = cursor.getFloat(5);


                /*Call<Example> call = searchAlpha.getStockInfo("GLOBAL_QUOTE", stock_symbol,API_KEY);
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
*/
                    live_price = 2;

                    float profit_dollars = (stock_bought * stock_shares) - (live_price * stock_shares);

                    String  profit_dollars_s = String.format("%.2f",profit_dollars);

                    float  profit_percent = profit_dollars / (stock_bought * stock_shares);
                    String profit_percent_s = String.format("%.2f",profit_percent);

                    capital_gain_loss_percent += profit_percent;
                    capital_gain_loss_dollars += profit_dollars;

                    capital_gain_loss_percent_s = String.format("%.2f",capital_gain_loss_percent) + " %";
                    capital_gain_loss_dollars_s = String.format("%.2f",capital_gain_loss_dollars) + " $";

                    Log.i("live_price: ", Float.toString(live_price));
                    //textViewResult.append(content);
                    Log.i("Stock Name", stock_name);
                    Log.i("Stock Symbol", stock_symbol);
                    Log.i("profit price", profit_dollars_s);
                    Log.i("profit percent", profit_percent_s);
                    Log.i("capital_dollars", capital_gain_loss_dollars_s );
                    Log.i(" capital_percent ", capital_gain_loss_percent_s );

                    System.out.println("Hello");

                    // if(S.equals("B"))
                    //{
                    display.add(new Stock(stock_name, stock_symbol, profit_percent_s, profit_dollars_s));
                    System.out.println("Hello");
                    System.out.println(display);

                }

            }


                   /* @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                         textViewResult.setText(t.getMessage());
                    }
                });*/





        }
        cursor.close();

        //  else
        //{
        //  Toast.makeText(capital_gain_loss.this,"No Stocks!", Toast.LENGTH_SHORT).show();
        //}


        mtextViewpercent.setText(capital_gain_loss_percent_s);
        mtextViewDollars.setText(capital_gain_loss_dollars_s);
        mtextViewDollars.requestFocus();
        mtextViewDollars.setTextSize(15);


        if(capital_gain_loss_dollars<0)
        {
            mimageView.setImageResource(R.drawable.economics);
        }

        else
        {
            mimageView.setImageResource(R.drawable.economic);
        }
        capital_gain_loss_adapter adapter = new capital_gain_loss_adapter(this,  R.layout.adapter_capital,display);
        mListView.setAdapter(adapter);

        //capital_gain_loss_adapter adapter_one = new capital_gain_loss_adapter(this,R.layout.activity_capital_gain_loss,stock_perf);
        //textViewResult.setAdapter(adapter_one);
    }
}

