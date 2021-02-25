package com.example.sapling;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ToDo: needs to be connected to Categories Activity via onClick

public class TechCategory extends AppCompatActivity {

    private List<String> categories = new ArrayList<String>(
            Arrays.asList("C", "Java", "Python", "Kotlin"));

    // change images
    private List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.science_logo,
            R.drawable.biology_logo, R.drawable.chem_logo, R.drawable.space_logo));

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        CustomAdapter adapter = new CustomAdapter(this, categories, images);
        recyclerView.setAdapter(adapter);
    }
}
