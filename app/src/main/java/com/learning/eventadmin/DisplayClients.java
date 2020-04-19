package com.learning.eventadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayClients extends AppCompatActivity {

    ListView listViewC;
    FirebaseDatabase database;
    DatabaseReference ref;
    List<Clients> clientsList;
    String ide;
    Button buttonBackToEventDetails;
    Events event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        listViewC = findViewById(R.id.listViewC);
        database = FirebaseDatabase.getInstance();
        ref=database.getReference("client");
        clientsList = new ArrayList<>();

        Intent intent =getIntent();
        ide = intent.getStringExtra("eventId");
        event = (Events) intent.getExtras().getSerializable("eventObj");

        buttonBackToEventDetails = findViewById(R.id.buttonBackToEventDetails);

        buttonBackToEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(DisplayClients.this,EventDetails.class);
                intent.putExtra("eventObj",event);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clientsList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    Clients client = ds.getValue(Clients.class);

                    if(ide.equals(client.getEventIDj())) {
                        clientsList.add(client);
                    }
                }

                ClientsAdapter adapter = new ClientsAdapter(DisplayClients.this,clientsList);
                listViewC.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
