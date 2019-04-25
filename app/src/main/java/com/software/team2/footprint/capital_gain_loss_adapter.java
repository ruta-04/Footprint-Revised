package com.software.team2.footprint;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class capital_gain_loss_adapter extends ArrayAdapter<Stock> {

    private static final String TAG = "capital_gain_loss_adapter";
    public static final float capital_gain=0;

    private Context mContext;
    int mResource;


    public capital_gain_loss_adapter(Context context, int resource, ArrayList<Stock> objects){

        super(context,resource,objects);
        mContext = context;
        mResource =resource;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        String stock_name = getItem(position).getName();
        String stock_symbol = getItem(position).getSymbol();
        String stock_price = getItem(position).getPrice();
        String stock_change = getItem(position).getChange();


        Stock stock = new Stock(stock_name,stock_symbol,stock_price,stock_change);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);



        TextView tvName = (TextView) convertView.findViewById(R.id.stock_name_g);
        TextView tvsymbol = (TextView) convertView.findViewById(R.id.stock_symbol_g);
        TextView tvprofit_percent = (TextView) convertView.findViewById(R.id.profit_percent_g);
        TextView tvprofit_dollars = (TextView) convertView.findViewById(R.id.profit_dollars_g);



        tvName.setText(stock_name);
        tvsymbol.setText(stock_symbol);
        tvprofit_percent.setText(stock_price);
        tvprofit_dollars.setText(stock_change);

        if(Float.valueOf(stock_price)<0.0)
        {
            tvprofit_percent.setTextColor(Color.rgb(255,0,0));
        }

        else if(Float.valueOf(stock_price)>0.0)
        {
            tvprofit_percent.setTextColor(Color.rgb(0,255,0));
        }
        tvprofit_dollars.setText(stock_change);
        if(Float.valueOf(stock_change)<0.0)
        {
            tvprofit_dollars.setTextColor(Color.rgb(255,0,0));
        }

        else if(Float.valueOf(stock_change)>0.0)
        {
            tvprofit_dollars.setTextColor(Color.rgb(0,255,0));
        }


        notifyDataSetChanged();
        return convertView;
    }
}

