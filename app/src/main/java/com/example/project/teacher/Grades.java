package com.example.project.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Grades extends Fragment {
    private DatabaseReference databaseReference;

    public Grades() {
        // Required empty public constructor
        databaseReference = FirebaseDatabase.getInstance().getReference("students");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);
        EditText idEditText = view.findViewById(R.id.stu_id);
        EditText madEditText = view.findViewById(R.id.stu_name);
        EditText webEngnEditText = view.findViewById(R.id.stu_Father);
        EditText aiEditText = view.findViewById(R.id.stu_city);
        EditText cnEditText = view.findViewById(R.id.stu_gender);
        Button uploadButton = view.findViewById(R.id.btn);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String studentId = idEditText.getText().toString();
                final String madMarksStr = madEditText.getText().toString();
                final String webEngnMarksStr = webEngnEditText.getText().toString();
                final String aiMarksStr = aiEditText.getText().toString();
                final String cnMarksStr = cnEditText.getText().toString();

                if (studentId.isEmpty() || madMarksStr.isEmpty() || webEngnMarksStr.isEmpty() || aiMarksStr.isEmpty() || cnMarksStr.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(getActivity(), "Marks uploaded successfully.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "ID does not exist.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        return view;
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
