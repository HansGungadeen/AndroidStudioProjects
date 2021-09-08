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

public class MyBooking extends AppCompatActivity {

    String n1;
    String UID;



private static final String apiurl="https://icarpark.000webhostapp.com/androidapp/qrcode.php";
    String qryString;

    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
    ListView lv;
    TextView tv;
    ImageView ivOutput;
    Button btGeneratetest;

    ArrayList<String> holder=new ArrayList<>();


    @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            UID = getIntent().getStringExtra("userID");
            qryString = "?userID=" + UID;


            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_my_booking);

            btGeneratetest = findViewById(R.id.bt_generatetest);
            ivOutput = findViewById(R.id.iv_output);
            TextView date = findViewById(R.id.date);


            date.setText(currentDate);
            fetchdatabooking();




            btGeneratetest.setOnClickListener(new View.OnClickListener()
            {

                public void onClick (View v)
                {

                    String n2 = currentDate;


                    MultiFormatWriter writer = new MultiFormatWriter();

                    try
                    {
                        BitMatrix matrix = writer.encode(n1, BarcodeFormat.QR_CODE, 1000, 1000);

                        BarcodeEncoder encoder = new BarcodeEncoder();

                        Bitmap bitmap = encoder.createBitmap(matrix);

                        ivOutput.setImageBitmap(bitmap);

                        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();


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

            tv.setText("Loading ...");
            class dbManager extends AsyncTask<String, Void, String> {






                protected void onPostExecute(String data) {





                     n1 = data;
                    try {

                        tv.setText(" ");

                        JSONArray ja = new JSONArray(data);
                        JSONObject jo = null;

                        holder.clear();


                        for (int i = 0; i < ja.length(); i++) {
                            jo = ja.getJSONObject(i);
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
                            //String test1 = acttimeparse.toString();

                            Date ToTimeparse = df.parse(ToTime);


                            boolean diff = ToTimeparse.before(acttimeparse);


                            if(diff == true) {

                                tv.setText("Booking Expired");
                                btGeneratetest.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v)
                                    {

                                        Toast.makeText(getApplicationContext(), "Booking Expired", Toast.LENGTH_LONG).show();

                                    }
                                });
                            }

                            else
                            {

                                tv.setText("You may place same text from holder here and comment below holder.add");

                                holder.add("Location: " + location + "\n" + "Slot: " + slot + "\n" + "From: " + FromTime + "\n" + "To: " + ToTime + "\n" + "Duration: " + Duration + " minutes" + "\n" + "Amount Paid: Rs " + AmountPaid);

//                                Toast.makeText(getApplicationContext(), timestamp + test1, Toast.LENGTH_LONG).show();

                            }

                        }

                        ArrayAdapter<String> at = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, holder);
                        lv.setAdapter(at);




                    } catch (Exception ex)
                    {
                        tv.setText("No/Invalid Booking");

                        btGeneratetest.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v)
                            {
                                  Toast.makeText(getApplicationContext(), "Error.Invalid QR", Toast.LENGTH_LONG).show();
                            }
                        });

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

