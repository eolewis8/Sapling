package com.example.sapling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private EditText firstNameText;
    private EditText lastNameText;
    private Switch instructorSwitch;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String DEBUG_TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setLogo(R.drawable.sapling_logo);
        // getSupportActionBar().setDisplayUseLogoEnabled(true);
        // getSupportActionBar().setTitle("Sapling");
        emailText = findViewById(R.id.signup_email);
        emailText.requestFocus();
        passwordText = findViewById(R.id.signup_password);
        firstNameText = findViewById(R.id.first_name);
        lastNameText = findViewById(R.id.last_name);
        instructorSwitch = findViewById(R.id.switch1);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public void login(View view) {
        String emailID = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        Boolean isInstructorChecked = instructorSwitch.isChecked();
        Integer isInstructor = isInstructorChecked ? 1 : 0;
        // Check for invalid inputs
        if (emailID.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Email and password cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(emailID, password)
                .addOnCompleteListener(this, task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "SignUp unsuccessful: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        userRef = database.getReference("users/" + emailID.substring(0,
                                emailID.indexOf("@")));
                        Users user = new Users(firstName, lastName, emailID, isInstructor);
                        userRef.setValue(user);

                        Toast.makeText(getApplicationContext(), "User registered successfully!",
                                Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref =
                                getApplicationContext().getSharedPreferences("sapling",
                                        Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("isInstructor", isInstructorChecked);
                        editor.putString("playerID", emailID.substring(0,
                                    emailID.indexOf("@")));
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(),
                                CategoriesActivity.class));
                    }
                });
    }
}