package com.example.sapling;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ToDo: needs to be connected to Categories Activity via onClick

public class ScienceCategory extends AppCompatActivity {

    private List<String> categories = new ArrayList<String>(
            Arrays.asList("Physics", "Biology", "Chemistry", "Astrophysics"));

    private List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.science_logo,
            R.drawable.biology_logo, R.drawable.chem_logo, R.drawable.space_logo));

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DisciplineAdapter adapter = new DisciplineAdapter(this, categories, images);
        recyclerView.setAdapter(adapter);
    }

    public class MainActivity extends AppCompatActivity {
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

