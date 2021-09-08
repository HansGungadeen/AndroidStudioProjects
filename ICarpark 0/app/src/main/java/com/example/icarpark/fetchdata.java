package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class fetchdata extends AppCompatActivity {

//    TextView tv;


    private static final String apiurl="https://icarpark.000webhostapp.com/androidapp/json_fetch.php";
    ListView lv;
    ArrayList<String>holder=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchdata);

//        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
//
//        tv.setText(sp.getString("Email",""));

        Toast.makeText(getApplicationContext(),"Loading Users",Toast.LENGTH_LONG).show();


        fetchdata();
    }

    public void fetchdata()
    {

        lv = (ListView)findViewById(R.id.lv);
        class dbManager extends AsyncTask<String,Void,String>
        {

            protected void onPostExecute(String data)
            {
                try
                {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    holder.clear();

                    for (int i=0;i<ja.length();i++)
                    {
                        jo = ja.getJSONObject(i);
                        String userID = jo.getString("userID");
                        String name = jo.getString("FirstName");
                        holder.add(userID+name);
//                        holder.add(name);

                    }

                    ArrayAdapter<String> at = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                    lv.setAdapter(at);

                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... strings)
            {
                try
                {
                    URL url=new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while ( (line=br.readLine())!=null)
                    {
                        data.append(line+"\n");
                    }

                     br.close();

                    return data.toString();

                }catch (Exception ex)
                {
                    return ex.getMessage();
                }
//                return null;
            }
        }

        dbManager obj = new dbManager();
        obj.execute(apiurl);

    }
}