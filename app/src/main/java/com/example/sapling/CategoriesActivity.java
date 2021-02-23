package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.viewHolder> {

    private static final String DEBUG_TAG = "Adapter";

    public static class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public viewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.category_image);
            this.name = (TextView) itemView.findViewById(R.id.category_name);
        }
    }

    private Activity context;
    private List<String> categories;
    private List<Integer> images;

    public CustomAdapter(Activity context, List<String> categories, List<Integer> images) {
        this.context = context;
        this.categories = categories;
        this.images = images;
        Log.i(DEBUG_TAG, "Categories : " + categories.toString());
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_items, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.viewHolder holder, int position) {
        holder.image.setImageResource(images.get(position));
        holder.name.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}