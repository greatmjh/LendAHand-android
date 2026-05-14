package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class makeDonation extends AppCompatActivity {

    int quantity = 1;
    ItemCategory pickedCategory;
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
        Button itemTypeButton = findViewById(R.id.itemTypeButton);
        ItemPickerUI.chooseFromScreen(this, new ItemPickerUI.ItemPickerCallback() {
            @Override
            public void onComplete(ItemCategory result) {
                pickedCategory = result;
                itemTypeButton.setText(result.getItemName());
            }
        });
    }

    public void AddDonationOnClick(View v) {
        if (pickedCategory == null) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
        } else {
            EditText itemNameEntry = findViewById(R.id.itemNameEntry);
            String itemName = itemNameEntry.getText().toString();
            DataManager.getInstance(this).APIMakeDonation(pickedCategory.getItemID(), itemName, quantity);
            finish();
        }
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