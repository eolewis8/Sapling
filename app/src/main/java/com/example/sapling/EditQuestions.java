package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;

import com.example.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditQuestions extends AppCompatActivity {

    EditText choice1Text;
    EditText choice2Text;
    EditText choice3Text;
    EditText choice4Text;
    EditText questionText;
    EditText answerText;
    private String ID;
    private String subject;
    private String title;
    FirebaseDatabase database;
    DatabaseReference questionRef;
    private static final String DEBUG_TAG = "EditQuestions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_questions);
        questionText = findViewById(R.id.edit_question);
        answerText = findViewById(R.id.edit_answer);
        choice1Text = findViewById(R.id.edit_choice1);
        choice2Text = findViewById(R.id.edit_choice2);
        choice3Text = findViewById(R.id.edit_choice3);
        choice4Text = findViewById(R.id.edit_choice4);
        Intent intent = getIntent();
        subject = intent.getStringExtra("Subject");
        title = intent.getStringExtra("Title");
        ID = String.valueOf(intent.getIntExtra("ID", -1));
        questionText.setText(intent.getStringExtra("Question"));
        answerText.setText(intent.getStringExtra("Answer"));
        choice1Text.setText(intent.getStringExtra("Choice1"));
        choice2Text.setText(intent.getStringExtra("Choice2"));
        choice3Text.setText(intent.getStringExtra("Choice3"));
        choice4Text.setText(intent.getStringExtra("Choice4"));
        database = FirebaseDatabase.getInstance();
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

    public void saveQuestionToDb(View view) {
        String choice1 = choice1Text.getText().toString();
        String choice2 = choice2Text.getText().toString();
        String choice3 = choice3Text.getText().toString();
        String choice4 = choice4Text.getText().toString();
        String question = questionText.getText().toString();
        Log.i(DEBUG_TAG, "Choice 1: " + choice1);
        Log.i(DEBUG_TAG, "Choice 2: " + choice2);
        Log.i(DEBUG_TAG, "Choice 3: " + choice3);
        Log.i(DEBUG_TAG, "Choice 4: " + choice4);
        String answer = answerText.getText().toString();
        questionRef = database.getReference("Questions/" + subject + "/" + title + "/" + ID);
        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Question newQuestion = new Question(question, answer, choice1, choice2, choice3,
                        choice4);
                questionRef.setValue(newQuestion);
                questionRef.removeEventListener(this);
                Toast.makeText(getApplicationContext(), "Successfully updated question!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DisplayQuestionsActivity.class);
                intent.putExtra("Subject", subject);
                intent.putExtra("Title", title);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instructor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.home:
                intent = new Intent(this, CategoriesActivity.class);
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