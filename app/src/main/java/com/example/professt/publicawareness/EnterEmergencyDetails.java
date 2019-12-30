package com.example.professt.publicawareness;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class EnterEmergencyDetails extends AppCompatActivity {

    private EditText location;
    private EditText people;
    private EditText details;
    private EditText e_date;
    private EditText longitude;
    private EditText latitude;
    private EditText emergencyType;
    Button getTimeButton,getDateButton,captureButton;
    TextView time,dates;


    Bitmap FixBitmap;
    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;
    boolean check = true;

    private int GALLERY = 1, CAMERA = 2;

    String getUser;
    String locationName,peopleAffected,emergencyDetails,timeValue,dateValue;
    private ProgressDialog loading;


    String userID="";

    Double get_latitude=0.0;
    Double get_longitude=0.0;
    String type="";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_emergency_details);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        userID=getSharedPreferences("MyPrefs",MODE_PRIVATE).getString("userid","");

        Intent myIntent = getIntent();
        Bundle myBundle=myIntent.getExtras();
        get_latitude=Double.parseDouble(myBundle.get("latitude").toString());
        get_longitude=Double.parseDouble(myBundle.get("longitude").toString());
        type=myBundle.get("type").toString();
        final String longitude = myIntent.getStringExtra("longitude");
        final String latitude = myIntent.getStringExtra("latitude");
        final String emergencyType = myIntent.getStringExtra("emergencyType");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/DD HH:mm:ss");
        Date date = new Date();
        final String emergencyTime = dateFormat.format(date);
        System.out.println(dateFormat.format(date));

        location = (EditText) findViewById(R.id.emergency_location);
        people = (EditText) findViewById(R.id.people_affected);
        details = (EditText) findViewById(R.id.emergency_details);
        getDateButton = (Button)findViewById(R.id.getDateButton);
        getTimeButton = (Button)findViewById(R.id.getTimeButton);
        time = (TextView)findViewById(R.id.time);
        dates = (TextView)findViewById(R.id.date);
        captureButton = (Button)findViewById(R.id.captureImage);

        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Key.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getUser = sharedPreferences.getString(Key.USER_SHARED_PREF, "Not Available");


        getTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dfTime = new SimpleDateFormat("HH:mm");
                String times = dfTime.format(Calendar.getInstance().getTime());

                time.setText(times);

            }
        });

        getDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");
                String date=dfDate.format(Calendar.getInstance().getTime());

                dates.setText(date);
            }
        });

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePhotoFromCamera();
            }
        });


        Button report = (Button) findViewById(R.id.enter_emergency_details);

        report.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if (FixBitmap==null)
                {
                    Toast.makeText(EnterEmergencyDetails.this,"Please Upload Image",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    locationName = location.getText().toString();
                    peopleAffected = people.getText().toString();
                    emergencyDetails = details.getText().toString();
                    timeValue = time.getText().toString();
                    dateValue = dates.getText().toString();
                    final String image = imageToString(FixBitmap);




                    if(locationName.isEmpty())
                    {
                        location.setError("Please Enter Location Address");
                        location.requestFocus();
                    }
                    else if(peopleAffected.isEmpty())
                    {
                        people.setError("Please Enter how many People are Affected");
                        people.requestFocus();
                    }

                    else if(emergencyDetails.isEmpty())
                    {
                        details.setError("Please Enter description");
                        details.requestFocus();
                    }

                    else if(timeValue.isEmpty())
                    {
                        Toast.makeText(EnterEmergencyDetails.this, "Please take Current Time", Toast.LENGTH_SHORT).show();
                    }

                    else if(dateValue.isEmpty())
                    {
                        Toast.makeText(EnterEmergencyDetails.this, "Please take Current Date", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        loading = new ProgressDialog(EnterEmergencyDetails.this);
                        loading.setIcon(R.drawable.load);
                        loading.setTitle("Emergency Help");
                        loading.setMessage("Please wait...");
                        loading.show();


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Key.EMERGENCY_SMS_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //for track response in Logcat
                                Log.d("RESPONSE", "" + response);

                                //if we are getting success from server
                                if (response.equals("success")) {
                                    //creating a shared preference
                                    loading.dismiss();
                                    //starting profile activity
                                    Intent intent = new Intent(EnterEmergencyDetails.this, MainActivity.class);
                                    startActivity(intent);

                                    Toast.makeText(EnterEmergencyDetails.this, "Emergency Help Sent Successfully", Toast.LENGTH_SHORT).show();

                                    location.setText("");
                                    people.setText("");
                                    details.setText("");
                                    time.setText("");
                                    dates.setText("");

                                }
                                else if (response.equals("failure")) {
                                    Toast.makeText(EnterEmergencyDetails.this, "Failed", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                }
                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(EnterEmergencyDetails.this, "There is an error", Toast.LENGTH_SHORT).show();
                                        loading.dismiss();
                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                //return super.getParams();

                                Map<String,String> params = new HashMap<>();

                                //Ading parameters to request
                                params.put(Key.KEY_NAME,getUser);
                                params.put(Key.KEY_PEOPLE,peopleAffected);
                                params.put(Key.KEY_LOCATION,locationName);
                                params.put(Key.KEY_DETAILS,emergencyDetails);
                                params.put(Key.KEY_TIME,timeValue);
                                params.put(Key.KEY_DATE,dateValue);
                                params.put(Key.KEY_LATITUDE,get_latitude+"");
                                params.put(Key.KEY_LONGITUDE,get_longitude+"");
                                params.put(Key.KEY_TYPE,type);
                                params.put(Key.KEY_ENCODE,locationName);
                                params.put(Key.KEY_IMAGE,image);

                                Log.d("PathFinal", "" + image);

                                //returning parameter
                                return params;

                            }
                        };

                        //Adding the string request to the queue
                        RequestQueue requestQueue = Volley.newRequestQueue(EnterEmergencyDetails.this);
                        requestQueue.add(stringRequest);
                    }

                }


            }
        });
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(imgBytes, android.util.Base64.DEFAULT);
    }


    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    // Star activity for result method to Set captured image on image view after click.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera

            }
            else {

                Toast.makeText(EnterEmergencyDetails.this, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(EnterEmergencyDetails.this,ReportEmergency.class);
        startActivity(intent);

    }


}
