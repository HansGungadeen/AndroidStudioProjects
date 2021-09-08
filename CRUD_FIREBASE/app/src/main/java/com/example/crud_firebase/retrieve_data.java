package com.example.crud_firebase;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class retrieve_data extends Activity {

    //create var for variable db
    FirebaseDatabase firebaseDatabase;
    //create database ref
    DatabaseReference databaseReference;

    //var for text view
    private TextView retrieveTV;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_data);

        //get instance of firebase db
        firebaseDatabase = FirebaseDatabase.getInstance();
        //get reference of db
        databaseReference = firebaseDatabase.getReference("Data");
        //init object class var
        retrieveTV = findViewById(R.id.idTVRetrieveData);

        //call method
        getdata();

    }

    private void getdata(){
        //calling add value event listener method to get values from db
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String value = snapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                retrieveTV.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(retrieve_data.this,"Failed to get data", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
