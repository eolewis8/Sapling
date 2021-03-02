package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnswerQuestionsActivity extends AppCompatActivity {

    private Button choice1Button, choice2Button, choice3Button, choice4Button;
    private TextView questionText, timerText;
    DatabaseReference databaseReference;
    private boolean shouldShowTimer = true;
    Integer currentQuestion = 1;
    Integer numCorrect = 0;
    int numIncorrect = 0;
    private Button correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        choice1Button = findViewById(R.id.choice1);
        choice2Button = findViewById(R.id.choice2);
        choice3Button = findViewById(R.id.choice3);
        choice4Button = findViewById(R.id.choice4);
        questionText = findViewById(R.id.question);
        timerText = findViewById(R.id.timer);
        correctAnswer = choice1Button;
        populateQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void populateQuestion() {
        if (currentQuestion < 6) {

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions").child(currentQuestion.toString());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    choice1Button.setClickable(true);
                    choice2Button.setClickable(true);
                    choice3Button.setClickable(true);
                    choice4Button.setClickable(true);
                    shouldShowTimer = true;
                    displayTimer();
                    Question question = snapshot.getValue(Question.class);
                    questionText.setText(question.getQuestion());
                    choice1Button.setText(question.getChoice1());
                    choice2Button.setText(question.getChoice2());
                    choice3Button.setText(question.getChoice3());
                    choice4Button.setText(question.getChoice4());
                        if (choice2Button.getText().toString().equals(question.getAnswer())) {
                            correctAnswer = choice2Button;
                        } else if (choice3Button.getText().toString().equals(question.getAnswer())) {
                            correctAnswer = choice3Button;
                        } else if (choice4Button.getText().toString().equals(question.getAnswer())) {
                            correctAnswer = choice4Button;
                        }
                        Button finalCorrectAnswer = correctAnswer;
                        choice1Button.setOnClickListener(v -> {
                            shouldShowTimer = false;
                            choice1Button.setClickable(false);
                            choice2Button.setClickable(false);
                            choice3Button.setClickable(false);
                            choice4Button.setClickable(false);
                            if (choice1Button == finalCorrectAnswer) {
                                choice1Button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
                                numCorrect += 1;
                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    choice1Button.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    currentQuestion += 1;
                                    populateQuestion();
                                    }, 1000);

                            } else {
                                choice1Button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_OVER);
                                finalCorrectAnswer.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
                                numIncorrect += 1;
                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    choice1Button.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    finalCorrectAnswer.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    currentQuestion += 1;
                                    populateQuestion();
                                    }, 1000);
                            }
                        });
                        choice2Button.setOnClickListener(v -> {
                            shouldShowTimer = false;
                            choice1Button.setClickable(false);
                            choice2Button.setClickable(false);
                            choice3Button.setClickable(false);
                            choice4Button.setClickable(false);
                            if (choice2Button == finalCorrectAnswer) {
                                numCorrect += 1;
                                choice2Button.getBackground().setColorFilter(Color.GREEN,
                                        PorterDuff.Mode.SRC_OVER);
                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    choice2Button.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    currentQuestion += 1;
                                    populateQuestion();
                                }, 1000);
                            } else {
                                choice2Button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_OVER);
                                finalCorrectAnswer.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
                                numIncorrect += 1;
                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    choice2Button.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    finalCorrectAnswer.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    currentQuestion += 1;
                                    populateQuestion();
                                }, 1000);
                            }
                        });
                        choice3Button.setOnClickListener(v -> {
                            shouldShowTimer = false;
                            choice1Button.setClickable(false);
                            choice2Button.setClickable(false);
                            choice3Button.setClickable(false);
                            choice4Button.setClickable(false);
                            if (choice3Button == finalCorrectAnswer) {
                                choice3Button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
                                numCorrect += 1;
                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    choice3Button.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    currentQuestion += 1;
                                    populateQuestion();
                                }, 1000);
                            } else {
                                choice3Button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_OVER);
                                finalCorrectAnswer.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
                                numIncorrect += 1;
                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    choice3Button.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    finalCorrectAnswer.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    currentQuestion += 1;
                                    populateQuestion();
                                }, 1000);
                            }

                        });
                        choice4Button.setOnClickListener(v -> {
                            shouldShowTimer = false;
                            choice1Button.setClickable(false);
                            choice2Button.setClickable(false);
                            choice3Button.setClickable(false);
                            choice4Button.setClickable(false);
                            if (choice4Button == finalCorrectAnswer) {
                                choice4Button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
                                numCorrect += 1;
                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    choice4Button.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    currentQuestion += 1;
                                    populateQuestion();
                                }, 1000);
                            } else {
                                choice4Button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_OVER);
                                finalCorrectAnswer.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
                                numIncorrect += 1;
                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    choice4Button.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    finalCorrectAnswer.getBackground().setColorFilter(
                                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                                    currentQuestion += 1;
                                    populateQuestion();
                                }, 1000);
                            }
                        });
                    }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    public void displayTimer() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (shouldShowTimer) {
                    timerText.setText("Time left : " + millisUntilFinished / 1000 + "s");
                } else {
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                correctAnswer.setBackgroundColor(Color.GREEN);
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    correctAnswer.setBackgroundColor(Color.parseColor("#FFB6C1"));
                    currentQuestion += 1;
                    populateQuestion();
                }, 1000);
            }
        }.start();
    }
}