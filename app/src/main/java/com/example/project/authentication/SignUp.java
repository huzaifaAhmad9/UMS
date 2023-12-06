/*
package com.example.project.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.User_Activity;
import com.example.project.User_Interface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText email, pass, id;
    Button btn;
    TextView login;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("students");

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        id = findViewById(R.id.id);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressbar);
        btn = findViewById(R.id.btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, User_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String enteredID = id.getText().toString();
                final String enteredEmail = email.getText().toString();
                String enteredPassword = pass.getText().toString();

                if (TextUtils.isEmpty(enteredID) || TextUtils.isEmpty(enteredEmail) || TextUtils.isEmpty(enteredPassword)) {
                    Toast.makeText(SignUp.this, "Email, ID, and password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(enteredID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                String studentName = dataSnapshot.child("name").getValue(String.class);
                                String studentId = dataSnapshot.child("id").getValue(String.class);
                                String studentGrade = dataSnapshot.child("grade").getValue(String.class);
                                boolean studentAttendance = dataSnapshot.child("present").getValue(Boolean.class);
                                double studentPercentage = dataSnapshot.child("percentage").getValue(double.class);


                                progressBar.setVisibility(View.VISIBLE);

                                mAuth.createUserWithEmailAndPassword(enteredEmail, enteredPassword)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                progressBar.setVisibility(View.GONE);
                                                if (task.isSuccessful()) {
                                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                                    if (currentUser != null) {
                                                        // Save additional information to the database using the user's UID
                                                        saveStudentInfoToDatabase(currentUser.getUid(), enteredEmail, enteredPassword, studentName, studentId,studentGrade,studentAttendance,studentPercentage);

                                                        Toast.makeText(SignUp.this, "Account Created Successfully !!!", Toast.LENGTH_SHORT).show();
                                                        // Change this line
                                                        Intent intent = new Intent(SignUp.this, User_Interface.class);
                                                        intent.putExtra("StudentName", studentName);
                                                        intent.putExtra("StudentId", studentId);
                                                        intent.putExtra("StudentAttendance", String.valueOf(studentAttendance));
                                                        intent.putExtra("StudentGrade", studentGrade);
                                                        intent.putExtra("StudentPercentage", String.valueOf(studentPercentage));

                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(SignUp.this, "User not found.", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(SignUp.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(SignUp.this, "No Student is registered against that ID.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(SignUp.this, "Error checking ID: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

//    private void saveStudentInfoToDatabase(String studentUid, String studentEmail, String studentPassword, String studentName, String studentId,String studentGrade,boolean studentAttendance,double studentPercentage) {
//        // Store additional information in the "student_info" node under the student's UID
//        DatabaseReference studentInfoRef = FirebaseDatabase.getInstance().getReference("student_info").child(studentUid);
//        studentInfoRef.child("email").setValue(studentEmail);
//        studentInfoRef.child("password").setValue(studentPassword); // Note: Storing passwords should be done securely in production applications.
//        studentInfoRef.child("name").setValue(studentName);
//        studentInfoRef.child("id").setValue(studentId);
//        studentInfoRef.child("grade").setValue(studentGrade);
//        studentInfoRef.child("percentage").setValue(studentPercentage);
//        studentInfoRef.child("attendance").setValue(studentAttendance);
//
//
//
//
//
//
//        // -----------------------------
//
//
//
//
//    }



    private void saveStudentInfoToDatabase(String studentUid, String studentEmail, String studentPassword, String studentName, String studentId, String studentGrade, Boolean studentAttendance, Double studentPercentage) {
        // Store additional information in the "student_info" node under the student's UID
        DatabaseReference studentInfoRef = FirebaseDatabase.getInstance().getReference("student_info").child(studentUid);
        studentInfoRef.child("email").setValue(studentEmail);
        studentInfoRef.child("password").setValue(studentPassword); // Note: Storing passwords should be done securely in production applications.
        studentInfoRef.child("name").setValue(studentName);
        studentInfoRef.child("id").setValue(studentId);
        studentInfoRef.child("grade").setValue(studentGrade);

        // Check for null values in attendance and set a default value if needed
        if (studentAttendance == null) {
            studentAttendance = false; // Set a default value, e.g., absent
        }
        studentInfoRef.child("attendance").setValue(studentAttendance);

        // Check for null values in percentage and set a default value if needed
        if (studentPercentage == null) {
            studentPercentage = 0.0; // Set a default value, e.g., 0.0
        }
        studentInfoRef.child("percentage").setValue(studentPercentage);

        // Add more fields as needed
    }













}

*/



