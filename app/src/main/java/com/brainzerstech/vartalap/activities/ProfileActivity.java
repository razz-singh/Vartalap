package com.brainzerstech.vartalap.activities;

import android.app.ActionBar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.brainzerstech.vartalap.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilePic;
    TextView tvName, tvAgeStateCountry,tvId,tvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        profilePic = findViewById(R.id.profile_pic);
        tvName = findViewById(R.id.name);
        tvAgeStateCountry = findViewById(R.id.tv_age_state_country);
        tvId = findViewById(R.id.id);
        tvStatus = findViewById(R.id.status);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("VUsers/"+FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Uri imgPath = Uri.parse(dataSnapshot.child("vAvtarUrl").getValue().toString());
                    Picasso.get().load(imgPath).into(profilePic);
                    tvName.setText(dataSnapshot.child("VUserName").getValue().toString());
                    tvAgeStateCountry.setText(dataSnapshot.child("vUserAge").getValue()+"years, "+dataSnapshot.child("vUserState").getValue()+", "+dataSnapshot.child("vUserCountry").getValue());
                    tvId.setText(dataSnapshot.getKey());
                    tvStatus.setText(dataSnapshot.child("vUserStatus").getValue().toString());

                }
                else{

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
