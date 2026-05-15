package com.example.lendahand.screens.requests_received;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.R;
import com.example.lendahand.apiclasses.IncomingReqItem;

import java.util.List;

public class RequestsRecievedAdapter extends RecyclerView.Adapter<RequestsRecievedAdapter.MyViewHolder> {
    private List<IncomingReqItem> itemList;

    public void setItemList(List<IncomingReqItem> itemList) {
        this.itemList = itemList;
    }

    public RequestsRecievedAdapter(List<IncomingReqItem> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RequestsRecievedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donation_request_received_card, parent, false);
        return new RequestsRecievedAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsRecievedAdapter.MyViewHolder holder, int position) {
        IncomingReqItem requestItem = itemList.get(position);
        holder.mainText.setText(String.format("%s (%d)", requestItem.getItemName(), requestItem.getItemQty()));
        holder.subText.setText(requestItem.getSubtext());
        if (requestItem.isOpen()) {
            holder.threeButtons.setVisibility(VISIBLE);
        } else {
            holder.threeButtons.setVisibility(INVISIBLE);
        }
        holder.threeButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open the view request screen
                Intent intent = new Intent(v.getContext(), ViewRequestDonor.class);
                //send this incoming request class to the screen
                intent.putExtra("reqData", requestItem);
                v.getContext().startActivity(intent);
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
        ImageButton threeButtons;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            mainText = itemView.findViewById(R.id.mainText);
            subText = itemView.findViewById(R.id.subText);
            threeButtons = itemView.findViewById(R.id.threeButtons);
        }
    }
}