package com.example.project.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.User_Activity;
import com.example.project.User_Interface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText email, pass, id;
    Button btn;
    TextView login;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("students");

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        id = findViewById(R.id.id);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressbar);
        btn = findViewById(R.id.btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, User_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String enteredID = id.getText().toString();
                final String enteredEmail = email.getText().toString();
                String enteredPassword = pass.getText().toString();

                if (TextUtils.isEmpty(enteredID) || TextUtils.isEmpty(enteredEmail) || TextUtils.isEmpty(enteredPassword)) {
                    Toast.makeText(SignUp.this, "Email, ID, and password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(enteredID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                String studentName = dataSnapshot.child("name").getValue(String.class);
                                String studentId = dataSnapshot.child("id").getValue(String.class);
                                String studentGrade = dataSnapshot.child("grade").getValue(String.class);
                                Boolean studentAttendance = dataSnapshot.child("present").getValue(Boolean.class);
                                Double studentPercentage = dataSnapshot.child("percentage").getValue(Double.class);

                                // Handle null values
                                if (studentAttendance == null) {
                                    studentAttendance = false; // Set a default value, e.g., absent
                                }

                                if (studentPercentage == null) {
                                    studentPercentage = 0.0; // Set a default value, e.g., 0.0
                                }

                                progressBar.setVisibility(View.VISIBLE);

                                Boolean finalStudentAttendance = studentAttendance;
                                Double finalStudentPercentage = studentPercentage;
                                mAuth.createUserWithEmailAndPassword(enteredEmail, enteredPassword)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                progressBar.setVisibility(View.GONE);
                                                if (task.isSuccessful()) {
                                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                                    if (currentUser != null) {
                                                        // Save additional information to the database using the user's UID
                                                        saveStudentInfoToDatabase(currentUser.getUid(), enteredEmail, enteredPassword, studentName, studentId, studentGrade, finalStudentAttendance, finalStudentPercentage);

                                                        Toast.makeText(SignUp.this, "Account Created Successfully !!!", Toast.LENGTH_SHORT).show();
                                                        // Change this line
                                                        Intent intent = new Intent(SignUp.this, User_Interface.class);
                                                        intent.putExtra("StudentName", studentName);
                                                        intent.putExtra("StudentId", studentId);
                                                        intent.putExtra("StudentAttendance", String.valueOf(finalStudentAttendance));
                                                        intent.putExtra("StudentGrade", studentGrade);
                                                        intent.putExtra("StudentPercentage", String.valueOf(finalStudentPercentage));

                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(SignUp.this, "User not found.", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(SignUp.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(SignUp.this, "No Student is registered against that ID.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(SignUp.this, "Error checking ID: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void saveStudentInfoToDatabase(String studentUid, String studentEmail, String studentPassword, String studentName, String studentId, String studentGrade, Boolean studentAttendance, Double studentPercentage) {
        // Store additional information in the "student_info" node under the student's UID
        DatabaseReference studentInfoRef = FirebaseDatabase.getInstance().getReference("student_info").child(studentUid);
        studentInfoRef.child("email").setValue(studentEmail);
        studentInfoRef.child("password").setValue(studentPassword); // Note: Storing passwords should be done securely in production applications.
        studentInfoRef.child("name").setValue(studentName);
        studentInfoRef.child("id").setValue(studentId);
        studentInfoRef.child("grade").setValue(studentGrade);

        // Check for null values in attendance and set a default value if needed
        if (studentAttendance == null) {
            studentAttendance = false; // Set a default value, e.g., absent
        }
        studentInfoRef.child("attendance").setValue(studentAttendance);

        // Check for null values in percentage and set a default value if needed
        if (studentPercentage == null) {
            studentPercentage = 0.0; // Set a default value, e.g., 0.0
        }
        studentInfoRef.child("percentage").setValue(studentPercentage);

        // Add more fields as needed
    }
}






