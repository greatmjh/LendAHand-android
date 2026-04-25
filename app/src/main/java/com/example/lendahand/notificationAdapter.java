package com.example.lendahand;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.MyViewHolder> {
    private ArrayList<Notification> itemList;

    public notificationAdapter(ArrayList<Notification> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notification notifItem = itemList.get(position);

        // handle bolding for unread
        if (notifItem.isRead()) {
            holder.notificationBody.setTypeface(Typeface.DEFAULT);
            holder.notificationTitle.setTypeface(Typeface.DEFAULT);
            holder.notificationTime.setTypeface(Typeface.DEFAULT);
        } else {
            holder.notificationBody.setTypeface(Typeface.DEFAULT_BOLD);
            holder.notificationTitle.setTypeface(Typeface.DEFAULT_BOLD);
            holder.notificationTime.setTypeface(Typeface.DEFAULT_BOLD);
        }

        //set text
        holder.notificationBody.setText(notifItem.getBody());
        holder.notificationTitle.setText(notifItem.getTitle());
        holder.notificationTime.setText(notifItem.getRelativeTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView notificationTime, notificationTitle, notificationBody;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            notificationTime = itemView.findViewById(R.id.notificationTime);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationBody = itemView.findViewById(R.id.notificationBody);
        }
    }
}
