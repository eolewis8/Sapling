package com.example.sapling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
}