package com.example.polyblog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    Button loginBtn;
    TextView forgotPassword, needAnAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);
        forgotPassword = findViewById(R.id.forgot_password);
        needAnAccount = findViewById(R.id.need_an_account);

        goto_Register(needAnAccount);
    }

    public void goto_Register(View view) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

}