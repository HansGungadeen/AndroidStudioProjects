package com.example.polyblog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCommentTest extends AppCompatActivity {

    //Declaring String Variable
    Button Submit_Comment_Button;
    EditText NameText,CourseText,CommentText;

    //Declaring String Variable for FIrebase
    public static final String Firebase_Server_URL = "https://polyblog-eb474-default-rtdb.firebaseio.com/";

    //declaring string variables to store Name,Course,Comment from Edit TExt
    String NameHolder,CourseHolder,CommentHolder;

    Firebase firebase;

    DatabaseReference databaseReference;

    //root database name for firebase database
    public static final String Database_Path = "comments";
//    private final CommentDetails commentDetails = new CommentDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment_test);

        Firebase.setAndroidContext(AddCommentTest.this);
        firebase = new Firebase(Firebase_Server_URL);
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        Submit_Comment_Button = (Button) findViewById(R.id.submit_comment);
        //get data from name_field to here
        NameText = (EditText) findViewById(R.id.nameText);
        //get data from phone_field to here
        CourseText = (EditText) findViewById(R.id.courseText);
//        //get data from comment_field to here
        CommentText = (EditText) findViewById(R.id.commentText);

        Submit_Comment_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentDetails commentDetails = new commentDetails();
                GetDataFromEditText();
                //Adding name into class function object.
                commentDetails.setName(NameHolder);
                commentDetails.setCourse(CourseHolder);
                commentDetails.setComment(CommentHolder);
                String CommentRecordIdFromServer = databaseReference.push().getKey();
                databaseReference.child(CommentRecordIdFromServer).setValue(commentDetails);

                String CommentRecordIDFromServer = databaseReference.push().getKey();
                databaseReference.child(CommentRecordIdFromServer).setValue(commentDetails);

                Toast.makeText(AddCommentTest.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();

            }
        });
    }
        private void GetDataFromEditText() {
//
        NameHolder = NameText.getText().toString().trim();
        CourseHolder = CourseText.getText().toString().trim();
        CommentHolder = CommentText.getText().toString().trim();
        }
    }