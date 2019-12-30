package com.example.professt.publicawareness;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class ViewMapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;

    /** variables for longitude and latitude */
    double longitude, latitude;

    String getUser;

    private ProgressDialog loading;

    public ArrayList<MapBean> mMapBean = new ArrayList<MapBean>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /** setOnClickListener for zooming in  */
        findViewById(R.id.zoomInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        /** setOnClickListener for zooming out  */
        findViewById(R.id.zoomOutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        /** initializing latitude and longitude */
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);

        /** refreshButton for camera point to main point */
        findViewById(R.id.refreshButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
            }
        });

        /** initializing checkBox for switching satelliteView */
        final CheckBox checkBox = (CheckBox) findViewById(R.id.satelliteViewCheckBox);

        /**
         * setting Up setOnClickListener
         * @param OnClickListener
         * */
        checkBox.setOnClickListener(new View.OnClickListener() {
            /**
             * override onClick method
             * @param view
             * */
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                else mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });


//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                if (marker.equals(mMap))
//                {
//                    String name= marker.getTitle();
//                    Intent intent = new Intent(ViewMapsActivity.this,ImageActivity.class);
//                    intent.putExtra("posted", name);
//                    startActivity(intent);
//                    //handle click here
//                }
//                return true;
//            }
//        });
    }

    private void getData() {

//for showing progress dialog
        loading = new ProgressDialog(ViewMapsActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Key.SHOW_EMERGENCY;

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE_VIEW", "" + response);
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Intent intent = new Intent(ViewMapsActivity.this, MainActivity.class);
                        startActivity(intent);
                        loading.dismiss();
                        Toast.makeText(ViewMapsActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ViewMapsActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {

        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Key.JSON_ARRAY);

            if (result.length() == 0) {
                showData("Sorry", "No Emergency Help Found in the city");
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

                    LatLng temp = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));


                    Log.d("temp", "temp: "+temp);
                    Log.d("name", "name: "+name);
                    Log.d("location", "location: "+location);
                    drawMarkers(id,details, type, temp, date, time, location, people, name);

                    //mMapBean.add(new MapBean(details, type, date, time, location, people, name, temp));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showData(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

    public void drawMarkers(String id,String emergencyTitle, String emergencyType, LatLng coordinate, String Date, String Time, String Location, String People, String Name) {
        if (emergencyType.equalsIgnoreCase("fire")) {
            mMap.addMarker(new MarkerOptions().position(coordinate)
                    .title(id)
                    .snippet("Posted By: " + Name + "\n" + "People Effected: " + People + "\n" + "Address: " + Location + "\n" +"Date: "+ Date + "\n" +"Time: " + Time + "\n" + coordinate.latitude + "," + coordinate.longitude)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fire)));

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    Context mContext = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                    LinearLayout info = new LinearLayout(mContext);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(mContext);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(mContext);
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });
            ;

//            myMarker = map.addMarker(new MarkerOptions()
//                    .position(coordinate)
//                    .title(emergencyTitle)
//                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
//                    .draggable(true)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fire)));

        } else if (emergencyType.equalsIgnoreCase("health")) {
            mMap.addMarker(new MarkerOptions().position(coordinate)
                    .title(id)
                    .snippet("Posted By: " + Name + "\n" + "People Effected: " + People + "\n" + "Address: " + Location + "\n" +"Date: "+ Date + "\n" +"Time: " + Time + "\n" + coordinate.latitude + "," + coordinate.longitude)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_health)));

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    Context mContext = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                    LinearLayout info = new LinearLayout(mContext);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(mContext);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(mContext);
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });
            ;

//            myMarker = map.addMarker(new MarkerOptions()
//                    .position(coordinate)
//                    .title(emergencyTitle)
//                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
//                    .draggable(true)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_health)));
        } else if (emergencyType.equalsIgnoreCase("crime")) {
            mMap.addMarker(new MarkerOptions().position(coordinate)
                    .title(id)
                    .snippet("Posted By: " + Name + "\n" + "People Effected: " + People + "\n" + "Address: " + Location + "\n" +"Date: "+ Date + "\n" +"Time: " + Time + "\n" + coordinate.latitude + "," + coordinate.longitude)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_crime)));

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    Context mContext = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                    LinearLayout info = new LinearLayout(mContext);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(mContext);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(mContext);
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });
            ;

//            myMarker = map.addMarker(new MarkerOptions()
//                    .position(coordinate)
//                    .title(emergencyTitle)
//                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
//                    .draggable(true)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_crime)));
        } else {
            mMap.addMarker(new MarkerOptions().position(coordinate)
                    .title(id)
                    .snippet("Posted By: " + Name + "\n" + "People Effected: " + People + "\n" + "Address: " + Location + "\n" +"Date: "+ Date + "\n" +"Time: " + Time + "\n" + coordinate.latitude + "," + coordinate.longitude)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    Context mContext = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                    LinearLayout info = new LinearLayout(mContext);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(mContext);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(mContext);
                    snippet.setTextColor(Color.RED);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });
            ;

