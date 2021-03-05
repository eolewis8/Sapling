package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayQuestionsActivity extends AppCompatActivity {

    private List<Question> questions = new ArrayList<>();
    private List<Integer> images = new ArrayList<>();
    private String subject;
    private String title;
    private static final String DEBUG_TAG = "DisplayQuestionsActivity";
    FirebaseDatabase database;
    DatabaseReference questionRef;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_questions);
        subject = getIntent().getStringExtra("Subject");
        title = getIntent().getStringExtra("Title");
        Log.i(DEBUG_TAG, "Chosen subject is : " + subject);
        Log.i(DEBUG_TAG, "Chosen title is : " + title);
        database = FirebaseDatabase.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getQuestionFromDB();
        QuestionAdapter adapter = new QuestionAdapter(this, questions, images, subject,
                title);
        recyclerView.setAdapter(adapter);
    }

    private void getQuestionFromDB() {
        questionRef = database.getReference("Questions/" + subject + "/" + title);
        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questions.clear();
                images.clear();
                Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                for (DataSnapshot data: dataSnapshots) {
                    Question question = data.getValue(Question.class);
                    questions.add(question);
                    images.add(R.drawable.edit_icon);
                }
                refreshRecyclerView();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void refreshRecyclerView() {
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            QuestionAdapter adapter = (QuestionAdapter) recyclerView.getAdapter();
            adapter.notifyNewDataAdded(questions, images);
        }
    }

    public void addQuestion(View view) {
        Intent intent = new Intent(this, AddQuestionsActivity.class);
        intent.putExtra("Subject", subject);
        intent.putExtra("Title", title);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
}

class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private static final String DEBUG_TAG = "Adapter";

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.edit_icon);
            this.name = (TextView) itemView.findViewById(R.id.question);
        }
    }

    private Activity context;
    private List<Question> questions;
    private List<Integer> images;
    private String subject;
    private String title;


    public QuestionAdapter(Activity context, List<Question> questions, List<Integer> images,
                           String subject, String title) {
        this.context = context;
        this.questions = questions;
        this.images = images;
        this.subject = subject;
        this.title = title;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.question_list, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.QuestionViewHolder holder, int position) {
        holder.image.setImageResource(images.get(position));
        holder.image.setClickable(true);
        holder.image.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditQuestions.class);
            intent.putExtra("ID", position);
            intent.putExtra("Question", questions.get(position).getQuestion());
            intent.putExtra("Answer", questions.get(position).getAnswer());
            intent.putExtra("Choice1", questions.get(position).getChoice1());
            intent.putExtra("Choice2", questions.get(position).getChoice2());
            intent.putExtra("Choice3", questions.get(position).getChoice3());
            intent.putExtra("Choice4", questions.get(position).getChoice4());
            intent.putExtra("Subject", subject);
            intent.putExtra("Title", title);
            this.context.startActivity(intent);
        });
        holder.name.setText(questions.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void notifyNewDataAdded(List<Question> questions, List<Integer> images)
    {
        this.questions = questions;
        this.images = images;
        notifyDataSetChanged();
    }
}