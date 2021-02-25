package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    FirebaseAuth firebaseAuth;
    private static final String DEBUG_TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.et_email);
        emailText.requestFocus();
        passwordText = findViewById(R.id.et_password);

    }

    public void login(View view) {
        String emailID = emailText.getText().toString();
        String password = passwordText.getText().toString();
        Log.i(DEBUG_TAG, "User ID : " + emailID);
        Log.i(DEBUG_TAG, "Password: " + password);
        firebaseAuth.signInWithEmailAndPassword(emailID, password)
                .addOnCompleteListener(this, task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Invalid email ID/Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Login successful", Toast.LENGTH_SHORT).show();
                        UserDbHelper dbHelper = new UserDbHelper(getApplicationContext());
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        String[] columns = {UsersInfoContract.Users.IS_INSTRUCTOR};
                        String selection = UsersInfoContract.Users.USER_EMAIL + " LIKE? ";
                        String[] selectionArgs = {"%" + emailID + "%"};
                        Cursor cursor = db.query(UsersInfoContract.Users.TABLE_NAME,columns,
                                selection,selectionArgs,null, null, null);
                        cursor.moveToFirst();
                        int isInstructor =
                                cursor.getInt(cursor.getColumnIndex(UsersInfoContract.Users.IS_INSTRUCTOR));
                        Log.i(DEBUG_TAG, "Instructor : " + isInstructor);
                        db.close();
                        SharedPreferences sharedPref =
                                getApplicationContext().getSharedPreferences("sapling",
                                        Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("isInstructor", isInstructor == 1);
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

}