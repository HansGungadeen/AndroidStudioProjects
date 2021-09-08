package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewBook extends AppCompatActivity {

   private Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);


        Next=findViewById(R.id.Next);


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityNewBook();
            }
        });


    }


    private void openActivityNewBook()
    {
        Intent intent = new Intent(this, BookingTime.class);
        startActivity(intent);
    }

}