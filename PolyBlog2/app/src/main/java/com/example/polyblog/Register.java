package com.example.polyblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.polyblog.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase db; //This is for the database
    DatabaseReference userRef; //This is for the model folder find in com.polyblog for the java class

    EditText edtEmail,edtName,edtPassword;
    Button btnRegister;

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        userRef = db.getReference("User");

        loadingBar = new ProgressDialog(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPass);
        btnRegister = findViewById(R.id.reg);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String pass = edtPassword.getText().toString();

                RegisterUser(email,pass);
            }
        });

    }

    private void RegisterUser(String email, String pass) {

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter your email!!", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(this, "Please enter your password!!", Toast.LENGTH_SHORT).show();
        }

        else{

//            loadingBar.setTitle("Registration");
//            loadingBar.setMessage("Please wait, while we are register");
            final AlertDialog loadingBar = new SpotsDialog(Register.this);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
               @Override
                public void onComplete(@NonNull Task<AuthResult> task){

                    if (task.isSuccessful())
                    {
                        //save to db

                        User user = new User();
                        user.setEmail(edtEmail.getText().toString());
                        user.setName(edtName.getText().toString());
                        user.setPassword(edtPassword.getText().toString());

                        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Register.this, "User Register successfully !!!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        loadingBar.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        });
                    }
                    else{
                        Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

                    loadingBar.dismiss();

                }
            });
        }

    }
}
