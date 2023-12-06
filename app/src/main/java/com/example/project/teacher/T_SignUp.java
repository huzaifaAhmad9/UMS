package com.example.project.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.project.authentication.Staff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class T_SignUp extends AppCompatActivity {

    EditText email, pass, id;
    Button btn;
    TextView login;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsign_up);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("teachers");

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        id = findViewById(R.id.id);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressbar);
        btn = findViewById(R.id.btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(T_SignUp.this, Staff.class);
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
                    Toast.makeText(T_SignUp.this, "Email, ID, and password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(enteredID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String teacherName = dataSnapshot.child("name").getValue(String.class);
                                progressBar.setVisibility(View.VISIBLE);

                                mAuth.createUserWithEmailAndPassword(enteredEmail, enteredPassword)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                                                progressBar.setVisibility(View.GONE);
                                                if (task.isSuccessful()) {
                                                    // Save additional information to the database
                                                    saveTeacherInfoToDatabase(enteredID, enteredEmail, enteredPassword, teacherName);

                                                    Toast.makeText(T_SignUp.this, "Account Created Successfully !!!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(T_SignUp.this, T_Interface.class);
                                                    intent.putExtra("TeacherName", teacherName);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(T_SignUp.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(T_SignUp.this, "No Teacher is registered against that ID.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(T_SignUp.this, "Error checking ID: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



/*        // Check if the user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, go directly to User_Interface
            Intent intent = new Intent(T_SignUp.this, T_Interface.class);
            startActivity(intent);
            finish();
        }*/



    }

    private void saveTeacherInfoToDatabase(String teacherID, String teacherEmail, String teacherPassword, String teacherName) {
        // Store additional information in the "teacher_info" node under the teacher's ID
        DatabaseReference teacherInfoRef = FirebaseDatabase.getInstance().getReference("teacher_info").child(teacherID);
        teacherInfoRef.child("email").setValue(teacherEmail);
        teacherInfoRef.child("password").setValue(teacherPassword); // Note: Storing passwords should be done securely in production applications.
        teacherInfoRef.child("name").setValue(teacherName);
        // Add more fields as needed
    }
}
