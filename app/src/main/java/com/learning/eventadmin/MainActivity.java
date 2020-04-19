package com.learning.eventadmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button createEvent;
    Button viewEvents;
    EditText eventStartingDate;
    EditText eventEndingDate;
    EditText eventName;
    EditText eventVenue;
    EditText eventOrganiser;
    EditText eventTime;
    EditText eventContactInfo;
    EditText eventTicketPrice;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    Button btnLogout;
    AlertDialog.Builder builder;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createEvent = findViewById(R.id.createEvent);
        viewEvents = findViewById(R.id.viewEvents);
        eventStartingDate = findViewById((R.id.eventDateFrom));
        eventEndingDate = findViewById(R.id.eventDateTo);
        eventName = findViewById(R.id.eventName);
        eventVenue = findViewById(R.id.eventVenue);
        eventOrganiser = findViewById(R.id.eventOrganiser);
        eventTime = findViewById(R.id.eventStartingTime);
        eventContactInfo = findViewById(R.id.eventContactInfo);
        eventTicketPrice = findViewById(R.id.eventPrice);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Events");
        mAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.log);
        builder = new AlertDialog.Builder(this);

        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartDate();
            }

        };
        final DatePickerDialog.OnDateSetListener endingDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndingDate();
            }

        };

        eventStartingDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, startDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        eventEndingDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, endingDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour <= 9 && selectedMinute <= 9){
                            eventTime.setText( "0" + selectedHour + ":" + "0" + selectedMinute);
                        } else if(selectedHour <= 9){
                            eventTime.setText( "0" + selectedHour + ":" + selectedMinute);
                        } else if(selectedMinute <= 9){
                            eventTime.setText( selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            eventTime.setText( selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                FirebaseAuth.getInstance().signOut();
                                Intent intToMain = new Intent(MainActivity.this, Login.class);
                                startActivity(intToMain);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Logout Alert!");
                alert.show();
            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });
        viewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayEvents.class);
                startActivity(intent);
            }
        });

    }

    private void updateStartDate() {
        String myFormat = "dd-MMM-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        eventStartingDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateEndingDate() {
        String myFormat = "dd-MMM-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        eventEndingDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void addEvent() {
        String name = eventName.getText().toString();
        String venue = eventVenue.getText().toString();
        String organiser = eventOrganiser.getText().toString();
        String startDate = eventStartingDate.getText().toString();
        String endDate = eventEndingDate.getText().toString();
        String startTime = eventTime.getText().toString();
        String contactInfo = eventContactInfo.getText().toString();
        String ticketPrice = eventTicketPrice.getText().toString();
        String userUID = Login.User_UID;

        if (name.isEmpty()) {
            eventName.setError("Name is required");
            eventName.requestFocus();
            return;
        }

        if (venue.isEmpty()) {
            eventVenue.setError("Venue is required");
            eventVenue.requestFocus();
            return;
        }

        if (organiser.isEmpty()) {
            eventOrganiser.setError("Organiser Name is required");
            eventOrganiser.requestFocus();
            return;
        }

        if (startDate.isEmpty()) {
            eventStartingDate.setError("Start date is required");
            eventStartingDate.requestFocus();
            return;
        }

        if (endDate.isEmpty()) {
            eventEndingDate.setError("End date is required");
            eventEndingDate.requestFocus();
            return;
        }

        if (startTime.isEmpty()) {
            eventTime.setError("Start Time is required");
            eventTime.requestFocus();
            return;
        }

        if (contactInfo.isEmpty()) {
            eventContactInfo.setError("Contact Info is required");
            eventContactInfo.requestFocus();
            return;
        } else if(!contactInfo.matches("[6-9]{1}[0-9]{9}")){
            eventContactInfo.setError("Phone Number is invalid");
            eventContactInfo.requestFocus();
            return;
        }

        if (ticketPrice.isEmpty()) {
            eventTicketPrice.setError("Price is required");
            eventTicketPrice.requestFocus();
            return;
        } else if (!ticketPrice.matches("[0-9]+.[0-9]{2}")){
            eventTicketPrice.setError("Please! enter price in format '99.99'");
            eventTicketPrice.requestFocus();
            return;
        }

        String id = ref.push().getKey();
        Toast toast = Toast.makeText(MainActivity.this, "Request Sent", Toast.LENGTH_SHORT);
        toast.show();

        Events event = new Events(name, id, organiser, startDate, endDate, startTime, contactInfo, venue, ticketPrice, userUID);
        ref.child(id).setValue(event);
    }

    public void btnDisplay(View view) {
        finish();
        Intent intent = new Intent(MainActivity.this, DisplayEvents.class);
        startActivity(intent);
    }
}

