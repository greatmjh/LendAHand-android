package com.example.lendahand;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class ManageRequestAdapter extends RecyclerView.Adapter<ManageRequestAdapter.MyViewHolder> {
    private List<OutgoingReqItem> itemList;

    public void setItemList(List<OutgoingReqItem> itemList) {
        this.itemList = itemList;
    }

    public ManageRequestAdapter(List<OutgoingReqItem> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donation_request_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OutgoingReqItem requestItem = itemList.get(position);
        holder.mainText.setText(requestItem.getItemName());
        holder.subText.setText(requestItem.getSubtext());
        if (requestItem.isOpen()) {
            holder.deleteButton.setVisibility(VISIBLE);
        } else {
            holder.deleteButton.setVisibility(INVISIBLE);
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.remove(position);
                ManageRequestAdapter.super.notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mainText, subText;
        ImageButton deleteButton;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            mainText = itemView.findViewById(R.id.mainText);
            subText = itemView.findViewById(R.id.subText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
