package com.example.polyblog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Courses extends AppCompatActivity {

    ImageButton button_big_data,button_iot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        button_iot = (ImageButton) findViewById(R.id.button_iot);

        button_iot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIot();
            }
        });

        button_big_data = (ImageButton) findViewById(R.id.button_big_data);

        button_big_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBigData();
            }
        });


    }

    private void showBigData() {
        Intent intent = new Intent(this,course_big_data.class);
        startActivity(intent);
    }

    private void showIot() {
        Intent intent = new Intent(this,course_iot.class);
        startActivity(intent);
    }
}