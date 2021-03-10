package com.example.sapling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Users;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ToDo: Will display top 3 winners by student name
public class WinnersMultiActivity extends FragmentActivity implements PlayAgainDialogFragment.AlertDialogListener {

    private List<String> categories = new ArrayList<String>();
    private static final String DEBUG_TAG = "WinnersMultiActivity";

    // Make a List of strings of their scores from firebase and add to card view

    private List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.gold_logo,
            R.drawable.silver_logo, R.drawable.bronze_logo));
    private List<Integer> scores = new ArrayList<>();

    private Map<Integer, Integer> imageMap = new HashMap<>();

    private RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference statsPlayerRef, scoreRef, userRef, statsRef;
    String playerID, roomName;
    private ValueEventListener userValueEventListener, statsValueEventListener;
    private DialogFragment dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners_multi);
        database = FirebaseDatabase.getInstance();
        SharedPreferences sharedPref =
                this.getSharedPreferences("sapling", Context.MODE_PRIVATE);
        playerID = sharedPref.getString("playerID", "");
        roomName = getIntent().getStringExtra("roomName");
        statsPlayerRef = database.getReference("rooms/" + roomName + "/stats/" + playerID);
        scoreRef = database.getReference("users/" + playerID + "/score");
        userRef = database.getReference("users/");
        statsRef = database.getReference("rooms/" + roomName + "/stats/");
        addStatsPlayerEventListener();
        imageMap.put(1, R.drawable.gold_logo);
        imageMap.put(2, R.drawable.silver_logo);
        imageMap.put(3, R.drawable.bronze_logo);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ScoreAdapter adapter = new ScoreAdapter(this, categories, images, scores);
        recyclerView.setAdapter(adapter);
        updateRank();
        displayTopPlayers();
        dialog = new PlayAgainDialogFragment();
        dialog.show(getSupportFragmentManager(), "PlayAgainDialogFragment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void addStatsPlayerEventListener() {
        statsPlayerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int score = snapshot.getValue(Integer.class);
                Log.i(DEBUG_TAG, "Score obtained is " + score);
                scoreRef.setValue(ServerValue.increment(score));
                statsPlayerRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateRank() {
        userValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int rank = (int) snapshot.getChildrenCount();
                Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                for (DataSnapshot data: dataSnapshots) {
                    userRef.child(data.getKey()).child("avgRank").setValue(rank);
                    rank -= 1;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userRef.orderByChild("score").addValueEventListener(userValueEventListener);
    }

    public void displayTopPlayers() {
        statsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(DEBUG_TAG, snapshot.getValue().toString());
                categories.clear();
                scores.clear();
                Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                for (DataSnapshot data: dataSnapshots) {
                    categories.add(data.getKey());
                    scores.add(data.getValue(Integer.class));
                }
                Collections.reverse(categories);
                Collections.reverse(scores);
                refreshRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        statsRef.orderByValue().limitToLast(3).addValueEventListener(statsValueEventListener);
    }

    private void refreshRecyclerView() {
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            ScoreAdapter adapter = (ScoreAdapter) recyclerView.getAdapter();
            Log.i(DEBUG_TAG, "Top players : " + categories);
            Log.i(DEBUG_TAG, "Scores : " + scores);
            adapter.notifyNewDataAdded(categories, images, scores);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (userValueEventListener != null) {
            userRef.removeEventListener(userValueEventListener);
        }
        if (statsValueEventListener != null) {
            statsRef.removeEventListener(statsValueEventListener);
        }
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

}
