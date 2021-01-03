package com.example.androiddevelopmentfinal.Http;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.androiddevelopmentfinal.R;
import android.os.Looper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JsonActivity extends AppCompatActivity {

    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        textview=(TextView)findViewById(R.id.textView3);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendRequstWitthOkhttp();
            }
        });
    }

    private void SendRequstWitthOkhttp()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("http://localhost/get_data.json")
                        .build();
                Response response= null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String responseData= null;
                try {
                    responseData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pareJson(responseData);
            }
        }).start();
    }
    private void pareJson(String responseData) {
        try {
            StringBuilder str2=new StringBuilder();
            JSONArray jsonArray=new JSONArray(responseData);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                str2.append("id:").append(jsonObject.getInt("id")).append("  ");
                str2.append("version:").append(jsonObject.getString("version")).append("");
                str2.append("name:").append(jsonObject.getString("name")).append("\n");
                str2.append("----------------------\n" );
            }
            textview.setText(str2.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
