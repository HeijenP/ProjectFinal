package com.example.paul.projectfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/*
 * File: DatabaseActivity.java
 * Last edited: 20-10-2017
 * By: Paul Heijen
 *
 * This activity allows an user to add an atm entry to the database. */

public class DatabaseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListenerTest;
    private FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setListener();
    }

    //method that verifies if a user is still logged in
    private void setListener() {
        authListenerTest = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d("sign in", user.getUid());
                } else {
                    Log.d("sign out", "user is signed out");
                    goToRegisterLoginActivity();
                }
            }
        };
    }

    //method that, if not logged in, redirects a user to login/register activity.
    public void goToRegisterLoginActivity() {
        Intent firstIntent = new Intent(this, SecondActivity.class);
        this.startActivity(firstIntent);
    }

    //method that takes input from the user, in the form of edittexts, and puts this into the
    // firebase database, if all fields are filled in.
    public void addToDb(View view){
        EditText et1 = (EditText) findViewById(R.id.editText);
        EditText et2 = (EditText) findViewById(R.id.editText2);
        EditText et3 = (EditText) findViewById(R.id.editText3);
        EditText et4 = (EditText) findViewById(R.id.editText4);

        String id = et1.getText().toString();
        String straatnaam = et2.getText().toString();
        String coordinaten = et3.getText().toString();
        String bank = et4.getText().toString();

        if(id.matches("")|straatnaam.matches("")|coordinaten.matches("")|bank.matches("")){
            makeToast("One of the required fields hasn't been filled in, try again!");
        }else{
//            checkRequirements(id, straatnaam, coordinaten, bank);
            atmEntry newEntry = new atmEntry(id, straatnaam, coordinaten, bank);

            et1.getText().clear();
            et2.getText().clear();
            et3.getText().clear();
            et4.getText().clear();

            mDatabase.child("atms").child(newEntry.getID()).setValue(newEntry);
            makeToast("Succesfully added ATM to database");
        }
    }

    // easy toast maker method
    public void makeToast(String msg) {
        Toast.makeText(DatabaseActivity.this, msg,
                Toast.LENGTH_SHORT).show();
    }
}


