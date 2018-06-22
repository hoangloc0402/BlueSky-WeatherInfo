package com.ttcnpm.group28.weatherapp.dinhduc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.hoangloc.ActivityMain;

public class SplashScreen extends Activity {
    Thread splashThread;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        ConstraintLayout l = findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ConstraintLayout iv = findViewById(R.id.splash_layout);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this,
                            ActivityMain.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();
                }
                catch (InterruptedException e) {
                    //
                }
                finally {
                    SplashScreen.this.finish();
                }
            }
        };
        splashThread.start();
    }
}
