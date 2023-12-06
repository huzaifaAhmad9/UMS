package com.example.project.teacher;

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
import com.example.project.modals.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Search_Teacher extends Fragment {

    EditText id;
    Button btn;
    TextView resultText;
    private DatabaseReference databaseReference;



    public Search_Teacher() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search__teacher, container, false);
        id = view.findViewById(R.id.stu_id);
        btn = view.findViewById(R.id.btn);
        resultText = view.findViewById(R.id.result);

        // Initialize the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("teachers");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String teacherId = id.getText().toString();

                // Check if the ID exists in the database
                databaseReference.child(teacherId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // ID exists, display relevant data
                            Teacher teacher = dataSnapshot.getValue(Teacher.class);
                            if (teacher != null) {
                                String result = "ID: " + teacher.id + "\n" +
                                        "Name: " + teacher.name + "\n" +
                                        "Contact: " + teacher.contact + "\n" +
                                        "Qualification: " + teacher.qualification + "\n" +
                                        "City: " + teacher.city + "\n" +
                                        "Department: " + teacher.department;
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
        });

        return view;
    }
}