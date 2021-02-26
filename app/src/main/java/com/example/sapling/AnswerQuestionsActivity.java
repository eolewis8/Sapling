package com.example.sapling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnswerQuestionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<String> categories = new ArrayList<String>(
            Arrays.asList("Answer 1", " Answer 2", " Answer 3", "Answer 4"));

    // get rid of images
    private List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.c_logo,
            R.drawable.java_logo, R.drawable.python_logo, R.drawable.kotlin_logo));


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DisciplineAdapter adapter = new DisciplineAdapter(this, categories, images);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}