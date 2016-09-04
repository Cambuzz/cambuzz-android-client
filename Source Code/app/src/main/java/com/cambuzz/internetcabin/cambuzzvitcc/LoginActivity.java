package com.cambuzz.internetcabin.cambuzzvitcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cambuzz.internetcabin.cambuzzvitcc.swipe.Majormain;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity
{
    EditText et_email, et_pass;
    Button bt_login, bt_signup;
    TextView notamember;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager_login session_login;
    String email, password, responseAsText="";

    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        a = 0;

        et_email = (EditText)findViewById(R.id.et_email);
        et_pass = (EditText)findViewById(R.id.et_password);
        bt_login = (Button)findViewById(R.id.bt_login);
        bt_signup = (Button)findViewById(R.id.bt_signup);
        notamember = (TextView)findViewById(R.id.tv_not_a_member);

        Typeface MontFont = Typeface.createFromAsset(getAssets(), "Montserrat-Regular.otf");
        et_email.setTypeface(MontFont);
        et_pass.setTypeface(MontFont);
        bt_login.setTypeface(MontFont);
        bt_signup.setTypeface(MontFont);
        notamember.setTypeface(MontFont);

        session_login = new SessionManager_login(getApplicationContext());

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUp.class);
                startActivity(i);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                email = et_email.getText().toString();
                password = et_pass.getText().toString();

                if (email.length() > 0 && password.length() > 0)
                {

                    Thread thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                HttpClient httpclient = new DefaultHttpClient();
                                HttpPost httppost = new HttpPost("http://cambuzz.co.in/login_verify_app.php");
                                try
                                {
                                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                                    nameValuePairs.add(new BasicNameValuePair("username", email));
                                    nameValuePairs.add(new BasicNameValuePair("password", password));
                                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                    //sending data to php page
                                    httpclient.execute(httppost);
                                    HttpResponse response = httpclient.execute(httppost);
                                    responseAsText = EntityUtils.toString(response.getEntity());


                                    if (responseAsText.contains("true"))
                                    {
                                        a = 1;
                                    }
                                    else if (responseAsText.contains("false"))
                                    {
                                        a = 2;
                                    }
                                    else
                                    {
                                        a = 3;
                                    }

                                } catch (ClientProtocolException e) {
                                    // TODO Auto-generated catch block
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                    try
                    {
                        thread.join();

                        if (a == 1)  //Successful login
                        {
                            session_login.createLoginSession(email, password);

                            Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(LoginActivity.this, Majormain.class);
                            startActivity(i);

                            finish();
                        }
                        else if(a == 2) //Wrong reg or password
                        {
                            Toast.makeText(getBaseContext(), "Sign up first,  Invalid registration number or password!", Toast.LENGTH_SHORT).show();

                            Vibrator vii = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vii.vibrate(100);
                        }
                        else if(a == 3)
                        {
                            Toast.makeText(getBaseContext(), "Response not found!\nCheck your internet connection.", Toast.LENGTH_SHORT).show();

                            Vibrator vii = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vii.vibrate(100);
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                else
                {
                    Toast.makeText(getBaseContext(), "All field are required", Toast.LENGTH_SHORT).show();

                    Vibrator vii = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vii.vibrate(100);
                }

            }
        });



    }




}
