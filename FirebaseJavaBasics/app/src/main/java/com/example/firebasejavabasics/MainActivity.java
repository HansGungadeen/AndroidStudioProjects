package com.example.firebasejavabasics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button register,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.register);
//        login = findViewById(R.id.login);

        register.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            finish();
        });

        login.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        });
    }
}