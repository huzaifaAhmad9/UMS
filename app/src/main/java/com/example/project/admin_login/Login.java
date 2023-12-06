package com.example.project.admin_login;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project.R;

public class Login extends Fragment {
    TextView txt;
    LinearLayout swipe;
    LottieAnimationView animation, animation1;

    public Login() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        txt = view.findViewById(R.id.txt);
        swipe = view.findViewById(R.id.swipe);

        // Set up the ObjectAnimator for a bouncing effect
        ObjectAnimator bounceAnim = ObjectAnimator.ofFloat(txt, "translationY", 0f, -30f, 0f);
        bounceAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        bounceAnim.setRepeatCount(ObjectAnimator.INFINITE);
        bounceAnim.setDuration(2000); // Adjust the duration as needed
        bounceAnim.start();  // Start the animation



        // set up ObjectAnimator for a swipe layout
        // Create ObjectAnimator for translationX property
        ObjectAnimator swipeAnimator = ObjectAnimator.ofFloat(swipe, "translationX", 0f, 200f);
        swipeAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        swipeAnimator.setDuration(1500); // Set animation duration in milliseconds
        // Start the swipe animation when the activity starts
        swipeAnimator.start();








        animation = view.findViewById(R.id.animation);
        animation.setAnimation(R.raw.about);
        animation.playAnimation();

        animation1 = view.findViewById(R.id.animation1);
        animation1.setAnimation(R.raw.arrows);
        animation1.playAnimation();

        return view;
    }
}
