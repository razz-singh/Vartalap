package com.brainzerstech.vartalap.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.brainzerstech.vartalap.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;

public class Splash extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null){

                    SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_pref_name),MODE_PRIVATE);
                    boolean isProfileCreated = prefs.getBoolean(getString(R.string.is_profile_created), false);

                    if (isProfileCreated){
                        startActivity(new Intent(Splash.this, TabbedActivity.class));
                    }
                    else{
                        /*Insert data to database*/
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("VUsers");
                        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("vUserToken").setValue(FirebaseInstanceId.getInstance().getToken());
                        startActivity(new Intent(Splash.this, CreateOrEditProfile.class));
                    }
                    finish();

                }
                else{
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN
                    );
                }
            }
        },1000);

    }
}
