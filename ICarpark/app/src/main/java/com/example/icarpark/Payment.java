package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

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

public class Payment extends AppCompatActivity implements PaymentResultListener
{
    TextView tv;
    //Button paybtn;

    String fname;
    String lname;
    String Eml;
    String Lcn;
    String Phn;

    private final String url="https://icarpark.000webhostapp.com/androidapp/booking.php";
    private final String urlfetch="https://icarpark.000webhostapp.com/androidapp/parkingprice.php";
    private final String urlupdate="https://icarpark.000webhostapp.com/androidapp/paymentupdate.php";
    private final String urlupdatefailed="https://icarpark.000webhostapp.com/androidapp/paymentupdatefailed.php";

    String FromTime;
    String ToTime;
    String UID;
    String state1;
    String state2;
    long totalduration;
    static String bcd;
    double setprice;
    ListView lv;
    String newFromTime;
    String newToTime;
    long differenceInHours;
    long differenceInMinutes;

    String abcd;
    ArrayList<Double> holder=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        fetchdata();



        tv=(TextView)findViewById(R.id.tv);
        //paybtn=(Button)findViewById(R.id.paybtn);
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
            differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;

            totalduration = differenceInHourstomins + differenceInMinutes;
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
        makepayment();

    }

    private void makepayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_fBjPOZnpZnrbxu");

        checkout.setImage(R.drawable.logo);
        final Activity activity = this;



        try
        {
            JSONObject options = new JSONObject();

            options.put("name", "Smart Parking");
            options.put("description", "Payment for new parking");
            options.put("theme.color", "#3399cc");
            options.put("currency", "MUR");
            options.put("amount", (setprice*totalduration*100));//300 X 100
            options.put("prefill.email", Eml);
            options.put("prefill.contact", +230 + Phn);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);
        }
        catch(Exception e)
        {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }


    @Override
    public void onPaymentSuccess(String s)
    {
        String qryupdate = "?FromTime=" + FromTime + "&ToTime=" + ToTime + "&userID=" + UID + "&location=" + state1 + "&slot=" + state2 + "&duration=" + totalduration + "&price=" + (setprice*totalduration);


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
        obj.execute(urlupdate + qryupdate);

        openMyBooking();
        Toast.makeText(getApplicationContext(),"Payment Sucessfull",Toast.LENGTH_LONG).show();

    }


    @Override
    public void onPaymentError(int i, String s)
    {
        String qryupdate = "?FromTime=" + FromTime + "&ToTime=" + ToTime + "&userID=" + UID + "&location=" + state1 + "&slot=" + state2 + "&duration=" + totalduration + "&price=" + (setprice*totalduration);


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
        obj.execute(urlupdatefailed + qryupdate);

        openMyBooking();
        Toast.makeText(getApplicationContext(),"Payment Failed",Toast.LENGTH_LONG).show();

    }

    private void openMyBooking()
    {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        double v =  setprice*totalduration;
        intent.putExtra("userID", UID);
        intent.putExtra("FirstName", fname);
        intent.putExtra("LastName", lname);
        intent.putExtra("Email", Eml);
        intent.putExtra("Phone", +230 + Phn);
        intent.putExtra("LicenseNumber", Lcn);
        intent.putExtra(" val", v);
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
                        tv.setText("Location: " + "\n" + state1 + "\n" + "Slot Number: " + "\n" + state2 + "\n" + "From Time: " + "\n" + newFromTime + "\n" + "To Time: "  + "\n" + newToTime + "\n" + "Parking Duration: " + "\n"  + differenceInHours + " hours "
                                + differenceInMinutes + " minutes "
                                + "\n" + "Amount Payable: " + "\n"  + "Rs " + (setprice*totalduration));

                    }

                    ArrayAdapter<Double> at = new ArrayAdapter<Double>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                    lv.setAdapter(at);


                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                }

            }

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

