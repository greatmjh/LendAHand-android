package com.example.lendahand.screens.top_donors;

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

import com.example.lendahand.helpers.DataManager;
import com.example.lendahand.R;
import com.example.lendahand.apiclasses.TopDonorItem;
import com.example.lendahand.helpers.PreviousView;
import com.example.lendahand.screens.MenuActivity;
import com.example.lendahand.screens.ViewProfile;

import java.util.List;

public class TopDonors extends AppCompatActivity {

    public void menuBtnClick(View v){
        PreviousView.setPrevView(TopDonors.class);

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void viewProfileOnClick(View v){
        Intent intent = new Intent(this, ViewProfile.class);
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
        TextView txtStatus = findViewById(R.id.topDonorsStatus);
        //Load items from server
        DataManager.getInstance(this).APIGetTopDonors(new DataManager.TopDonorsCallback() {
            @Override
            public void onSuccess(List<TopDonorItem> items) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!items.isEmpty()) {
                            //set adapter
                            TopDonorsAdapter adapter = new TopDonorsAdapter(items);
                            recyclerView.setAdapter(adapter);
                            txtStatus.setVisibility(View.GONE);
                        } else {
                            txtStatus.setText("There are no top donors yet.");
                        }

                    }
                });

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }

}