package com.example.project.teacher;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.modals.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Add_Teacher extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText id, name, contact, qualification, city;
    Spinner spinner;
    Button btn;
    ImageView idWarningIcon;
    private DatabaseReference databaseReference;

    public Add_Teacher() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_teacher, container, false);

        // ----------------------------------------------------------------------------

        id = view.findViewById(R.id.id);
        name = view.findViewById(R.id.name);
        contact = view.findViewById(R.id.contact);
        qualification = view.findViewById(R.id.qualification);
        city = view.findViewById(R.id.city);
        btn = view.findViewById(R.id.btn);

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

        // Set the item selected listener for the Spinner
        spinner.setOnItemSelectedListener(this);

        // Initialize the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("teachers");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id1 = id.getText().toString();
                String name1 = name.getText().toString();
                String contact1 = contact.getText().toString();
                String qualification1 = qualification.getText().toString();
                String city1 = city.getText().toString();

                // Ensure a department is selected from the Spinner
                String selectedDepartment = spinner.getSelectedItem() != null ? spinner.getSelectedItem().toString() : "";

                if (id1.isEmpty() || name1.isEmpty() || contact1.isEmpty() || qualification1.isEmpty() || city1.isEmpty() || selectedDepartment.isEmpty()) {
                    // At least one of the fields is empty
                    Toast.makeText(getActivity(), "Please fill in all the required fields!", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the ID already exists in the database
                    databaseReference.child(id1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // ID already exists, show a toast message
                                Toast.makeText(getActivity(), "ID already exists in the database!", Toast.LENGTH_SHORT).show();
                            } else {
                                // ID is unique, add the data to the database
                                Teacher teacher = new Teacher(id1, name1, contact1, qualification1, city1, selectedDepartment);
                                databaseReference.child(id1).setValue(teacher);

                                // Clear the input fields
                                id.setText("");
                                name.setText("");
                                contact.setText("");
                                qualification.setText("");
                                city.setText("");

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
        // Handle the selection if needed
        // For example, you can retrieve the selected item: parent.getItemAtPosition(position).toString()
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle the case where nothing is selected
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close the Firebase database connection if needed
    }
}
