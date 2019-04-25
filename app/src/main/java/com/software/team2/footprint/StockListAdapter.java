package com.software.team2.footprint;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class StockListAdapter extends ArrayAdapter<Stock>{

    private static final String TAG ="StockListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView name;
        TextView symbol;
        TextView price;
        TextView change;
    }

    public StockListAdapter(Context context, int resource, ArrayList<Stock> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getName();
        String symbol = getItem(position).getSymbol();
        String price = getItem(position).getPrice();
        String change = getItem(position).getChange();

        //Create the person object with the information
        Stock stock = new Stock(name, symbol, price, change);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewSymbol);
        TextView tvSymbol = (TextView) convertView.findViewById(R.id.textViewCompany);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
        TextView tvChange = (TextView) convertView.findViewById(R.id.textViewChange);

        tvName.setText(name);
        tvSymbol.setText(symbol);
        tvPrice.setText(price);
        tvChange.setText(change);

        return convertView;




   /*     holder= new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView1);
            holder.symbol = (TextView) convertView.findViewById(R.id.textView2);
            holder.price = (TextView) convertView.findViewById(R.id.textView3);
            holder.change = (TextView) convertView.findViewById(R.id.textView3);

            result = convertView;

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

    */}
}

