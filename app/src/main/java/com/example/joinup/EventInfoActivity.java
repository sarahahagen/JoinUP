package com.example.joinup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class EventInfoActivity extends AppCompatActivity {
    // TODO: add "back" button
    final static String TAG = "EventInfoActivity";
    private TextView titleTextView;
    private TextView eventDetailsTextView;
    private TextView numberOfPeopleAttending;

    FirebaseAuth mAuth =  FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        // show the up arrow, aka the "back button"
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        Intent signInIntent = new Intent(this, GoogleSSO.class);
        startActivity(signInIntent);
        if (mAuth.getCurrentUser() == null) {
            startActivity(signInIntent);
        }

        // get the Views
        titleTextView = findViewById(R.id.titleTextViewFinal);
        eventDetailsTextView = findViewById(R.id.eventDetailsTextViewFinal);
        numberOfPeopleAttending = findViewById(R.id.numberOfPeopleTextView);

        // get an Intent if there is one
        Intent intent = getIntent();
        if (intent != null) {
            // retrieve the title and set the titleTextView to the title
            String title = intent.getStringExtra("title");
            titleTextView.setText(title);

            // retrieve the event details and set the eventDetailsTextView to the infoStr
            String infoStr = intent.getStringExtra("info");
            eventDetailsTextView.setText(infoStr);

            // retrieve the number of people attending and set the numberOfPeopleAttending to the numberOfPeopleStr
            int numberOfPeopleStr = intent.getIntExtra("number", 0);
            numberOfPeopleAttending.setText("Number of students attending: " + numberOfPeopleStr);

            if (mAuth.getCurrentUser().getEmail().equals(intent.getStringExtra("coordinator"))) {
                Log.d("MainActivityTag", "Current user is coordinator");
                ArrayList<String> attendees = intent.getStringArrayListExtra("attendees");
                for (String s : attendees) {
                    Log.d("MainActivityTag", s);
                }
                ListView emailList =  (ListView) findViewById(R.id.attendeeList);
                ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.email_list, attendees);

                emailList.setAdapter(adapter);
            }
        }
    }

    // adds the menu bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // returns to MainActivity without saving any information
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}