package com.example.project.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delete extends Fragment {
    EditText id;
    Button btn;
    TextView resultText; // Add a TextView to display related data
    private DatabaseReference databaseReference;

    public Delete() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        id = view.findViewById(R.id.stu_id);
        btn = view.findViewById(R.id.btn);
        resultText = view.findViewById(R.id.result); // Initialize the TextView

        // Initialize the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("students");

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
                MediaPlayer mediaPlayer =  MediaPlayer.create(getActivity(),R.raw.hlo);
                mediaPlayer.start();

                handleWarningIcon(id);
                final String studentId = id.getText().toString();
                // Check if the entered ID is not empty
                if (studentId.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a valid ID.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if the ID exists in the database
                databaseReference.child(studentId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // ID exists, show related data and a confirmation dialog
                            showDeleteConfirmationDialog(studentId);
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

    private void showDeleteConfirmationDialog(final String studentId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete this?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setTitle("Delete?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the record from the database
                databaseReference.child(studentId).removeValue();
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
        String relatedData = "Name: " + dataSnapshot.child("name").getValue(String.class) + "\n" +
                "Father: " + dataSnapshot.child("father").getValue(String.class) + "\n" +
                "City: " + dataSnapshot.child("city").getValue(String.class) + "\n" +
                "Gender: " + dataSnapshot.child("gender").getValue(String.class) + "\n" +
                "Department: " + dataSnapshot.child("department").getValue(String.class);

        resultText.setText(relatedData);

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
