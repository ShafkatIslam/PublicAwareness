package com.example.professt.publicawareness;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ImageActivity extends AppCompatActivity {

    TextView postedById,timeId,dateId,typeId;
    ImageView imageView1;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        postedById = (TextView)findViewById(R.id.postedById);
        timeId = (TextView)findViewById(R.id.timeId);
        dateId = (TextView)findViewById(R.id.dateId);
        typeId = (TextView)findViewById(R.id.typeId);
        imageView1 = (ImageView) findViewById(R.id.imageView);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("posted");
            getData(j);
            //postedById.setText(j);
        }

    }

    private void getData(String j) {

        //for showing progress dialog
        loading = new ProgressDialog(ImageActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Key.SHOW_DETAILS+"&text="+j;

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toast.makeText(ImageActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ImageActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Key.JSON_ARRAY);

            if (result.length() == 0) {

            } else {

                for (int i = 0; i < result.length(); i++) {

                    JSONObject jo = result.getJSONObject(i);
                    String id = jo.getString(Key.KEY_ID);
                    String name = jo.getString(Key.KEY_NAME);
                    String people = jo.getString(Key.KEY_PEOPLE);
                    String location = jo.getString(Key.KEY_LOCATION);
                    String details = jo.getString(Key.KEY_DETAILS);
                    String time = jo.getString(Key.KEY_TIME);
                    String date = jo.getString(Key.KEY_DATE);
                    String latitude = jo.getString(Key.KEY_LATITUDE);
                    String longitude = jo.getString(Key.KEY_LONGITUDE);
                    String type = jo.getString(Key.KEY_TYPE);
                    String image = jo.getString(Key.KEY_IMAGE);

                    LatLng temp = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));


                    Glide.with(this).load((Key.MAIN_URL+"/alert/"+image))
                            .placeholder(R.drawable.load)
                            .error(R.drawable.no_image)
                            //.override(120, 120) // resizes the image to 100x200 pixels but does not respect aspect ratio
                            .fitCenter()   // scale to fit entire image within ImageView
                            .into(imageView1);
                    postedById.setText(name);
                    timeId.setText(time);
                    dateId.setText(date);
                    typeId.setText(type);

                    Log.d("temp", "temp: "+temp);
                    Log.d("name", "name: "+name);
                    Log.d("location", "location: "+location);
                    Log.d("image", "image: "+image);


                    //mMapBean.add(new MapBean(details, type, date, time, location, people, name, temp));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(ImageActivity.this,ViewMapsActivity.class);
        startActivity(intent);

    }
}
