package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class WinnersSingleActivity extends AppCompatActivity {

    private TextView scoreText;
    private FirebaseDatabase database;
    private DatabaseReference scoreRef, userRef;
    private String playerID;
    private int score;
    private static final String DEBUG_TAG = "WinnersSingleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners_single);
        scoreText = findViewById(R.id.score);
        Intent intent = getIntent();
        score = intent.getIntExtra("totalScore", 0);
        scoreText.setText("Total Score: " + score);
        SharedPreferences sharedPref =
                this.getSharedPreferences("sapling", Context.MODE_PRIVATE);
        playerID = sharedPref.getString("playerID", "");
        database = FirebaseDatabase.getInstance();
        scoreRef = database.getReference("users/" + playerID + "/score");
        userRef = database.getReference("users/");
        updateScoreForCurrentPlayer();
        updateRank();
    }

    private void updateScoreForCurrentPlayer() {
        scoreRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(DEBUG_TAG, "Score obtained is " + score);
                scoreRef.setValue(ServerValue.increment(score));
                scoreRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateRank() {
        userRef.orderByChild("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int rank = (int) snapshot.getChildrenCount();
                Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                for (DataSnapshot data: dataSnapshots) {
                    Users user = data.getValue(Users.class);
                    String emailID = user.getEmail();
                    if (emailID.substring(0, emailID.indexOf("@")).equals(playerID)) {
                        scoreText.setText("Total score: " + score + "\n\nGlobal Rank: " + rank);
                    }
                    userRef.child(data.getKey()).child("avgRank").setValue(rank);
                    rank -= 1;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}