package com.example.icarpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Dashboard extends AppCompatActivity {

    private Button button1, button2, button3, button4, button5, button8;
    private static final String apiurl="https://icarpark.000webhostapp.com/androidapp/faq.php";


    TextView tv;
    String UID;
    String fname;
    String lname;
    String Eml;
    String Lcn;
    String Phn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        UID = getIntent().getStringExtra("userID");
        fname = getIntent().getStringExtra("FirstName");
        lname = getIntent().getStringExtra("LastName");
        Eml = getIntent().getStringExtra("Email");
        Phn = getIntent().getStringExtra("Phone");
        Lcn = getIntent().getStringExtra("LicenseNumber");


        tv=(TextView)findViewById(R.id.tv);

        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
        button8=findViewById(R.id.button8);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityNewBooking();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityCancelBooking();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityProfile();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMyBooking();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityHelp();
            }
        });


        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("FirstName"))
        tv.setText(sp.getString("FirstName",""));


    }

    private void openActivityCancelBooking() {
        Intent intent = new Intent(this, CancelBooking.class);
        intent.putExtra("userID", UID);
        startActivity(intent);
    }

    private void openActivityProfile() {
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("userID", UID);
        intent.putExtra("FirstName", fname);
        intent.putExtra("LastName", lname);
        intent.putExtra("Email", Eml);
        intent.putExtra("Phone", Phn);
        intent.putExtra("LicenseNumber", Lcn);
        startActivity(intent);
    }

    private void openActivityMyBooking()
    {
        Intent intent = new Intent(this, MyBooking.class);
        intent.putExtra("userID", UID);
        startActivity(intent);
    }

    public void openActivityNewBooking()
    {
        Intent intent = new Intent(this, NewBooking.class);
        intent.putExtra("userID", UID);
        intent.putExtra("FirstName", fname);
        intent.putExtra("LastName", lname);
        intent.putExtra("Email", Eml);
        intent.putExtra("Phone", Phn);
        intent.putExtra("LicenseNumber", Lcn);
        startActivity(intent);
    }

    public void openActivityHelp()
    {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

    //Logout out function to be called in login activity
    public void logout_process(View view) {
        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        if (sp.contains("FirstName")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("FirstName");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //finish();

    }
}