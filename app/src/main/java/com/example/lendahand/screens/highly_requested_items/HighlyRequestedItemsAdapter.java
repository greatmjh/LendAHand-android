package com.example.lendahand.screens.highly_requested_items;

import static android.view.View.INVISIBLE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.R;
import com.example.lendahand.apiclasses.HighlyRequestedItem;

import java.util.ArrayList;



public class HighlyRequestedItemsAdapter extends RecyclerView.Adapter<HighlyRequestedItemsAdapter.MyViewHolder>{
    private final ArrayList<HighlyRequestedItem> itemList;

    public HighlyRequestedItemsAdapter(ArrayList<HighlyRequestedItem> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public HighlyRequestedItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_my_donations_item, parent, false);
        return new HighlyRequestedItemsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlyRequestedItemsAdapter.MyViewHolder holder, int position) {
        HighlyRequestedItem reqItem = itemList.get(position);

        holder.nameText.setText(reqItem.getItemName());
        holder.availableText.setText(String.format("%s needed", reqItem.getQtyNeeded()));
        holder.deleteButton.setVisibility(INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView nameText;
        final TextView availableText;

        final ImageButton deleteButton;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            nameText = itemView.findViewById(R.id.itemName);
            availableText = itemView.findViewById(R.id.numAvailable);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
