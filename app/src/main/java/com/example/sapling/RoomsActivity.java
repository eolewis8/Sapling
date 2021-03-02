package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
    int playerHash;
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
        playerHash = sharedPref.getInt("playerHash", -1);
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
                            Toast.makeText(getApplicationContext(), "Room is full", Toast.LENGTH_SHORT).show();
                        } else {
                            roomNameRef.child("players/" + String.valueOf(playerHash)).setValue(String.valueOf(playerHash));
                            roomNameRef.child("stats/" + String.valueOf(playerHash)).setValue(0);
                            roomNameRef.child("numConnected").setValue(room.getNumConnected() + 1);
                            Intent intent = new Intent(getApplicationContext(), WaitForPlayersActivity.class);
                            intent.putExtra("roomName", roomName);
                            startActivity(intent);
                            roomNameRef.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        populateRoomsEventListener();
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
                    roomsList.add(room.getKey());
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
}