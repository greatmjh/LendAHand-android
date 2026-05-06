package com.example.lendahand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class findDonationsSubcategoryAdapter extends RecyclerView.Adapter<findDonationsSubcategoryAdapter.MyViewHolder>{

    private List<String> subcategories;
    private OnSubcategoryClickListener listener;
    private int selectedPosition = -1;

    public interface OnSubcategoryClickListener {
        void onClick(String subcategory);
    }

    public findDonationsSubcategoryAdapter(List<String> subcategories){
        this.subcategories = subcategories;
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
        String filterName = subcategories.get(position);
        holder.textView.setText(filterName);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null){
                selectedPosition = holder.getAbsoluteAdapterPosition();
                notifyDataSetChanged();
                listener.onClick(filterName);
            }
        });
    }

    @Override
    public int getItemCount(){
        return subcategories.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.subcategoryText);
        }
    }


}
