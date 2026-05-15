package com.example.lendahand.screens.notifications;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.helpers.DataManager;
import com.example.lendahand.R;
import com.example.lendahand.apiclasses.NotificationItem;
import com.example.lendahand.screens.requests_received.RequestsReceived;
import com.example.lendahand.screens.manage_requests.ManageRequests;

import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private final List<NotificationItem> itemList;

    public NotificationAdapter(List<NotificationItem> itemList){
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
        NotificationItem notifItem = itemList.get(position);

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

        //give holder a reference to actual notification item
        holder.item = notifItem;

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView notificationTime;
        final TextView notificationTitle;
        final TextView notificationBody;

        NotificationItem item;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            notificationTime = itemView.findViewById(R.id.notificationTime);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationBody = itemView.findViewById(R.id.notificationBody);

            itemView.findViewById(R.id.notif_cardview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //mark this notification as read
                    DataManager.getInstance(null).APIMarkAsRead(item.getUuid());
                    item.read = true;
                    switch (item.getOnclick()) {
                        case "outgoingRequests":
                            Intent intentOut = new Intent(view.getContext(), ManageRequests.class);
                            view.getContext().startActivity(intentOut);
                            break;

                        case "incomingRequests":
                            Intent intentIn = new Intent(view.getContext(), RequestsReceived.class);
                            view.getContext().startActivity(intentIn);
                            break;
                    }
                }
            });
        }
    }
}
