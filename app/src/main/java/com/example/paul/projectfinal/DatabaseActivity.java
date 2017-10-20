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

public class DatabaseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListenerTest;
    private FirebaseUser user;
//    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setListener();

//        updateCounter();

//        Log.d("count3", mDatabase.child("counter"));

         ;


//        myRef.setValue("Hello, World!");
    }

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

    public void goToRegisterLoginActivity() {
        Intent firstIntent = new Intent(this, SecondActivity.class);
        this.startActivity(firstIntent);
    }

    public void addToDb(View view){
        EditText et1 = (EditText) findViewById(R.id.editText);
        EditText et2 = (EditText) findViewById(R.id.editText2);
        EditText et3 = (EditText) findViewById(R.id.editText3);
        EditText et4 = (EditText) findViewById(R.id.editText4);

//        Editable eid = et1.getText();
//        Editable estraatnaam = et2.getText();
//        Editable ecoordinaten = et3.getText();
//        Editable ebank = et4.getText();

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

//    public boolean checkRequirements(String id, String straatnaam, String coordinaten, String bank){
//        if()
//    }

//    public int updateCounter() {
//        int count = 0;
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int count = dataSnapshot.child("counter").getValue(Integer.class);
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("ERROR", "NOT LOADED");
//            }
//        };
//        mDatabase.addValueEventListener(postListener);
//
//        return count;
//
//    }

//    public void updateCounter2() {
//        mDatabase.child("counter").runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(final MutableData currentData) {
//                if (currentData.getValue() == null) {
//                    currentData.setValue("0");
//                } else {
//                    String stringValue = (String) currentData.getValue();
//                    int intValue = Integer.parseInt(stringValue);
//                    int increasedIntValue = intValue + 1;
//                    currentData.setValue(String.valueOf(increasedIntValue));
//                }
//                return Transaction.success(currentData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot currentData) {
//                if (databaseError != null) {
//                    System.out.println("Firebase counter increment failed!");
//                } else {
//                    System.out.println("Firebase counter increment succeeded!");
//                }
//            }
//        });
//    }

    public void makeToast(String msg) {
        Toast.makeText(DatabaseActivity.this, msg,
                Toast.LENGTH_SHORT).show();
    }
}


