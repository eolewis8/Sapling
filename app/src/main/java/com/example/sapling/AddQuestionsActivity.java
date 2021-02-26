package com.example.sapling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddQuestionsActivity extends AppCompatActivity {

    EditText choice1Text;
    EditText choice2Text;
    EditText choice3Text;
    EditText choice4Text;
    EditText questionText;
    EditText answerText;

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
    }

    public void saveQuestionToDb(View view) {
        String choice1 = choice1Text.getText().toString();
        String choice2 = choice2Text.getText().toString();
        String choice3 = choice3Text.getText().toString();
        String choice4 = choice4Text.getText().toString();
        String question = questionText.getText().toString();
        String answer = answerText.getText().toString();
        String subject = getIntent().getStringExtra("Subject");
        QuestionsDbHelper dbHelper = new QuestionsDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_SUBJECT, subject);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_TITLE, "Computer");
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_CHOICE1, choice1);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_CHOICE2, choice2);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_CHOICE3, choice3);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_CHOICE4, choice4);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION, question);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_ANSWER, answer);
        long recordId = db.insert(QuestionsInfoContract.Questions.TABLE_NAME,
                null, contentValues);
        db.close();
        if (recordId == -1) {
            Toast.makeText(getApplicationContext(), "Question insertion failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Question added successfully!",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DisplayQuestionsActivity.class);
            intent.putExtra("Subject", subject);
            startActivity(intent);
        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
}