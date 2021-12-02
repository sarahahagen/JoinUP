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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivityTag";
    String title;
    String info;
    int numberOfAttendees;
    String imageUrl;
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
                    imageUrl = data.getStringExtra("imageLink");
                    info = data.getStringExtra("info");
                    //numberOfAttendees = data.getIntExtra("number", 0);
                    //imageID = data.getIntExtra("imageID", 0);

                    // adds video to videoList and RecyclerView, if the addButton was clicked
                    if (addButtonClicked) {
                        eventsList.add(new EventDetail(title, info, numberOfAttendees));
                        adapter.notifyItemInserted(adapter.getItemCount() - 1);
                    }

                    // updates the video to videoList and RecyclerView, if the video view was clicked
                    if (clickOnVideo) {
                        /*
                        eventsList.get(index).setEventTitle(title);
                        eventsList.get(index).setEventInfo(info);
                        eventsList.get(index).setNumberOfStudents(numberOfAttendees);
                        //if (imageID == 0) {
                        //  eventsList.get(index).setWatched(watched);
                        //}
                        adapter.notifyItemChanged(index);

                         */
                    }

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
            ImageView imageView;
            TextView eventDetailsTextView;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);

                titleTextView = itemView.findViewById(R.id.titleTextView);
                imageView = itemView.findViewById(R.id.imageView);
                eventDetailsTextView = itemView.findViewById(R.id.eventDetailsTextView);

                // wire 'em up
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public void updateView(EventDetail eventDetail) {
                titleTextView.setText(eventDetail.getEventTitle());
                imageView.setImageResource(R.drawable.placeholder_image);
                eventDetailsTextView.setText(eventDetail.getEventInfo());
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                clickOnVideo = true;
                Toast.makeText(MainActivity.this, "Decide what to do here", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EventInfoActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick: ");
                // show an alert dialog
                // use an AlertDialog.Builder object and method chaining to build an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Event Long Clicked")
                        .setMessage("Do you plan on attending?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // executes when the user presses "YES"
                                index = getAdapterPosition();
                                eventsList.get(index).setNumberOfStudents(1);
                                Toast.makeText(MainActivity.this, "WOOHOO! You have been added.", Toast.LENGTH_SHORT).show();
                           }
                     }).setNegativeButton("NO", null);
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