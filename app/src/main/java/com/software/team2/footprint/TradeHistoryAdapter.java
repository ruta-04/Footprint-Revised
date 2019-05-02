package com.software.team2.footprint;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class TradeHistoryAdapter extends RecyclerView.Adapter<com.software.team2.footprint.TradeHistoryAdapter.itemViewHolder> implements Filterable {


    ArrayList<TradeHistoryItem> tradeList = new ArrayList<>();
    private List<TradeHistoryItem> tradeListFull;

    class itemViewHolder extends RecyclerView.ViewHolder {

        TextView symbolTextView;
        TextView companyTextView;
        TextView transcationTypeTextView;
        TextView sPriceTextView;
        TextView bPriceTextView;
        TextView netlTextView;
        TextView netgTextView;
        TextView sharesTextView;
        TextView dateTextView;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            symbolTextView = itemView.findViewById(R.id.tsymbol);
            companyTextView = itemView.findViewById(R.id.tcompany);
            transcationTypeTextView = itemView.findViewById(R.id.ttransactiontype);
            sPriceTextView = itemView.findViewById(R.id.tsprice);
            bPriceTextView = itemView.findViewById(R.id.tbprice);
            netlTextView = itemView.findViewById(R.id.netl);
            netgTextView = itemView.findViewById(R.id.netg);
            sharesTextView = itemView.findViewById(R.id.tshares);
            dateTextView = itemView.findViewById(R.id.tdate);
        }
    }

    public TradeHistoryAdapter(ArrayList<TradeHistoryItem> tradeList) {
        this.tradeList = tradeList;
        tradeListFull = new ArrayList<>(tradeList);
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trade_item,
                viewGroup, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder itemViewHolder, int i) {
        TradeHistoryItem currentItem = tradeList.get(i);

        itemViewHolder.symbolTextView.setText(currentItem.getSymbol());
        itemViewHolder.companyTextView.setText(currentItem.getCompany());
        itemViewHolder.sharesTextView.setText("No of shares: " + Integer.toString(currentItem.getShares()));
        itemViewHolder.dateTextView.setText(currentItem.getDate());
        if((currentItem.getTransactionType()).compareToIgnoreCase("B") == 0)
        {
            itemViewHolder.transcationTypeTextView.setText("Bought on:");
            itemViewHolder.bPriceTextView.setText("Purchase Price: $" + Float.toString(currentItem.getPrice()));
            //itemViewHolder.sPriceTextView.setText(Float.toString(currentItem.getSprice()));

        }
        else if((currentItem.getTransactionType()).compareToIgnoreCase("S") == 0)
        {
            itemViewHolder.transcationTypeTextView.setText("Sold on:");
            itemViewHolder.sPriceTextView.setVisibility(View.VISIBLE);
            itemViewHolder.bPriceTextView.setText("Purchase Price: $" + Float.toString(currentItem.getEachPrice()));
            itemViewHolder.sPriceTextView.setText("Sell Price: $" + Float.toString(currentItem.getPrice()));
            float netPrice = 0;
            if(currentItem.getShares() == 0)
            {
                float shares = currentItem.getTotalPrice()/currentItem.getPrice();
                netPrice = shares * (currentItem.getPrice() - currentItem.getEachPrice());
            }
            else
            {
                netPrice = currentItem.getShares() * (currentItem.getPrice() - currentItem.getEachPrice());
            }


            if(netPrice > 0)
            {
                itemViewHolder.netgTextView.setVisibility(View.VISIBLE);
                itemViewHolder.netgTextView.setText("$"+Float.toString(netPrice));

            }
            else
            {
                itemViewHolder.netlTextView.setVisibility(View.VISIBLE);
                itemViewHolder.netlTextView.setText("$"+Float.toString(netPrice));
            }


        }


    }

    @Override
    public int getItemCount() {
        return tradeList.size();
    }

    @Override
    public Filter getFilter() {
        return tradeFilter;
    }

    private Filter tradeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TradeHistoryItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(tradeListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TradeHistoryItem item : tradeListFull) {
                    if (item.getCompany().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    else if((item.getSymbol().toLowerCase().contains(filterPattern)))
                    {
                        filteredList.add(item);
                    }
                    else if((item.getDate().toLowerCase().contains(filterPattern)))
                    {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            tradeList.clear();
            tradeList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };
}
