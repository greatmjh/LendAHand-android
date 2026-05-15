package com.example.lendahand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FindDonationsRVAdapter extends RecyclerView.Adapter<FindDonationsRVAdapter.MyViewHolder>{

    ArrayList<RVLevelItem> subcategory;
    private FindDonationsRVAdapter.OnSubcategoryClickListener listener;
    private int selectedPosition = -1;

    private Context context;

    private findDonations parent;

    public interface OnSubcategoryClickListener {
        void onClick(RVLevelItem subcategory);
    }

    public FindDonationsRVAdapter(ArrayList<RVLevelItem> subcategories, Context context, findDonations parent){
        this.subcategory = subcategories;
        this.context = context;
        this.parent = parent;
    }

    public void setOnSubcategoryClickListener(FindDonationsRVAdapter.OnSubcategoryClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public FindDonationsRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context)
                .inflate(R.layout.find_donations_nested_rv, parent, false);
        return new FindDonationsRVAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindDonationsRVAdapter.MyViewHolder holder, int position){
        parent.setInnerRecyclerView(holder.recyclerView);

        RVLevelItem filterName = subcategory.get(position);

        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAbsoluteAdapterPosition();

            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);

            if (listener != null){
                listener.onClick(filterName);
            }
        });


    }

    @Override
    public int getItemCount(){
        return subcategory.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.subcategoryRV);
        }
    }

    public void updateData(ArrayList<RVLevelItem> newList){
        this.subcategory = newList;
        notifyDataSetChanged();
    }

    public void resetPosition(){
        selectedPosition = -1;
        notifyItemChanged(selectedPosition);
    }
}