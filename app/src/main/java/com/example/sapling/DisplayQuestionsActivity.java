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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayQuestionsActivity extends AppCompatActivity {

//    private List<String> questions = new ArrayList<String>(
//            Arrays.asList("When __________ callback is called by Android, app is visible but has " +
//                    "no focus", "Engineering", "Maths", "Technology"));
    private List<String> questions = new ArrayList<>();
    private List<Integer> images = new ArrayList<>();
    private String subject;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_questions);
        subject = getIntent().getStringExtra("subject");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        QuestionsDbHelper dbHelper = new QuestionsDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {QuestionsInfoContract.Questions.QUESTION};
        String title = "Computer";
        String selection = QuestionsInfoContract.Questions.QUESTION_SUBJECT + " LIKE? AND " +
                QuestionsInfoContract.Questions.QUESTION_TITLE + " LIKE?";
        String[] selectionArgs = {"%" + subject + "%", "%" + title + "%"};
        Cursor cursor = db.query(QuestionsInfoContract.Questions.TABLE_NAME,columns,
                selection,selectionArgs,null, null, null);
        while(cursor.moveToNext()) {
            String question =
                    cursor.getString(cursor.getColumnIndex(QuestionsInfoContract.Questions.QUESTION));
            questions.add(question);
            images.add(R.drawable.edit_icon);
        }
        db.close();
        QuestionAdapter adapter = new QuestionAdapter(this, questions, images);
        recyclerView.setAdapter(adapter);
    }

    public void addQuestion(View view) {

        Intent intent = new Intent(this, AddQuestionsActivity.class);
        intent.putExtra("subject", subject);
        startActivity(intent);
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
    private List<String> questions;
    private List<Integer> images;


    public QuestionAdapter(Activity context, List<String> questions, List<Integer> images) {
        this.context = context;
        this.questions = questions;
        this.images = images;
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
            this.context.startActivity(intent);
        });
        holder.name.setText(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}