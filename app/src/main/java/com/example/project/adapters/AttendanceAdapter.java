package com.example.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.modals.Student;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.StudentViewHolder> {
    private List<Student> studentList;
    private Context context;
    private DatabaseReference databaseReference;

    public AttendanceAdapter(Context context, List<Student> students, DatabaseReference databaseReference) {
        this.context = context;
        this.studentList = students;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private ToggleButton attendanceToggleButton;
        TextView txt1,txt2;
        private Student student;

        public StudentViewHolder(View itemView) {
            super(itemView);
            attendanceToggleButton = itemView.findViewById(R.id.toggleButton);
            txt1 = itemView.findViewById(R.id.textViewId);
            txt2 = itemView.findViewById(R.id.textViewName);
        }

        public void bind(final Student student) {
            this.student = student;
            // Set ID and Name to TextViews
            txt1.setText("ID: " + student.getId());
            txt2.setText("Name: " + student.getName());

            attendanceToggleButton.setChecked(student.isPresent());

            attendanceToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    student.setPresent(isChecked);
                    // Update attendance status in the database
                    databaseReference.child("students").child(student.getId()).child("present").setValue(isChecked);
                }
            });
        }
    }
}
