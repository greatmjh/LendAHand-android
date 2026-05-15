package com.example.lendahand.screens.find_donations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.R;

import java.util.ArrayList;

public class FindDonationsRVAdapter extends RecyclerView.Adapter<FindDonationsRVAdapter.MyViewHolder>{

    ArrayList<RVLevelItem> subcategory;
    private FindDonationsRVAdapter.OnSubcategoryClickListener listener;
    private int selectedPosition = -1;

    private final Context context;

    private final FindDonations parent;

    public interface OnSubcategoryClickListener {
        void onClick(RVLevelItem subcategory, int selectedPosition);
    }

    public FindDonationsRVAdapter(ArrayList<RVLevelItem> subcategories, Context context, FindDonations parent){
        this.subcategory = subcategories;
        this.context = context;
        this.parent = parent;
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
        parent.setInnerRecyclerView(holder.recyclerView, position);

        RVLevelItem filterName = subcategory.get(position);

        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAbsoluteAdapterPosition();

            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);

            if (listener != null){
                listener.onClick(filterName, selectedPosition);
            }
        });


    }

    @Override
    public int getItemCount(){
        return subcategory.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.subcategoryRV);
        }
    }

    public void updateData(ArrayList<RVLevelItem> newList){
        this.subcategory = newList;
        notifyDataSetChanged();
    }

}