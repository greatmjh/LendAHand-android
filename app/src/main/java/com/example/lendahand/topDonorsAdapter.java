package com.example.lendahand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class topDonorsAdapter extends RecyclerView.Adapter<topDonorsAdapter.MyViewHolder> {
    private ArrayList<topDonorItem> itemList;

    public topDonorsAdapter(ArrayList<topDonorItem> itemList){
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
        topDonorItem requestItem = itemList.get(position);

        holder.nameText.setText(requestItem.getUserName());
        holder.donatedText.setText(String.format("Donated %s item%s",
                requestItem.getNumItemsDonated(), (requestItem.getNumItemsDonated() == 1 ? "" : "s")));
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
