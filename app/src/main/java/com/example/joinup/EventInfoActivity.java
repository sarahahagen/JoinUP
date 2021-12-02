package com.example.joinup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EventInfoActivity extends AppCompatActivity {
    // TODO: add "back" button
    final static String TAG = "EventInfoActivity";
    private TextView titleTextView;
    private ImageView imageView;
    private TextView eventDetailsTextView;
    private TextView numberOfPeopleAttending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        // get the Views
        titleTextView = findViewById(R.id.finalTitleTextView);
        eventDetailsTextView = findViewById(R.id.finalDetailsTextView);
        numberOfPeopleAttending = findViewById(R.id.numberOfPeopleAttendingTextView);
        //image = findViewById(R.id.imageView);

        // get an Intent if there is one
        Intent intent = getIntent();
        if (intent != null) {
            // retrieve the title and set the titleTextView to the title
            String title = intent.getStringExtra("title");
            titleTextView.setText(title);

            // retrieve the title and set the eventDetailsTextView to the infoStr
            String infoStr = intent.getStringExtra("info");
            eventDetailsTextView.setText(infoStr);

            // retrieve the number of people attending and set the numberOfPeopleAttending to the numberOfPeopleStr
            String numberOfPeopleStr = intent.getStringExtra("number");
            numberOfPeopleAttending.setText(numberOfPeopleStr);

            // retrieve the image ID and set the image view to the image from drawable
            //int imageID = intent.getIntExtra("imageID", 0);
            //if (imageID != 0) {
              //  image.setImageResource(imageID);
            //}
        }
    }
}