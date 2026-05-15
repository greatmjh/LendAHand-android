package com.example.lendahand.screens.requests_received;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lendahand.helpers.DataManager;
import com.example.lendahand.R;
import com.example.lendahand.apiclasses.IncomingReqItem;

import java.util.UUID;

public class ViewRequestDonor extends AppCompatActivity {

    UUID requestID;
    public void backOnClick (View v)    {
        //Intent intent = new Intent(this, requestsReceived.class);
        //startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_request_donor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //load the request data that was passed by the previous screen
        try {
            IncomingReqItem reqData = getIntent().getExtras().getParcelable("reqData");
            //set all the fields we need to set
            TextView viewRequestSubtitle = findViewById(R.id.viewReqSubtitle);
            TextView aboutDoneeHeader = findViewById(R.id.aboutDoneeHeader);
            TextView doneeBio = findViewById(R.id.doneeBio);
            TextView doneeDistance = findViewById(R.id.doneeDistance);
            TextView doneePhone = findViewById(R.id.doneePhone);

            viewRequestSubtitle.setText(String.format(getString(R.string.view_request_top_line), reqData.getDoneeName(), reqData.getItemName()));
            aboutDoneeHeader.setText((String.format(getString(R.string.view_request_about_donee), reqData.getDoneeName())));
            doneeBio.setText(reqData.getDoneeBio());
            doneeDistance.setText(String.format(getString(R.string.view_request_distance), reqData.getDoneeDistance()));
            doneePhone.setText(reqData.getDoneePhone());

            //Set the request ID so we can respond to it
            requestID = UUID.fromString(reqData.getRequestID());
        } catch (NullPointerException e) {
            Log.e("viewRequestDonor", "Unable to get data from intent");
            //just leave the screen as default if we can't get the data
        }
    }

    public void acceptOnClick(View v) {
        DataManager.getInstance(this).APIRespondToRequest(requestID, true);
        finish();
    }

    public void rejectOnClick(View v) {
        DataManager.getInstance(this).APIRespondToRequest(requestID, false);
        finish();
    }
}