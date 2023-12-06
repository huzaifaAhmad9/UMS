package com.example.project.teacher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.modals.FileUpload;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class U_Lectures extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;

    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private Button btnSelectFile;
    private Button btnUpload;
    private ProgressBar progressBar;
    private EditText etFileName;
    private ImageView selectedImageView;

    private Uri selectedFileUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utasks);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("uploaded_lecs");

        // Set up the "Select File" button
        btnSelectFile = findViewById(R.id.btnSelectImage);
        btnSelectFile.setOnClickListener(v -> pickFile());

        // Set up the "Upload to Firebase" button
        btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(v -> {
            String fileName = etFileName.getText().toString().trim(); // Get custom file name from EditText

            if (fileName.isEmpty()) {
                Toast.makeText(this, "Please enter a file name", Toast.LENGTH_SHORT).show();
            } else if (selectedFileUri == null) {
                Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile(selectedFileUri, fileName);
            }
        });

        // Initialize progress views
        progressBar = findViewById(R.id.progressBar);

        // Initialize EditText for custom file name
        etFileName = findViewById(R.id.etFileName);

        // Initialize ImageView for displaying the selected file (in the case of images)
        selectedImageView = findViewById(R.id.selectedImageView);
    }

    private void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Handle the selected file
            selectedFileUri = data.getData();

            // Check the file type
            String fileType = getContentResolver().getType(selectedFileUri);

            if (fileType != null) {
                if (fileType.startsWith("image")) {
                    // Load image into the ImageView using Glide
                    Glide.with(this)
                            .load(selectedFileUri)
                            .into(selectedImageView);

                    // Make the ImageView visible
                    selectedImageView.setVisibility(ImageView.VISIBLE);
                } else {
                    // Display a toast for other file types
                    Toast.makeText(this, getFileTypeDescription(fileType) + " selected", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Display a toast for unsupported file types
                Toast.makeText(this, "Unsupported file type", Toast.LENGTH_SHORT).show();
            }

            // Display a toast
            Toast.makeText(this, "File selected successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileTypeDescription(String fileType) {
        switch (fileType) {
            case "application/pdf":
                return "PDF file";
            case "application/msword":
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return "Word document";
            case "application/vnd.ms-excel":
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                return "Excel spreadsheet";
            case "application/vnd.ms-powerpoint":
            case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                return "PowerPoint presentation";
            case "text/plain":
                return "Text file";
            default:
                return "File";
        }
    }

    private void uploadFile(Uri fileUri, String fileName) {
        // Create a reference to the file you want to upload
        StorageReference fileRef = storageRef.child("uploads/" + fileName);

        // Upload the file
        UploadTask uploadTask = fileRef.putFile(fileUri);

        // Show progress views
        progressBar.setVisibility(ProgressBar.VISIBLE);

        // Handle successful uploads
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Hide progress views on success
            progressBar.setVisibility(ProgressBar.GONE);

            // Clear the EditText upon successful upload
            etFileName.setText("");

            // Reset selected file URI
            selectedFileUri = null;

            // Hide the ImageView
            selectedImageView.setVisibility(ImageView.GONE);

            // Handle successful uploads, and use download URL as needed
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String downloadUrl = uri.toString();

                // Store file information in Realtime Database
                FileUpload fileUpload = new FileUpload(fileName, downloadUrl);
                databaseRef.push().setValue(fileUpload);

                // Display a toast upon successful upload
                Toast.makeText(U_Lectures.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
            });
        });

        // Handle unsuccessful uploads
        uploadTask.addOnFailureListener(exception -> {
            Toast.makeText(this, "Unsuccessful !!!!", Toast.LENGTH_SHORT).show();
            // Hide progress views on failure
            progressBar.setVisibility(ProgressBar.GONE);

            // Handle unsuccessful uploads
            // ...
        });
    }
}
