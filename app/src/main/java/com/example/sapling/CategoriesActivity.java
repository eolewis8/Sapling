package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private List<String> categories = new ArrayList<String>(
            Arrays.asList("Science", "Engineering", "Maths", "Technology"));

    private List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.sapling_logo,
            R.drawable.sapling_logo, R.drawable.sapling_logo, R.drawable.sapling_logo));

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        CustomAdapter adapter = new CustomAdapter(this, categories, images);
        recyclerView.setAdapter(adapter);
    }
}