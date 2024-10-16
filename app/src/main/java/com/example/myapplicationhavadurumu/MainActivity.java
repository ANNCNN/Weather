package com.example.myapplicationhavadurumu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvTemp= findViewById(R.id.tvTemp);
        TextView tvDay= findViewById(R.id.tvDay);
        ImageView ivPhoto=findViewById(R.id.ivPhoto);


        String url = "https://mustafaozcan.net/json/havadurumu.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response: " , response.toString());
                        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                        Log.d("Day: " , String.valueOf(day));
                        for (int i=0; i< response.length(); i++){

                            if (day ==i){
                                try {
                                    JSONObject json = response.getJSONObject(i);
                                    String photo =json.getString("resim");
                                    Glide.with(MainActivity.this).load(photo).into(ivPhoto);
                                    tvDay.setText(json.getString("gun"));
                                    tvTemp.setText(String.valueOf(json.getInt("sicaklik")));

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }


                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);




    }
}