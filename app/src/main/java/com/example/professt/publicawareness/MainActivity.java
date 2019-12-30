package com.example.professt.publicawareness;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.professt.publicawareness.Nearest_Fire_Station.ListFireStations;
import com.example.professt.publicawareness.Nearest_Hospital.ListHealthCenters;
import com.example.professt.publicawareness.Nearest_Police_Station.ListPoliceStations;
import com.example.professt.publicawareness.Service.MyService;
import com.google.android.gms.maps.model.LatLng;
import com.karan.churi.PermissionManager.PermissionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ImageButton Interact;
    private ImageButton Report;
    private ImageButton Delete;
    private ImageButton Logout;
    private ImageButton nearestMedical;
    private ImageButton nearestFire;
    private ImageButton nearestpolice;
    private ImageButton emergencyCall;

    private MediaPlayer mediaPlayer;

    private ProgressDialog loading;



    private AlertDialog.Builder alertdialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Interact = (ImageButton) findViewById(R.id.ViewEmergency);
        Report =  (ImageButton) findViewById(R.id.ReportEmergency);
        Delete = (ImageButton) findViewById(R.id.DeleteEmergency);
        Logout = (ImageButton) findViewById(R.id.logout);
        nearestMedical = (ImageButton)findViewById(R.id.nearestMedical);
        nearestFire = (ImageButton)findViewById(R.id.nearestFire);
        nearestpolice = (ImageButton)findViewById(R.id.nearestpolice);
        emergencyCall = (ImageButton)findViewById(R.id.emergencyCall);

        mediaPlayer = MediaPlayer.create(this,R.raw.sirenss);


        Interact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

          /*      Toast.makeText(MainMenuActivity.this,
                        "ImageButton is clicked!", Toast.LENGTH_SHORT).show();*/
                if(mediaPlayer!=null && mediaPlayer.isPlaying())  //condition check
                {
                    mediaPlayer.stop();   //stop the mediaPlayer
                    mediaPlayer.release(); //by using this method we can release everything which mediaPlayer object took all the resources
                    mediaPlayer = null; //it is applied not to take extra memory
                }
                goToViewEmergency();

            }

        });

        Report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

         /*       Toast.makeText(MainMenuActivity.this,
                        "ImageButton is clicked!", Toast.LENGTH_SHORT).show();*/
                startService(new Intent(MainActivity.this, MyService.class));
                goToReportEmergency();

            }

        });

        Delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

               /* Toast.makeText(MainMenuActivity.this,
                        "ImageButton is clicked!", Toast.LENGTH_SHORT).show();*/
                sendEmail();

            }

        });

        Logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                alertdialogBuilder = new AlertDialog.Builder(MainActivity.this);    //creating object of alertDialogBuilder

                //setting the properties of alertDialogBuilder:

                //for setting title
                alertdialogBuilder.setTitle("Citizen Alert");

                //for setting message
                alertdialogBuilder.setMessage("Do you want to exit?");

                //for setting icon
                alertdialogBuilder.setIcon(R.drawable.exit);

                //for setting cancelable
                alertdialogBuilder.setCancelable(false);

                //for setting Button
                alertdialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //exit
//                finish();;
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                finish();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity(); // Close all activites
                            System.exit(0);  // Releasing resources
                        }
