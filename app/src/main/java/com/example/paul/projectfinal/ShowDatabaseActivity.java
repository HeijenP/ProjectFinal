package com.example.paul.projectfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowDatabaseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListenerTest;
    private FirebaseUser user;
    private ArrayList<String> atmIDlist = new ArrayList<>();
    private ArrayList<atmEntry> atmlist = new ArrayList<>();
    private ArrayAdapter arrayAdapter;
    private ListView atmlistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setListener();

        makeAtmListAdapter();
        getAtmsFromDB();
        listenToLongClicks();
    }

    private void setListener() {
        authListenerTest = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("sign in", user.getUid());
                } else {
                    Log.d("sign out", "no user signed in");
                    goToSecondActivity();
                }
            }
        };
    }

    public void signOut (View view) {
        user = null;
        mAuth.signOut();
        goToSecondActivity();
        setListener();
    }

    public void makeAtmListAdapter() {
        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, atmIDlist);
        atmlistview = (ListView) findViewById(R.id.atmlistview);
        assert atmlistview != null;
        atmlistview.setAdapter(arrayAdapter);
    }

    public void makeAtmIdList(Map<String, Object> atmsMap) {

        for (Map.Entry<String, Object> entry : atmsMap.entrySet()) {
            Map atm = (Map) entry.getValue();
            String atmID = (String) atm.get("id");
//            Log.d("stringcheck", atmID);
            String atmStraatnaam = (String) atm.get("straatNaam");
            Log.d("straatnaamcheck", atmStraatnaam);
            String atmCoordinaten = (String) atm.get("coordinaten");
            String atmBank = (String) atm.get("bank");
            atmEntry newEntry = new atmEntry(atmID, atmStraatnaam, atmCoordinaten, atmBank);
            atmlist.add(newEntry);
            atmIDlist.add(atmID);
        }
//        Log.d("nog lekker", (atmlist.toString()));
        arrayAdapter.notifyDataSetChanged();
    }

    public void listenToLongClicks() {
        atmlistview.setClickable(true);
        atmlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                viewItem(position);
                return true;
            }
        });
    }

    public void viewItem(int pos) {
        atmEntry entryToView = atmlist.get(pos);
        goToShowRouteActivity(entryToView);
    }

    public void getAtmsFromDB() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> atmsMap = (HashMap<String, Object>) dataSnapshot.child("atms").getValue();
                Log.d("nog lekker", ("HASH MAP DUMP: " + atmsMap.toString()));
                makeAtmIdList(atmsMap);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR", "NOT LOADED");
            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    public void goToShowRouteActivity(atmEntry atmEntry) {
        Intent atmIntent = new Intent(this, ShowDatabaseEntryActivity.class);
        atmIntent.putExtra("id", atmEntry.getID());
        atmIntent.putExtra("straatNaam", atmEntry.getstraatNaam());
        atmIntent.putExtra("coordinaten", atmEntry.getcoordinaten());
        atmIntent.putExtra("bank", atmEntry.getBank());
        this.startActivity(atmIntent);
    }

    public void goToSecondActivity(){
        Intent intent = new Intent(this,SecondActivity.class);
        this.startActivity(intent);
        finish();
    }

    public void goToAddAtm(View view) {
        Intent addRouteIntent = new Intent(this, DatabaseActivity.class);
        this.startActivity(addRouteIntent);
    }

}
