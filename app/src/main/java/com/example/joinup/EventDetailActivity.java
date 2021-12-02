package com.example.joinup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailActivity extends AppCompatActivity {
    final static String TAG = "EventDetailActivity";
    private EditText titleEditText;
    private EditText imageLinkEditText;
    private EditText eventDetailsEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // get the Views
        titleEditText = findViewById(R.id.titleEditTextMultiLine);
        eventDetailsEditText = findViewById(R.id.detailsEditTextMultiLine);
        imageLinkEditText = findViewById(R.id.imageLinkEditText);

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
                    intent.putExtra("imageLink", imageLinkEditText.getText().toString());
                    intent.putExtra("info", eventDetailsEditText.getText().toString());
                    EventDetailActivity.this.setResult(Activity.RESULT_OK, intent);
                    EventDetailActivity.this.finish();
                }
                else {
                    Toast.makeText(EventDetailActivity.this, "There is no title.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}