package com.learning.eventadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventDetails extends AppCompatActivity {

    Events event;
    private DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Events");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        final Intent intent =getIntent();

        event = (Events) intent.getExtras().getSerializable("eventObj");

        TextView nameD = findViewById(R.id.textViewNameD);
        TextView organiserD = findViewById(R.id.textViewOrganiserD);
        final TextView venueD = findViewById(R.id.textViewVenueD);
        TextView startDateD = findViewById(R.id.textViewStartDateD);
        TextView startTimeD = findViewById(R.id.textViewStartTimeD);
        TextView endDateD = findViewById(R.id.textViewEndDateD);
        TextView phoneD = findViewById(R.id.textViewPhoneD);
        final TextView statusD = findViewById(R.id.textViewStatusD);
        TextView priceD = findViewById(R.id.textViewTicketPriceD);
        Button buttonRemove = findViewById(R.id.buttonRemove);
        Button buttonToClientList = findViewById(R.id.buttonClientList);
        Button buttonBackToDisplay = findViewById(R.id.buttonBackToDisplay);

        nameD.setText(event.getName());
        organiserD.setText(event.getOrganiser());
        venueD.setText(event.getVenue());
        startDateD.setText(event.getStartDate());
        startTimeD.setText(event.getStartTime());
        endDateD.setText(event.getEndDate());
        phoneD.setText(event.getContactInfo());
        statusD.setText(event.getStatus());
        priceD.setText(event.getTicketPrice());


        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(event.getId()).removeValue();
                Intent intent = new Intent(EventDetails.this, DisplayEvents.class);
                startActivity(intent);
            }
        });

        buttonToClientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetails.this, DisplayClients.class);
                intent.putExtra("eventId",event.getId());
                intent.putExtra("eventObj",event);
                startActivity(intent);
            }
        });

        buttonBackToDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(EventDetails.this, DisplayEvents.class);
                startActivity(intent);
            }
        });
    }
}
