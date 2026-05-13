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

public class makeDonation extends AppCompatActivity {

    int quantity = 1;
    public void backOnClick(View v) {
        previousView.setPrevView(makeDonation.class);

        Intent intent = new Intent(this, manageMyDonations.class);
        startActivity(intent);
    }

    public void plusOnClick(View v){
        quantity ++;
        TextView quantityText = findViewById(R.id.quantityValText);
        quantityText.setText(Integer.toString(quantity));
    }

    public void minusOnClick(View v){
        if (quantity > 1) {
            quantity --;
            TextView quantityText = findViewById(R.id.quantityValText);
            quantityText.setText(Integer.toString(quantity));
        }
    }

    public void selectItemOnClick(View v)   {
        TextView itemTypeText = findViewById(R.id.itemTypeText);
        ItemPickerUI.chooseFromScreen(this, new ItemPickerUI.ItemPickerCallback() {
            @Override
            public void onComplete(ItemCategory result) {
                itemTypeText.setText(result.getItemName());
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_donation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView quantityText = findViewById(R.id.quantityValText);
        quantityText.setText(Integer.toString(quantity));
    }
}