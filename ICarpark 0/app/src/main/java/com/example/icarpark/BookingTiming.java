package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;


public class BookingTiming extends AppCompatActivity
{

            EditText FromTime,ToTime;

            private final String url="https://icarpark.000webhostapp.com/androidapp/booking.php";

            @Override
            protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_booking_timing);

                FromTime=(EditText)findViewById(R.id.FromTime);
                ToTime=(EditText)findViewById(R.id.ToTime);

                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

                TextView date = findViewById(R.id.date);
                TextView date2 = findViewById(R.id.date2);
                date.setText(currentDate);
                date2.setText(currentDate);
            }

            public void booking_process (View view)
            {

                String n1 = FromTime.getText().toString().trim();
                String n2 = ToTime.getText().toString().trim();
                String qryString = "?FromTime=" + n1  + "&ToTime=" + n2 ;



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

                        protected void onPostExecute(String data)
                        {
                            FromTime.setText("");
                            ToTime.setText("");

                            Toast.makeText(getApplicationContext(), "Registration Success. Please Login", Toast.LENGTH_SHORT).show();

                        }
                    }

                    dbclass obj = new dbclass();
                    obj.execute(url + qryString);


            }

}


