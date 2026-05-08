package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class topDonors extends AppCompatActivity {

    public void menuBtnClick(View v){
        previousView.setPrevView(topDonors.class);

        Intent intent = new Intent(this, menuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_donors);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTopDonors);
        //Load items from server
        DataManager.getInstance(this).APIGetTopDonors(new DataManager.TopDonorsCallback() {
            @Override
            public void onSuccess(List<topDonorItem> items) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //set adapter
                        topDonorsAdapter adapter = new topDonorsAdapter(items);
                        recyclerView.setAdapter(adapter);
                    }
                });

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }

}