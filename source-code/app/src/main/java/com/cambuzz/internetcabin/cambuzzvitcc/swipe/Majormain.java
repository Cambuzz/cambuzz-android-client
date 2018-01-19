package com.cambuzz.internetcabin.cambuzzvitcc.swipe;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.cambuzz.internetcabin.cambuzzvitcc.About;
import com.cambuzz.internetcabin.cambuzzvitcc.AlertDialogManager;
import com.cambuzz.internetcabin.cambuzzvitcc.MUN;
import com.cambuzz.internetcabin.cambuzzvitcc.MainActivity;
import com.cambuzz.internetcabin.cambuzzvitcc.MyService;
import com.cambuzz.internetcabin.cambuzzvitcc.R;
import com.cambuzz.internetcabin.cambuzzvitcc.SessionManager_login;
import com.cambuzz.internetcabin.cambuzzvitcc.Teacher_search;
import com.cambuzz.internetcabin.cambuzzvitcc.Tutorial;
import com.cambuzz.internetcabin.cambuzzvitcc.mydata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Majormain extends ActionBarActivity {

    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private String[] tabs = { "BUZZ!", "TechnoVIT'15", "Faculty Track" };

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager for user login details!
    SessionManager_login session_login;

    String registrationNumber, userPassword;

    String[] recieve;
    StringTokenizer recieved_str;
    String periodss, teachers, cabins;
    String catching, recieved;
    String status = "";
    String days = "";
    String[] arr = new String[2];
    ArrayList<String> teachersdata;

    String finalData = "";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.super_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mPager=(ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));
        mTabs=(SlidingTabLayout)findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);

        // Session class instance
        session_login = new SessionManager_login(getApplicationContext());

        //Check if user is logged in or not  -  if no : then start login activity!  -  else nothing!
        //session_login.checkLogin();


        //Get user's login data from session
        HashMap<String, String> user = session_login.getUserDetails();
        registrationNumber = user.get(SessionManager_login.KEY_NAME);
        userPassword = user.get(SessionManager_login.KEY_EMAIL);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if(isFirstRun) {

            new loadsome().execute();

            //getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();
            //Intent i = new Intent(Majormain.this, Tutorial.class);
            //startActivity(i);
        }
        else {
            //Starting push notification service
            Intent i = new Intent(this, MyService.class);
            startService(i);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // In case you have an item
        return true;
    }


    public class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    return new MainActivity();
                case 1:
                    return new MUN();
                case 2:
                    return new Teacher_search();
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 3;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_feedback) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse( "market://details?id=" + appPackageName)));
            }
            catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }

        if(id == R.id.action_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        if(id == R.id.action_about) {
            Intent i = new Intent(Majormain.this, About.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    public class loadsome extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream = getResources().openRawResource(R.raw.finallist);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                String csvLine;
                while ((csvLine = reader.readLine()) != null) {
                    recieve = csvLine.split(",");
                    teachers = recieve[0];
                    periodss = recieve[1];
                    cabins = recieve[2];
                    mydata entry = new mydata(Majormain.this);
                    entry.open();
                    entry.createentry(teachers, periodss, cabins);
                    entry.close();
                }
            }
            catch (IOException ex) {
                throw new RuntimeException("Error in reading CSV file: " + ex);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    throw new RuntimeException("Error while closing input stream: " + e);
                }
            }
            return "go";
        }
    }
}
