package com.example.sapling;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatsActivity extends AppCompatActivity {

    private TextView playerNameText;
    private TextView emailIDText;
    private TextView highScoreText;
    private TextView avgRankText;
    private FirebaseDatabase database;
    private DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        playerNameText = findViewById(R.id.player_name);
        emailIDText  = findViewById(R.id.player_email);
        highScoreText = findViewById(R.id.player_total_score);
        avgRankText = findViewById(R.id.player_avg_rank);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users/" + Util.getCurrentUser());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                playerNameText.setText("Name: " + user.getFirstName() + " " + user.getLastName());
                emailIDText.setText("Email: " + user.getEmail());
                highScoreText.setText("Total score: " + user.getScore());
                avgRankText.setText("Average Rank: " + user.getAvgRank());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch(id) {
            case R.id.home:
                intent = new Intent(this, CategoriesActivity.class);
                startActivity(intent);
                break;
            case R.id.go_profile:
                intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
                break;
            case R.id.rules:
                intent = new Intent(this, RulesActivity.class);
                startActivity(intent);
                break;
            case R.id.log_out:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
