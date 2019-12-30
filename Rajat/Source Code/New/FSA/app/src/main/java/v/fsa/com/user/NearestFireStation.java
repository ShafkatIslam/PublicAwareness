package v.fsa.com.user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import v.fsa.com.startup.fsa.R;
import v.fsa.com.startup.fsa.v.fsa.com.config.Config.Config;

public class NearestFireStation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location currentLocation;
    private boolean firstTimeFlag = true;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private static final int PERMISSIONS_REQUEST = 1;
    private ArrayList<MapLocationHistory> loc_history;
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private String server_link = Config.getServerLink() + "getLocationHistory.php";
    double distanceList[], distanceListRaw[];
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ProgressDialog progressDialog;
    int distanceIndenti[];
    int nearestList[];

    private final int[] MAP_TYPES = {
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 3;
    LatLng latLng = null;
    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null)
                return;
            currentLocation = locationResult.getLastLocation();
            if (firstTimeFlag && mMap != null) {
                animateCamera(currentLocation);
                firstTimeFlag = false;
            }
            // showMarker(currentLocation);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_fire_station);

        // Check GPS is enabled
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        int permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {

            if (firstTimeFlag && mMap != null && currentLocation != null) {
                animateCamera(currentLocation);
                firstTimeFlag = false;
            }

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void animateCamera(@NonNull Location location) {

        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        // mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
        //  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(17)
                .bearing(90)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        findNearestFireStation();


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
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""

            ));

        } catch (JSONException e) {


            e.printStackTrace();
        }


    }

    private void completing() {


        progressDialog.dismiss();

        int i = 0;
        float zoomLevel = 12.0f;

        distanceList = new double[latlngs.size()];
        distanceIndenti = new int[latlngs.size()];
        distanceListRaw = new double[latlngs.size()];

        System.out.println("Map List");


        for (LatLng point : latlngs) {
            double distance_from_current_location = distance(latLng.latitude, latLng.longitude, point.latitude, point.longitude) * 1000;
            distanceList[i] = distance_from_current_location;
            distanceListRaw[i] = distance_from_current_location;

            System.out.println("lat :" + point.latitude + "Lognitu " + point.longitude + " distance " + distance_from_current_location);

            i++;
        }

        nearestLocationCalculate();
        nearestLocationView();

    }

    private void nearestLocationView() {


        LatLng sydney = new LatLng(latLng.latitude, latLng.longitude);
        int nearestIndex = 0;
        double nDistance = 0.0;

        int drow=latlngs.size();
        if(drow > 5)
            drow=5;

        nearestList = new int[drow];

        for (int j = 0; j < drow; j++) {

            double nearestlocation = distanceList[j];

            for (int k = 0; k < latlngs.size(); k++) {

                if (nearestlocation == distanceListRaw[k]) {


                    nearestList[j] = k;

                }


            }


        }



   /*     Polygon pp = mMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        sydney

                ));*/


        for (int i = 0; i < drow; i++) {

            Polygon polyline1 = mMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(
                                    Double.parseDouble(loc_history.get(nearestList[i]).getLatitude()),
                                    Double.parseDouble(loc_history.get(nearestList[i]).getLongnitute())),sydney

                            ));


        }




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
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    private void findNearestFireStation(){


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Map Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        loc_history = new ArrayList<>();
        latlngs=new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();
                        try {


                            JSONObject obj = new JSONObject(response);
                            JSONArray users = obj.getJSONArray("posts");
                            for (int i = 0; i < users.length(); i++)
                            {

                                JSONObject c = users.getJSONObject(i);
                                loadServerData(c);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        completing();

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
                // params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(NearestFireStation.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient = null;
        mMap = null;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
            startCurrentLocationUpdates();

            // startTrackerService();

        } else {
            finish();
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        mMap.setMapType( MAP_TYPES[curMapTypeIndex] );
        mMap.setTrafficEnabled( true );
        mMap.getUiSettings().setZoomControlsEnabled( true );

    }
}
