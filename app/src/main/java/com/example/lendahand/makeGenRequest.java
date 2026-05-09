package com.example.lendahand;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.UUID;

public class makeGenRequest extends AppCompatActivity {

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
        Intent intent = new Intent(this, manageRequests.class);
        startActivity(intent);
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
                        makeGenRequestAdapter adapter = new makeGenRequestAdapter(displayedItems);
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
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}