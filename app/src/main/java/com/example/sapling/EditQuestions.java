package com.example.sapling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        QuestionsDbHelper dbHelper = new QuestionsDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_CHOICE1, choice1);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_CHOICE2, choice2);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_CHOICE3, choice3);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_CHOICE4, choice4);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION, question);
        contentValues.put(QuestionsInfoContract.Questions.QUESTION_ANSWER, answer);
        String whereClause = QuestionsInfoContract.Questions._ID + "=?";
        String[] whereArgs = {ID};
        int count = db.update(QuestionsInfoContract.Questions.TABLE_NAME, contentValues,
                whereClause, whereArgs);
        Log.i(DEBUG_TAG, "Count : " + count);
        db.close();
        if (count < 1) {
            Toast.makeText(getApplicationContext(), "No records updated",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Successfully updated question!",
                    Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, DisplayQuestionsActivity.class);
        intent.putExtra("Subject", subject);
        intent.putExtra("Title", title);
        startActivity(intent);
    }
}