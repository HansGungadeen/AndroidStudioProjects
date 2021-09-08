package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class Profile extends AppCompatActivity
{

    String UID;
    String fname;
    String lname;
    String Eml;
    String Lcn;
    String Phn;

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv=(TextView)findViewById(R.id.tv);

        UID = getIntent().getStringExtra("userID");
        fname = getIntent().getStringExtra("FirstName");
        lname = getIntent().getStringExtra("LastName");
        Eml = getIntent().getStringExtra("Email");
        Phn = getIntent().getStringExtra("Phone");
        Lcn = getIntent().getStringExtra("LicenseNumber");


        tv.setText("FirstName: " +fname + "\n" + "Lastname: "+ lname + "\n" + "Email: " + Eml + "\n" + "Phone Number: " + Phn + "\n" + "License Number: "  +Lcn);




    }

}


//Previous Test Code

//    EditText FromDate,FromTime,ToDate,ToTime;
//    Button btGenerate;
//    ImageView ivOutput;



//        Calendar calendar = Calendar.getInstance();
//        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
//
//        TextView date = findViewById(R.id.date);
//        TextView date2 = findViewById(R.id.date2);
//        date.setText(currentDate);
//        date2.setText(currentDate);

//        FromTime = findViewById(R.id.FromTime);
//        ToTime = findViewById(R.id.ToTime);
//        btGenerate = findViewById(R.id.bt_generate);
//        ivOutput = findViewById(R.id.iv_output);
//
//        FromTime=(EditText)findViewById(R.id.FromTime);


//        btGenerate.setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v)
//            {
//                String n1 = FromTime.getText().toString().trim();
//                String n2 = ToTime.getText().toString().trim();
//                String n3 = currentDate;
//                String n4 = currentDate;
//
//                String total = n1 + n2 + n3 + n4;
//
//                MultiFormatWriter writer = new MultiFormatWriter();
//
//                try {
//                    BitMatrix matrix = writer.encode("total", BarcodeFormat.QR_CODE, 500, 500);
//
//                    BarcodeEncoder encoder = new BarcodeEncoder();
//
//                    Bitmap bitmap = encoder.createBitmap(matrix);
//
//                    ivOutput.setImageBitmap(bitmap);
//
//                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                    manager.hideSoftInputFromWindow(FromTime.getApplicationWindowToken() , 0);
//                    manager.hideSoftInputFromWindow(ToTime.getApplicationWindowToken() , 0);
//
//
//                } catch (WriterException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });


