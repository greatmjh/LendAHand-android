package com.example.lendahand.screens.top_donors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.R;
import com.example.lendahand.apiclasses.TopDonorItem;

import java.util.List;


public class topDonorsAdapter extends RecyclerView.Adapter<topDonorsAdapter.MyViewHolder> {
    private List<TopDonorItem> itemList;

    public topDonorsAdapter(List<TopDonorItem> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_donor_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TopDonorItem requestItem = itemList.get(position);

        holder.nameText.setText(requestItem.getName());
        holder.donatedText.setText(String.format("Donated %s item%s",
                requestItem.getItemCount(), (requestItem.getItemCount() == 1 ? "" : "s")));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, donatedText;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            nameText = itemView.findViewById(R.id.donorName);
            donatedText = itemView.findViewById(R.id.donated);
        }
    }
}
