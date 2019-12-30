package com.example.professt.publicawareness;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EmailVerification extends AppCompatActivity {

    EditText otpEditText;
    Button submitButton;

    String otp,emails;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        otpEditText = (EditText)findViewById(R.id.otpEditText);
        submitButton = (Button)findViewById(R.id.submitButton);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
             emails =(String) b.get("email");
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp = otpEditText.getText().toString();

                if(otp.isEmpty())
                {
                    otpEditText.setError("Please Enter OTP");
                    otpEditText.requestFocus();
                }
                else
                {
                    loading = new ProgressDialog(EmailVerification.this);
                    loading.setIcon(R.drawable.load);
                    loading.setTitle("Login");
                    loading.setMessage("Please wait...");
                    loading.show();

                    //creating a string request
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Key.OTP_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("OTP", "" + response);

                            //if we are getting success from server
                            if (response.equals("success")) {

                                //creating a shared preference

                                SharedPreferences sp = EmailVerification.this.getSharedPreferences(Key.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                loading.dismiss();

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Key.VERIFY_URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        //for track response in Logcat
                                        Log.d("Verify", "" + response);

                                        //if we are getting success from server
                                        if (response.equals("success")) {
                                            //creating a shared preference
                                            loading.dismiss();
                                            //starting profile activity
                                            Intent intent = new Intent(EmailVerification.this, LoginActivity2.class);

                                            startActivity(intent);

                                            Toast.makeText(EmailVerification.this, "Registration Successfull", Toast.LENGTH_SHORT).show();

                                            otpEditText.setText("");


                                        } else if (response.equals("failure")) {
                                            Toast.makeText(EmailVerification.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                            loading.dismiss();
                                        }
                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(EmailVerification.this, "There is an error", Toast.LENGTH_SHORT).show();
                                                loading.dismiss();
                                            }
                                        }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        //return super.getParams();

                                        Map<String,String> params = new HashMap<>();

                                        //Ading parameters to request
                                        params.put(Key.KEY_VERIFY,"yes");
                                        params.put(Key.KEY_EMAIL,emails);

                                        //returning parameter
                                        return params;

                                    }
                                };

                                //Adding the string request to the queue
                                RequestQueue requestQueue = Volley.newRequestQueue(EmailVerification.this);
                                requestQueue.add(stringRequest);

//                                //starting profile activity
//                                Intent intent = new Intent(EmailVerification.this, LoginActivity2.class);
//                                startActivity(intent);
//
//                                Toast.makeText(EmailVerification.this, "Registration Successfull", Toast.LENGTH_SHORT).show();

                            }
                            else if (response.equals("failure")) {

                                //if the server response is not success
                                //displaying an error message or toast
                                Toast.makeText(EmailVerification.this, "Invalid OTP or Email", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            } else {
                                //if the server response is not success
                                //displaying an error message or toast
                                Toast.makeText(EmailVerification.this, "Invalid OTP or Email", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(EmailVerification.this, "There is an error", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //return super.getParams();

                            Map<String,String> params = new HashMap<>();

                            //Ading parameters to request
                            params.put(Key.KEY_EMAIL,emails);
                            params.put(Key.KEY_OTP,otp);

                            //returning parameter
                            return params;

                        }
                    };

                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(EmailVerification.this);
                    requestQueue.add(stringRequest);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(EmailVerification.this,SignUp.class);
        startActivity(intent);

    }
}
