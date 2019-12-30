package com.example.professt.publicawareness;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;



public class SignUp extends AppCompatActivity {

    private ImageView profileImageView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private EditText cnicEditText;
    private EditText cellnoEditText;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private Button signupButton;
    private ProgressDialog progressDialog;
    private Bitmap bitmap;

    private ProgressDialog loading;

    String username,passwords,emails,cnic,cellno,gender;

    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        profileImageView = (ImageView) findViewById(R.id.imageview_profile);
        usernameEditText = (EditText) findViewById(R.id.edittext_username);
        passwordEditText = (EditText) findViewById(R.id.edittext_password);
        emailEditText = (EditText) findViewById(R.id.edittext_email);
        cnicEditText = (EditText) findViewById(R.id.edittext_cnic);
        cellnoEditText = (EditText) findViewById(R.id.edittext_cellno);
        genderRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_gender);
        //maleRadioButton = (RadioButton) findViewById(R.id.radiobutton_male);
        //femaleRadioButton = (RadioButton) findViewById(R.id.radiobutton_female);
        signupButton = (Button) findViewById(R.id.btn_signup);
        signupButton.setOnClickListener(onClickSignUpButtonListener);
        profileImageView.setOnClickListener(onClickProfileImageViewListener);
    }

    View.OnClickListener onClickProfileImageViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            chooseImage();
        }
    };

    private void chooseImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK)
            try {
                // We need to recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(data.getData());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=2;
                bitmap = BitmapFactory.decodeStream(stream, null, options);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                double scale = 100.0/height;
                height = (int)(height*scale);
                width = (int)(width*scale);
                bitmap = Bitmap.createScaledBitmap(bitmap, width,height, false);
                stream.close();
                profileImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }


    View.OnClickListener onClickSignUpButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int selectedId = genderRadioGroup.getCheckedRadioButtonId();
            maleRadioButton = (RadioButton) findViewById(selectedId);

            String value = maleRadioButton.getText().toString();

            username = usernameEditText.getText().toString();
            passwords = passwordEditText.getText().toString();
            emails = emailEditText.getText().toString();
            cnic = cnicEditText.getText().toString();
            cellno = cellnoEditText.getText().toString();
            gender = value;

            if(username.isEmpty())
            {
                usernameEditText.setError("Please Enter Name");
                usernameEditText.requestFocus();
            }
            else if(passwords.isEmpty())
            {
                passwordEditText.setError("Please Enter Password");
                passwordEditText.requestFocus();
            }
            else if(emails.isEmpty())
            {
                emailEditText.setError("Please Enter Email");
                emailEditText.requestFocus();
            }
            else if(cnic.isEmpty())
            {
                cnicEditText.setError("Please Enter Address");
                cnicEditText.requestFocus();
            }
            else if(cellno.isEmpty())
            {
                cellnoEditText.setError("Please Enter Cell No");
                cellnoEditText.requestFocus();
            }
            else if(gender.isEmpty())
            {
                Toast.makeText(SignUp.this, "Please select gender", Toast.LENGTH_SHORT).show();
            }
            else
            {
                loading = new ProgressDialog(SignUp.this);
                loading.setIcon(R.drawable.load);
                loading.setTitle("Sign Up");
                loading.setMessage("Please wait...");
                loading.show();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Key.SIGNUP_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //for track response in Logcat
                        Log.d("RESPONSE", "" + response);

                        //if we are getting success from server
                        if (response.equals("success")) {
                            //creating a shared preference
                            loading.dismiss();
                            //starting profile activity
                            Intent intent = new Intent(SignUp.this, EmailVerification.class);
                            intent.putExtra("email",emails);
                            startActivity(intent);

                            Toast.makeText(SignUp.this, "OTP sends Successfull", Toast.LENGTH_SHORT).show();

                            usernameEditText.setText("");
                            passwordEditText.setText("");
                            emailEditText.setText("");
                            cnicEditText.setText("");
                            cellnoEditText.setText("");

                        } else if (response.equals("exists")) {
                            Toast.makeText(SignUp.this, "User already exists", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        } else if (response.equals("failure")) {
                            Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SignUp.this, "There is an error", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //return super.getParams();

                        Map<String,String> params = new HashMap<>();

                        //Ading parameters to request
                        params.put(Key.KEY_NAME,username);
                        params.put(Key.KEY_PASSWORD,passwords);
                        params.put(Key.KEY_EMAIL,emails);
                        params.put(Key.KEY_ADD,cnic);
                        params.put(Key.KEY_CELL,cellno);
                        params.put(Key.KEY_GNDER,gender);

                        //returning parameter
                        return params;

                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
                requestQueue.add(stringRequest);
            }




//                User.Gender gender;
//                if (genderRadioGroup.getCheckedRadioButtonId() == R.id.radiobutton_male) {
//                    gender = User.Gender.MALE;
//                } else {
//                    gender = User.Gender.FEMALE;
//                }
        }


    };

/*    private void register(String username,String password, String email, String cnic, String cellno, User.Gender gender)
    {
        progressDialog.show();
        UserService.getInstance(SignUp.this).register(username,password,email,cnic,cellno,gender,registerListener,bitmap);
    }*/

    /*   UserService.RegisterListener registerListener = new UserService.RegisterListener() {
           @Override
           public void onResponce(boolean registered, String message, User user) {
               progressDialog.dismiss();
               Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
               if(registered)
               {
                   goToMainMenuActivity();
               }
           }
       };
   */
    private void goToMainMenuActivity()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToLoginActivity2()
    {
        Intent intent = new Intent(this,LoginActivity2.class);
        startActivity(intent);
        //finish();
    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(SignUp.this,LoginActivity2.class);
        startActivity(intent);

    }
}

