package com.cambuzz.internetcabin.cambuzzvitcc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.cambuzz.internetcabin.cambuzzvitcc.swipe.Majormain;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Thread timer = new Thread()
        {
          public void run()
          {
                try {
                    sleep(2000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                    startActivity(new Intent(Splash.this, Majormain.class));
                }
          }

        };
        timer.start();
    }
}
