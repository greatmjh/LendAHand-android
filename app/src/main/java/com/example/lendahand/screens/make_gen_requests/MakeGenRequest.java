package com.example.lendahand.screens.make_gen_requests;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.helpers.DataManager;
import com.example.lendahand.helpers.ItemCategory;
import com.example.lendahand.helpers.ItemPickerUI;
import com.example.lendahand.R;
import com.example.lendahand.apiclasses.HighlyRequestedItem;

import java.util.ArrayList;

public class MakeGenRequest extends AppCompatActivity {

    ItemCategory currentlySelectedItem;
    ArrayList<HighlyRequestedItem> displayedItems;
    public void selectItemOnClick(View v)   {
        TextView itemTypeText = findViewById(R.id.itemTypeText);
        ItemPickerUI.chooseFromScreen(this, new ItemPickerUI.ItemPickerCallback() {
            @Override
            public void onComplete(ItemCategory result) {
                itemTypeText.setText(result.getItemName());
                currentlySelectedItem = result;
            }
        });
    }

    public void backOnClick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_gen_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOnMakeGenRequest);

        DataManager.getInstance(this).APIGetMyGeneralRequests(new DataManager.GenRequestCallback() {
            @Override
            public void onSuccess(ArrayList<HighlyRequestedItem> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Set Adapter
                        displayedItems = result;
                        MakeGenRequestAdapter adapter = new MakeGenRequestAdapter(displayedItems);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void makeGenRequestClick(View v) {
        if (currentlySelectedItem == null) {
            Toast.makeText(this, "Please select an item!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //Make sure the item isn't already requested
            for (HighlyRequestedItem item : displayedItems) {
                if (item.getItemId().equals(currentlySelectedItem.getItemID())) {
                    //This is already requested so we don't need to do anything
                    return;
                }
            }

            //Make the request
            DataManager.getInstance(this).APIMakeGeneralRequest(currentlySelectedItem.getItemID(), 1);
            //Update with the item
            displayedItems.add(new HighlyRequestedItem(currentlySelectedItem.getItemName(), 1, currentlySelectedItem.getItemID()));
            RecyclerView recyclerView = findViewById(R.id.recyclerViewOnMakeGenRequest);
            if (recyclerView.getAdapter() != null) {
                recyclerView.getAdapter().notifyItemInserted(displayedItems.size() - 1);
            }
        }
    }
}