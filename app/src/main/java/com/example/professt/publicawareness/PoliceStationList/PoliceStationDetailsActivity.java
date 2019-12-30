package com.example.professt.publicawareness.PoliceStationList;

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

import com.example.professt.publicawareness.R;

public class PoliceStationDetailsActivity extends AppCompatActivity {

    TextView policeStationNames,policeStationNumber,policeStationAddress;
    ImageView callId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_station_details);

        policeStationNames = (TextView)findViewById(R.id.policeStationName);
        policeStationNumber = (TextView)findViewById(R.id.policeStationNumber);
        policeStationAddress = (TextView)findViewById(R.id.policeStationAddress);
        callId = (ImageView) findViewById(R.id.callId);

        callId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(PoliceStationDetailsActivity.this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    //Creating intents for making a call
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" +policeStationNumber.getText().toString()));
                    PoliceStationDetailsActivity.this.startActivity(callIntent);

                }else{
                    Toast.makeText(PoliceStationDetailsActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        PoliceStationName policeStationName = PoliceStationName.getInstance();
        String name = policeStationName.getId();

        if(name.equalsIgnoreCase("Kotwali Police Station"))
        {
            policeStationNames.setText("Kotwali Police Station");
            policeStationNumber.setText("01713-373256");
            policeStationAddress.setText("5 No Kotwali Circle, Chittagong, Bangladesh");

        }
        else if(name.equalsIgnoreCase("Bakalia Police Station"))
        {
            policeStationNames.setText("Bakalia Police Station");
            policeStationNumber.setText("01713-373261");
            policeStationAddress.setText("18 No. East Bakalia Ward, Chittagong, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Chawkbazar Police Station"))
        {
            policeStationNames.setText("Chawkbazar Police Station");
            policeStationNumber.setText("01769-690064");
            policeStationAddress.setText("Lal Chand Road, Chawk Bazar, Chittagong 4003, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Panchlaish Model Police Station"))
        {
            policeStationNames.setText("Panchlaish Model Police Station");
            policeStationNumber.setText("01713-373258");
            policeStationAddress.setText("O.R. Nizam Rd, Chittagong 4203, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Sadarghat Police Station"))
        {
            policeStationNames.setText("Sadarghat Police Station");
            policeStationNumber.setText("01769-690065");
            policeStationAddress.setText("Sadarghat Rd, Chittagong, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Bayazid Bostamy Police Station"))
        {
            policeStationNames.setText("Bayazid Bostamy Police Station");
            policeStationNumber.setText("01713-373262");
            policeStationAddress.setText("Baizid Thana road, Chittagong 4210, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Khulshi Police Station"))
        {
            policeStationNames.setText("Khulshi Police Station");
            policeStationNumber.setText("01713-373260");
            policeStationAddress.setText("2 S Khulshi Rd, Chittagong, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Doublemooring Police Station"))
        {
            policeStationNames.setText("Doublemooring Police Station");
            policeStationNumber.setText("01713-373268");
            policeStationAddress.setText("280 Dhaka Trunk Rd, Chittagong, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Pahartali Police Station"))
        {
            policeStationNames.setText("Pahartali Police Station");
            policeStationNumber.setText("01713-373273");
            policeStationAddress.setText("Thana Road,Pahartoli,Chittagong,Bangladesh");
        }
        else if(name.equalsIgnoreCase("Halishahar Police Station"))
        {
            policeStationNames.setText("Halishahar Police Station");
            policeStationNumber.setText("01713-373269");
            policeStationAddress.setText("Lane No. 6, Block B, DR.Jahangir Kobir road,Halishohor,Chittagong");
        }
        else if(name.equalsIgnoreCase("Akbar Shah Police Station"))
        {
            policeStationNames.setText("Akbar Shah Police Station");
            policeStationNumber.setText("01769-690066");
            policeStationAddress.setText("Beside Akbar Shah Thana, Rd No. 1, Chittagong 4207, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Bandar Police Station"))
        {
            policeStationNames.setText("Bandar Police Station");
            policeStationNumber.setText("01713-373267");
            policeStationAddress.setText("Mooring Rd, Chittagong, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Patenga Police Station"))
        {
            policeStationNames.setText("Patenga Police Station");
            policeStationNumber.setText("01713-373270");
            policeStationAddress.setText("14 No Colony Rd, Chittagong, Bangladesh");
        }
        else if(name.equalsIgnoreCase("New Chandgaon Police Station"))
        {
            policeStationNames.setText("New Chandgaon Police Station");
            policeStationNumber.setText("01713-373271");
            policeStationAddress.setText("6 No. East Sholoshohor Ward, Chittagong, Bangladesh");
        }
        else if(name.equalsIgnoreCase("EPZ Police Station"))
        {
            policeStationNames.setText("EPZ Police Station");
            policeStationNumber.setText("01769-690067");
            policeStationAddress.setText("M. A Aziz Road, Chittagong 4218, Bangladesh");
        }

    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(PoliceStationDetailsActivity.this,PoliceStationNameActivity.class);
        startActivity(intent);

    }
}
