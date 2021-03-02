package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MultiplayerActivity extends AppCompatActivity {

    private Button choice1Button, choice2Button, choice3Button, choice4Button;
    private TextView questionText, timerText;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference statsPlayerRef;
    DatabaseReference statsRef;
    DatabaseReference roomRef;
    private boolean shouldShowTimer = true;
    Integer currentQuestion = 1;
    Integer numCorrect = 0;
    int numIncorrect = 0;
    private Button correctAnswer;
    int playerHash;
    String roomName;
    int currentPoints = 300;
    int totalPoints = 0;
    Map<String, Integer> playerToPointsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        choice1Button = findViewById(R.id.choice1);
        choice2Button = findViewById(R.id.choice2);
        choice3Button = findViewById(R.id.choice3);
        choice4Button = findViewById(R.id.choice4);
        questionText = findViewById(R.id.question);
        timerText = findViewById(R.id.timer);
        correctAnswer = choice1Button;
        SharedPreferences sharedPref =
                this.getSharedPreferences("sapling", Context.MODE_PRIVATE);
        playerHash = sharedPref.getInt("playerHash", -1);
        database = FirebaseDatabase.getInstance();
        playerToPointsMap.put(String.valueOf(playerHash), 0);
        roomName = getIntent().getStringExtra("roomName");
        statsRef = database.getReference("rooms/" + roomName + "/stats/");
        roomRef = database.getReference("rooms/" + roomName);
        statsRef.orderByValue().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    Map<String, Object> scores = new HashMap<>();
                    scores.put("highScore", snapshot.getValue());
                    scores.put("highScorePlayer", snapshot.getKey());
                    roomRef.updateChildren(scores);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        statsPlayerRef = database.getReference("rooms/" + roomName + "/stats/" + String.valueOf(playerHash));
        populateQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void populateQuestion() {
        if (currentQuestion < 5) {
            databaseReference = database.getReference("Questions/" + currentQuestion.toString());
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
                            choice1Button.setBackgroundColor(Color.GREEN);
                            numCorrect += 1;
                            totalPoints += currentPoints;
                            statsPlayerRef.setValue(totalPoints);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                choice1Button.setBackgroundColor(Color.parseColor("#FFB6C1"));
                                currentQuestion += 1;
                                populateQuestion();
                            }, 1000);

                        } else {
                            choice1Button.setBackgroundColor(Color.RED);
                            finalCorrectAnswer.setBackgroundColor(Color.GREEN);
                            numIncorrect += 1;
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                choice1Button.setBackgroundColor(Color.parseColor("#FFB6C1"));
                                finalCorrectAnswer.setBackgroundColor(Color.parseColor("#FFB6C1"));
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
                            totalPoints += currentPoints;
                            statsPlayerRef.setValue(totalPoints);
                            choice2Button.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                choice2Button.setBackgroundColor(Color.parseColor("#FFB6C1"));
                                currentQuestion += 1;
                                populateQuestion();
                            }, 1000);
                        } else {
                            choice2Button.setBackgroundColor(Color.RED);
                            finalCorrectAnswer.setBackgroundColor(Color.GREEN);
                            numIncorrect += 1;
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                choice2Button.setBackgroundColor(Color.parseColor("#FFB6C1"));
                                finalCorrectAnswer.setBackgroundColor(Color.parseColor("#FFB6C1"));
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
                            choice3Button.setBackgroundColor(Color.GREEN);
                            numCorrect += 1;
                            totalPoints += currentPoints;
                            statsPlayerRef.setValue(totalPoints);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                choice3Button.setBackgroundColor(Color.parseColor("#FFB6C1"));
                                currentQuestion += 1;
                                populateQuestion();
                            }, 1000);
                        } else {
                            choice3Button.setBackgroundColor(Color.RED);
                            finalCorrectAnswer.setBackgroundColor(Color.GREEN);
                            numIncorrect += 1;
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                finalCorrectAnswer.setBackgroundColor(Color.parseColor("#FFB6C1"));
                                choice3Button.setBackgroundColor(Color.parseColor("#FFB6C1"));
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
                            choice4Button.setBackgroundColor(Color.GREEN);
                            numCorrect += 1;
                            totalPoints += currentPoints;
                            statsPlayerRef.setValue(totalPoints);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                choice4Button.setBackgroundColor(Color.parseColor("#FFB6C1"));
                                currentQuestion += 1;
                                populateQuestion();
                            }, 1000);
                        } else {
                            choice4Button.setBackgroundColor(Color.RED);
                            finalCorrectAnswer.setBackgroundColor(Color.GREEN);
                            numIncorrect += 1;
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                finalCorrectAnswer.setBackgroundColor(Color.parseColor("#FFB6C1"));
                                choice4Button.setBackgroundColor(Color.parseColor("#FFB6C1"));
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
                    currentPoints -= 10;
                } else {
                    currentPoints = 300;
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