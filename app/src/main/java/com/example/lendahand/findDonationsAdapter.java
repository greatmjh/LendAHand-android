package com.example.lendahand;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class findDonationsAdapter extends RecyclerView.Adapter<findDonationsAdapter.MyViewHolder> {
    private ArrayList<findDonationsItem> itemList;

    public findDonationsAdapter(ArrayList<findDonationsItem> itemList){
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
        findDonationsItem donationItem = itemList.get(position);

        holder.itemText.setText(donationItem.getItemName());
        holder.numText.setText(String.format("%s unit%s", donationItem.getNumUnits(), (donationItem.getNumUnits() == 1 ? "" : "s")));
        holder.distText.setText(String.format("%s km away", donationItem.getDistance()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemText, numText, distText;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            itemText = itemView.findViewById(R.id.itemNameText);
            numText = itemView.findViewById(R.id.numUnitsText);
            distText = itemView.findViewById(R.id.distText);
        }
    }

    public void updateData(ArrayList<findDonationsItem> newList){
        this.itemList = newList;
        notifyDataSetChanged();
    }
}
