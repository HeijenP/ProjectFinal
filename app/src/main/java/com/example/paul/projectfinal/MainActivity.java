package com.example.paul.projectfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/*
 * File: MainActivity.java
 * Last edited: 20-10-2017
 * By: Paul Heijen
 *
 * The first screen of the app shows a welcome greeting plus an iconic picture. On
 * the bottom is a continue button that can be used to go to the database. If a user
 * is allready logged in this will immediately happen when pressed. If not, one shall
 * be redirected to a login/register activity */


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean checkSign = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        setListener();
    }

    // this method checks if a user is allready logged in.
    public void setListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // The user was already signed in
                    Log.d("User signed in:", user.getUid());
                    checkSign = true;
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //method triggered by onclick of continue button. Dependant on login status, user will be directed to next activity.
    public void goToSecondMain(View view) {

        if(checkSign) {
            Intent intent = new Intent(this, ShowDatabaseActivity.class);
            this.startActivity(intent);
        } else {
            Intent intent = new Intent(this,SecondActivity.class);
            this.startActivity(intent);
        }
    }
}
