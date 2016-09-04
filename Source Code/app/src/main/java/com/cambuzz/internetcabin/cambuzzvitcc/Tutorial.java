package com.cambuzz.internetcabin.cambuzzvitcc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cambuzz.internetcabin.cambuzzvitcc.swipe.Majormain;

public class Tutorial extends Activity{

    ImageButton nextt, skipp;
    TextView showw;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a = 0;
        setContentView(R.layout.activity_tutorial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        nextt = (ImageButton)findViewById(R.id.bt_tutorial_next);
        skipp = (ImageButton)findViewById(R.id.bt_tutorial_skip);
        showw = (TextView)findViewById(R.id.tv_tutorial);

        nextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(a == 0)
                {
                    showw.setText("Get all the Buzz happening in the campus, right at the start screen.\n\n Don't miss even a single event or opportunity. Stay aware. Get updated. ");

                    a = 1;
                }
                else if(a == 1)
                {
                    showw.setText("Never miss out on events going on in campuss. \n\nGet live feeds from ongoing events!");
                    a = 2;
                }
                else if(a == 2)
                {
                    String s = "<b>WORK HARD! PLAY HARD!</b>";
                    showw.setText("That's it VITians, Enjoy! \n\n\n\n\n\n"+ Html.fromHtml(s));
                    a = 3;
                }
                else
                {

                    Intent i = new Intent(Tutorial.this, Majormain.class);
                    startActivity(i);
                    finish();
                }
            }
        });


        skipp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tutorial.this, Majormain.class);
                startActivity(i);
                finish();
            }
        });


    }



}
