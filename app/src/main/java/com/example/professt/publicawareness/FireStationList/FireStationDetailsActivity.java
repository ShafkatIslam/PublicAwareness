package com.example.professt.publicawareness.FireStationList;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.professt.publicawareness.HospitalList.HospitalDetailsActivity;
import com.example.professt.publicawareness.R;

public class FireStationDetailsActivity extends AppCompatActivity {

    TextView fireStationNames,fireStationNumber,fireStationAddress;
    ImageView callId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_station_details);

        fireStationNames = (TextView)findViewById(R.id.fireStationName);
        fireStationNumber = (TextView)findViewById(R.id.fireStationNumber);
        fireStationAddress = (TextView)findViewById(R.id.fireStationAddress);
        callId = (ImageView) findViewById(R.id.callId);

        callId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(FireStationDetailsActivity.this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    //Creating intents for making a call
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" +fireStationNumber.getText().toString()));
                    FireStationDetailsActivity.this.startActivity(callIntent);

                }else{
                    Toast.makeText(FireStationDetailsActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FireStationName fireStationName = FireStationName.getInstance();
        String name = fireStationName.getId();

        if(name.equalsIgnoreCase("Bangladesh Fire Service and Civil Defense, Agrabad"))
        {
            fireStationNames.setText("Bangladesh Fire Service and Civil Defense, Agrabad");
            fireStationNumber.setText("031-630334");
            fireStationAddress.setText("Dewan Haat, Sheikh Mujib Rd, Chittagong");

        }
        else if(name.equalsIgnoreCase("Fire Service and Civil Defence Nandankanon"))
        {
            fireStationNames.setText("Fire Service and Civil Defence Nandankanon");
            fireStationNumber.setText("031-630334");
            fireStationAddress.setText("Anderkilla, Chittagong, Bangladesh");

        }
        else if(name.equalsIgnoreCase("Fire Service and Civil Defence,Anderkilla"))
        {
            fireStationNames.setText("Fire Service and Civil Defence,Anderkilla");
            fireStationNumber.setText("031-619575");
            fireStationAddress.setText("Fire service & civil defense,Nabab Sirajuddullah Road,Chittagong");

        }
        else if(name.equalsIgnoreCase("Fire service and civil defense,Lamar bazer"))
        {
            fireStationNames.setText("Fire service and civil defense,Lamar bazer");
            fireStationNumber.setText("031-630233");
            fireStationAddress.setText("Lamar bazer,CMB moor,Kalurghat,Chittagong");

        }
        else if(name.equalsIgnoreCase("CEPZ Fire Station"))
        {
            fireStationNames.setText("CEPZ Fire Station");
            fireStationNumber.setText("031-800419");
            fireStationAddress.setText("CEPZ Entry Rd, Chittagong 4223, Bangladesh");

        }
        else if(name.equalsIgnoreCase("Fire service and civil defense,Potenga"))
        {
            fireStationNames.setText("Fire service and civil defense,Potenga");
            fireStationNumber.setText("01730002423");
            fireStationAddress.setText("Nearest Shah Amanat International Airport, Potenga,Chittagong");

        }
        else if(name.equalsIgnoreCase("Bandor fire service Station"))
        {
            fireStationNames.setText("Bandor fire service Station");
            fireStationNumber.setText("031-2520339");
            fireStationAddress.setText("Port Internal Rd, Chittagong, Bangladesh");

        }

    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(FireStationDetailsActivity.this,FireStationNameActivity.class);
        startActivity(intent);

    }
}
