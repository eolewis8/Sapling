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

class DisciplineAdapter extends RecyclerView.Adapter<DisciplineAdapter.CategoryViewHolder> {

    private static final String DEBUG_TAG = "Adapter";
    private static Boolean isInstructor;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        Activity context;

        public CategoryViewHolder(View itemView, Activity context) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.category_image);
            this.name = (TextView) itemView.findViewById(R.id.category_name);
            this.context = context;
            itemView.setOnClickListener(v -> {
                SharedPreferences sharedPref =
                        context.getSharedPreferences("sapling", Context.MODE_PRIVATE);
                isInstructor = sharedPref.getBoolean("isInstructor", false);

                if (isInstructor) {
                    String subject = context.getIntent().getStringExtra("Subject");
                    Intent intent = new Intent(context, DisplayQuestionsActivity.class);
                    intent.putExtra("Subject", subject);
                    intent.putExtra("Title", name.getText().toString());
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, SingleOrMultiActivity.class);
                    String subject = context.getIntent().getStringExtra("Subject");
                    intent.putExtra("Subject", subject);
                    intent.putExtra("Title", name.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }

    private Activity context;
    private List<String> categories;
    private List<Integer> images;

    public DisciplineAdapter(Activity context, List<String> categories, List<Integer> images) {
        this.context = context;
        this.categories = categories;
        this.images = images;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_items, parent, false);
        return new CategoryViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.image.setImageResource(images.get(position));
        holder.name.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}

