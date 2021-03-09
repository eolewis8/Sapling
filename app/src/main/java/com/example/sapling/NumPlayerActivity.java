package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.model.Rooms;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumPlayerActivity extends AppCompatActivity {

    EditText numPlayerText;
    FirebaseDatabase database;
    DatabaseReference roomRef;
    //DatabaseReference playerRef;
    String roomName, subject, title;
    long numPlayers;
    String requiredPlayers;
    String playerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_player);
        numPlayerText = findViewById(R.id.num_players);
        database = FirebaseDatabase.getInstance();
        roomName = getIntent().getStringExtra("roomName");
        roomRef = database.getReference("rooms/" + roomName);
        SharedPreferences sharedPref =
                this.getSharedPreferences("sapling", Context.MODE_PRIVATE);
        playerID = sharedPref.getString("playerID", "");
        Intent intent = getIntent();
        subject = intent.getStringExtra("Subject");
        title = intent.getStringExtra("Title");

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void populateRoom(View view) {
        requiredPlayers = numPlayerText.getText().toString();
        Map<String, String> players = new HashMap<>();
        players.put(playerID, playerID);
        Map<String, Integer> stats = new HashMap<>();
        stats.put(playerID, 0);
        String readableRoomName = title + "-" + playerID;
        Rooms room = new Rooms(Integer.parseInt(requiredPlayers), readableRoomName, players, stats);
        roomRef.setValue(room);
        Intent intent = new Intent(getApplicationContext(), WaitForPlayersActivity.class);
        intent.putExtra("roomName", roomName);
        intent.putExtra("Subject", subject);
        intent.putExtra("Title", title);
        startActivity(intent);
    }
}