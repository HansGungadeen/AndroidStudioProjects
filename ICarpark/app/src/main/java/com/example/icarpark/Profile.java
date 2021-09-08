package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class Profile extends AppCompatActivity
{

    String UID;
    String fname;
    String lname;
    String Eml;
    String Lcn;
    String Phn;

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv=(TextView)findViewById(R.id.tv);
        UID = getIntent().getStringExtra("userID");
        fname = getIntent().getStringExtra("FirstName");
        lname = getIntent().getStringExtra("LastName");
        Eml = getIntent().getStringExtra("Email");
        Phn = getIntent().getStringExtra("Phone");
        Lcn = getIntent().getStringExtra("LicenseNumber");


        tv.setText("FirstName: " + "\n" +fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase() + "\n" + "Lastname: " + "\n" + lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase() + "\n" + "Email: " + "\n" + Eml + "\n" + "Phone Number: " + "\n" + Phn + "\n" + "License Number: " + "\n" + Lcn);


    }

}