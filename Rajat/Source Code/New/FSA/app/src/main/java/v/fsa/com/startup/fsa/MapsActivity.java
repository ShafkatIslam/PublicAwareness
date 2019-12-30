package v.fsa.com.startup.fsa;
import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import v.fsa.com.firebase.SharedPrefManager;
import v.fsa.com.startup.fsa.v.fsa.com.config.Config.Config;
import v.fsa.com.startup.fsa.v.fsa.com.config.Config.MapLocationHistory;
import v.fsa.com.startup.fsa.v.fsa.com.config.Config.RequestUserPermission;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    ArrayList<LatLng> markerPoints;
    TextView tvDistanceDuration;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private ProgressDialog progressDialog;
    private String server_link = Config.getServerLink() + "getLocationHistory.php";
    private ArrayList<MapLocationHistory> loc_history;
    String MY_API_KEY = "AIzaSyClCFw6xVOFLpc0ZH1nyC33-Y60Z2uk9UM";
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    double distanceList[],distanceListRaw[];
    int distanceIndenti[];
    //  private String TAG = "so47492459";
    private final int[] MAP_TYPES = {
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 3, timer_status = 0;
    private Location mylocation;

    /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    /* Position */
    private static final int MINIMUM_TIME = 10000;  // 10s
    private static final int MINIMUM_DISTANCE = 50; // 50m
    private static final int PERMISSIONS_REQUEST = 1;

    int update_type = 0,location_load_status=0;
    long notify_interval = 2000;

    /* GPS */
    private String mProviderName;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    int location_status = 0;
    Double sendLati = 0.0;
    Double sendLogni = 0.0;
    Timer myTimer;
    private Handler mHandler = new Handler();
    Geocoder geocoder;


    Button btpic, btnup, btnMap;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    public static String URL = "http://192.168.0.105/AndroidFileUpload/image_upload.php";
    // LogCat tag
    private static final String TAG = LoginActivity.class.getSimpleName();


    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 101;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    RequestUserPermission requestUserPermission = new RequestUserPermission(this);
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    ProgressDialog dialog;
    private Button btnCapturePicture, btnRecordVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        //tvDistanceDuration = (TextView) findViewById(R.id.tv_distance_time);
        markerPoints = new ArrayList<LatLng>();
        update_type = 0;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        myTimer = new Timer();
        myTimer.schedule(new TimerTaskToGetLocation(), 5, notify_interval);
        geocoder = new Geocoder(this, Locale.getDefault());
        setUpGClient();
        init();

    }

    private void init() {

        location_load_status=0;
        timer_status = 0;
        location_status = 0;
        getFireStationLocationList();
        requestUserPermission.verifyStoragePermissions();
        SharedPrefManager mm = new SharedPrefManager(getApplicationContext());
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        btpic = (Button) findViewById(R.id.cpic);

        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // record video
                recordVideo();
            }
        });
        btpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickpic();
            }
        });
    }

    private void loadServerData(JSONObject c) {
        try {
            latlngs.add(new LatLng(Double.parseDouble(
                    c.getString("latitude")),
                    Double.parseDouble(c.getString("longnitute"))));
            loc_history.add(new MapLocationHistory(
                    c.getString("address"),
                    c.getString("latitude"),
                    c.getString("longnitute"),
                    c.getString("countryName"),
                    c.getString("cityName"),
                    c.getString("postalCode"),
                    c.getString("knownName"),
                    c.getString("stateName"),
                    "",
                    ""

            ));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private  void checkDataList(){
        int i=0;
        distanceList = new double[latlngs.size()];
        distanceIndenti = new int[latlngs.size()];
        distanceListRaw = new double[latlngs.size()];
        for (LatLng point : latlngs) {
            double distance_from_current_location = distance(sendLati, sendLogni, point.latitude, point.longitude) * 1000;
            distanceList[i] = distance_from_current_location;
            distanceListRaw[i] = distance_from_current_location;
            i++;
        }
        nearestLocationCalculate();
    }

    public void getFireStationLocationList() {


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Map Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        loc_history = new ArrayList<>();
        latlngs = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray users = obj.getJSONArray("posts");

                            for (int i = 0; i < users.length(); i++) {
                                JSONObject c = users.getJSONObject(i);
                                loadServerData(c);
                            }
                            location_load_status=1;
                           // checkDataList();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", "1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void clickpic() {

        fileUri=null;

        // Check Camera
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    private void recordVideo() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile = new
                File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator
                + "VID_" + timeStamp + ".mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        fileUri = Uri.fromFile(mediaFile);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);


    }

    private synchronized void setUpGClient() {

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private void checkPermissions(){
        int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }else{
            getMyLocation();
        }

    }
    @Override
    public void onConnected(Bundle bundle) {

        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Do whatever you need
        //You can display a message here
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //You can display a message here
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                    photo = (Bitmap) data.getExtras().get("data");
                    Uri tempUri = getImageUri(getApplicationContext(), photo);
                    File finalFile = new File(getRealPathFromURI(tempUri));
                    //fileUri=finalFile.getPath();
                    //fileUri = finalFile.getPath().toString()
                    picturePath = finalFile.getPath();
                    launchUploadActivity(true);
                    //System.out.println(" Image Url "+finalFile);


                break;
            case CAMERA_CAPTURE_VIDEO_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    launchUploadActivity(false);

                } else if (resultCode == RESULT_CANCELED) {

                    // user cancelled recording
                    Toast.makeText(getApplicationContext(),
                            "User cancelled video recording", Toast.LENGTH_SHORT)
                            .show();

                } else {
                    // failed to record video
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                            .show();

            }
                break;
        }
    }

    private void launchUploadActivity(boolean isImage){


        //Toast.makeText(getApplicationContext(),"File =>"+fileUri,Toast.LENGTH_LONG).show();
        //System.out.println("Video Path = "+fileUri.getPath());
        Intent i = new Intent(MapsActivity.this, UploadActivity.class);

        if(!isImage)
            i.putExtra("filePath", fileUri.getPath());
        else
            i.putExtra("filePath", picturePath);

        i.putExtra("isImage", isImage);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }
    private void getMyLocation(){


        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation =LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(MapsActivity.this,
                                                    android.Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(MapsActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;

        //System.out.println("Location Value 2 = "+location+" timer status ="+timer_status);


        if (mylocation != null) {
            this.location_status=1;
            sendLati=mylocation.getLatitude();
            sendLogni=mylocation.getLongitude();

        }


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

    private void getLocation(){


       // System.out.println("Location Value = "+mylocation+" timer status ="+timer_status);

        if(mylocation != null){

            this.location_status=1;
            sendLati=mylocation.getLatitude();
            sendLogni=mylocation.getLongitude();

            Date curDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            String DateToStr = format.format(curDate);

            locationUpdateTime(DateToStr);
        }



    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {


        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }
    private  void nearestLocationCalculate(){


        for(int j=1;j<=latlngs.size();j++){


            for(int k=1;k<=latlngs.size() - j;k++){


                if(distanceList[k-1] > distanceList[k])
                {
                    double temp=distanceList[k-1];

                    distanceList[k-1]=distanceList[k];
                    distanceList[k]=temp;

                    distanceIndenti[k-1]=k;
                    // distanceIndenti[k]=k - 1;

                }

            }


        }
        nearestLocationView();
    }

    private void nearestLocationView(){

       // distanceList = new double[latlngs.size()];
       // distanceIndenti = new int[latlngs.size()];
       // distanceListRaw = new double[latlngs.size()];

        progressDialog.dismiss();

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.current_location);
        BitmapDescriptor station = BitmapDescriptorFactory.fromResource(R.drawable.fire_station);
        float zoomLevel = 16.0f;
        LatLng sydney = new LatLng(sendLati, sendLogni);
      //  mMap.setMaxZoomPreference(16);
        mMap.addMarker(new MarkerOptions()

                .position(sydney)
                .title("Your Location")
                .icon(icon)
                .draggable(true)

        );
        //System.out.println("Data List "+Double.parseDouble(loc_history.get(0).getLatitude())+
            //    Double.parseDouble(loc_history.get(0).getLongnitute()));


        int ii,i;
        for(ii=0;ii< latlngs.size();ii++){

            LatLng l = new LatLng(Double.parseDouble(loc_history.get(ii).getLatitude()),
                    Double.parseDouble(loc_history.get(ii).getLongnitute()));
            mMap.addMarker(new MarkerOptions()

                    .position(l)
                    .title(loc_history.get(ii).getKnownName())
                    .icon(station)

            );

            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(sendLati,sendLogni), l)
                    .width(5)
                    .color(Color.RED));

           /*

            );

          //  System.out.println("Data List "+Double.parseDouble(loc_history.get(ii).getLatitude())+
                   // Double.parseDouble(loc_history.get(ii).getLongnitute()));
            mMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(new LatLng(
                            Double.parseDouble(loc_history.get(ii).getLatitude()),
                            Double.parseDouble(loc_history.get(ii).getLongnitute()))));*/
        }



        mMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        mMap.setTrafficEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sendLati,sendLogni), zoomLevel));




    }
    private void completing(final GoogleMap googleMap){




        if(location_status > 0 && timer_status == 0 && location_load_status == 1) {


            int i = 0;
            checkDataList();
            location_status=0;
            timer_status = 1;


          /*  for (LatLng point : latlngs) {
                double distance_from_current_location = distance(sendLati, sendLogni, point.latitude, point.longitude) * 1000;
                distanceList[i] = distance_from_current_location;
                distanceListRaw[i] = distance_from_current_location;
                i++;
            }
            nearestLocationCalculate();*/





           /* googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.d("System out", "onMarkerDragStart..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
                }

                @SuppressWarnings("unchecked")
                @Override
                public void onMarkerDragEnd(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.d("System out", "onMarkerDragEnd..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);

                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                }

                @Override
                public void onMarkerDrag(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.i("System out", "onMarkerDrag...");
                }
            });*/

//Don't forget to Set draggable(true) to marker, if this not set marker does not drag.


            // Setting onclick event listener for the map
          /*  googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    // Already two locations
                    if (markerPoints.size() > 1) {
                        markerPoints.clear();
                        mMap.clear();
                    }

                    // Adding new item to the ArrayList
                    markerPoints.add(point);

                    // Creating MarkerOptions
                    MarkerOptions options = new MarkerOptions();

                    // Setting the position of the marker
                    options.position(point);


                    if (markerPoints.size() == 1) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    } else if (markerPoints.size() == 2) {
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }

                    // Add new marker to the Google Map Android API V2
                    googleMap.addMarker(options);

                    // Checks, whether start and end locations are captured
                    if (markerPoints.size() >= 2) {
                        LatLng origin = markerPoints.get(0);
                        LatLng dest = markerPoints.get(1);

                        // Getting URL to the Google Directions API
                        String url = getDirectionsUrl(origin, dest);

                        System.out.println("Url "+url);
                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                }
            });*/

        }
    }
    private void locationUpdateTime(String time){


        completing(mMap);
        timer_status=1;

    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {


                    getLocation();

                }
            });

        }
    }

    private void startTrackerService() {

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        timer_status=0;
        location_status=0;

      /*  progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Map Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        completing(mMap);

    //  getFireStationLocationList();




    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+ "&key=" + MY_API_KEY;

        //String url_2="https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=AIzaSyDHYC4oiuGqJ23DQSnx4Kg4d_UFzVOf6ds";
        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";

            if (result.size() < 1) {
                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if (j == 0) {    // Get distance from the list
                        distance = (String) point.get("distance");
                        continue;
                    } else if (j == 1) { // Get duration from the list
                        duration = (String) point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            tvDistanceDuration.setText("Distance:" + distance + ", Duration:" + duration);

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }


}

