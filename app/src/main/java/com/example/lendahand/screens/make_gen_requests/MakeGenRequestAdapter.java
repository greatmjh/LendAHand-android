package com.example.lendahand.screens.make_gen_requests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.helpers.DataManager;
import com.example.lendahand.R;
import com.example.lendahand.apiclasses.HighlyRequestedItem;

import java.util.ArrayList;


public class MakeGenRequestAdapter extends RecyclerView.Adapter<MakeGenRequestAdapter.MyViewHolder> {
    private ArrayList<HighlyRequestedItem> itemList;

    public MakeGenRequestAdapter(ArrayList<HighlyRequestedItem> itemList){
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
        HighlyRequestedItem requestItem = itemList.get(position);

        holder.itemText.setText(requestItem.getItemName());
        holder.deleteButton.setImageResource(R.drawable.delete);

        holder.deleteButton.setOnClickListener(v -> {
            itemList.remove(position);
            //tell the server to delete
            DataManager.getInstance(null).APIMakeGeneralRequest(requestItem.getItemId(), 0);
            MakeGenRequestAdapter.super.notifyItemRemoved(position);
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
