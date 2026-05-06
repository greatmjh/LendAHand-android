package com.example.lendahand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class makeGenRequestAdapter extends RecyclerView.Adapter<makeGenRequestAdapter.MyViewHolder> {
    private ArrayList<genRequestItem> itemList;

    public makeGenRequestAdapter(ArrayList<genRequestItem> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gen_request_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        genRequestItem requestItem = itemList.get(position);

        holder.itemText.setText(requestItem.getItemName());
        holder.deleteButton.setImageResource(requestItem.getImage());

        holder.deleteButton.setOnClickListener(v -> {
            itemList.remove(position);
            makeGenRequestAdapter.super.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
     public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        ImageButton deleteButton;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            itemText = itemView.findViewById(R.id.itemText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
