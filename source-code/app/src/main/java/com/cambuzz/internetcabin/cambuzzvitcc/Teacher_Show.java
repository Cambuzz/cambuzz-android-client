package com.cambuzz.internetcabin.cambuzzvitcc;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;


public class Teacher_Show extends Activity
{

    String gotData;
    Button showi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_show);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle gotBasket = getIntent().getExtras();
        gotData = gotBasket.getString("mykeyy");

        showi = (Button)findViewById(R.id.bt_dataShow);

        showi.setText(gotData);
    }


}
