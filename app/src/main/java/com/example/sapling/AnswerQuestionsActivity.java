package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AnswerQuestionsActivity extends AppCompatActivity {

    private Button choice1Button, choice2Button, choice3Button, choice4Button;
    private TextView questionText, timerText, scoreText;
    DatabaseReference categoryRef, questionRef;
    FirebaseDatabase database;
    private boolean shouldShowTimer = true;
    Integer currentQuestion = 0;
    int currentPoints = 300;
    int totalPoints = 0;
    int totalQuestions = 0;
    private String subject;
    private String title;
    private Button correctAnswer;
    private static final String DEBUG_TAG = "AnswerQuestionsActivity";
    private List<Integer> questionNumbers = new ArrayList<>();

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
        scoreText = findViewById(R.id.player_score);
        correctAnswer = choice1Button;
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        subject = intent.getStringExtra("Subject");
        title = intent.getStringExtra("Title");
        categoryRef = database.getReference("Questions/" + subject + "/" + title + "/");
        getTotalQuestions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void getTotalQuestions() {
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalQuestions = (int) snapshot.getChildrenCount();
                Log.d(DEBUG_TAG, "Question count : " + totalQuestions);
                int count = (totalQuestions > 5) ? 5 : totalQuestions;
                while (questionNumbers.size() != count) {
                    int random = new Random().nextInt(totalQuestions);
                    if (!questionNumbers.contains(random)) {
                        questionNumbers.add(random);
                    }
                }
                populateQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void populateQuestion() {
        if (currentQuestion < questionNumbers.size()) {
            questionRef = database.getReference("Questions/" + subject + "/" + title + "/"
                    + questionNumbers.get(currentQuestion).toString());
            questionRef.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    scoreText.setText("Score: 0");
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
                                totalPoints += currentPoints;
                                scoreText.setText("Score: " + currentPoints);
                                choice1Button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
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
                                totalPoints += currentPoints;
                                scoreText.setText("Score: " + currentPoints);
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
                                totalPoints += currentPoints;
                                scoreText.setText("Score: " + currentPoints);
                                choice3Button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
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
                                totalPoints += currentPoints;
                                scoreText.setText("Score: " + currentPoints);
                                choice4Button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
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

        } else {
            Intent intent = new Intent(getApplicationContext(), WinnersSingleActivity.class);
            intent.putExtra("totalScore", totalPoints);
            startActivity(intent);
        }
    }

    public void displayTimer() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (shouldShowTimer) {
                    timerText.setText("Time left : " + millisUntilFinished / 1000 + "s");
                    currentPoints -= 10;
                } else {
                    currentPoints = 300;
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                scoreText.setText("Score: 0");
                correctAnswer.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_OVER);
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    correctAnswer.getBackground().setColorFilter(
                            Color.parseColor("#b3e5fc"), PorterDuff.Mode.SRC_OVER);
                    currentQuestion += 1;
                    populateQuestion();
                }, 1000);
            }
        }.start();
    }
}