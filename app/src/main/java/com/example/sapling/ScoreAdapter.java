package com.example.sapling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.CategoryViewHolder> {

    private static final String DEBUG_TAG = "Adapter";

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView score;
        Activity context;

        public CategoryViewHolder(View itemView, Activity context) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.category_image);
            this.name = (TextView) itemView.findViewById(R.id.player_name);
            this.score = itemView.findViewById(R.id.score);
            this.context = context;
        }
    }

    private Activity context;
    private List<String> categories;
    private List<Integer> scores;
    private List<Integer> images;

    public ScoreAdapter(Activity context, List<String> categories, List<Integer> images,
                        List<Integer> scores) {
        this.context = context;
        this.categories = categories;
        this.images = images;
        this.scores = scores;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.winners_list, parent, false);
        return new CategoryViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.image.setImageResource(images.get(position));
        holder.name.setText(categories.get(position));
        holder.score.setText("Score: " + scores.get(position));
    }

    @Override
    public int getItemCount() {
        return Math.min(categories.size(), 3);
    }

    public void notifyNewDataAdded(List<String> categories, List<Integer> images,
                                   List<Integer> scores)
    {
        this.categories = categories;
        this.images = images;
        this.scores = scores;
        notifyDataSetChanged();
    }
}
