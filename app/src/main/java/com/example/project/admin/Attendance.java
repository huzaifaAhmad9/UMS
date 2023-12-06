package com.example.project.admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.adapters.AttendanceAdapter;
import com.example.project.R;
import com.example.project.modals.Student;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class Attendance extends Fragment {
    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;
    private List<Student> studentList;
    private DatabaseReference databaseReference;

    public Attendance() {
        // Required empty public constructor
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.example.project.R.layout.fragment_attendance, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        studentList = new ArrayList<>();
        adapter = new AttendanceAdapter(requireContext(), studentList, databaseReference);
        recyclerView.setAdapter(adapter);

        // Fetch student data from Firebase and add it to studentList
        databaseReference.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Student student = snapshot.getValue(Student.class);
                    studentList.add(student);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });

        return view;
    }
}
