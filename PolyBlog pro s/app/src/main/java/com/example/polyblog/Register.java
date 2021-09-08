package com.example.polyblog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText edtEmail,edtPassword;

    Button btnRegister;

    ProgressDialog loadingBar;
    private Object GlobalVar;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPass);

        btnRegister = findViewById(R.id.reg);

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                RegisterUser(email,password);
            }
        });



    }

    private void RegisterUser(String email, String password) {

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter your email!!", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password!!", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(Register.this,"User Registered",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
                loadingBar.dismiss();

            }
        });
    }
}
