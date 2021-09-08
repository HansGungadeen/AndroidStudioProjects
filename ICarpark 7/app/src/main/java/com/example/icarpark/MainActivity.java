package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=findViewById(R.id.button1);

        button2=findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityLogin();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRegister();
            }
        });
    }

    private void openActivityRegister()
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void openActivityLogin() 
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {

        //finish();
        // super.onBackPressed();
    }
}