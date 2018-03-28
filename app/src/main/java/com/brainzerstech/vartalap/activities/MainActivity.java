package com.brainzerstech.vartalap.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.brainzerstech.vartalap.R;
import com.brainzerstech.vartalap.pojos.Friends;
import com.brainzerstech.vartalap.pojos.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    ArrayList<Friends> vFriendList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vFriendList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("TAG", "Key: " + key + " Value: " + value);
            }
        }

        handler();
        // [END handle_data_extras]

        Button subscribeButton = findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                // [END subscribe_topics]

                // Log and toast
                String msg = getString(R.string.msg_subscribed);
                Log.d("TAG", msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button logTokenButton = findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                String token = FirebaseInstanceId.getInstance().getToken();

                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d("TAG", msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                dbRef= database.getReference("Users");

//                Friends friend1 = new Friends(token,"Vishal Mishra","Baaaahubaaallleeeeeeeeee....","Gaziabad");
//                Friends friend2 = new Friends(token,"Razz Singh","Why this kolavery D?","Noida Extention");
//                Friends friend3 = new Friends(token,"Shrey Agarwal","Mujhko pasand rang kala","Meerut");
//vFriendList.add(friend1);
//vFriendList.add(friend2);

//                Users users = new Users(token,"Razz Singh","Why this kolavery D?","Noida", vFriendList, friend1);
//                dbRef.child("9716273125").child("friends").child("9540456169").setValue(friend1);
//                dbRef.child("9716273125").child("friends").child("9258725330").setValue(friend2);
//                dbRef.child("9716273125").child("friends").child("1234567890").setValue(friend3);

                // Read from the database
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Users value = dataSnapshot.getValue(Users.class);
                        Log.d("Value:", "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Error:", "Failed to read value.", error.toException());
                    }
                });
            }
        });
    }

    void handler(){
        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        String msg = getString(R.string.msg_token_fmt, token);
        Log.d("TAG", msg);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef= database.getReference("Users");

//                Friends friend1 = new Friends(token,"Vishal Mishra","Baaaahubaaallleeeeeeeeee....","Gaziabad");
//                Friends friend2 = new Friends(token,"Razz Singh","Why this kolavery D?","Noida Extention");
//                Friends friend3 = new Friends(token,"Shrey Agarwal","Mujhko pasand rang kala","Meerut");
//vFriendList.add(friend1);
//vFriendList.add(friend2);

//                Users users = new Users(token,"Razz Singh","Why this kolavery D?","Noida", vFriendList, friend1);
//                dbRef.child("9716273125").child("friends").child("9540456169").setValue(friend1);
//                dbRef.child("9716273125").child("friends").child("9258725330").setValue(friend2);
//                dbRef.child("9716273125").child("friends").child("1234567890").setValue(friend3);

        // Read from the database
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Users value = dataSnapshot.getValue(Users.class);
                Log.d("Value:", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error:", "Failed to read value.", error.toException());
            }
        });
    }

    public void sendMessage(View view) {
        
    }
}
