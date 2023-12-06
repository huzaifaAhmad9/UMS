package com.example.project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.modals.Teacher;

import java.util.List;

public class Teacher_Adapter extends RecyclerView.Adapter<Teacher_Adapter.TeacherViewHolder> {

    private List<Teacher> teacherList;
    public Teacher_Adapter(List<Teacher> teachers) {
        this.teacherList = teachers;
    }

    @NonNull
    @Override
    public Teacher_Adapter.TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list_item, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Teacher_Adapter.TeacherViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        holder.bind(teacher);

    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView idTextView;
        private TextView fatherTextView;
        private TextView cityTextView;
        private TextView genderTextView;
        private TextView departmentTextView;



        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            idTextView = itemView.findViewById(R.id.textViewId);
            fatherTextView = itemView.findViewById(R.id.textViewFather);
            cityTextView = itemView.findViewById(R.id.textViewCity);
            genderTextView = itemView.findViewById(R.id.textViewGender);
            departmentTextView = itemView.findViewById(R.id.textViewDepartment);
        }


        public void bind(Teacher teacher) {
            idTextView.setText("ID: " + teacher.getId());
            nameTextView.setText("Name: " + teacher.getName());
            fatherTextView.setText("Contact: " + teacher.getContact());
            cityTextView.setText("Qualification: " + teacher.getQualification());
            genderTextView.setText("City: " + teacher.getCity());
            departmentTextView.setText("Department: " + teacher.getDepartment());
        }



    }
}