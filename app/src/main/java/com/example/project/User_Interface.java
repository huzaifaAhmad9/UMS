/*
package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Interface extends AppCompatActivity {
    TextView text, studentDetailsText;
    CardView result;
    FirebaseAuth mAuth;
    DatabaseReference studentsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);
        text = findViewById(R.id.text);
        result = findViewById(R.id.result);
        mAuth = FirebaseAuth.getInstance();

        // Initialize DatabaseReference
        studentsReference = FirebaseDatabase.getInstance().getReference().child("students");


        // getting student name from previous activity
        String studentName = getIntent().getStringExtra("StudentName");
        text = findViewById(R.id.text);
        text.setText(studentName);


        // set on click listener for result activity
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve the logged-in user's ID or name
                String loggedInUserId = mAuth.getCurrentUser().getUid(); // Assuming you're using Firebase Authentication

                // Retrieve the student details for the logged-in user from the "students" node
                studentsReference.child(loggedInUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Student student = dataSnapshot.getValue(Student.class);
                            if (student != null) {
                                // Pass the Student object to the UserGrades activity
                                Intent intent = new Intent(User_Interface.this, UserGrades.class);
                                intent.putExtra("StudentDetails", student);
                                startActivity(intent);
                            } else {
                                Toast.makeText(User_Interface.this, "No student details found for the current user.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(User_Interface.this, "No data found for the current user.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(User_Interface.this, "Error fetching student details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}















               */
/* // Retrieve the logged-in user's ID or name
                String loggedInUserId = mAuth.getCurrentUser().getUid(); // Assuming you're using Firebase Authentication

                // Retrieve the student details for the logged-in user from the "students" node
                studentsReference.child(loggedInUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Student student = dataSnapshot.getValue(Student.class);
                            if (student != null) {
                                showStudentDetailsDialog(student);
                            } else {
                                Toast.makeText(User_Interface.this, "No student details found for the current user.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(User_Interface.this, "No data found for the current user.", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(User_Interface.this, "Error fetching student details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showStudentDetailsDialog(Student student) {
        String detailsMessage =
                "ID: " + student.getId() + "\n" +
                        "Name: " + student.getName() + "\n" +
                        "City: " + student.getCity() + "\n" +
                        "Department: " + student.getDepartment() + "\n" +
                        "Father: " + student.getFather() + "\n" +
                        "Gender: " + student.getGender() + "\n" +
                        "Present: " + student.isPresent();

        studentDetailsText.setText(detailsMessage);
    }
*//*

























              */
/*  Intent intent = new Intent(User_Interface.this,UserGrades.class);
                startActivity(intent);
                finish();*//*









*/



package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.student.UserAttendance;
import com.example.project.student.UserGrades;
import com.example.project.student.UserLecs;
import com.example.project.user.UserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Interface extends AppCompatActivity {
    private static final String TAG = "User_Interface";

    TextView text,stu_id,attendance,percentage1,gra;
    CircleImageView circleImageView;
    CardView result,info,lecture,roll,invoice;
    Button btn;
    FirebaseAuth mAuth;
    DatabaseReference studentsReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);
        lecture = findViewById(R.id.lecture);
//        roll = findViewById(R.id.roll);
        invoice = findViewById(R.id.invoice);
        stu_id = findViewById(R.id.stu_id);
        attendance = findViewById(R.id.attendance);
        percentage1 = findViewById(R.id.percentage1);
        gra = findViewById(R.id.gra);
//        attend = findViewById(R.id.attend);
        text = findViewById(R.id.text);
//        result = findViewById(R.id.result);
        info = findViewById(R.id.info);
        btn = findViewById(R.id.btn);
        circleImageView = findViewById(R.id.circleImageView);
        mAuth = FirebaseAuth.getInstance();

        // Initialize DatabaseReference
        studentsReference = FirebaseDatabase.getInstance().getReference().child("students");

        // getting student name from previous activity
        String studentName = getIntent().getStringExtra("StudentName");
        text.setText(studentName);
        // getting student id from previous activity
        String studentId = getIntent().getStringExtra("StudentId");
        stu_id.setText(studentId);
        // getting student attendance from previous activity
        String studentAttendance = getIntent().getStringExtra("StudentAttendance");
        attendance.setText(studentAttendance);
        // getting student grade from previous activity
        String studentGrade = getIntent().getStringExtra("StudentGrade");
        gra.setText(studentGrade);
        // getting student percentage from previous activity
        String studentPercentage = getIntent().getStringExtra("StudentPercentage");
        double percentageValue = Double.parseDouble(studentPercentage);
        percentage1.setText(percentageValue + " %");






        // set on click listener for result activity
//        result.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Retrieve the logged-in user's ID
//
//                String loggedInUserId = mAuth.getCurrentUser().getUid();
//                // Log user ID for debugging
//                Log.d(TAG, "User ID: " + loggedInUserId);
//
//                // Retrieve the student details for the logged-in user from the "students" node
//                studentsReference.child(loggedInUserId).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Log dataSnapshot for debugging
//                        Log.d(TAG, "DataSnapshot: " + dataSnapshot);
//
//                        if (dataSnapshot != null && dataSnapshot.exists()) {
//                            Student student = dataSnapshot.getValue(Student.class);
//
//                            if (student != null) {
//                                // Pass the Student object to the UserGrades activity
//                                Intent intent = new Intent(User_Interface.this, UserGrades.class);
//                                intent.putExtra("StudentDetails", student);
//                                startActivity(intent);
//                            } else {
//                                Toast.makeText(User_Interface.this, "No student details found for the current user.", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(User_Interface.this, "No data found for the current user.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.e(TAG, "Error fetching student details: " + databaseError.getMessage());
//                        Toast.makeText(User_Interface.this, "Error fetching student details.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });






        // set o click on info
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Interface.this, UserDetails.class);
                startActivity(intent);
            }
        });


        // setting lecs
        lecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Interface.this, UserLecs.class);
                startActivity(intent);
            }
        });

        // setting roll no slip
//        roll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(User_Interface.this, UserGrades.class);
//                startActivity(intent);
//            }
//        });

        // setting invoices
        invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Interface.this, UserAttendance.class);
                startActivity(intent);
            }
        });








        // setting logout button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }

            private void confirmDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(User_Interface.this);
                builder.setMessage("Do you want to LogOut?");
                builder.setCancelable(false);
                builder.setIcon(R.drawable.baseline_logout_24);
                builder.setTitle("Log-Out");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(User_Interface.this, "LOGGING-OUT !!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(User_Interface.this, User_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled the delete operation
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // setting update profile
//        circleImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(User_Interface.this,UserAttendance.class);
//                // Retrieve the user ID from the login activity
//                String loggedInUserId = getIntent().getStringExtra("loggedInUserId");
//                intent.putExtra("loggedInUserId", loggedInUserId);
//                startActivity(intent);
//            }
//        });



//        attend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String loggedInUserId = mAuth.getCurrentUser().getUid();
//                // Pass the logged-in user ID to UserAttendanceActivity
//                Intent intent = new Intent(User_Interface.this, UserLecs.class);
//                intent.putExtra("LoggedInUserId", loggedInUserId);
//                startActivity(intent);
//            }
//        });





    }
}
