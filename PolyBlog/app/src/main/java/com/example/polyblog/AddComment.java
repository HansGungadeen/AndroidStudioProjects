package com.example.polyblog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;


public class AddComment extends Fragment {
    //Declaring String Variable
    Button Submit_Comment_Button;
    EditText NameText, CourseText, CommentText;

    //Declaring String Variable for FIrebase
    public static final String Firebase_Server_URL = "https://polyblog-eb474-default-rtdb.firebaseio.com/";

    //declaring string variables to store Name,Course,Comment from Edit TExt
    String NameHolder, CourseHolder, CommentHolder;

    Firebase firebase;

    DatabaseReference databaseReference;

    //root database name for firebase database
    public static final String Database_Path = "comments";
//    private final CommentDetails commentDetails = new CommentDetails();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_comment, null);
    }
}