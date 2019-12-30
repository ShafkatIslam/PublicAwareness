package com.example.professt.publicawareness.HospitalList;

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

public class HospitalDetailsActivity extends AppCompatActivity {

    TextView hospitalNames,hospitalNumber,hospitalAddress;
    ImageView callId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        hospitalNames = (TextView)findViewById(R.id.hospitalName);
        hospitalNumber = (TextView)findViewById(R.id.hospitalNumber);
        hospitalAddress = (TextView)findViewById(R.id.hospitalAddress);
        callId = (ImageView) findViewById(R.id.callId);

        callId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(HospitalDetailsActivity.this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    //Creating intents for making a call
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" +hospitalNumber.getText().toString()));
                    HospitalDetailsActivity.this.startActivity(callIntent);

                }else{
                    Toast.makeText(HospitalDetailsActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        HospitalName hospitalName = HospitalName.getInstance();
        String name = hospitalName.getId();

        if(name.equalsIgnoreCase("Chittagong Medical College Hospital"))
        {
            hospitalNames.setText("Chittagong Medical College Hospital");
            hospitalNumber.setText("01819-637685");
            hospitalAddress.setText("57, K.B. Fazlul Kader road, P.S. Panchlaish,Chittagong");

        }
        else if(name.equalsIgnoreCase("University Of Science and Technology Chittagong"))
        {
            hospitalNames.setText("University Of Science and Technology Chittagong");
            hospitalNumber.setText("031-659070");
            hospitalAddress.setText("Zakir Hossain Road, Foy’s Lake, Khulshi, Chittagong");
        }
        else if(name.equalsIgnoreCase("Chittagong Metropolitan Hospital Pvt. Ltd."))
        {
            hospitalNames.setText("Chittagong Metropolitan Hospital Pvt. Ltd.");
            hospitalNumber.setText("031-654732");
            hospitalAddress.setText("698/452 O.R. Nizam Road, Chittagong 4001, Bangladesh");
        }
        else if(name.equalsIgnoreCase("National Hospital Pvt. Ltd"))
        {
            hospitalNames.setText("National Hospital Pvt. Ltd");
            hospitalNumber.setText("031-623713");
            hospitalAddress.setText("14/15, Dampara Lane, Mehedibug, Chittagong 4000, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Chittagong General Hospital"))
        {
            hospitalNames.setText("Chittagong General Hospital");
            hospitalNumber.setText("031- 715166");
            hospitalAddress.setText("Chittagong General Hospital,Anderkilla Chittagong.");
        }
        else if(name.equalsIgnoreCase("Chittagong Diabetic General Hospital"))
        {
            hospitalNames.setText("Chittagong Diabetic General Hospital");
            hospitalNumber.setText("01844-041140");
            hospitalAddress.setText("Zakir Hossain Road, Khulsi, Chittagong.");
        }
        else if(name.equalsIgnoreCase("Surgiscope Hospital Limited Unit-II"))
        {
            hospitalNames.setText("Surgiscope Hospital Limited Unit-II");
            hospitalNumber.setText("01777-222884");
            hospitalAddress.setText("Panchlaish Road, Chittagong 4203, Bangladesh");
        }
        else if(name.equalsIgnoreCase("CSCR Hospital"))
        {
            hospitalNames.setText("CSCR Hospital");
            hospitalNumber.setText("031-657071");
            hospitalAddress.setText("1675/A, O.R Nizam Road, Chittagong 4203, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Popular Hospital,Chittagong"))
        {
            hospitalNames.setText("Popular Hospital,Chittagong");
            hospitalNumber.setText("031-655401");
            hospitalAddress.setText("36 K.B. Fazlul Kader Rd, Chittagong, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Royal Hospital (Pvt.) Limited"))
        {
            hospitalNames.setText("Royal Hospital (Pvt.) Limited");
            hospitalNumber.setText("031-658842");
            hospitalAddress.setText("O.R. Nizam Rd, Chittagong 4001, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Islami Bank Hospital"))
        {
            hospitalNames.setText("Islami Bank Hospital");
            hospitalNumber.setText("01731-253990");
            hospitalAddress.setText("03 Sheikh Mujib Rd, Chittagong, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Chittagong Health Point Hospital"))
        {
            hospitalNames.setText("Chittagong Health Point Hospital");
            hospitalNumber.setText("01915-443881");
            hospitalAddress.setText("1501, Beside Meristops Clinic, O.R Nizam Road,Chittagong");
        }
        else if(name.equalsIgnoreCase("Dr. Sihab"))
        {
            hospitalNames.setText("Chittagong Medical College Hospital");
            hospitalNumber.setText("01819-637685");
            hospitalAddress.setText("57, K.B. Fazlul Kader road, P.S. Panchlaish");
        }
        else if(name.equalsIgnoreCase("Chittagong Medical Centre LTD."))
        {
            hospitalNames.setText("Chittagong Medical Centre LTD.");
            hospitalNumber.setText("031-651054");
            hospitalAddress.setText("953, O.R. Nizam Rd, Chittagong 4000, Bangladesh.");
        }
        else if(name.equalsIgnoreCase("Chittagong Mount Hospital (Pvt.) Ltd."))
        {
            hospitalNames.setText("Chittagong Mount Hospital (Pvt.) Ltd.");
            hospitalNumber.setText("031-623262");
            hospitalAddress.setText("Surson Rd, Chittagong, Bangladesh");
        }

        else if(name.equalsIgnoreCase("Chittagong People Hospital Limited."))
        {
            hospitalNames.setText("Chittagong People's Hospital Limited.");
            hospitalNumber.setText("031-658911");
            hospitalAddress.setText("94 K.B. Fazlul Kader Rd, Chittagong 4203, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Chittagong Treatment Hospital pvt. Ltd."))
        {
            hospitalNames.setText("Chittagong Treatment Hospital pvt. Ltd.");
            hospitalNumber.setText("031-652351");
            hospitalAddress.setText("100, O. R Nizam Road, Chittagong 4000, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Delta Health Care Chittagong Limited"))
        {
            hospitalNames.setText("Delta Health Care Chittagong Limited");
            hospitalNumber.setText("03125-50005");
            hospitalAddress.setText("28, Katalgonj, Mirzapul Road, Panchalish, Chittagong");
        }
        else if(name.equalsIgnoreCase("Ekushey Hospital Private Ltd"))
        {
            hospitalNames.setText("Ekushey Hospital Private Ltd");
            hospitalNumber.setText("031-657629");
            hospitalAddress.setText("Sugandha Road 2, Chittagong 4212, Bangladesh");
        }
        else if(name.equalsIgnoreCase("Max Hospital and Diagnostic Ltd."))
        {
            hospitalNames.setText("Max Hospital and Diagnostic Ltd.");
            hospitalNumber.setText("031-622519");
            hospitalAddress.setText("Late Alhaj Zahur Ahmed Chowdhury Rd, Chittagong");
        }

    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(HospitalDetailsActivity.this,HospitalNameActivity.class);
        startActivity(intent);

    }
}
