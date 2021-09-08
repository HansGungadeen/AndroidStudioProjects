package com.example.polyblog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4,btn5,btnSignOut;

    TextView txtName;

    FirebaseAuth mAuth;

    private CircleImageView profileImageView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        profileImageView = findViewById(R.id.dp);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btnSignOut = findViewById(R.id.btnSignOut);

        txtName = findViewById(R.id.txtName);

        txtName.setText(GlobalVar.currentUser.getName());



        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                signOutUser();

            }
        });

        txtName = findViewById(R.id.txtName);

        txtName.setText(GlobalVar.currentUser.getName());

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent course = new Intent(Home.this, Course.class);
                startActivity(course);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(Home.this,EditProfile.class);
                startActivity(profile);
            }
        });

        getUserinfo();

    }

    private void getUserinfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0)
                {
                    String name = dataSnapshot.child("name").getValue().toString();

                    txtName.setText(name);

                    if (dataSnapshot.hasChild("image"))
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileImageView);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void signOutUser() {
        Intent mainActivity = new Intent(Home.this,MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
        finish();
    }
}
