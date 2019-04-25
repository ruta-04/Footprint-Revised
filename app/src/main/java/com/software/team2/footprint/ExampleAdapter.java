package com.software.team2.footprint;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<Stock> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.recycler_stock_symbol);
            mTextView2 = itemView.findViewById(R.id.recycler_stock_name);
            mTextView3 = itemView.findViewById(R.id.recycler_stock_price);
            mTextView4 = itemView.findViewById(R.id.recycler_stock_change);
        }
    }

    public ExampleAdapter(ArrayList<Stock> exampleList){
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_item,viewGroup,false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder exampleViewHolder, int i) {

        Stock currStock = mExampleList.get(i);
        exampleViewHolder.mTextView1.setText(currStock.getSymbol());
        exampleViewHolder.mTextView2.setText(currStock.getName());
        exampleViewHolder.mTextView3.setText(currStock.getPrice());
        exampleViewHolder.mTextView4.setText(currStock.getChange());


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
