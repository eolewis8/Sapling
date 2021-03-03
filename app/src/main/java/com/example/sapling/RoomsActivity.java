package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.model.Rooms;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomsActivity extends AppCompatActivity {

    ListView listView;
    List<String> roomsList;
    String roomName = "";
    FirebaseDatabase database;
    DatabaseReference roomsRef;
    String playerID;
    private static final String TAG = "RoomsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        database = FirebaseDatabase.getInstance();

        listView = findViewById(R.id.room_list);
        roomsList = new ArrayList<>();
        SharedPreferences sharedPref =
                this.getSharedPreferences("sapling", Context.MODE_PRIVATE);
        playerID = sharedPref.getString("playerID", "");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roomName = roomsList.get(position);
                DatabaseReference roomNameRef = database.getReference("rooms/" + roomName);
                roomNameRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Rooms room = snapshot.getValue(Rooms.class);
                        if (room.getNumConnected() + 1 > room.getNumPlayers()) {
                            roomNameRef.child("available").setValue(false);
                            Toast.makeText(getApplicationContext(), "Room is full", Toast.LENGTH_SHORT).show();
                            roomNameRef.removeEventListener(this);
                        } else {
                            roomNameRef.child("players/" + playerID).setValue(playerID);
                            roomNameRef.child("stats/" + playerID).setValue(0);
                            roomNameRef.child("numConnected").setValue(room.getNumConnected() + 1);
                            Intent intent = new Intent(getApplicationContext(), WaitForPlayersActivity.class);
                            intent.putExtra("roomName", roomName);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        populateRoomsEventListener();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
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

    public void createRoom(View view) {
        DatabaseReference roomRef = database.getReference("rooms/");
        DatabaseReference newRoomRef = roomRef.push();
        roomName = newRoomRef.getKey();
        Intent intent = new Intent(getApplicationContext(), NumPlayerActivity.class);
        intent.putExtra("roomName", roomName);
        startActivity(intent);
    }

    public void populateRoomsEventListener() {
        roomsRef = database.getReference("rooms/");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomsList.clear();
                Iterable<DataSnapshot> rooms = snapshot.getChildren();
                for (DataSnapshot room: rooms) {
                    if (room.child("available").getValue(Boolean.class)) {
                        roomsList.add(room.getKey());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(RoomsActivity.this,
                        android.R.layout.simple_list_item_1, roomsList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                Toast.makeText(this, "Go to Categories Screen", Toast.LENGTH_SHORT);
                // case android.R.id.go_profile:
                //    Toast.makeText(this, "Go to profile screen/stats screen", Toast.LENGTH_SHORT);
                //case android.R.id.log_out:
                //    Toast.makeText(this, "Go to Sign In", Toast.LENGTH_SHORT);
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}