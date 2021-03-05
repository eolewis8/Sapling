package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionsActivity extends AppCompatActivity {

    EditText choice1Text;
    EditText choice2Text;
    EditText choice3Text;
    EditText choice4Text;
    EditText questionText;
    EditText answerText;
    FirebaseDatabase database;
    DatabaseReference questionRef;
    private static final String DEBUG_TAG = "AddQuestionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
        choice1Text = findViewById(R.id.choice1);
        choice2Text = findViewById(R.id.choice2);
        choice3Text = findViewById(R.id.choice3);
        choice4Text = findViewById(R.id.choice4);
        questionText = findViewById(R.id.question);
        answerText = findViewById(R.id.answer);
        database = FirebaseDatabase.getInstance();
    }

    public void saveQuestionToDb(View view) {
        String choice1 = choice1Text.getText().toString();
        String choice2 = choice2Text.getText().toString();
        String choice3 = choice3Text.getText().toString();
        String choice4 = choice4Text.getText().toString();
        String question = questionText.getText().toString();
        String answer = answerText.getText().toString();
        String subject = getIntent().getStringExtra("Subject");
        String title = getIntent().getStringExtra("Title");
        Log.i(DEBUG_TAG, "Add questions subject : " + subject);
        Log.i(DEBUG_TAG, "Add questions title : " + title);
        questionRef = database.getReference("Questions/" + subject + "/" + title);
        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Question> questions = new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                for (DataSnapshot data: dataSnapshots) {
                    Question question = data.getValue(Question.class);
                    questions.add(question);
                }
                questions.add(new Question(question, answer, choice1, choice2, choice3, choice4));
                questionRef.setValue(questions);
                questionRef.removeEventListener(this);
                Toast.makeText(getApplicationContext(),
                        "Question added successfully!",
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

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
}