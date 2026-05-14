package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class changePassword extends AppCompatActivity {

    public void backOnClick(View view) { finish(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onChangePasswordClick(View v) {
        EditText etCurrPass = findViewById(R.id.currPass);
        EditText etNewPass = findViewById(R.id.newPass);
        EditText etConfirmPass = findViewById(R.id.confirmNewPass);
        String currPass = etCurrPass.getText().toString();
        String newPass = etNewPass.getText().toString();
        String confirmPass = etConfirmPass.getText().toString();

        if (newPass.equals(confirmPass)) {
            //Do the password change
            DataManager.getInstance(this).APIChangePassword(currPass, newPass, new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                }
            });
        } else {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }
    }
}