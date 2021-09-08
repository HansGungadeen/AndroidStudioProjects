package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
    ProgressDialog progressDialogCancel;

    private static final String apiurl="https://icarpark.000webhostapp.com/androidapp/qrcode.php";
    private static final String apiurlcancelbooking="https://icarpark.000webhostapp.com/androidapp/cancelbooking.php";
    String qryString;

    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
    ListView lv;
    ImageView ivOutput;
    Button bt_cancelbooking;
    TextView tvcancel;
    TextView tvdate;

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
        TextView tvdate = findViewById(R.id.tvdate);
        TextView tvcancel = findViewById(R.id.tvcancel);

        tvdate.setText(currentDate);
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

                openCancelDialog();
                //

            }

        });

       // finish();
    }


    public void fetchdatabooking()
    {
        TextView tv = findViewById(R.id.tv);
        lv = (ListView) findViewById(R.id.lv);

        bt_cancelbooking.setVisibility(View.GONE);
        progressDialogCancel = new ProgressDialog(CancelBooking.this);
        progressDialogCancel.show();
        progressDialogCancel.setContentView(R.layout.progress_dialog);
        progressDialogCancel.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //tv.setText("Loading ...");
        class dbManager extends AsyncTask<String, Void, String> {
            protected void onPostExecute(String data) {
                n1 = data;
                try {
                    progressDialogCancel.dismiss();
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
                        Date bookingcreationtime = df.parse(timestamp);
                        calendar.setTime(bookingcreationtime);
                        calendar.add(Calendar.HOUR, 0);
                        calendar.add(Calendar.MINUTE, 5);
                        calendar.add(Calendar.SECOND, 0);
                        Date limit = calendar.getTime();


                        boolean diff = acttimeparse.before(limit);


                        if(diff == false) {

                            bt_cancelbooking.setVisibility(View.GONE);
                            tv.setText("Cancellation Time Expired");

                        }

                        else
                        {

                            String cancellimit = limit.toString();
                            tv.setVisibility(View.GONE);
                            bt_cancelbooking.setVisibility(View.VISIBLE);
                            progressDialogCancel.dismiss();
                            holder.add("Location: " + location + "\n" + "Slot: " + slot + "\n" + "From: " + FromTime + "\n" + "To: " + ToTime + "\n" + "Duration: " + Duration + " minutes" + "\n" + "Amount Paid: Rs " + AmountPaid);

                        }


                    }


                    ArrayAdapter<String> at = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, holder);
                    lv.setAdapter(at);


                } catch (Exception ex) {

                    bt_cancelbooking.setVisibility(View.GONE);
                    tv.setText("No booking");

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

            }
        });

    }

    private void openCancelDialog() {
            CancelDialog canceldialog = new CancelDialog();
            canceldialog.show(getSupportFragmentManager(), "CancelDialog");

    }

}