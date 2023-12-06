
package com.example.project.admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.modals.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Add extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText stu_id, stu_name, stu_Father, stu_city, stu_gender;
    Spinner spinner;
    Button btn;
    ImageView idWarningIcon;
    private DatabaseReference databaseReference;

    public Add() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        stu_id = view.findViewById(R.id.stu_id);
        stu_name = view.findViewById(R.id.stu_name);
        stu_Father = view.findViewById(R.id.stu_Father);
        stu_city = view.findViewById(R.id.stu_city);
        stu_gender = view.findViewById(R.id.stu_gender);
        btn = view.findViewById(R.id.btn);
//        idWarningIcon = view.findViewById(R.id.stu_id_warning);
        spinner = view.findViewById(R.id.spinner);
        ArrayList<String> arr = new ArrayList<>();
        // Adding elements to the array
        arr.add("CS");
        arr.add("SE");
        arr.add("IT");
        arr.add("D.Science");

        // Create an ArrayAdapter and set it to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Initialize the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("students");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = stu_id.getText().toString();
                String name = stu_name.getText().toString();
                String father = stu_Father.getText().toString();
                String city = stu_city.getText().toString();
                String gender = stu_gender.getText().toString();
                String selectedDepartment = spinner.getSelectedItem().toString();

                if (id.isEmpty() || name.isEmpty() || father.isEmpty() || city.isEmpty() || gender.isEmpty()) {

                    // At least one of the fields is empty
                    Toast.makeText(getActivity(), "Please fill in all the required fields!", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the ID already exists in the database
                    databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // ID already exists, show a toast message
                                Toast.makeText(getActivity(), "ID already exists in the database!", Toast.LENGTH_SHORT).show();
                            } else {
                                // ID is unique, add the data to the database
                                Student student = new Student(id, name, father, city, gender, selectedDepartment);
                                databaseReference.child(id).setValue(student);

                                // Clear the input fields
                                stu_id.setText("");
                                stu_name.setText("");
                                stu_Father.setText("");
                                stu_city.setText("");
                                stu_gender.setText("");

                                Toast.makeText(getActivity(), "Data added to Firebase!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedValue = parent.getItemAtPosition(position).toString();
        // Now, 'selectedValue' contains the value selected from the Spinner
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // optional
    }
}



