package com.software.team2.footprint;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchExampleAdapter extends RecyclerView.Adapter<SearchExampleAdapter.SearchExampleViewHolder>{

    private ArrayList<Stock> mSearchExampleList;

    public static class SearchExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;

        public SearchExampleViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.text_ticker_symbol);
            mTextView2 = itemView.findViewById(R.id.text_company_name);
        }
    }

    public SearchExampleAdapter(ArrayList<Stock> exampleList){
        mSearchExampleList = exampleList;
    }

    @NonNull
    @Override
    public SearchExampleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item,viewGroup,false);
        SearchExampleViewHolder evh = new SearchExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(SearchExampleAdapter.SearchExampleViewHolder exampleViewHolder, int i) {

        Stock currStock = mSearchExampleList.get(i);
        exampleViewHolder.mTextView1.setText(currStock.getSymbol());
        exampleViewHolder.mTextView2.setText(currStock.getName());
    }

    @Override
    public int getItemCount() {
        return mSearchExampleList.size();
    }
}
