package com.example.professt.publicawareness;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.professt.publicawareness.data.AppController;
import com.example.professt.publicawareness.data.CustomRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ViewEmergency extends FragmentActivity {
    private GoogleMap map;
    private final LatLng coordinate = new LatLng(10.0, 11.1);
    private LocationManager locationManager;
    private String provider;
    private String emergencyTitle;
    private String emergencyType;
    private String Date;

    private Location location;
    private Marker myMarker;

    String getUser;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emergency);

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabledWiFi = service
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabledWiFi) {
            Toast.makeText(this, "WiFi signal not found", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            Toast.makeText(this, "Selected Provider " + provider,
                    Toast.LENGTH_SHORT).show();
            //onLocationChanged(location);
        } else {

            //do something
        }

        MapsInitializer.initialize(getApplicationContext());

        getData();
    }


    private void getData() {

//for showing progress dialog
        loading = new ProgressDialog(ViewEmergency.this);
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

                        Intent intent = new Intent(ViewEmergency.this,MainActivity.class);
                        startActivity(intent);
                        loading.dismiss();
                        Toast.makeText(ViewEmergency.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ViewEmergency.this);
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

                    drawMarkers(details, type, temp, date, time, location, people, name);

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

    public void drawMarkers(String emergencyTitle,  String emergencyType, LatLng coordinate, String Date, String Time, String Location, String People, String Name) {
        if(emergencyType.compareTo("fire")==0) {
            map.addMarker(new MarkerOptions().position(coordinate)
                    .title(emergencyTitle)
                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fire)));;

//            myMarker = map.addMarker(new MarkerOptions()
//                    .position(coordinate)
//                    .title(emergencyTitle)
//                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
//                    .draggable(true)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fire)));
        }

        else if(emergencyType.compareTo("health")==0) {
            map.addMarker(new MarkerOptions().position(coordinate)
                    .title(emergencyTitle)
                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_health)));;

//            myMarker = map.addMarker(new MarkerOptions()
//                    .position(coordinate)
//                    .title(emergencyTitle)
//                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
//                    .draggable(true)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_health)));
        }

        else if(emergencyType.compareTo("crime")==0) {
            map.addMarker(new MarkerOptions().position(coordinate)
                    .title(emergencyTitle)
                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_crime)));;

//            myMarker = map.addMarker(new MarkerOptions()
//                    .position(coordinate)
//                    .title(emergencyTitle)
//                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
//                    .draggable(true)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_crime)));
        }

        else {
            map.addMarker(new MarkerOptions().position(coordinate)
                    .title(emergencyTitle)
                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));;

//            myMarker = map.addMarker(new MarkerOptions()
//                    .position(coordinate)
//                    .title(emergencyTitle)
//                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
//                    .draggable(true)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
        }
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        //locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        //locationManager.removeUpdates(this);
    }

}
