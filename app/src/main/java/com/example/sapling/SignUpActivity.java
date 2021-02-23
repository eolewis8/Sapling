package com.example.sapling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    FirebaseAuth firebaseAuth;
    private static final String DEBUG_TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.sapling_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Sapling");
        emailText = findViewById(R.id.signup_email);
        emailText.requestFocus();
        passwordText = findViewById(R.id.signup_password);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String emailID = emailText.getText().toString();
        String password = passwordText.getText().toString();
        Log.i(DEBUG_TAG, "User ID : " + emailID);
        Log.i(DEBUG_TAG, "Password: " + password);
        firebaseAuth.createUserWithEmailAndPassword(emailID, password)
                .addOnCompleteListener(this, task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "SignUp unsuccessful: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "User registered successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),
                                QuestionsActivity.class));
                    }
                });
    }
}