package com.example.joinup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {
    final static String TAG = "CalendarActivityTag";
    ActivityResultLauncher<Intent> launcher;
    String title;
    String info;
    final static int READ_CALENDAR_REQUEST_CODE = 1; // needs to be unique within your app's requests
    final static int WRITE_CALENDAR_REQUEST_CODE = 2; // needs to be unique within your app's requests

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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
                    info = data.getStringExtra("info");
                }
            }
        });

        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setData(CalendarContract.Events.CONTENT_URI);
        calIntent.putExtra(CalendarContract.Events.TITLE, title);
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, info);
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Gonzaga");
        calIntent.putExtra(CalendarContract.Events.ALL_DAY, true);

        enableReadAndWriteCalendar();
        if (calIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(calIntent);
        }
        else {
            Toast.makeText(CalendarActivity.this, "There is no app supporting this", Toast.LENGTH_SHORT).show();
        }
    }

    private void enableReadAndWriteCalendar() {
        // we need to get the user's permission at runtime to access their calendar
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                == PackageManager.PERMISSION_GRANTED) {
            // we have permission!!
            // TODO
            Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
        }
        else {
            // we need to request permission
            // creates an alert dialog and prompts the user to choose grant or deny
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR},
                    READ_CALENDAR_REQUEST_CODE);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_CALENDAR},
                    WRITE_CALENDAR_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // this callback executes once the user has made their choice in the alert dialog
        if (requestCode == READ_CALENDAR_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // we finally have the user's permission
                enableReadAndWriteCalendar();
            }
            else {
                Toast.makeText(this, "Read permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == WRITE_CALENDAR_REQUEST_CODE) {
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // we finally have the user's permission
                enableReadAndWriteCalendar();
            }
            else {
                Toast.makeText(this, "Write permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}