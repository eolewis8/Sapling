package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SingleOrMultiActivity extends AppCompatActivity {

    String subject;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_or_single);
        Intent intent = getIntent();
        subject = intent.getStringExtra("Subject");
        title = intent.getStringExtra("Title");


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void playSingleOrMultiplayerMode(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.single:
                intent = new Intent(this, AnswerQuestionsActivity.class);
                break;
            case R.id.multi:
                intent = new Intent(this, RoomsActivity.class);
                break;
            default:
                intent = new Intent(this, AnswerQuestionsActivity.class);
                break;
        }
        intent.putExtra("Subject", subject);
        intent.putExtra("Title", title);
        startActivity(intent);
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
}