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

public class EngrCategory extends AppCompatActivity {

    private List<String> categories = new ArrayList<String>(
            Arrays.asList("Electrical", "Civil", "Mechanical", "Industrial"));


    private List<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.ee_logo,
            R.drawable.civil_logo, R.drawable.engr_logo, R.drawable.industry_logo));

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engr);
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
