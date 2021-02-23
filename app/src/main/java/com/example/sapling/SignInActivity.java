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
                        startActivity(new Intent(getApplicationContext(), QuestionsActivity.class));
                    }
                });
    }
}