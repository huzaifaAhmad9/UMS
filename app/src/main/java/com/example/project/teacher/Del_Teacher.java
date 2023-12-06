package com.example.project.teacher;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Del_Teacher extends Fragment {

    EditText id;
    Button btn;
    TextView resultText; // Add a TextView to display related data
    private DatabaseReference databaseReference;

    public Del_Teacher() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_del__techear, container, false);
        id = view.findViewById(R.id.stu_id);
        btn = view.findViewById(R.id.btn);
        resultText = view.findViewById(R.id.result); // Initialize the TextView

        // Initialize the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("teachers");
        // Set up the addTextChangedListener to handle the warning icon and clear the resultText
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Handle the warning icon when text changes
                handleWarningIcon(id);
                // Clear the resultText when text changes
                resultText.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not used
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWarningIcon(id);
                final String Teacher_ID = id.getText().toString();
                // Check if the entered ID is not empty
                if (Teacher_ID.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a valid ID.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if the ID exists in the database
                databaseReference.child(Teacher_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // ID exists, show related data and a confirmation dialog
                            showDeleteConfirmationDialog(Teacher_ID);
                            displayRelatedData(dataSnapshot);
                        } else {
                            // ID doesn't exist, show a toast message
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

    private void showDeleteConfirmationDialog(final String teacherId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete this?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.baseline_delete_forever_24);
        builder.setTitle("Delete?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the record from the database
                databaseReference.child(teacherId).removeValue();
                Toast.makeText(getActivity(), "Record deleted!", Toast.LENGTH_SHORT).show();
                id.setText(""); // Clear the input field
                resultText.setText(""); // Clear the resultText
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

    private void handleWarningIcon(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_warning_24, 0);
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    private void displayRelatedData(DataSnapshot dataSnapshot) {
        // Extract and display the related data in the resultText
        StringBuilder relatedData = new StringBuilder();
        relatedData.append("Name: ").append(dataSnapshot.child("name").getValue(String.class)).append("\n");
        relatedData.append("Contact: ").append(dataSnapshot.child("contact").getValue(String.class)).append("\n");
        relatedData.append("Qualification: ").append(dataSnapshot.child("qualification").getValue(String.class)).append("\n");
        relatedData.append("City: ").append(dataSnapshot.child("city").getValue(String.class)).append("\n");
        relatedData.append("Department: ").append(dataSnapshot.child("department").getValue(String.class));

        resultText.setText(relatedData.toString());

        // Clear the resultText after a delay
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultText.setText("");
            }
        }, 5000); // Display the data for 5 seconds
    }
}
