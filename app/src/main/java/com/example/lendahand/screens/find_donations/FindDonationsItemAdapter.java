package com.example.lendahand.screens.find_donations;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.R;
import com.example.lendahand.apiclasses.DonationOffer;

import java.util.ArrayList;


public class FindDonationsItemAdapter extends RecyclerView.Adapter<FindDonationsItemAdapter.MyViewHolder> {
    private ArrayList<DonationOffer> itemList;

    public FindDonationsItemAdapter(ArrayList<DonationOffer> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.find_donations_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonationOffer donationItem = itemList.get(position);

        holder.itemText.setText(donationItem.getItemName());
        holder.numText.setText(String.format("%s units", donationItem.getQty()));
        holder.distText.setText(String.format("%.1f km away", donationItem.getDistanceKm()));
        holder.donationItem = donationItem;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView itemText;
        final TextView numText;
        final TextView distText;
        DonationOffer donationItem;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            itemText = itemView.findViewById(R.id.itemNameText);
            numText = itemView.findViewById(R.id.numUnitsText);
            distText = itemView.findViewById(R.id.distText);
            itemView.findViewById(R.id.find_donation_cardview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MakeRequestPopup.class);
                    intent.putExtra("donorName", donationItem.getDonorName());
                    intent.putExtra("offerID", donationItem.getOfferID().toString());
                    intent.putExtra("itemName", donationItem.getItemName());
                    intent.putExtra("distance", donationItem.getDistanceKm());
                    intent.putExtra("qtyMaximum", donationItem.getQty());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public void updateData(ArrayList<DonationOffer> newList){
        this.itemList = newList;
        notifyDataSetChanged();
    }
}