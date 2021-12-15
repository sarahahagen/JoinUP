package com.example.joinup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivityTag";
    String title;
    String info;
    int numberOfAttendees;
    String imageDescription;
    List<EventDetail> eventsList;
    ActivityResultLauncher<Intent> launcher;
    CustomAdapter adapter;
    boolean addButtonClicked = false;
    boolean clickOnVideo = false;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(this, GoogleSSO.class);
        startActivity(intent);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Logged in as: " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(intent);
        }

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()  {
            /**
             Updates the RecyclerView and videoList.
             *
             * Pre-conditions: using card_view.xml and working with another Intent.
             * Post-conditions: updates the RecyclerView and videoList.
             * @param result result is from the other Intent.
             */
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d(TAG, "onActivityResult: ");
                // this callback executes when MainActivity is returned to after it started an activity
                // for a result
                // grab the result here
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    title = data.getStringExtra("title");
                    imageDescription = data.getStringExtra("description");
                    info = data.getStringExtra("info");
                    //numberOfAttendees = data.getIntExtra("number", 0);

                    // adds event to eventsList and RecyclerView, if the addButton was clicked
                    if (addButtonClicked) {
                        eventsList.add(new EventDetail(title, info, imageDescription, numberOfAttendees));
                        adapter.notifyItemInserted(adapter.getItemCount() - 1);
                    }

                    // updates the video to videoList and RecyclerView, if the video view was clicked

                    // reset values
                    addButtonClicked = false;
                    clickOnVideo = false;
                }
            }
        });

        //eventsList.add(new EventDetail("Frasier Watch Party", "Please stop by ISE"));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        eventsList = new ArrayList<>();
        eventsList.add(new EventDetail());
        eventsList.add(new EventDetail());
        eventsList.add(new EventDetail());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu xml
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cam_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch(itemId) {
            case R.id.addMenuItem:
                addButtonClicked = true;
                Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
                launcher.launch(intent);
                return true;
            // TODO: fix about menu
            case R.id.aboutMenuItem:
                Toast.makeText(this, "TODO: about app", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            TextView titleTextView;
            TextView imageDescriptionTextView;
            TextView eventDetailsTextView;
            TextView numberOfEventsTextView;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);

                titleTextView = itemView.findViewById(R.id.titleTextView);
                imageDescriptionTextView = itemView.findViewById(R.id.imageDescriptionTextView);
                eventDetailsTextView = itemView.findViewById(R.id.eventDetailsTextView);

                numberOfEventsTextView = findViewById(R.id.numberOfEventsTextView);
                numberOfEventsTextView.setText("Number of events: " + getItemCount());

                // wire 'em up
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public void updateView(EventDetail eventDetail) {
                titleTextView.setText(eventDetail.getEventTitle());
                imageDescriptionTextView.setText(eventDetail.getImageDescription());
                eventDetailsTextView.setText(eventDetail.getEventInfo());
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                Intent intent = new Intent(MainActivity.this, EventInfoActivity.class);
                index = getLayoutPosition();
                clickOnVideo = true;
                intent.putExtra("title", eventsList.get(getLayoutPosition()).getEventTitle());
                intent.putExtra("info", eventsList.get(getLayoutPosition()).getEventInfo());
                intent.putExtra("number", eventsList.get(getLayoutPosition()).getNumberOfStudents());
                intent.putExtra("description", eventsList.get(getLayoutPosition()).getImageDescription());
                launcher.launch(intent);
            }

            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick: ");
                // show an alert dialog
                // use an AlertDialog.Builder object and method chaining to build an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Event Long Clicked")
                        .setMessage("Do you plan on attending? If you clicked YES previously, you can click NO if you decide on not attending.")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // executes when the user presses "YES"
                                index = getLayoutPosition();
                                eventsList.get(index).setNumberOfStudents(1);

                                Toast.makeText(MainActivity.this, "WOOHOO! You have been added.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                                intent.putExtra("title", eventsList.get(getLayoutPosition()).getEventTitle());
                                intent.putExtra("info", eventsList.get(getLayoutPosition()).getEventInfo());
                                launcher.launch(intent);
                           }
                     }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // executes when the user presses "NO"
                        index = getLayoutPosition();
                        eventsList.get(index).setNumberOfStudents(-1);
                        Toast.makeText(MainActivity.this, "You have decided not to attend :(.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

                return true;
            }
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.event_detail, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            // get the EventDetail at position then pass it to our updateView() method for displaying
            EventDetail eventDetail = eventsList.get(position);
            holder.updateView(eventDetail);
        }

        @Override
        public int getItemCount() {
            return eventsList.size();
        }
    }
}