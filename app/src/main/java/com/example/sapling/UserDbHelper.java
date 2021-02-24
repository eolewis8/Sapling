package com.example.sapling;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " +
            UsersInfoContract.Users.TABLE_NAME + "(" +
            UsersInfoContract.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            UsersInfoContract.Users.USER_FIRST_NAME + " TEXT NOT NULL, " +
            UsersInfoContract.Users.USER_LAST_NAME + " TEXT NOT NULL, " +
            UsersInfoContract.Users.USER_EMAIL + " TEXT NOT NULL, " +
            UsersInfoContract.Users.IS_INSTRUCTOR + " INTEGER NOT NULL" + ")";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +
            UsersInfoContract.Users.TABLE_NAME;


    public UserDbHelper(Context context){
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
