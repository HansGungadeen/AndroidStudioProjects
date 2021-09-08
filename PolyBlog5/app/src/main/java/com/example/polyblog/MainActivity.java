package com.example.polyblog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button_comments,view_events,add_comment,courses,about;

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_comments = (Button) findViewById(R.id.button_comments);
        view_events = (Button) findViewById(R.id.view_events);
        add_comment = (Button) findViewById(R.id.add_comment);
        courses = (Button) findViewById(R.id.courses);
        about = (Button) findViewById(R.id.about);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAbout();
            }
        });

        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCourses();
            }
        });

        button_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComment();
            }
        });

        view_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEvent();
            }
        });

        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });


    }

    private void showAbout() {
        Intent intent = new Intent(this,About_us.class);
        startActivity(intent);
    }

    private void showCourses() {
        Intent intent = new Intent(this,Courses.class);
        startActivity(intent);
    }

    private void sendToLogin() {
        Intent toLogin = new Intent(this,SignInActivity.class);
        startActivity(toLogin);
//        finish();
    }

    private void addComment() {
        Intent intent = new Intent(this, AddComment.class);
        startActivity(intent);
    }

    private void showEvent() {
        Intent intent = new Intent(this,Events.class);
        startActivity(intent);
    }

    private void showComment() {
        Intent intent = new Intent(this,Comments.class);
        startActivity(intent);
    }
}