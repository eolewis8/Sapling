package com.example.sapling;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "questions.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " +
            QuestionsInfoContract.Questions.TABLE_NAME + "(" +
            QuestionsInfoContract.Questions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            QuestionsInfoContract.Questions.QUESTION_SUBJECT + " TEXT NOT NULL, " +
            QuestionsInfoContract.Questions.QUESTION_TITLE + " TEXT NOT NULL, " +
            QuestionsInfoContract.Questions.QUESTION + " TEXT NOT NULL, " +
            QuestionsInfoContract.Questions.QUESTION_CHOICE1 + " TEXT NOT NULL, " +
            QuestionsInfoContract.Questions.QUESTION_CHOICE2 + " TEXT NOT NULL, " +
            QuestionsInfoContract.Questions.QUESTION_CHOICE3 + " TEXT NOT NULL, " +
            QuestionsInfoContract.Questions.QUESTION_CHOICE4 + " TEXT NOT NULL, " +
            QuestionsInfoContract.Questions.QUESTION_ANSWER + " TEXT NOT NULL " + ")";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +
            QuestionsInfoContract.Questions.TABLE_NAME;


    public QuestionsDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
