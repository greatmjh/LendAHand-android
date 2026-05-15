package com.example.lendahand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class findDonationsSubcategoryAdapter extends RecyclerView.Adapter<findDonationsSubcategoryAdapter.MyViewHolder>{

    private ItemCategory[] subcategories;
    private OnSubcategoryClickListener listener;
    private int selectedPosition = -1;

    int parentPosition;

    public interface OnSubcategoryClickListener {
        void onClick(ItemCategory subcategory, int parentPosition);
    }

    public findDonationsSubcategoryAdapter(ItemCategory[] subcategories, int parentPosition){
        this.subcategories = subcategories;
        this.parentPosition = parentPosition;

    }

    public void setOnSubcategoryClickListener(OnSubcategoryClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public findDonationsSubcategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.find_donations_subcategory_chip, parent, false);
        return new findDonationsSubcategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull findDonationsSubcategoryAdapter.MyViewHolder holder, int position){
        ItemCategory filterName = subcategories[position];
        holder.textView.setText(filterName.getItemName());

        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAbsoluteAdapterPosition();

            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);

            if (listener != null){
                listener.onClick(filterName, parentPosition);
            }
        });

        if (position == selectedPosition) {
            holder.textView.setBackgroundResource(R.drawable.bg_subcategory_onclick);
        } else {
            holder.textView.setBackgroundResource(R.drawable.bg_subcategory);
        }
    }

    @Override
    public int getItemCount(){
        return subcategories.length;
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.subcategoryText);
        }
    }

    public void updateData(ItemCategory[] newList){
        this.subcategories = newList;
        notifyDataSetChanged();
    }

    public void resetPosition(){
        selectedPosition = -1;
        notifyItemChanged(selectedPosition);
    }

    public void updateItemList(ItemCategory subcategory){
        //TODO: based on what is clicked, update itemlist recyclerview

    }
}
