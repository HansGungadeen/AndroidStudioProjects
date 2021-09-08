package com.example.crud_firebase;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class retrieve_data2 extends Activity {
    //create variables for list view
    private ListView coursesLV;
    //create a new array list
    ArrayList<String> coursesArraylist;
    //creating a variable for database reference
    DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_data2);

        //init var for list views
        coursesLV = findViewById(R.id.idLVCourses);
        //init array list
        coursesArraylist = new ArrayList<String>();

        //call method
        initializeListView();
    }

    private void initializeListView(){
        //create a new array adapter for list view
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,coursesArraylist);

        //get ref of db
        reference = FirebaseDatabase.getInstance().getReference("artists/-McPUZZm2qzD86hm8KI7");
        // in below line we are calling method for add child event
        // listener to get the child of our database.
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added to
                // our data base and after adding new child
                // we are adding that item inside our array list and
                // notifying our adapter that the data in adapter is changed.
                coursesArraylist.add(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when the new child is added.
                // when the new child is added to our list we will be
                // notifying our adapter that data has changed.
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // below method is called when we remove a child from our database.
                // inside this method we are removing the child from our array list
                // by comparing with it's value.
                // after removing the data we are notifying our adapter that the
                // data has been changed.
                coursesArraylist.remove(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when we move our
                // child in our database.
                // in our code we are note moving any child.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // this method is called when we get any
                // error from Firebase with error.
            }
        });
        // below line is used for setting
        // an adapter to our list view.
        coursesLV.setAdapter(adapter);

    }
}
