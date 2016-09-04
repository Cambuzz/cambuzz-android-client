package com.cambuzz.internetcabin.cambuzzvitcc;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import android.os.AsyncTask;

public class Teacher_search extends android.support.v4.app.Fragment
{
    AutoCompleteTextView actv;
    ImageView proff;
    TextView fetch;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.teacher_search, container, false);
        teachersdata = new ArrayList<String>();



        //to display availability
        actv = (AutoCompleteTextView)rootView. findViewById(R.id.autocomplete_faculty);
        proff = (ImageView)rootView.findViewById(R.id.iv_teachersearch);
        fetch = (TextView)rootView.findViewById(R.id.tv_fetchingData);

   /*     InputStream inputStream = getResources().openRawResource(R.raw.finallist);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try
        {
            String csvLine;
            while ((csvLine = reader.readLine()) != null)
            {
                recieve = csvLine.split(",");
                teachers = recieve[0];
                periodss = recieve[1];
                cabins = recieve[2];
                teachersdata.add(recieve[0]);
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        new loadsome().execute();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teachersdata);
        actv.setAdapter(adapter);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId)
            {
                catching = (String) parent.getItemAtPosition(position);//teacher name
                new readsome().execute(catching);

                fetch.setText("Please wait. Fetching data.");

            }
        });*/
        return  rootView;

    } //oncreate endsssssssssssss
/////////////////////////////////////////////////////// onActivity is created just to make sure that context is not null...
 ///////////////////// thus ll the getActivity(); methods should come under on Activity created!111111111
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        InputStream inputStream = getResources().openRawResource(R.raw.finallist);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try
        {
            String csvLine;
            while ((csvLine = reader.readLine()) != null)
            {
                recieve = csvLine.split(",");
                teachers = recieve[0];
                periodss = recieve[1];
                cabins = recieve[2];
                teachersdata.add(recieve[0]);
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
//        new loadsome().execute();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teachersdata);
        actv.setAdapter(adapter);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId)
            {
                catching = (String) parent.getItemAtPosition(position);//teacher name
                new readsome().execute(catching);

                fetch.setText("Please wait.\nFetching data.");

            }
        });


    }

    public class loadsome extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            InputStream inputStream = getResources().openRawResource(R.raw.finallist);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try
            {
                String csvLine;
                while ((csvLine = reader.readLine()) != null)
                {
                    recieve = csvLine.split(",");
                    teachers = recieve[0];
                    periodss = recieve[1];
                    cabins = recieve[2];
                    mydata entry = new mydata(getActivity());
                    entry.open();
                    entry.createentry(teachers, periodss, cabins);
                    entry.close();
                }
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Error in reading CSV file: " + ex);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    throw new RuntimeException("Error while closing input stream: " + e);
                }
            }
            return "go";
        }
    }


    public class readsome extends AsyncTask<String,Intent,String>
    {
        protected void onPreExecute()
        {
            String result;
        }

        @Override
        protected String doInBackground(String... params) {

            try
            {
                mydata entry = new mydata(getActivity());
                entry.open();
                arr = entry.searching(catching);
                entry.close();
            }
            catch(SQLException e1)
            {
                e1.printStackTrace();
            }
            days="";
            status="";
            recieved_str = new StringTokenizer(arr[0]);
            int matrix[][] = new int[5][13];
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 13; j++)
                {
                    matrix[i][j] = Integer.parseInt(recieved_str.nextToken());
                }
            }
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("E HH mm");
            StringTokenizer date_str = new StringTokenizer(ft.format(dNow));
            String day = date_str.nextToken();
            int hour = Integer.parseInt(date_str.nextToken());
            int minute = Integer.parseInt(date_str.nextToken());

            int r = 10;

            int timarr[][] = new int[13][2];
            timarr[0][0] = 8;
            timarr[0][1] = 50;
            timarr[1][0] = 9;
            timarr[1][1] = 45;
            timarr[2][0] = 10;
            timarr[2][1] = 40;
            timarr[3][0] = 11;
            timarr[3][1] = 35;
            timarr[4][0] = 12;
            timarr[4][1] = 30;
            timarr[5][0] = 13;
            timarr[5][1] = 20;
            timarr[6][0] = 14;
            timarr[6][1] = 05;
            timarr[7][0] = 14;
            timarr[7][1] = 55;
            timarr[8][0] = 15;
            timarr[8][1] = 50;
            timarr[9][0] = 16;
            timarr[9][1] = 45;
            timarr[10][0] = 17;
            timarr[10][1] = 40;
            timarr[11][0] = 18;
            timarr[11][1] = 35;
            timarr[12][0] = 19;
            timarr[12][1] = 30;
            String tim_disp[] = {"8:50", "9:45", "10:40", "11:35", "12:30", "13:20", "14:05", "14:55", "15:50", "16:45", "17:40", "18:35", "19:30"};

            String day_disp[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            if (day.equalsIgnoreCase("Mon")) r = 0;
            else if (day.equalsIgnoreCase("Tue")) r = 1;
            else if (day.equalsIgnoreCase("Wed")) r = 2;
            else if (day.equalsIgnoreCase("Thu")) r = 3;
            else if (day.equalsIgnoreCase("Fri")) r = 4;
            int start = 0;
            int end = 0;
            int itr = 0;


            if (r > 4)
            {
                r = 0;
                itr = 0;
            }
            else if ((hour >= 19 && minute >= 30) || (hour >= 20))
            {
                r++;
                //$itr=0;
            } else if (hour >= 8)
            {
                for (int i = 0; i < 13; i++)
                {
                    if (hour == timarr[i][0])
                    {
                        if (minute <= timarr[i][1])
                        {
                            itr = i;
                        } else itr = i + 1;
                        break;
                    }
                }
            }
            r = r % 5;
            days += (day_disp[r]);
            for (int i = itr; i < 13; i++) {
                if (matrix[r][i] == 0) start = i;
                else continue;
                while (matrix[r][i] == 0) {
                    end = i;
                    i++;
                    if (i == 13) break;
                }
                if (start == 0) {
                    status += ("8:00-" + tim_disp[end] + ",");
                } else {
                    status += (tim_disp[start - 1] + "-" + tim_disp[end] + ",");
                }
            }
            return status;
        }

        protected void onPostExecute(String result)
        {
          //  tv3.setText(status);
          //  tv2.setText(arr[1]);
          //  tv1.setText(days);

            if (arr[1].equals("\"\""))
            {
                arr[1] = "Not available!";
            }

            finalData = days + "'s free slots : \n" + status + "\n\nCabin details : \n" + arr[1];



            Bundle basket = new Bundle();
            basket.putString("mykeyy",finalData);

            Intent i = new Intent(getActivity(), Teacher_Show.class);
            i.putExtras(basket);
            startActivity(i);


            fetch.setText("");

        }
    }




}
