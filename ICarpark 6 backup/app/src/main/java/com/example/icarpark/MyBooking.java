package com.example.icarpark;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class MyBooking extends AppCompatActivity {

    String n1;
    String UID;
    String bookingId;
    ProgressDialog progressDialogBooking;

private static final String apiurl="https://icarpark.000webhostapp.com/androidapp/qrcode.php";
    String qryString;

    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
    ListView lv;
    TextView tv;
    ImageView ivOutput;
    Button btGenerateqr;

    ArrayList<String> holder=new ArrayList<>();


    @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            UID = getIntent().getStringExtra("userID");
            qryString = "?userID=" + UID;


            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_my_booking);

            btGenerateqr = findViewById(R.id.bt_generateqr);
            ivOutput = findViewById(R.id.iv_output);
            TextView date = findViewById(R.id.date);


            date.setText(currentDate);
            fetchdatabooking();


            btGenerateqr.setOnClickListener(new View.OnClickListener()
            {

                public void onClick (View v)
                {

                    String n2 = currentDate;
                    MultiFormatWriter writer = new MultiFormatWriter();

                    try
                    {
                        BitMatrix matrix = writer.encode(n1.substring(7, n1.indexOf("\",")), BarcodeFormat.QR_CODE, 1300, 1100);

                        BarcodeEncoder encoder = new BarcodeEncoder();

                        Bitmap bitmap = encoder.createBitmap(matrix);

                        ivOutput.setImageBitmap(bitmap);

                        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


                    }
                    catch (WriterException e)
                    {
                        e.printStackTrace();
                    }

                }

            });

        }



        public void fetchdatabooking()
        {
            TextView tv = findViewById(R.id.tv);

            lv = (ListView) findViewById(R.id.lv);

            btGenerateqr.setVisibility(View.GONE);

                    //tv.setText("Loading ...");

            progressDialogBooking = new ProgressDialog(MyBooking.this);
            progressDialogBooking.show();
            progressDialogBooking.setContentView(R.layout.progress_dialog);
            progressDialogBooking.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



            class dbManager extends AsyncTask<String, Void, String> {

                protected void onPostExecute(String data) {

                     //n1 = data.substring(7, data.indexOf("\","));
                    n1 = data;

                    try {

                        tv.setText(" ");
                        progressDialogBooking.dismiss();

                        JSONArray ja = new JSONArray(data);
                        JSONObject jo = null;

                        holder.clear();


                        for (int i = 0; i < ja.length(); i++) {
                            jo = ja.getJSONObject(i);
                            bookingId = jo.getString("bookingId");
                            String location = jo.getString("location");
                            String slot = jo.getString("slot");
                            String FromTime = jo.getString("FromTime");
                            String ToTime = jo.getString("ToTime");


                            String Duration = jo.getString("duration");
                            String AmountPaid = jo.getString("price");
                            String timestamp = jo.getString("timestamp");

                            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                            Calendar calendar = Calendar.getInstance();
                            String actualtime = DateFormat.getTimeInstance().format(calendar.getTime());
                            Date acttimeparse = df.parse(actualtime);
                            calendar.setTime(acttimeparse);
                            Date ToTimeparse = df.parse(ToTime);


                            boolean diff = ToTimeparse.before(acttimeparse);


                            if(diff == true) {
                                tv.setVisibility(View.VISIBLE);
                                tv.setText("Booking Expired");
                                //tv.setVisibility(View.GONE);
                            }

                            else
                            {
                                tv.setVisibility(View.GONE);
                                btGenerateqr.setVisibility(View.VISIBLE);
                                progressDialogBooking.dismiss();
                                holder.add("Location: " + location + "\n" + "Slot: " + slot + "\n" + "Time: " + FromTime + " - " + ToTime);

                            }

                        }

                        ArrayAdapter<String> at = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, holder);
                        lv.setAdapter(at);




                    } catch (Exception ex)
                    {
                        btGenerateqr.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v)
                            {
                                Toast.makeText(getApplicationContext(), "Error.Invalid QR", Toast.LENGTH_LONG).show();
                            }
                        });
                        btGenerateqr.setVisibility(View.GONE);
                        tv.setVisibility(View.VISIBLE);
                        tv.setText("No Booking");

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
}

