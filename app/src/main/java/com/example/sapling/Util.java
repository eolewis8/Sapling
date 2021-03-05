package com.example.sapling;

import com.google.firebase.auth.FirebaseAuth;

public class Util {
    public static String getCurrentUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            String emailID = firebaseAuth.getCurrentUser().getEmail();
            return emailID.substring(0, emailID.indexOf("@"));
        }
        return "";
    }
}
