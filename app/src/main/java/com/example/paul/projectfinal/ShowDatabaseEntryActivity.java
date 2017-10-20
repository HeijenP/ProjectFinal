package com.example.paul.projectfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShowDatabaseEntryActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authListenerTest;
    private FirebaseUser user;
    private atmEntry anAtm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database_entry);

        getAtmInfo();
        setViews();
        setListener();


    }

    public void getAtmInfo() {
        Bundle extras = getIntent().getExtras();
        String atmID = extras.getString("id");
        String atmStraatNaam = extras.getString("straatNaam");
        String atmCoordinaten = extras.getString("coordinaten");
        String atmBank = extras.getString("bank");
        anAtm = new atmEntry(atmID, atmStraatNaam, atmCoordinaten, atmBank);
    }


    public void setViews() {
        TextView atmID = (TextView) findViewById(R.id.idview);
        TextView atmStraatNaam = (TextView) findViewById(R.id.straatnaamview);
        TextView atmCoordinaten = (TextView) findViewById(R.id.coordinatenview);
        TextView atmBank = (TextView) findViewById(R.id.bankview);

        atmID.setText(anAtm.getID());
        atmStraatNaam.setText(anAtm.getstraatNaam());
        atmCoordinaten.setText(anAtm.getcoordinaten());
        atmBank.setText(anAtm.getBank());
    }


    private void setListener() {
        authListenerTest = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d("sign in", user.getUid());
                } else {
                    Log.d("sign out", "user signed out");
                    goToRegisterLoginActivity();
                }
            }
        };
    }

    public void goToRegisterLoginActivity() {
        Intent firstIntent = new Intent(this, SecondActivity.class);
        this.startActivity(firstIntent);
        finish();
    }

    public void goToDatabase(View view) {
        Intent dbIntent = new Intent(this,ShowDatabaseActivity.class);
        this.startActivity(dbIntent);
        finish();
    }
}

