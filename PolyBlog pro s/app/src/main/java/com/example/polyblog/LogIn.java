package com.example.polyblog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.polyblog.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class LogIn extends Activity {
    EditText edtEmail,edtPassword;

    Button btnSignIn;

    private FirebaseAuth mAuth;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPass);
        btnSignIn = findViewById(R.id.signIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                Signin(email,password);
            }
        });
    }

    private void Signin(String email, String password) {

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter your email!!", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password!!", Toast.LENGTH_SHORT).show();
        }

        else {

//            loadingBar.setTitle("Sign In ");
//            loadingBar.setMessage("Please wait....");
            final AlertDialog loadingBar = new SpotsDialog(LogIn.this);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        loadingBar.dismiss();

                        FirebaseDatabase.getInstance().getReference("User")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        GlobalVar.currentUser = dataSnapshot.getValue(User.class);
                                        Intent home = new Intent(LogIn.this,Home.class);
                                        startActivity(home);
                                        Toast.makeText(LogIn.this, "Successfully sign in", Toast.LENGTH_SHORT).show();
//                                        setContentView(R.layout.activity_home);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(LogIn.this, "Sign in failed!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
