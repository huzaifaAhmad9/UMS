/*
package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project.authentication.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Activity extends AppCompatActivity {

    LottieAnimationView animation;
    EditText email, pass;
    Button btn;
    TextView signup;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference studentInfoReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mAuth = FirebaseAuth.getInstance();
        studentInfoReference = FirebaseDatabase.getInstance().getReference("student_info");

        animation = findViewById(R.id.animation);
        progressBar = findViewById(R.id.progressbar);
        btn = findViewById(R.id.btn);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        signup = findViewById(R.id.signup);
        animation.setAnimation(R.raw.login);
        animation.playAnimation();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Activity.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


  */
/*      // Check if the user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, go directly to User_Interface
            Intent intent = new Intent(Staff.this, T_Interface.class);
            startActivity(intent);
            finish();
        }*//*



    }

    private void loginUser() {
        String e = email.getText().toString().trim();
        String p = pass.getText().toString().trim();

        if (e.isEmpty() || p.isEmpty()) {
            Toast.makeText(User_Activity.this, "Email and password are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(User_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Login successful, fetch additional information
                            fetchStudentInfoFromDatabase(e);
                        } else {
                            // Login failed, show an error message.
                            Toast.makeText(User_Activity.this, "Login failed. Check your email and password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchStudentInfoFromDatabase(final String userEmail) {
        studentInfoReference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String studentName = snapshot.child("name").getValue(String.class);
                    // Opening activity
                    Intent intent = new Intent(User_Activity.this, User_Interface.class);
                    intent.putExtra("StudentName", studentName);
                    startActivity(intent);
                    finish();
                    return; // Exit the loop after finding the matching email
                }
                // If no matching email is found in the database
                Toast.makeText(User_Activity.this, "No user information found.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(User_Activity.this, "Error fetching user information: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


*/





package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project.authentication.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Activity extends AppCompatActivity {

    LottieAnimationView animation;
    EditText email, pass;
    Button btn;
    TextView signup;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference studentInfoReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mAuth = FirebaseAuth.getInstance();
        studentInfoReference = FirebaseDatabase.getInstance().getReference("student_info");

        animation = findViewById(R.id.animation);
        progressBar = findViewById(R.id.progressbar);
        btn = findViewById(R.id.btn);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        signup = findViewById(R.id.signup);
        animation.setAnimation(R.raw.login);
        animation.playAnimation();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Activity.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String e = email.getText().toString().trim();
        String p = pass.getText().toString().trim();

        if (e.isEmpty() || p.isEmpty()) {
            Toast.makeText(User_Activity.this, "Email and password are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(User_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Login successful, fetch additional information
                            fetchStudentInfoFromDatabase(e);
                        } else {
                            // Login failed, show an error message.
                            Toast.makeText(User_Activity.this, "Login failed. Check your email and password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


//    private void fetchStudentInfoFromDatabase(final String userEmail) {
//        studentInfoReference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                boolean userFound = false;
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    userFound = true;
//                    String studentName = snapshot.child("name").getValue(String.class);
//                    String studentId = snapshot.child("id").getValue(String.class);
//                    String studentGrade = snapshot.child("grade").getValue(String.class);
//                    double studentPercentage = snapshot.child("percentage").getValue(Double.class); // Corrected to Double.class
//                    Boolean studentAttendance = snapshot.child("present").getValue(Boolean.class);
//
//                    // Check if studentAttendance is null, and set a default value
//                    boolean isPresent = studentAttendance != null && studentAttendance;
//
//                    // Opening activity
//                    Intent intent = new Intent(User_Activity.this, User_Interface.class);
//                    intent.putExtra("StudentName", studentName);
//                    intent.putExtra("StudentId", studentId);
//                    intent.putExtra("StudentAttendance", isPresent ? "Present" : "Absent");
//                    intent.putExtra("StudentGrade", studentGrade);
//                    intent.putExtra("StudentPercentage", String.valueOf(studentPercentage));
//                    startActivity(intent);
//                    finish();
//                }
//
//                if (!userFound) {
//                    // If no matching email is found in the database
//                    Toast.makeText(User_Activity.this, "No user information found.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(User_Activity.this, "Error fetching user information: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }



    private void fetchStudentInfoFromDatabase(final String userEmail) {
        studentInfoReference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean userFound = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userFound = true;
                    String studentName = snapshot.child("name").getValue(String.class);
                    String studentId = snapshot.child("id").getValue(String.class);
                    String studentGrade = snapshot.child("grade").getValue(String.class);
                    double studentPercentage = snapshot.child("percentage").getValue(Double.class); // Corrected to Double.class
                    Boolean studentAttendance = snapshot.child("present").getValue(Boolean.class);

                    // Check if studentAttendance is null, and set a default value
                    boolean isPresent = studentAttendance != null && studentAttendance;

                    // Opening activity
                    Intent intent = new Intent(User_Activity.this, User_Interface.class);
                    intent.putExtra("StudentName", studentName);
                    intent.putExtra("StudentId", studentId);
                    intent.putExtra("StudentAttendance", isPresent); // Pass boolean directly
                    intent.putExtra("StudentGrade", studentGrade);
                    intent.putExtra("StudentPercentage", String.valueOf(studentPercentage));
                    startActivity(intent);
                    finish();
                }

                if (!userFound) {
                    // If no matching email is found in the database
                    Toast.makeText(User_Activity.this, "No user information found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(User_Activity.this, "Error fetching user information: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}










