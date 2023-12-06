package com.example.project.offline;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    private static DatabaseReference databaseReference;

    public static DatabaseReference getDatabaseReference() {
        if (databaseReference == null) {
            // Enable Firebase offline persistence
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }
}












/*
// Instead of this:
// databaseReference = FirebaseDatabase.getInstance().getReference("students");

// Use the singleton:
databaseReference = FirebaseUtils.getDatabaseReference().child("students");
*/















