package com.example.project.authentication;

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
import com.example.project.R;
import com.example.project.teacher.T_Interface;
import com.example.project.teacher.T_SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Staff extends AppCompatActivity {

    LottieAnimationView animation;
    EditText email, pass;
    Button btn;
    TextView signup;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference teacherInfoReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        mAuth = FirebaseAuth.getInstance();
        teacherInfoReference = FirebaseDatabase.getInstance().getReference("teacher_info");

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
                Intent intent = new Intent(Staff.this, T_SignUp.class);
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


  /*      // Check if the user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, go directly to User_Interface
            Intent intent = new Intent(Staff.this, T_Interface.class);
            startActivity(intent);
            finish();
        }*/





    }

    private void loginUser() {
        String e = email.getText().toString().trim();
        String p = pass.getText().toString().trim();

        if (e.isEmpty() || p.isEmpty()) {
            Toast.makeText(Staff.this, "Email and password are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(Staff.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Login successful, fetch additional information
                            fetchTeacherInfoFromDatabase(e);
                        } else {
                            // Login failed, show an error message.
                            Toast.makeText(Staff.this, "Login failed. Check your email and password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchTeacherInfoFromDatabase(final String userEmail) {
        teacherInfoReference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String teacherName = snapshot.child("name").getValue(String.class);
                    // Opening activity
                    Intent intent = new Intent(Staff.this, T_Interface.class);
                    intent.putExtra("TeacherName", teacherName);
                    startActivity(intent);
                    finish();
                    return; // Exit the loop after finding the matching email
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Staff.this, "Error fetching user information: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
