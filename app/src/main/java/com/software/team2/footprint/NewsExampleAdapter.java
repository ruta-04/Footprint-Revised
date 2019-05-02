package com.software.team2.footprint;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsExampleAdapter extends RecyclerView.Adapter<NewsExampleAdapter.NewsExampleViewHolder>{

    private ArrayList<Stock> mNewsExampleList;

    public static class NewsExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;

        public NewsExampleViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.text_date_time);
            mTextView2 = itemView.findViewById(R.id.text_headline);
            mTextView3 = itemView.findViewById(R.id.text_summary);
            mTextView4 = itemView.findViewById(R.id.text_url);
        }
    }

    public NewsExampleAdapter(ArrayList<Stock> exampleList){
        mNewsExampleList = exampleList;
    }

    @NonNull
    @Override
    public NewsExampleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item,viewGroup,false);
        NewsExampleViewHolder evh = new NewsExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(NewsExampleAdapter.NewsExampleViewHolder exampleViewHolder, int i) {

        Stock currStock = mNewsExampleList.get(i);
        exampleViewHolder.mTextView1.setText(currStock.getName());
        exampleViewHolder.mTextView2.setText(currStock.getSymbol());
        exampleViewHolder.mTextView3.setText(currStock.getPrice());
        exampleViewHolder.mTextView4.setText(currStock.getChange());

    }

    @Override
    public int getItemCount() {
        return mNewsExampleList.size();
    }
}
