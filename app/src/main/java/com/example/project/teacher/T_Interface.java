package com.example.project.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.admin.Att1;
import com.example.project.authentication.Staff;

public class T_Interface extends AppCompatActivity {
    CardView card1,card2,card3,card4;
    Button btn;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinterface);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        btn = findViewById(R.id.btn);

        // getting teacher name from previous activity
         String teacherName = getIntent().getStringExtra("TeacherName");
         text = findViewById(R.id.text);
         text.setText("WELCOME " + teacherName);


        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1.setBackgroundColor(getResources().getColor(R.color.blue));
                Toast.makeText(T_Interface.this, "Mark Students Attendace !!!", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        card1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }, 500);
                Intent intent = new Intent(T_Interface.this, Att1.class);
                startActivity(intent);
//                finish();
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card2.setBackgroundColor(getResources().getColor(R.color.blue));
                Toast.makeText(T_Interface.this, "Upload Students Marks !!!", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        card2.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }, 500);

                Intent intent = new Intent(T_Interface.this, Grade1.class);
                startActivity(intent);
//                finish();
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card3.setBackgroundColor(getResources().getColor(R.color.blue));
                Toast.makeText(T_Interface.this, "Upload Tasks !!!", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        card3.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }, 500);

                Intent intent = new Intent(T_Interface.this, U_Tasks.class);
                startActivity(intent);

            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card4.setBackgroundColor(getResources().getColor(R.color.blue));
                Toast.makeText(T_Interface.this, "Upload Lectures !!!", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        card4.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }, 500);

                Intent intent = new Intent(T_Interface.this, U_Lectures.class);
                startActivity(intent);

            }
        });

        // for logout button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setBackgroundColor(getResources().getColor(R.color.red));
                confirmDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }, 500);
            }

            private void confirmDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(T_Interface.this);
                builder.setMessage("Do you want to LogOut?");
                builder.setCancelable(false);
                builder.setIcon(R.drawable.baseline_logout_24);
                builder.setTitle("Log-Out");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(T_Interface.this, "LOGGING-OUT !!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(T_Interface.this, Staff.class);
                        startActivity(intent);
                        finish();
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

        });

    }

}