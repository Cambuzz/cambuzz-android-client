package com.cambuzz.internetcabin.cambuzzvitcc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
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

public class SignUp extends AppCompatActivity
{

    EditText name, reg, email, pass, pass_confirm;

    Button signin;

    TextView response;

    String iname , ireg, iemail, ipass, ipass_confirm;

    String responseAsText, going;

    int b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        b = 0;

        name = (EditText)findViewById(R.id.et_signup_name);
        reg = (EditText)findViewById(R.id.et_signup_reg);
        email = (EditText)findViewById(R.id.et_signup_email);
        pass = (EditText)findViewById(R.id.et_signup_pass);
        pass_confirm = (EditText)findViewById(R.id.et_signup_confirmpass);

        signin = (Button)findViewById(R.id.bt_signup_confirm);

        response = (TextView)findViewById(R.id.tv_response);

        Typeface MontFont = Typeface.createFromAsset(getAssets(), "Montserrat-Regular.otf");
        name.setTypeface(MontFont);
        reg.setTypeface(MontFont);
        email.setTypeface(MontFont);
        pass.setTypeface(MontFont);
        pass_confirm.setTypeface(MontFont);
        signin.setTypeface(MontFont);
        response.setTypeface(MontFont);

        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (name.length()>0 && reg.length()>0 && email.length()>0 && pass.length()>0 && pass_confirm.length()>0)
                {
                    iname = name.getText().toString();
                    ireg = reg.getText().toString();
                    iemail = email.getText().toString();
                    ipass = pass.getText().toString();
                    ipass_confirm = pass_confirm.getText().toString();

                    if (! ipass.equals(ipass_confirm))
                    {
                        Toast.makeText(getBaseContext(), "Passwords not matched!", Toast.LENGTH_SHORT).show();
                        Vibrator vii = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vii.vibrate(100);
                    }
                    else
                    {

                        Thread thread = new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    HttpClient httpclient = new DefaultHttpClient();
                                    HttpPost httppost = new HttpPost("http://cambuzz.co.in/signup_verify_app.php");
                                    try
                                    {
                                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                                        nameValuePairs.add(new BasicNameValuePair("username", ireg));
                                        nameValuePairs.add(new BasicNameValuePair("name", iname));
                                        nameValuePairs.add(new BasicNameValuePair("email", iemail));
                                        nameValuePairs.add(new BasicNameValuePair("password", ipass));

                                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                        //sending data to php page
                                        httpclient.execute(httppost);
                                        HttpResponse response = httpclient.execute(httppost);
                                        responseAsText = EntityUtils.toString(response.getEntity());


                                        going = nameValuePairs.toString();


                                        if (responseAsText.contains("username_exists"))
                                        {
                                            b = 1;
                                        }
                                        else if (responseAsText.contains("email_exists"))
                                        {
                                            b = 2;
                                        }
                                        else if(responseAsText.contains("only_vit"))
                                        {
                                            b = 3;
                                        }
                                        else if(responseAsText.contains("true"))
                                        {
                                            b = 4;
                                        }
                                        else
                                        {
                                            b = 5;
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

                            Toast.makeText(SignUp.this, going +  "*"  +responseAsText, Toast.LENGTH_LONG).show();

                            if (b == 1)  //username already exists!
                            {
                                Vibrator vii = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vii.vibrate(100);
                                response.setText("User already registered with given registration number!");
                            }
                            else if(b == 2) //email address already taken
                            {
                                Vibrator vii = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vii.vibrate(100);
                                response.setText("User already registered with above email address");
                            }
                            else if(b == 3) //Not an official vit emaill
                            {
                                Vibrator vii = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vii.vibrate(100);
                                response.setText("Please use an official VIT email address");
                            }
                            else if(b == 4) //correctly filled
                            {
                                response.setText("Successful! Kindly check your VIT email and confirm your registration. Your link will be expired in 30 min.");
                            }
                            else if (b == 5) //No response
                            {
                                Toast.makeText(getBaseContext(), "Response not found!\nCheck your internet connection.", Toast.LENGTH_SHORT).show();

                                Vibrator vii = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vii.vibrate(100);
                            }


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }








                    }


                }
                else //Incomplete field
                {
                    Toast.makeText(getBaseContext(), "All field are required!", Toast.LENGTH_SHORT).show();
                    Vibrator vii = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vii.vibrate(100);
                }




            }
        });





    }





}
