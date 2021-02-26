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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayQuestionsActivity extends AppCompatActivity {

    private List<Questions> questions = new ArrayList<>();
    private List<Integer> images = new ArrayList<>();
    private String subject;
    private String title;
    private static final String DEBUG_TAG = "DisplayQuestionsActivity";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_questions);
        subject = getIntent().getStringExtra("Subject");
        title = getIntent().getStringExtra("Title");
        Log.i(DEBUG_TAG, "Chosen subject is : " + subject);
        Log.i(DEBUG_TAG, "Chosen title is : " + title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        QuestionsDbHelper dbHelper = new QuestionsDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = QuestionsInfoContract.Questions.QUESTION_SUBJECT + " LIKE? AND " +
                QuestionsInfoContract.Questions.QUESTION_TITLE + " LIKE?";
        String[] selectionArgs = {"%" + subject + "%", "%" + title + "%"};
        Cursor cursor = db.query(QuestionsInfoContract.Questions.TABLE_NAME,null,
                selection, selectionArgs,null, null, null);
        while(cursor.moveToNext()) {
            int ID = cursor.getInt(cursor.getColumnIndex(QuestionsInfoContract.Questions._ID));
            String question = cursor.getString(cursor.getColumnIndex(
                    QuestionsInfoContract.Questions.QUESTION));
            String answer = cursor.getString(cursor.getColumnIndex(
                    QuestionsInfoContract.Questions.QUESTION_ANSWER));
            String choice1 = cursor.getString(cursor.getColumnIndex(
                    QuestionsInfoContract.Questions.QUESTION_CHOICE1));
            String choice2 = cursor.getString(cursor.getColumnIndex(
                    QuestionsInfoContract.Questions.QUESTION_CHOICE2));
            String choice3 = cursor.getString(cursor.getColumnIndex(
                    QuestionsInfoContract.Questions.QUESTION_CHOICE3));
            String choice4 = cursor.getString(cursor.getColumnIndex(
                    QuestionsInfoContract.Questions.QUESTION_CHOICE4));
            questions.add(new Questions(ID, question, answer, choice1, choice2, choice3, choice4));
            images.add(R.drawable.edit_icon);
        }
        db.close();
        QuestionAdapter adapter = new QuestionAdapter(this, questions, images, subject,
                title);
        recyclerView.setAdapter(adapter);
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
    private List<Questions> questions;
    private List<Integer> images;
    private String subject;
    private String title;


    public QuestionAdapter(Activity context, List<Questions> questions, List<Integer> images,
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
            intent.putExtra("ID", questions.get(position).getID());
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
}