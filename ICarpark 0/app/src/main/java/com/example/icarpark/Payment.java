package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Payment extends AppCompatActivity {
    TextView tv;

    String fname;
    String lname;
    String Eml;
    String Lcn;
    String Phn;

    private final String url="https://icarpark.000webhostapp.com/androidapp/bookingtest.php";
    private final String urlfetch="https://icarpark.000webhostapp.com/androidapp/parkingprice.php";

    String FromTime;
    String ToTime;
    String UID;
    String state1;
    String state2;
    long totalduration;
    double price;
    String Price;
    static String bcd;
    double setprice;
    ListView lv;
    String newFromTime;
    String newToTime;
    String Duration;
    long differenceInHours;
    long differenceInMinutes;
    ArrayList<Double> holder=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        fetchdata();



        tv=(TextView)findViewById(R.id.tv);





        UID = getIntent().getStringExtra("userID");
        fname = getIntent().getStringExtra("FirstName");
        lname = getIntent().getStringExtra("LastName");
        Eml = getIntent().getStringExtra("Email");
        Phn = getIntent().getStringExtra("Phone");
        Lcn = getIntent().getStringExtra("LicenseNumber");
        state1 = getIntent().getStringExtra("building_location");
        state2 = getIntent().getStringExtra("SlotNumber");
        FromTime = getIntent().getStringExtra("FromTime");
        ToTime = getIntent().getStringExtra("Totime");



//        String newFromTime;
//        long totalduration;
//        String newToTime;
//        String Duration;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        Date d1 = null;

        try
        {
            d = df.parse(FromTime);
            Calendar calFromTime;
            calFromTime = Calendar.getInstance();
            calFromTime.setTime(d);

            d1 = df.parse(ToTime);
            Calendar calToTime;
            calToTime = Calendar.getInstance();
            calToTime.setTime(d1);

            newFromTime = df.format(calFromTime.getTime());
            newToTime = df.format(calToTime.getTime());

            // Calculating the difference in milliseconds
            long differenceInMilliSeconds
                    = Math.abs(d1.getTime() - d.getTime());

            // Calculating the difference in Hours
             differenceInHours
                    = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;

            long differenceInHourstomins = differenceInHours * 60;

            // Calculating the difference in Minutes
            differenceInMinutes
                    = (differenceInMilliSeconds / (60 * 1000)) % 60;

            totalduration = differenceInHourstomins + differenceInMinutes;


//            double setprice1 = Double.parseDouble("5".trim());
//            double a = setprice * setprice1;
            price = totalduration *  setprice;

//            tv.setText("Location: "+ state1 + "\n" + "Slot Number: " + state2 + "\n" + "From Time: " + newFromTime + "\n" + "To Time: "  + newToTime + "\n" + "Parking Duration: " + "\n"  + differenceInHours + " hours "
//                    + differenceInMinutes + " minutes "
//                    + "\n" + "Amount Payable: "  + "Rs " + setprice);
            tv.setText("Loading...");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

    }



    public void process(View view)
    {
        String qryString = "?FromTime=" + FromTime  + "&ToTime=" + ToTime + "&userID=" + UID + "&location=" + state1 + "&slot=" + state2 + "&duration=" + totalduration + "&price=" + (setprice*totalduration);

        class dbclass extends AsyncTask<String, Void, String>
        {

            @Override
            protected String doInBackground(String... param)
            {
                try
                {
                    URL url = new URL(param[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();

                }
                catch (Exception ex)
                {
                    return ex.getMessage();
                }

            }
        }

        dbclass obj = new dbclass();
        obj.execute(url + qryString);
        Toast.makeText(getApplicationContext(), "Processing", Toast.LENGTH_SHORT).show();

        openMyBooking();

    }

    private void openMyBooking() {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        intent.putExtra("userID", UID);
        intent.putExtra("FirstName", fname);
        intent.putExtra("LastName", lname);
        intent.putExtra("Email", Eml);
        intent.putExtra("Phone", Phn);
        intent.putExtra("LicenseNumber", Lcn);
        startActivity(intent);
    }


    public void fetchdata()
    {


        lv = (ListView)findViewById(R.id.lv);
        class dbManager extends AsyncTask<String,Void,String>
        {

            protected void onPostExecute(String data)
            {
                try
                {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    holder.clear();


                    for (int i=0;i<ja.length();i++)
                    {
                        jo = ja.getJSONObject(i);
                        String answer = jo.getString("answer");
                        bcd = (answer.substring(data.indexOf("Rs")-4, data.indexOf("per")-7)).trim();
                        setprice = Double.parseDouble(bcd);





//                        setprice = Double.parseDouble(bcd);
//                        Toast.makeText(getApplicationContext(), bcd,Toast.LENGTH_LONG).show();
//                        holder.add(1.0);

//                        tv.setText("Location: "+ state1 + "\n" + "Slot Number: " + state2 + "\n" + "From Time: " + newFromTime + "\n" + "To Time: "  + newToTime + "\n" + "Parking Duration: " + "\n"  + differenceInHours + " hours "
//                                + differenceInMinutes + " minutes "
//                                + "\n" + "Amount Payable: "  + "Rs " + (setprice*totalduration));


                        tv.setText("Location: "+ state1 + "\n" + "Slot Number: " + state2 + "\n" + "From Time: " + newFromTime + "\n" + "To Time: "  + newToTime + "\n" + "Parking Duration: " + "\n"  + differenceInHours + " hours "
                                + differenceInMinutes + " minutes "
                                + "\n" + "Amount Payable: "  + "Rs " + (setprice*totalduration));


                    }

                    ArrayAdapter<Double> at = new ArrayAdapter<Double>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                    lv.setAdapter(at);

//                    openDashboard();

                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                }

            }

//            private void openDashboard() {
//                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//                startActivity(intent);
//            }

            @Override
            protected String doInBackground(String... strings)
            {
                try
                {
                    URL url=new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while ( (line=br.readLine())!=null)
                    {
                        data.append(line+"\n");
                    }

                    br.close();

                    return data.toString();

                }catch (Exception ex)
                {
                    return ex.getMessage();
                }
            }
        }

        dbManager obj = new dbManager();
        obj.execute(urlfetch);

    }
}

