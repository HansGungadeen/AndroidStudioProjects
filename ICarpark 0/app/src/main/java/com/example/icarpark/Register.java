package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    //Variable declarations and initialisation
    EditText FirstName,LastName,Email,Phone,LicenseNumber,Password,Confirm_Password;

    String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String PasswordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!+%=@#&()–[{}]:;',?/*~$^=<>]).{8,20}$";

    private final String url="https://icarpark.000webhostapp.com/androidapp/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirstName=(EditText)findViewById(R.id.FirstName);
        LastName=(EditText)findViewById(R.id.LastName);
        Email=(EditText)findViewById(R.id.Email);
        Phone=(EditText)findViewById(R.id.Phone);
        LicenseNumber=(EditText)findViewById(R.id.LicenseNumber);
        Password=(EditText)findViewById(R.id.Password);
        Confirm_Password=(EditText)findViewById(R.id.Confirm_Password);


    }

    public void process(View view)
    {
        //Getting user inputs and its validations
        String n1 = FirstName.getText().toString().trim();
        String n2 = LastName.getText().toString().trim();
        String n3 = Email.getText().toString().trim();
        String n4 = Phone.getText().toString().trim();
        String n5 = LicenseNumber.getText().toString().trim();
        String n6 = Password.getText().toString().trim();
        String n7 = Confirm_Password.getText().toString().trim();
        String qryString = "?FirstName=" + n1  + "&LastName=" + n2 + "&Email=" + n3 + "&Phone=" + n4 + "&LicenseNumber=" + n5 + "&Password=" + n6 ;


        if(FirstName.length()==0) {
            FirstName.requestFocus();
            FirstName.setError("FIELD CANNOT BE EMPTY");
        }

        else if(LastName.length()==0)
        {
            LastName.requestFocus();
            LastName.setError("FIELD CANNOT BE EMPTY");
        }

        else if(Email.length()==0)
        {
            Email.requestFocus();
            Email.setError("FIELD CANNOT BE EMPTY");
        }

        else if (!n3.matches(EmailPattern) && Email.length() > 0)
        {
            Email.requestFocus();
            Email.setError("Invalid email");
        }


        else if(Phone.length()==0)
        {
            Phone.requestFocus();
            Phone.setError("FIELD CANNOT BE EMPTY");
        }

        else if(Password.length()==0)
        {
            Password.requestFocus();
            Password.setError("FIELD CANNOT BE EMPTY");

        }

        else if (!n6.matches(PasswordPattern) && Password.length() > 0)
        {

            Password.requestFocus();
            Password.setError("Password weak");
        }


        else if (!n7 .equals(n6))
        {
            Confirm_Password.requestFocus();
            Confirm_Password.setError("Password does not match");
        }


        else
        {
            class dbclass extends AsyncTask<String, Void, String>
            {

                @Override
                protected String doInBackground(String... param)
                {
                    try
                    {
                        URL url = new URL(param[0]);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        return br.readLine();
                    }
                    catch (Exception ex)
                    {
                        return ex.getMessage();

                    }

                }

                // Clearing input field on validations
                protected void onPostExecute(String data)
                {
                    FirstName.setText("");
                    LastName.setText("");
                    Email.setText("");
                    Phone.setText("");
                    LicenseNumber.setText("");
                    Password.setText("");
                    Confirm_Password.setText("");

                    Toast.makeText(getApplicationContext(), "Registration Success. Please Login", Toast.LENGTH_SHORT).show();

                }
            }

            dbclass obj = new dbclass();
            obj.execute(url + qryString);


        }

    }
}
