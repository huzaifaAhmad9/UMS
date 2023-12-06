package com.example.project.student;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.project.R;
import com.example.project.modals.FileUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UserLecs extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private List<FileUpload> lectureList;
    private ArrayAdapter<String> adapter;
    private long downloadID; // To store the download ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lecs);

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("uploaded_lecs");

        // Initialize views
        ListView listViewLectures = findViewById(R.id.listViewLectures);

        lectureList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewLectures.setAdapter(adapter);

        // Retrieve lectures from Firebase
        retrieveLectures();

        // Set item click listener to handle download
        listViewLectures.setOnItemClickListener((parent, view, position, id) -> {
            FileUpload selectedLecture = lectureList.get(position);
            downloadLecture(selectedLecture);
        });

        // Register BroadcastReceiver to handle download completion
        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver when the activity is destroyed
        unregisterReceiver(onDownloadComplete);
    }

    // BroadcastReceiver to handle download completion
    private final BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (id == downloadID) {
                // Download is complete
                Toast.makeText(UserLecs.this, "Download completed successfully", Toast.LENGTH_SHORT).show();
                showDownloadNotification();
            }
        }
    };

    private void retrieveLectures() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lectureList.clear();
                adapter.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FileUpload fileUpload = dataSnapshot.getValue(FileUpload.class);
                    if (fileUpload != null) {
                        lectureList.add(fileUpload);
                        adapter.add(fileUpload.getFileName());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserLecs.this, "Failed to retrieve lectures", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadLecture(FileUpload lecture) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Download Lecture")
                .setMessage("Do you want to download the lecture?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    // User clicked Yes, proceed with the download
                    startDownload(lecture);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // User clicked No, do nothing
                    dialog.dismiss();
                })
                .show();
    }

    private void startDownload(FileUpload lecture) {
        // Get the lecture's download URL
        String downloadUrl = lecture.getFileUrl();

        // Create a DownloadManager.Request with the download URL
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        // Set the title and description for the download notification
        request.setTitle(lecture.getFileName());
        request.setDescription("Downloading");

        // Set the destination directory for the downloaded file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, lecture.getFileName());

        // Enqueue the download request
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);

        // Display a message indicating the download has started
        Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();
    }

    private void showDownloadNotification( ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("download_channel", "Download Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "download_channel")
                .setSmallIcon(R.drawable.baseline_arrow_downward_24)
                .setContentTitle("File Downloaded")
                .setContentText("Your lecture file has been downloaded successfully")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(1, builder.build());
    }
}
