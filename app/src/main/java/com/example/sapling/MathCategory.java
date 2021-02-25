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

public class MathCategory extends AppCompatActivity {

    private List<String> categories = new ArrayList<String>(
            Arrays.asList("Algebra", "Calculus", "Statistics", "Geometry"));


    private List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.algebra_logo,
            R.drawable.calc_logo, R.drawable.stats_logo, R.drawable.geometry_logo));

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        CustomAdapter adapter = new CustomAdapter(this, categories, images);
        recyclerView.setAdapter(adapter);
    }
}