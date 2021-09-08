package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CancelBooking extends AppCompatActivity
{
    TextView tv;
    String n1;
    String UID;

    private static final String apiurl="https://icarpark.000webhostapp.com/androidapp/qrcode.php";
    private static final String apiurlcancelbooking="https://icarpark.000webhostapp.com/androidapp/cancelbooking.php";
    String qryString;

    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
    ListView lv;
    ImageView ivOutput;
    Button bt_cancelbooking;

    ArrayList<String> holder=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);



        UID = getIntent().getStringExtra("userID");
        qryString = "?userID=" + UID;

        bt_cancelbooking = findViewById(R.id.bt_cancelbooking);
        ivOutput = findViewById(R.id.iv_output);
        TextView date = findViewById(R.id.date);

        date.setText(currentDate);
        fetchdatabooking();
        process();

        bt_cancelbooking.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {

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
                obj.execute(apiurlcancelbooking + qryString);

                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();


            }
        });

    }


    public void fetchdatabooking()
    {
        TextView tv = findViewById(R.id.tv);
        lv = (ListView) findViewById(R.id.lv);
        tv.setText("Loading ...");
        class dbManager extends AsyncTask<String, Void, String> {
            protected void onPostExecute(String data) {
                n1 = data;
                try {
                    tv.setText(" ");

                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    holder.clear();
                    String slot;

                    for (int i = 0; i < ja.length(); i++) {


                        jo = ja.getJSONObject(i);
                        String location = jo.getString("location");
                        slot = jo.getString("slot");
                        String FromTime = jo.getString("FromTime");
                        String ToTime = jo.getString("ToTime");
                        String Duration = jo.getString("duration");
                        String AmountPaid = jo.getString("price");
                        String timestamp = jo.getString("timestamp");



                        SimpleDateFormat df = new SimpleDateFormat("HH:mm:SS");
                        Calendar calendar = Calendar.getInstance();
                        String actualtime = DateFormat.getTimeInstance().format(calendar.getTime());
                        Date acttimeparse = df.parse(actualtime);
                        calendar.setTime(acttimeparse);
//                        calendar.add(Calendar.HOUR, 0);
//                        calendar.add(Calendar.MINUTE, 15);
//                        calendar.add(Calendar.SECOND, 0);
//                        Date limit = calendar.getTime();


                        Date bookingcreationtime = df.parse(timestamp);
                        calendar.setTime(bookingcreationtime);
                        calendar.add(Calendar.HOUR, 0);
                        calendar.add(Calendar.MINUTE, 2);
                        calendar.add(Calendar.SECOND, 0);
                        Date limit = calendar.getTime();


                        boolean diff = acttimeparse.before(limit);


                        if(diff == false) {

                            tv.setText("Cancellation Expired");
                            bt_cancelbooking.setOnClickListener(new View.OnClickListener() {

                                public void onClick(View v)
                                {

                                    Toast.makeText(getApplicationContext(), "Cancellation Expired", Toast.LENGTH_LONG).show();

                                }
                            });
                        }

                        else
                        {

                            String timely = limit.toString();

                            Toast.makeText(getApplicationContext(), timely, Toast.LENGTH_LONG).show();

                            tv.setText("You may place same text from holder here and comment below holder.add");
                            holder.add("Location: " + location + "\n" + "Slot: " + slot + "\n" + "From: " + FromTime + "\n" + "To: " + ToTime + "\n" + "Duration: " + Duration + " minutes" + "\n" + "Amount Paid: Rs " + AmountPaid);


                        }




                    }


                    ArrayAdapter<String> at = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, holder);
                    lv.setAdapter(at);


                } catch (Exception ex) {

                    tv.setText("No booking");

                    bt_cancelbooking.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v)
                        {

                            Toast.makeText(getApplicationContext(), "Error. Unable to cancel booking", Toast.LENGTH_LONG).show();

                        }
                    });
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while ((line = br.readLine()) != null) {
                        data.append(line + "\n");
                    }

                    br.close();

                    return data.toString();

                } catch (Exception ex) {
                    return ex.getMessage();
                }
            }
        }

        dbManager obj = new dbManager();
        obj.execute(apiurl + qryString);
    }

    public void process()
    {

        bt_cancelbooking.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {

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
                obj.execute(apiurlcancelbooking + qryString);


                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();


            }
        });

    }

}