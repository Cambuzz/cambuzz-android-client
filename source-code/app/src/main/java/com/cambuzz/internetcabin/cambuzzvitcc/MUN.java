package com.cambuzz.internetcabin.cambuzzvitcc;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cambuzz.internetcabin.cambuzzvitcc.adapter.FeedListAdapter;
import com.cambuzz.internetcabin.cambuzzvitcc.adapter.FeedListAdapter_mun;
import com.cambuzz.internetcabin.cambuzzvitcc.app.AppController;
import com.cambuzz.internetcabin.cambuzzvitcc.data.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MUN extends Fragment
{
    // json array response url *********************************************************************
    private String urlJsonArry = "http://cambuzz.me/public/Inside/app_technovit.php";
    private static String TAG = MUN.class.getSimpleName();

    SessionManager session;
    //Setting up list
    private ListView listView;
    private FeedListAdapter_mun listAdapter;
    private List<FeedItem> feedItems;

    Button refresh, mun_buzz, mun_track;
    //**********************************************************************************************

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.mun_activity, container, false);

        refresh = (Button)rootView.findViewById(R.id.bt_refresh_mun_button);

     //   mun_buzz = (Button)rootView.findViewById(R.id.mun_to_buzz);
       // mun_track = (Button)rootView.findViewById(R.id.mun_to_track);

    /*    mun_buzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MUN.this, MainActivity.class);
                startActivity(i);
            }
        });

        mun_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MUN.this, Teacher_search.class);
                startActivity(i);
            }
        });*/

        //******************************************************************************************

        session = new SessionManager(getActivity().getApplicationContext());

        listView = (ListView)rootView.findViewById(R.id.list_mun);
        feedItems = new ArrayList<FeedItem>();
        listAdapter = new FeedListAdapter_mun(getActivity(), feedItems);
        listView.setAdapter(listAdapter);

        // making json array request
        makeJsonArrayRequest();

        //Starting push notification service
        //Intent i = new Intent(this, MyService.class);
        //startService(i);
        //**********************************************************************************************
        return rootView;
    }

    /**
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest()
    {
        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.d(TAG, response.toString());

                        try
                        {
          //                  refresh.setVisibility(View.INVISIBLE);
                            // Parsing json array response
                            // loop through each json object

                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject person = (JSONObject) response.get(i);

                                FeedItem item = new FeedItem();

                                item.setId(person.getInt("id"));

                                //for first object of json
                                //if(i == 0)
                                //{
                                //    int lol;
                                //    lol = person.getInt("id");
                                //    String username = Integer.toString(lol);
                                //    String password = "default_pasword"; //Its not used

                                    //saving id of the latest json object
                                //    session.createLoginSession(username, password);
                                //}

                                item.setName(person.getString("post_user"));

                                item.setTimeStamp(person.getString("post_time"));

                                String tempo0 = person.getString("school");
                                tempo0 = tempo0.replaceAll("&quot;"," \"");
                                tempo0 = tempo0.replaceAll("&amp;", "&");

                                item.setBuzz_title(tempo0);

                                String tempo1 = person.getString("content");
                                tempo1 = tempo1.replaceAll("&quot;"," \"");
                                tempo1 = tempo1.replaceAll("&amp;", "&");
                                item.setBuzz_content(tempo1);



                                // Image might be null sometimes
                                String image = ( person.getString("po_url") == "" ? null : person.getString("po_url"));

                                if (person.getString("po_url").equals(""))
                                {
                                    image = null;
                                }
                                else
                                {
                                    image = person.getString("po_url");
                                }

                                item.setImge(image);

                                item.setProfilePic(person.getString("dp_url"));

                                //item.setS_time("Start time :\n" + person.getString("start_date_time"));

                                //item.setE_time("End time :\n" +person.getString("end_date_time"));



                                feedItems.add(item);
                            }

                            listAdapter.notifyDataSetChanged();
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

                refresh.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(),"Please connect to the network!", Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }









}
