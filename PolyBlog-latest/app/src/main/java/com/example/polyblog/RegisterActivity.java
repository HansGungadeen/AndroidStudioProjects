package com.example.polyblog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {

    EditText registerEmail, registerPassword;
    Button registerBtn;
    TextView alreadyhaveAnAccount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        registerBtn = findViewById(R.id.register_btn);
        alreadyhaveAnAccount = findViewById(R.id.already_have_an_account);

        //redirects to login
        alreadyhaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

    }
}
