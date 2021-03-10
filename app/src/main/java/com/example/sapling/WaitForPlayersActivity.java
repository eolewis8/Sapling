package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Rooms;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitForPlayersActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference playersRef;
    DatabaseReference roomRef;
    String roomName, subject, title;
    long currentPlayers;
    TextView playerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_players);
        playerText = findViewById(R.id.playerCount);
        database = FirebaseDatabase.getInstance();
        roomName = getIntent().getStringExtra("roomName");
        roomRef = database.getReference("rooms/" + roomName);
        Intent intent = getIntent();
        subject = intent.getStringExtra("Subject");
        title = intent.getStringExtra("Title");
        addPlayersEventListener();
    }

    private void addPlayersEventListener() {
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Rooms room = snapshot.getValue(Rooms.class);
                if (room.getNumConnected() == room.getNumPlayers()) {
                    Toast.makeText(getApplicationContext(), "Desired number of players joined", Toast.LENGTH_SHORT).show();
                    roomRef.removeEventListener(this);
                    Intent intent = new Intent(getApplicationContext(), MultiplayerActivity.class);
                    intent.putExtra("roomName", roomName);
                    intent.putExtra("Subject", subject);
                    intent.putExtra("Title", title);
                    startActivity(intent);
                } else {
                    long numPlayers = room.getNumPlayers() - room.getNumConnected();
                    String text = "Waiting for " + numPlayers + " players to join";
                    if (numPlayers == 1) {
                        text = "Waiting for " + numPlayers + " player to join";
                    }
                    playerText.setText(text);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}