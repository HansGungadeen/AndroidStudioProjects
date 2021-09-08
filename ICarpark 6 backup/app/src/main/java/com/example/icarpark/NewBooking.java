package com.example.icarpark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewBooking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerLocation;
    Spinner spinnerSlots;
    String state1;
    String state2;
    String UID;
    String fname;
    String lname;
    String Eml;
    String Lcn;
    String Phn;

    ArrayList<String> locationList = new ArrayList<>();
    ArrayList<String> slotsList = new ArrayList<>();
    ArrayAdapter<String> locationAdapter;
    ArrayAdapter<String> slotsAdapter;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);

        UID = getIntent().getStringExtra("userID");
        fname = getIntent().getStringExtra("FirstName");
        lname = getIntent().getStringExtra("LastName");
        Eml = getIntent().getStringExtra("Email");
        Phn = getIntent().getStringExtra("Phone");
        Lcn = getIntent().getStringExtra("LicenseNumber");
        Button getText = findViewById(R.id.getText);


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        spinnerLocation = findViewById(R.id.spinnerLocation);
        spinnerSlots = findViewById(R.id.spinnerSlots);
        String url = "https://icarpark.000webhostapp.com/androidapp/location.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                locationList.add(". . . . .");
                try
                {
                    JSONArray jsonArray = response.getJSONArray("location");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String building_location = jsonObject.optString("building_location");
                        locationList.add(building_location);
                        locationAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, locationList);
                        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerLocation.setAdapter(locationAdapter);


                        getText.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                try {

                                    state1 = spinnerLocation.getSelectedItem().toString();
                                    state2 = spinnerSlots.getSelectedItem().toString();

                                   openActivityBookingTime();


                                } catch (Exception e) {

                                    if(state1 == ". . . . .")
                                    {

                                        spinnerLocation.requestFocus();
                                        Toast.makeText(getApplicationContext(), "Select your location first", Toast.LENGTH_SHORT).show();


                                    }

                                    else if (state2 == null)
                                    {
                                        Toast.makeText(getApplicationContext(), "No available slots", Toast.LENGTH_SHORT).show();

                                    }



                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
        spinnerLocation.setOnItemSelectedListener(this);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
    {
        if (adapterView.getId() == R.id.spinnerLocation)
        {
            slotsList.clear();
            String selectedLocation = adapterView.getSelectedItem().toString();
            String url = "https://icarpark.000webhostapp.com/androidapp/slots.php?building_location=" + selectedLocation;
            requestQueue = Volley.newRequestQueue(this);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        JSONArray jsonArray = response.getJSONArray("slots");


                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String SlotNumber = jsonObject.optString("SlotNumber");
                            slotsList.add(SlotNumber);
                            slotsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, slotsList);
                            slotsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSlots.setAdapter(slotsAdapter);


                        }




                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                }


            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {

                }
            });



            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

        private void openActivityBookingTime()
    {
        Intent intent = new Intent(this, BookingTime.class);
        intent.putExtra("userID", UID);
        intent.putExtra("FirstName", fname);
        intent.putExtra("LastName", lname);
        intent.putExtra("Email", Eml);
        intent.putExtra("Phone", Phn);
        intent.putExtra("LicenseNumber", Lcn);
        intent.putExtra("building_location", state1);
        intent.putExtra("SlotNumber", state2);
        startActivity(intent);
    }
}

