package com.example.icarpark;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookingTime extends AppCompatActivity {

    TextView FromDate, ToDate;
    String FromTime;

//    Time atime;

    String ToTime;
    String UID;
    String state1;
    String state2;
    String fname;
    String lname;
    String Eml;
    String Lcn;
    String Phn;
    String a;


//    private final String url="https://icarpark.000webhostapp.com/androidapp/booking1.php";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_time);

        UID = getIntent().getStringExtra("userID");
        fname = getIntent().getStringExtra("FirstName");
        lname = getIntent().getStringExtra("LastName");
        Eml = getIntent().getStringExtra("Email");
        Phn = getIntent().getStringExtra("Phone");
        Lcn = getIntent().getStringExtra("LicenseNumber");
        state1 = getIntent().getStringExtra("building_location");
        state2 = getIntent().getStringExtra("SlotNumber");
        Toast.makeText(getApplicationContext(), UID + state1 + state2, Toast.LENGTH_SHORT).show();


        Spinner spinnerfromtime = findViewById(R.id.Fromtimespinner);
        Spinner spinnertotime = findViewById(R.id.Totimespinner);

        ArrayList<String> fromtimelist = new ArrayList<>();
        ArrayList<String> totimelist = new ArrayList<>();


        String openTime = "15:30";
        String newFromTime;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        try
        {
            d = df.parse(openTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int diffcomp = d.compareTo(d);
            Calendar calendar = Calendar.getInstance();


            FromDate = findViewById(R.id.FromDate);
            for (int i = 0; i < 15; i++)
            {

                cal.add(Calendar.MINUTE, 30);

                newFromTime = df.format(cal.getTime());
                fromtimelist.add(newFromTime);
            }


            ArrayAdapter<String> fromtimeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromtimelist);
            fromtimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerfromtime.setAdapter(fromtimeAdapter);


        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }



        spinnerfromtime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromTime = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });



        String myTime = "15:30";
        String newToTime;
//        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        try
        {
            d1 = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d1);
            for (int i = 0; i < 15; i++)
            {

                cal.add(Calendar.MINUTE, 30);

                newToTime = df.format(cal.getTime());
                totimelist.add(newToTime);
            }

            ArrayAdapter<String> totimeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, totimelist);
            totimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnertotime.setAdapter(totimeAdapter);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }




        spinnertotime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToTime = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        FromDate = findViewById(R.id.FromDate);
        ToDate = findViewById(R.id.ToDate);
        FromDate.setText(currentDate);
        ToDate.setText(currentDate);
    }

    public void process(View view)
    {

        Toast.makeText(getApplicationContext(), "Processing", Toast.LENGTH_SHORT).show();

//        String qryString = "?FromTime=" + FromTime  + "&ToTime=" + ToTime ;


        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date dfromtime = null;

        try
        {
            dfromtime = df.parse(FromTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dfromtime);

            Date d = null;


//
//            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();

            try
            {
                d = df.parse(ToTime);
                Calendar cal123 = Calendar.getInstance();
                cal123.setTime(d);


                Calendar calendar = Calendar.getInstance();
                a = DateFormat.getTimeInstance().format(calendar.getTime());
                Date ab = df.parse(a);

                int diff = dfromtime.compareTo(d);


                if(diff > 0 || diff ==0 ) {

                    Toast.makeText(getApplicationContext(), "Invalid. To time should be greater", Toast.LENGTH_SHORT).show();
                }


//                 if(df.parse(FromTime).before(ab) || df.parse(ToTime).after(ab))
                else if(df.parse(FromTime).before(ab))
                {

                    Toast.makeText(getApplicationContext(), "Time cannot be before current time.", Toast.LENGTH_SHORT).show();

                }

                else
                {
                    openActivityPayment();
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }


        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }



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

//        dbclass obj = new dbclass();
//        obj.execute(url + qryString);



    }

    private void openActivityPayment()
    {
        Intent intent = new Intent(this, Payment.class);
        intent.putExtra("userID", UID);
        intent.putExtra("FirstName", fname);
        intent.putExtra("LastName", lname);
        intent.putExtra("Email", Eml);
        intent.putExtra("Phone", Phn);
        intent.putExtra("LicenseNumber", Lcn);
        intent.putExtra("building_location", state1);
        intent.putExtra("SlotNumber", state2);
        intent.putExtra("FromTime", FromTime);
        intent.putExtra("Totime", ToTime);
        startActivity(intent);
    }
}