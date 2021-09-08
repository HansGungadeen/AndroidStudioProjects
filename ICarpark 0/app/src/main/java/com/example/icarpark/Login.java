package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class Login extends AppCompatActivity {


    private static final String apiurl="https://icarpark.000webhostapp.com/androidapp/sessiontest.php";
    EditText Email,Password;
    TextView tv;

    //Password and email validator pattern
    String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String PasswordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email=(EditText)findViewById(R.id.Email);
        Password=(EditText)findViewById(R.id.Password);
        tv=(TextView)findViewById(R.id.tv);

        check_logout_message(tv);

    }


    //The login function to get user details and grant access
    public void login_process(View view)
    {
        String n1 = Email.getText().toString().trim();
        String n2 = Password.getText().toString().trim();
        String qry = "?Email="+ n1 +"&Password="+ n2;

        class dbprocess extends AsyncTask<String,Void,String>
        {
            @Override
            //On Post method with validations
            protected  void onPostExecute(String data)
            {

                if(Email.length()==0)
                {
                    Email.requestFocus();
                    Email.setError("FIELD CANNOT BE EMPTY");
                }

                else if (!n1.matches(EmailPattern) && Email.length() > 0)
                {
                    Email.requestFocus();
                    Email.setError("Invalid email");
                }

                else if(Password.length()==0)
                {
                    Password.requestFocus();
                    Password.setError("FIELD CANNOT BE EMPTY");
                }

                else if (!n2.matches(PasswordPattern) && Password.length() > 0)
                {
                    Password.requestFocus();
                    Password.setError("Wrong Password");
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Logging", Toast.LENGTH_SHORT).show();
                    if(data.contains("userID") && data.contains("FirstName"))
                    {
                        //Converting json array to json object
                        ArrayList<String> userID = new ArrayList<>();
                        ArrayList<String> FirstName = new ArrayList<>();
                        ArrayList<String> LastName = new ArrayList<>();
                        ArrayList<String> Email = new ArrayList<>();
                        ArrayList<String> Phone = new ArrayList<>();
                        ArrayList<String> LicenseNumber = new ArrayList<>();

                        try {

                            JSONObject obj = new JSONObject(data);

                            JSONArray userArray = obj.getJSONArray("users");


                            for (int i = 0; i < userArray.length(); i++) {

                                JSONObject userDetail = userArray.getJSONObject(i);
                                userID.add(userDetail.getString("userID"));
                                FirstName.add(userDetail.getString("FirstName"));
                                LastName.add(userDetail.getString("LastName"));
                                Email.add(userDetail.getString("Email"));
                                Phone.add(userDetail.getString("Phone"));
                                LicenseNumber.add(userDetail.getString("LicenseNumber"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Shared preferences to pass user login details to next activity

                        String fname = FirstName.toString().toUpperCase().substring(FirstName.toString().indexOf("[") + 1, FirstName.toString().indexOf("]"));
                        String lname = LastName.toString().toUpperCase().substring(LastName.toString().indexOf("[") + 1, LastName.toString().indexOf("]"));
                        String UID = userID.toString().substring(userID.toString().indexOf("[") + 1, userID.toString().indexOf("]"));
                        String Eml = Email.toString().substring(userID.toString().indexOf("[") + 1, Email.toString().indexOf("]"));
                        String Phn = Phone.toString().substring(userID.toString().indexOf("[") + 1, Phone.toString().indexOf("]"));
                        String Lcn = LicenseNumber.toString().substring(userID.toString().indexOf("[") + 1, LicenseNumber.toString().indexOf("]"));

                        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("FirstName", fname + " " + lname);

                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("userID", UID);
                        intent.putExtra("FirstName", fname);
                        intent.putExtra("LastName", lname);
                        intent.putExtra("Email", Eml );
                        intent.putExtra("Phone", Phn);
                        intent.putExtra("LicenseNumber", Lcn);

                        startActivity(intent);

                    }
                    else
                    {
                        Email.setText("");
                        Password.setText("");
                        tv.setTextColor(Color.parseColor("#8B0000"));
                        tv.setText(data);
                    }
                }

            }
            @Override
            protected String doInBackground(String... params)
            {
                String furl=params[0];
                try
                {
                    URL url=new URL(furl);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    return br.readLine();

                }
                catch (Exception ex)
                {
                    return ex.getMessage();
                }

            }
        }

        dbprocess obj=new dbprocess();
        obj.execute(apiurl+qry);

    }

    //The logout function
    public void check_logout_message(View view)
    {
        tv=(TextView)findViewById(R.id.tv);
        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("msg"))
        {
            tv.setText(sp.getString("msg", ""));
            SharedPreferences.Editor ed=sp.edit();
            ed.remove("msg");
            ed.commit();
        }
    }


}