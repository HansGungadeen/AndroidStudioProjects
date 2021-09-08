package com.example.icarpark;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Profile extends AppCompatActivity
{

    String UID;
    String fname;
    String lname;
    String Eml;
    String Lcn;
    String Phn;

    TextView tv;
    TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv=(TextView)findViewById(R.id.tv);
        tv1=(TextView)findViewById(R.id.tv1);
        UID = getIntent().getStringExtra("userID");
        fname = getIntent().getStringExtra("FirstName");
        lname = getIntent().getStringExtra("LastName");
        Eml = getIntent().getStringExtra("Email");
        Phn = getIntent().getStringExtra("Phone");
        Lcn = getIntent().getStringExtra("LicenseNumber");


        tv.setText(fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase() + " " + lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase());
        tv1.setText(Eml + "\n" + "+" + Phn + "\n" + Lcn);


    }

}