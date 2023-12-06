package com.example.project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project.R;
import com.example.project.modals.Student;

import java.util.List;
public class Student_Adapter extends RecyclerView.Adapter<Student_Adapter.StudentViewHolder> {
    private List<Student> studentList;

    public Student_Adapter(List<Student> students) {
        this.studentList = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item, parent, false);
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
        private TextView nameTextView;
        private TextView idTextView;
        private TextView fatherTextView;
        private TextView cityTextView;
        private TextView genderTextView;
        private TextView departmentTextView;

        public StudentViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            idTextView = itemView.findViewById(R.id.textViewId);
            fatherTextView = itemView.findViewById(R.id.textViewFather);
            cityTextView = itemView.findViewById(R.id.textViewCity);
            genderTextView = itemView.findViewById(R.id.textViewGender);
            departmentTextView = itemView.findViewById(R.id.textViewDepartment);
        }

        public void bind(Student student) {
            idTextView.setText("ID: " + student.getId());
            nameTextView.setText("Name: " + student.getName());
            fatherTextView.setText("Father: " + student.getFather());
            cityTextView.setText("City: " + student.getCity());
            genderTextView.setText("Gender: " + student.getGender());
            departmentTextView.setText("Department: " + student.getDepartment());
        }
    }
}
