package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class Slots extends AppCompatActivity {

    Spinner spinnerSlots;

    ArrayList<String> slotList = new ArrayList<>();
    ArrayAdapter<String> slotAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots);




        requestQueue = Volley.newRequestQueue(getApplicationContext());
        spinnerSlots = findViewById(R.id.spinnerSlots);
        String url = "https://icarpark.000webhostapp.com/androidapp/slots1.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                JSONArray jsonArray = null;
                try {
                    JSONArray jsonArray = response.getJSONArray("slots");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String slot = jsonObject.optString("slotnumber");
                        slotList.add(slot);
                        slotAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, slotList);
                        slotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSlots.setAdapter(slotAdapter);

                        Button getText = findViewById(R.id.getText);
                        getText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                String state = spinnerSlots.getSelectedItem().toString();

                                Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT).show();

                                openActivityBookingTime();

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
    }


    private void openActivityBookingTime()
    {
        Intent intent = new Intent(this, BookingTime.class);
        startActivity(intent);
    }
}