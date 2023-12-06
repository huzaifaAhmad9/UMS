package com.example.project.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Grade1 extends AppCompatActivity {
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade1);
        databaseReference = FirebaseDatabase.getInstance().getReference("students");
        EditText idEditText = findViewById(R.id.stu_id);
        EditText madEditText = findViewById(R.id.stu_name);
        EditText webEngnEditText = findViewById(R.id.stu_Father);
        EditText aiEditText = findViewById(R.id.stu_city);
        EditText cnEditText = findViewById(R.id.stu_gender);
        Button uploadButton = findViewById(R.id.btn);



        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String studentId = idEditText.getText().toString();
                final String madMarksStr = madEditText.getText().toString();
                final String webEngnMarksStr = webEngnEditText.getText().toString();
                final String aiMarksStr = aiEditText.getText().toString();
                final String cnMarksStr = cnEditText.getText().toString();

                if (studentId.isEmpty() || madMarksStr.isEmpty() || webEngnMarksStr.isEmpty() || aiMarksStr.isEmpty() || cnMarksStr.isEmpty()) {
                    Toast.makeText(Grade1.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    final int madMarks = Integer.parseInt(madMarksStr);
                    final int webEngnMarks = Integer.parseInt(webEngnMarksStr);
                    final int aiMarks = Integer.parseInt(aiMarksStr);
                    final int cnMarks = Integer.parseInt(cnMarksStr);

                    // Check if the student ID exists in the database
                    databaseReference.child(studentId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                double totalMarks = madMarks + webEngnMarks + aiMarks + cnMarks;
                                double percentage = (totalMarks / 400) * 100;
                                String grade = calculateGrade(percentage);

                                databaseReference.child(studentId).child("percentage").setValue(percentage);
                                databaseReference.child(studentId).child("grade").setValue(grade);

                                // Clear the EditText fields
                                idEditText.setText("");
                                madEditText.setText("");
                                webEngnEditText.setText("");
                                aiEditText.setText("");
                                cnEditText.setText("");

                                Toast.makeText(Grade1.this, "Marks uploaded successfully.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Grade1.this, "ID does not exist.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(Grade1.this, "Error occurred.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
    private String calculateGrade(double percentage) {
        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B";
        } else if (percentage >= 60) {
            return "C";
        } else if (percentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
}