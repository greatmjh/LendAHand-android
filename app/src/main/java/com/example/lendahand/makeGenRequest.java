package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class makeGenRequest extends AppCompatActivity {

    public void selectItemOnClick(View v)   {
        TextView itemTypeText = findViewById(R.id.itemTypeText);
        ItemPickerUI.chooseFromScreen(this, new ItemPickerUI.ItemPickerCallback() {
            @Override
            public void onComplete(ItemCategory result) {
                itemTypeText.setText(result.getItemName());
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
        ArrayList<genRequestItem> itemList = new ArrayList<>();

        //sample data
        itemList.add(new genRequestItem("Baked beans"));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set Adapter
        makeGenRequestAdapter adapter = new makeGenRequestAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }
}