//            myMarker = map.addMarker(new MarkerOptions()
//                    .position(coordinate)
//                    .title(emergencyTitle)
//                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
//                    .draggable(true)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
        }

        mMap.setOnMarkerClickListener(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


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
        mMap.setMyLocationEnabled(true);

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



//        for(int i =0;i<mMapBean.size();i++)
//        {
//            String emergencyType,emergencyTitle,Name,People,Location,Date,Time;
//            LatLng coordinate;
//
//            emergencyType = mMapBean.get(i).getTypes();
//            emergencyTitle = mMapBean.get(i).getDetails();
//            Name = mMapBean.get(i).getName();
//            People = mMapBean.get(i).getPeople();
//            Location = mMapBean.get(i).getLocation();
//            Date = mMapBean.get(i).getDates();
//            Time = mMapBean.get(i).getTimes();
//            coordinate = mMapBean.get(i).getTemp();
//
//            Log.d("type", "type: "+emergencyType);
//
//            if (emergencyType.equalsIgnoreCase("fire")) {
//                mMap.addMarker(new MarkerOptions().position(coordinate)
//                        .title(emergencyTitle)
//                        .snippet("Posted By: " + Name + "  " + "People Effected: " + People + "  " + "Address: " + Location + "  " + Date + "  " + Time + "  " + coordinate.latitude + "," + coordinate.longitude)
//                        .draggable(true)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fire)));
//
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
//
//
////            myMarker = map.addMarker(new MarkerOptions()
////                    .position(coordinate)
////                    .title(emergencyTitle)
////                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
////                    .draggable(true)
////                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_fire)));
//            } else if (emergencyType.equalsIgnoreCase("health")) {
//                mMap.addMarker(new MarkerOptions().position(coordinate)
//                        .title(emergencyTitle)
//                        .snippet("Posted By: " + Name + "  " + "People Effected: " + People + "  " + "Address: " + Location + "  " + Date + "  " + Time + "  " + coordinate.latitude + "," + coordinate.longitude)
//                        .draggable(true)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_health)));
//                ;
//
////            myMarker = map.addMarker(new MarkerOptions()
////                    .position(coordinate)
////                    .title(emergencyTitle)
////                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
////                    .draggable(true)
////                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_health)));
//            } else if (emergencyType.equalsIgnoreCase("crime")) {
//                mMap.addMarker(new MarkerOptions().position(coordinate)
//                        .title(emergencyTitle)
//                        .snippet("Posted By: " + Name + "  " + "People Effected: " + People + "  " + "Address: " + Location + "  " + Date + "  " + Time + "  " + coordinate.latitude + "," + coordinate.longitude)
//                        .draggable(true)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_crime)));
//                ;
//
////            myMarker = map.addMarker(new MarkerOptions()
////                    .position(coordinate)
////                    .title(emergencyTitle)
////                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
////                    .draggable(true)
////                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_crime)));
//            } else {
//                mMap.addMarker(new MarkerOptions().position(coordinate)
//                        .title(emergencyTitle)
//                        .snippet("Posted By: " + Name + "  " + "People Effected: " + People + "  " + "Address: " + Location + "  " + Date + "  " + Time + "  " + coordinate.latitude + "," + coordinate.longitude)
//                        .draggable(true)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
//                ;
//
////            myMarker = map.addMarker(new MarkerOptions()
////                    .position(coordinate)
////                    .title(emergencyTitle)
////                    .snippet("Posted By: "+ Name + "  " +"People Effected: "+ People + "  " + "Address: "+ Location + "  " + Date + "  "  + Time + "  "  + coordinate.latitude + "," + coordinate.longitude)
////                    .draggable(true)
////                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
//            }
//        }

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //setUpCameraAndMarkers();
                getData();
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        String name= marker.getTitle();
        String details= marker.getSnippet();
        Intent intent = new Intent(ViewMapsActivity.this,ImageActivity.class);
        intent.putExtra("posted", name);
        startActivity(intent);
        //handle click here

        return true;
    }


    /**
     * setUpCameraAndMarkers method
     * to mark location results on map
     * */
//    void setUpCameraAndMarkers(){
//
////        LatLng latLng;
////        for(int i=0; i<GeometryController.detailArrayList.size(); i++) {
////            latLng = new LatLng(GeometryController.detailArrayList.get(i).getGeometry()[0], GeometryController.detailArrayList.get(i).getGeometry()[1]);
////            mMap.addMarker(new MarkerOptions().position(latLng).title(GeometryController.detailArrayList.get(i).getHospitalName()));
////            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(ViewMapsActivity.this,MainActivity.class);
        startActivity(intent);

    }
}
