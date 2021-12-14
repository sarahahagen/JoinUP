package com.example.joinup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class EventDetailActivity extends AppCompatActivity {
    final static String TAG = "EventDetailActivity";
    private EditText titleEditText;
    private EditText imageDescriptionEditText;
    private EditText eventDetailsEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // show the up arrow, aka the "back button"
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // get the Views
        titleEditText = findViewById(R.id.titleEditTextMultiLine);
        eventDetailsEditText = findViewById(R.id.detailsEditTextMultiLine);
        imageDescriptionEditText = findViewById(R.id.imageDescriptionEditText);

        // send the Views off back to MainActivity
        // working with the save button
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            /**
             Returns to MainActivity with necessary video information, if the title is not empty.
             *
             * Pre-conditions: None
             * Post-conditions: updates the RecyclerView.
             * @param view view is the button
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (!titleEditText.getText().toString().equals("")) {
                    intent.putExtra("title", titleEditText.getText().toString());
                    intent.putExtra("description", imageDescriptionEditText.getText().toString());
                    intent.putExtra("info", eventDetailsEditText.getText().toString());
                    //intent.putExtra("category", )
                    EventDetailActivity.this.setResult(Activity.RESULT_OK, intent);
                    EventDetailActivity.this.finish();
                }
                else {
                    Toast.makeText(EventDetailActivity.this, "There is no title.", Toast.LENGTH_LONG).show();
                }
            }
        });
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