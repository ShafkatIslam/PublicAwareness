package com.example.professt.publicawareness;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.Manifest;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.professt.publicawareness.FireStationList.FireStationNameActivity;
import com.example.professt.publicawareness.HospitalList.HospitalNameActivity;
import com.example.professt.publicawareness.PoliceStationList.PoliceStationNameActivity;
import com.example.professt.publicawareness.Unused.User;
import com.example.professt.publicawareness.Unused.UserService;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity2 extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private CheckBox rememberMeCheckBox;
    private TextView otpForget;
    private Button loginButton;
    private Button signUpButton;
    private ProgressDialog progressDialog;
    private android.support.v7.app.AlertDialog.Builder alertdialogBuilder;

    private ProgressDialog loading;

    PermissionManager permissionManager;

    String username,passwords;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        loginButton =  (Button) findViewById(R.id.loginbtn);
        signUpButton =  (Button) findViewById(R.id.signupbtn);
        otpForget = (TextView)findViewById(R.id.otpForget);

        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);

        loginButton.setOnClickListener(onClickLoginButtonListener);
        signUpButton.setOnClickListener(onClickSignUpButtonListener);


        otpForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mEmailView.getText().toString().trim();

                if(username.isEmpty())
                {
                    mEmailView.setError("Please Enter Cell");
                    mEmailView.requestFocus();
                }
                else
                {
                    Intent intent = new Intent(LoginActivity2.this,EmailVerification.class);
                    intent.putExtra("email",username);
                    startActivity(intent);
                }

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        boolean remembered = sharedPreferences.getBoolean("remembered",false);
        if(remembered)
        {
            //rememberMeCheckBox.setChecked(true);
            String username = sharedPreferences.getString("username", null);
            String password = sharedPreferences.getString("password", null);
            //login(username,password);
        }
    }

    private void goToSignupActivity()
    {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }

    private void goToMainMenuActivity()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    View.OnClickListener onClickLoginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            username = mEmailView.getText().toString().trim();
            passwords = mPasswordView.getText().toString().trim();


            if(username.isEmpty())
            {
                mEmailView.setError("Please Enter Cell");
                mEmailView.requestFocus();
            }

            //checking password field/validation
            else if(passwords.isEmpty())
            {
                mPasswordView.setError("Please Enter Password");
                mPasswordView.requestFocus();
            }
            else
            {
                loading = new ProgressDialog(LoginActivity2.this);
                loading.setIcon(R.drawable.load);
                loading.setTitle("Login");
                loading.setMessage("Please wait...");
                loading.show();

                //creating a string request
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Key.LOGIN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("RESPONSE", "" + response);

                        //if we are getting success from server
                        if (response.equals("success")) {

                            //creating a shared preference

                            SharedPreferences sp = LoginActivity2.this.getSharedPreferences(Key.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sp.edit();
                            //Adding values to editor
                            editor.putString(Key.USER_SHARED_PREF, username);

                            //Saving values to editor
                            editor.commit();

                            loading.dismiss();

                            //starting profile activity
                            Intent intent = new Intent(LoginActivity2.this, MainActivity.class);
                            intent.putExtra("email",username);
                            startActivity(intent);

                            Toast.makeText(LoginActivity2.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                        }
                        else if (response.equals("failure")) {

                            //if the server response is not success
                            //displaying an error message or toast
                            Toast.makeText(LoginActivity2.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        } else {
                            //if the server response is not success
                            //displaying an error message or toast
                            Toast.makeText(LoginActivity2.this, "Invalid Usercell or password", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity2.this, "There is an error", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //return super.getParams();

                        Map<String,String> params = new HashMap<>();

                        //Ading parameters to request
                        params.put(Key.KEY_EMAIL,username);
                        params.put(Key.KEY_PASSWORD,passwords);

                        //returning parameter
                        return params;

                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity2.this);
                requestQueue.add(stringRequest);

            }

            ///vollry();
        }
    };

    View.OnClickListener onClickSignUpButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goToSignupActivity();
        }
    };

    View.OnClickListener onClickForgotPasswordTextViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity2.this);
            builder.setTitle("Warning");
            builder.setIcon(R.drawable.error);
            builder.setMessage("Not implemented");
            builder.setPositiveButton("OK",null);
            builder.show();
        }
    };

    UserService.LoginListener loginListener = new UserService.LoginListener() {
        @Override
        public void onResponce(boolean loggedin, String message, User user) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity2.this, message, Toast.LENGTH_SHORT).show();
            if(loggedin)
            {
                SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(rememberMeCheckBox.isChecked())
                {
                    editor.putBoolean("remembered", true);
                    editor.putString("username", user.getUsername());
                    editor.putString("password", user.getPassword());
                }
                else
                {
                    editor.putBoolean("remembered",false);
                    editor.remove("username");
                    editor.remove("password");
                }
                editor.commit();
                goToMainMenuActivity();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    //this method takes the "menu_layout.xml" file in java file

        MenuInflater menuInflater = getMenuInflater();   //"MenuInflater" is a service which coverts xml file into java file
        menuInflater.inflate(R.menu.menu_layout,menu);    //"Inflate" method turns the xml file into java file

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //Whwnever we select a menu Item the id of that menu Item goes to the "item " variable

        if(item.getItemId()==R.id.hospitalList)        //compare with the selected item id with item by "getItemId" method
        {
//            Toast.makeText(LoginActivity2.this,"Setting is selected",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity2.this, HospitalNameActivity.class);
            startActivity(intent);
            return true;      //Because the method returns a boolean value
        }

        if(item.getItemId()==R.id.policeStationList)        //compare with the selected item id with item by "getItemId" method
        {
            //Toast.makeText(LoginActivity2.this,"Share is selected",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity2.this, PoliceStationNameActivity.class);
            startActivity(intent);
            return true;      //Because the method returns a boolean value
        }

        if(item.getItemId()==R.id.fireStationList)        //compare with the selected item id with item by "getItemId" method
        {
            //Toast.makeText(LoginActivity2.this,"Feedback is selected",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity2.this, FireStationNameActivity.class);
            startActivity(intent);
            return true;      //Because the method returns a boolean value
        }

        if(item.getItemId()==R.id.sosCall)        //compare with the selected item id with item by "getItemId" method
        {
            if (ActivityCompat.checkSelfPermission(LoginActivity2.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                //Creating intents for making a call
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" +"999"));
                LoginActivity2.this.startActivity(callIntent);

            }else{
                Toast.makeText(LoginActivity2.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
            }
            return true;      //Because the method returns a boolean value
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        alertdialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);    //creating object of alertDialogBuilder

        //setting the properties of alertDialogBuilder:

        //for setting title
        alertdialogBuilder.setTitle("Mother & Baby Care");

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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity(); // Close all activites
                    System.exit(0);  // Releasing resources
                }
//                finish();;
//                System.exit(0);
//                onDestroy();
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
        android.support.v7.app.AlertDialog alertDialog = alertdialogBuilder.create();
        alertDialog.show();
    }
}
