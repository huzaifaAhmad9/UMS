package com.example.project.admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.modals.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search extends Fragment {
    EditText id;
    Button btn;
    TextView resultText;
    private DatabaseReference databaseReference;

    public Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        id = view.findViewById(R.id.stu_id);
        btn = view.findViewById(R.id.btn);
        resultText = view.findViewById(R.id.result);

        // Initialize the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("students");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String studentId = id.getText().toString();

                if (studentId.isEmpty()) {
                    // Show a toast if the EditText is empty
                    Toast.makeText(getActivity(), "ID cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the ID exists in the database
                    databaseReference.child(studentId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // ID exists, display relevant data
                                Student student = dataSnapshot.getValue(Student.class);
                                if (student != null) {
                                    String result = "ID: " + student.id + "\n" +
                                            "Name: " + student.name + "\n" +
                                            "Father: " + student.father + "\n" +
                                            "City: " + student.city + "\n" +
                                            "Gender: " + student.gender + "\n" +
                                            "Department: " + student.department;
                                    resultText.setText(result);
                                }
                            } else {
                                // ID doesn't exist, show a toast message
                                resultText.setText(""); // Clear any previous results
                                Toast.makeText(getActivity(), "ID does not exist in the database!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle the error, if any
                            Toast.makeText(getActivity(), "Error checking ID: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                 }
            }
        });


        return view;
    }
}