//                System.exit(0);
                onDestroy();
                    }
                });
                alertdialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Not exit
                        // Toast.makeText(MainActivity.this,"You have clicked on no button",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                alertdialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Not exit
                        //Toast.makeText(MainActivity.this,"You have clicked on cancel button",Toast.LENGTH_SHORT).show();
                    }
                });

                //showing alertDialog by creating alertDialog in object and creating alertDialogBuilder in this object
                AlertDialog alertDialog = alertdialogBuilder.create();
                alertDialog.show();

            }

        });

        nearestMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListHealthCenters.class);
                startActivity(intent);
            }
        });

        nearestFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListFireStations.class);
                startActivity(intent);
            }
        });

        nearestpolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListPoliceStations.class);
                startActivity(intent);
            }
        });

        emergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    //Creating intents for making a call
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" +"999"));
                    MainActivity.this.startActivity(callIntent);

                }else{
                    Toast.makeText(MainActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getData();

    }

    private void getData() {

        //for showing progress dialog
        loading = new ProgressDialog(MainActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Key.SHOW_EMERGENCY;

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

                        Intent intent = new Intent(MainActivity.this,LoginActivity2.class);
                        startActivity(intent);
                        loading.dismiss();
                        Toast.makeText(MainActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {

        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Key.JSON_ARRAY);

            if (result.length()==0)
            {
                showData("Sorry","No Emergency Help Found in the city");
            }
            else
            {

                for (int i = 0; i < result.length(); i++) {

                    JSONObject jo = result.getJSONObject(i);
                    String name = jo.getString(Key.KEY_NAME);
                    String people = jo.getString(Key.KEY_PEOPLE);
                    String location = jo.getString(Key.KEY_LOCATION);
                    String details = jo.getString(Key.KEY_DETAILS);
                    String time = jo.getString(Key.KEY_TIME);
                    String date = jo.getString(Key.KEY_DATE);
                    String latitude = jo.getString(Key.KEY_LATITUDE);
                    String longitude = jo.getString(Key.KEY_LONGITUDE);
                    String type = jo.getString(Key.KEY_TYPE);

                    LatLng temp = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                    Intent intent= getIntent();
                    Bundle b = intent.getExtras();

                    if(b!=null)
                    {
                        String j =(String) b.get("email");

                        if(j.equalsIgnoreCase("fireservice@gmail.com"))
                        {
                            if(mediaPlayer!=null)       //condition checked that if there is a song in media player
                            {
                                mediaPlayer.start();      //start the mediaPlayer
                                Toast.makeText(MainActivity.this,"Notification starts",Toast.LENGTH_SHORT).show();
                            }

                            if(type.equalsIgnoreCase("fire"))
                            {
                                NotificationManager notif3=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification notify3= null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    notify3 = new Notification.Builder
                                            (getApplicationContext()).setContentTitle("Public Alert").
                                            setContentTitle("Fire Notification").setSmallIcon(R.drawable.alarm).setStyle(new Notification.BigTextStyle().bigText("Posted by "+name+". \n"+details+". \nTime: "+time+". \nDate: "+date+". \nLocation: "+location)).build();
                                }

                                notify3.flags |= Notification.FLAG_AUTO_CANCEL;
                                notif3.notify(i, notify3);
                            }

                            else if(type.equalsIgnoreCase("crime"))
                            {
                                NotificationManager notif3=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification notify3= null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    notify3 = new Notification.Builder
                                            (getApplicationContext()).setContentTitle("Public Alert").
                                            setContentTitle("Crime Notification").setSmallIcon(R.drawable.alarm).setStyle(new Notification.BigTextStyle().bigText("Posted by "+name+". \n"+details+". \nTime: "+time+". \nDate: "+date+". \nLocation: "+location)).build();
                                }

                                notify3.flags |= Notification.FLAG_AUTO_CANCEL;
                                notif3.notify(i, notify3);
                            }
                            else if(type.equalsIgnoreCase("health"))
                            {
                                NotificationManager notif3=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification notify3= null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    notify3 = new Notification.Builder
                                            (getApplicationContext()).setContentTitle("Public Alert").
                                            setContentTitle("Accident Notification").setSmallIcon(R.drawable.alarm).setStyle(new Notification.BigTextStyle().bigText("Posted by "+name+". \n"+details+". \nTime: "+time+". \nDate: "+date+". \nLocation: "+location)).build();
                                }

                                notify3.flags |= Notification.FLAG_AUTO_CANCEL;
                                notif3.notify(i, notify3);
                            }
                        }

                    }

                }

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showData(String title, String message)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }


    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"royctg01@gmail.com"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            //Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToViewEmergency()
    {
        Intent intent = new Intent(this,ViewMapsActivity.class);
        startActivity(intent);
    }

    private void goToReportEmergency()
    {
        Intent intent = new Intent(this,ReportEmergency.class);
        startActivity(intent);
    }

    private void goToDeleteEmergency()
    {
        Intent intent = new Intent(this,DeleteEmergency.class);
        startActivity(intent);
    }

    private void goToLoginActivity2()
    {
        Intent intent = new Intent(this,LoginActivity2.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        alertdialogBuilder = new AlertDialog.Builder(MainActivity.this);    //creating object of alertDialogBuilder

        //setting the properties of alertDialogBuilder:

        //for setting title
        alertdialogBuilder.setTitle("Citizen Alert");

        //for setting message
        alertdialogBuilder.setMessage("Do you want to exit?");

        //for setting icon
        alertdialogBuilder.setIcon(R.drawable.exit);

        //for setting cancelable
        alertdialogBuilder.setCancelable(false);

        //for setting Button
        alertdialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //exit
//                finish();;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity(); // Close all activites
                    System.exit(0);  // Releasing resources
                }

//                System.exit(0);
                onDestroy();
            }
        });
        alertdialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Not exit
                // Toast.makeText(MainActivity.this,"You have clicked on no button",Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertdialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Not exit
                //Toast.makeText(MainActivity.this,"You have clicked on cancel button",Toast.LENGTH_SHORT).show();
            }
        });

        //showing alertDialog by creating alertDialog in object and creating alertDialogBuilder in this object
        AlertDialog alertDialog = alertdialogBuilder.create();
        alertDialog.show();

    }

}

