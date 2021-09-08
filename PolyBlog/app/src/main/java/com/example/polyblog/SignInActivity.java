package com.example.polyblog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.polyblog.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEmailField, mPasswordField;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);
        findViewById(R.id.button_sign_in).setOnClickListener(this);
        findViewById(R.id.button_sign_up).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() !=null){
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void onAuthSuccess(FirebaseUser firebaseUser) {
        String email = firebaseUser.getEmail();
        String username = email;
        if (email !=null && email.contains("@")){
            username = email.split("@")[0];
        }

        User user = new User(username,email);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private void signIn(){
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

        if (validateForm(email,password)){
            showProgressDialog();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((task) -> {
                hideProgressDialog();
                if (task.isSuccessful()){
                    onAuthSuccess(task.getResult().getUser());
                }else{
                    Toast.makeText(SignInActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void signUp(){
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

        if (validateForm(email,password)){
            showProgressDialog();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((task) -> {
                hideProgressDialog();
                if (task.isSuccessful()){
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast.makeText(SignInActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            });

        }
    }

    private boolean validateForm(String email, String password) {
        if (TextUtils.isEmpty(email)){
            mEmailField.setError("Required");
            return false;
        }else if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required");
            return false;
        }else {
            mEmailField.setError(null);
            mPasswordField.setError(null);
            return true;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_sign_in:
                signIn();
                break;
            case R.id.button_sign_up:
                signUp();
                break;
        }
    }
}