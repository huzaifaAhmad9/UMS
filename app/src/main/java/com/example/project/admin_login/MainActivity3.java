package com.example.project.admin_login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project.admin.Admin_Activity;
import com.example.project.R;

public class MainActivity3 extends AppCompatActivity {
    EditText username;
    EditText pass;
    Button btn;
    LottieAnimationView animation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        btn = findViewById(R.id.btn);
        animation = findViewById(R.id.animation);
        animation.setAnimation(R.raw.login);
        animation.playAnimation();
       btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = username.getText().toString();
                String p = pass.getText().toString();
                if (e.equals("admin") && p.equals("admin")){
                    Intent intent = new Intent(MainActivity3.this, Admin_Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();

                }else{
                    Toast.makeText(MainActivity3.this, "Invalid Credentials !!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}


