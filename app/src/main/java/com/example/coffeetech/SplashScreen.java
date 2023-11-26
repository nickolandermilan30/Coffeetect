package com.example.coffeetech;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 3000; // milliseconds


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this, LandingPage.class);
                startActivity(homeIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIMEOUT);

        MotionLayout motionLayout = findViewById(R.id.mainlayout);
        motionLayout.transitionToState(R.id.end); // Start the animation

        // Start the animation by transitioning to the "end" state
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
                // Animation started
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
                // Animation in progress (you can use progress for more advanced effects)
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                // Animation completed
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
                // Trigger event
            }
        });

    }


}


