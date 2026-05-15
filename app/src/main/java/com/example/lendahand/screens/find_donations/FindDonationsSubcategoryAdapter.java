package com.example.lendahand.screens.find_donations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.helpers.ItemCategory;
import com.example.lendahand.R;

public class FindDonationsSubcategoryAdapter extends RecyclerView.Adapter<FindDonationsSubcategoryAdapter.MyViewHolder>{

    private ItemCategory[] subcategories;
    private OnSubcategoryClickListener listener;
    private int selectedPosition = -1;

    final int parentPosition;

    public interface OnSubcategoryClickListener {
        void onClick(ItemCategory subcategory, int parentPosition);
    }

    public FindDonationsSubcategoryAdapter(ItemCategory[] subcategories, int parentPosition){
        this.subcategories = subcategories;
        this.parentPosition = parentPosition;

    }

    public void setOnSubcategoryClickListener(OnSubcategoryClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public FindDonationsSubcategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.find_donations_subcategory_chip, parent, false);
        return new FindDonationsSubcategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindDonationsSubcategoryAdapter.MyViewHolder holder, int position){
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
        final TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.subcategoryText);
        }
    }

}
