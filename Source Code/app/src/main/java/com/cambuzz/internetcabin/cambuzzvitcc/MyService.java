package com.cambuzz.internetcabin.cambuzzvitcc;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cambuzz.internetcabin.cambuzzvitcc.app.AppController;
import com.cambuzz.internetcabin.cambuzzvitcc.data.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MyService extends IntentService{

    boolean k;
    private String urlJsonArry = "http://cambuzz.co.in/public/Inside/app.php";
    private static String TAG = MainActivity.class.getSimpleName();

    SessionManager session;
    private PendingIntent pendingIntent;
    String oldBuzzId;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Session class instance
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        oldBuzzId = user.get(SessionManager.KEY_NAME);

        k = isNetworkAvailable();
        if(k)
        {
            makeJsonArrayRequest();

            Intent i = new Intent(MyService.this, MyService.class);
            PendingIntent pendingIntent = PendingIntent.getService(MyService.this, 2, i, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (10000), pendingIntent);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void makeJsonArrayRequest()
    {
        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.d(TAG, response.toString() + "FEEL ");

                        try
                        {
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject person = (JSONObject) response.get(i);
                                FeedItem item = new FeedItem();

                                item.setId(person.getInt("id"));
                                int lolu = person.getInt("id");
                                String newBuzzId = Integer.toString(lolu);


                                //When new buzz is created/noticed   :: Make a notification on dropdown menu
                                if ((!oldBuzzId.equals(newBuzzId)) && i==0&&oldBuzzId!=null)
                                {
                                    Intent ii = new Intent(MyService.this, MyReceiver.class);
                                    startService(ii);
                                    pendingIntent = PendingIntent.getBroadcast(MyService.this, 0 , ii, 0);
                                    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+ 10000, pendingIntent);

                                    String lol;
                                    lol = newBuzzId;
                                    String username = lol;
                                    String password = "default_pasword";
                                    session.createLoginSession(username, password);

                                    break;
                                }

                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());


            }
        });

        AppController.getInstance().addToRequestQueue(req);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    public MyService() {
        super("extra text");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

}
