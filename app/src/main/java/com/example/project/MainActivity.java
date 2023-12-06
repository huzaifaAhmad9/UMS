package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project.admin_login.MainActivity3;
import com.example.project.authentication.SignUp;
import com.example.project.authentication.Staff;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3;
    LottieAnimationView animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation = findViewById(R.id.animation);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        animation.setAnimation(R.raw.welcome);
        animation.playAnimation();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Change background color when the button is clicked
                btn1.setBackgroundColor(getResources().getColor(R.color.blue));

                // Delayed revert to previous color
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }, 500); // Change 1000 to the desired delay in milliseconds

                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
//                finish();
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Change background color when the button is clicked
                btn2.setBackgroundColor(getResources().getColor(R.color.blue));

                // Delayed revert to previous color
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn2.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }, 500); // Change 1000 to the desired delay in milliseconds

                Intent intent = new Intent(MainActivity.this, User_Activity.class);
                startActivity(intent);
//                finish();
            }
        });



        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Change background color when the button is clicked
                btn3.setBackgroundColor(getResources().getColor(R.color.blue));

                // Delayed revert to previous color
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn3.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }, 500); // Change 1000 to the desired delay in milliseconds

                Intent intent = new Intent(MainActivity.this, Staff.class);
                startActivity(intent);
//                finish();
            }
        });




    }
}
