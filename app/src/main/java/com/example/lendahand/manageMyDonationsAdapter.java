package com.example.lendahand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class manageMyDonationsAdapter extends RecyclerView.Adapter<manageMyDonationsAdapter.MyViewHolder>{
    private ArrayList<manageMyDonationsItem> itemList;

    public manageMyDonationsAdapter(ArrayList<manageMyDonationsItem> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public manageMyDonationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_my_donations_item, parent, false);
        return new manageMyDonationsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull manageMyDonationsAdapter.MyViewHolder holder, int position) {
        manageMyDonationsItem donationItem = itemList.get(position);

        holder.nameText.setText(donationItem.getItemDesc());
        holder.availableText.setText(String.format("%s unit%s available",
                donationItem.getQty(), (donationItem.getQty() == 1 ? "" : "s")));

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.remove(holder.getBindingAdapterPosition());
                manageMyDonationsAdapter.super.notifyItemRemoved(holder.getBindingAdapterPosition());
                //delete server side
                DataManager.getInstance(v.getContext()).APIEditDonationQty(donationItem.getOfferID(), 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, availableText;
        ImageButton deleteButton;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            nameText = itemView.findViewById(R.id.itemName);
            availableText = itemView.findViewById(R.id.numAvailable);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}
